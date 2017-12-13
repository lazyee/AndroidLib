package com.leeorz.lib.utils.photo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.Toast;

import com.leeorz.lib.app.AppConfig;
import com.leeorz.lib.utils.BitmapUtil;
import com.leeorz.lib.widget.crop.Crop;
import com.leeorz.lib.widget.photopicker.PhotoPickerActivity;
import com.leeorz.lib.widget.photopicker.utils.PhotoPickerIntent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 图片工具
 * Created by 嘉俊 on 2015/10/9.
 */
public class PhotoUtil {
    private static final String FILE_PROVIDER = "com.leeorz.lib.fileprovider";
    public static final int CAMERA = 10001;
    public static final int ALBUM = 10002;
    private boolean isCrop = false;
    private boolean isFreeCrop = false;
    private Activity mActivity;
    private String imagePath = "";
    private boolean isSpecifyRadio = false;
    private String outputImagePath = "";

    private int radioX = 1;
    private int radioY = 1;
    private OnDealImageListener onDealImageListener;

    public PhotoUtil(Activity activity) {
        this.mActivity = activity;
    }

    public void setIsCrop(boolean isCrop) {
        this.isCrop = isCrop;
        isSpecifyRadio = false;
    }

    public void setFreeCrop(boolean freeCrop) {
        isFreeCrop = freeCrop;
        isCrop = freeCrop;
        isSpecifyRadio = false;
    }

    public void setCropImageRadio(int x,int y) {
        radioX = x;
        radioY = y;
        isSpecifyRadio = true;
        isCrop = true;
    }

    /**
     * 打开摄像头
     */
    public void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 下面这句指定调用相机拍照后的照片存储的路径
        intent.putExtra(MediaStore.EXTRA_OUTPUT, getUri());
        intent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, "portrait");
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        mActivity.startActivityForResult(intent, CAMERA);
    }

    public void setOnDealImageListener(OnDealImageListener onDealImageListener) {
        this.onDealImageListener = onDealImageListener;
    }

    /**
     * 打开相册
     *
     * @param requestCount 需要选择的图片数量
     */
    public void openAlbum(int requestCount) {
        PhotoPickerIntent intent = new PhotoPickerIntent(mActivity);
        intent.setPhotoCount(requestCount);
        intent.setShowCamera(false);
        mActivity.startActivityForResult(intent, ALBUM);
    }

    /**
     * 处理图片
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void dealImage(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) return;

        if (requestCode == CAMERA) {
            if (isCrop) {
                beginCrop(imagePath);
            } else {
                if (onDealImageListener != null) {
//                    imagePath = BitmapUtil.ratingImageAndSave(imagePath);
                    int[] arr = getImageWidthAndHeight(imagePath);
                    onDealImageListener.onDealSingleImageComplete(getImage(imagePath, arr[0], arr[1]));
                }
            }
        } else if (requestCode == ALBUM) {
            getPickPhoto(data);
        } else if (requestCode == Crop.REQUEST_PICK && resultCode == mActivity.RESULT_OK) {
            beginCrop(data.getData().getPath());
        } else if (requestCode == Crop.REQUEST_CROP) {
            handleCrop(resultCode, data);
        }
    }

    private void getPickPhoto(Intent data) {
        if (data == null) return;
        ArrayList<String> photos = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
        if (photos.size() == 1 && isCrop) {
            beginCrop(photos.get(0));
        } else if (photos.size() == 1) {
            if (onDealImageListener != null) {
                imagePath = photos.get(0);
                int[] arr = getImageWidthAndHeight(imagePath);
                onDealImageListener.onDealSingleImageComplete(getImage(imagePath, arr[0], arr[1]));
            }
        } else if (photos.size() != 0) {
            if (onDealImageListener != null) {
                List<Photo> gImageList = new ArrayList();
                for (int i = 0; i < photos.size(); i++) {
                    int[] arr = getImageWidthAndHeight(photos.get(i));
                    gImageList.add(getImage(photos.get(i), arr[0], arr[1]));
                }
                onDealImageListener.onDealMultiImageComplete(gImageList);
            }
        }
    }

    private void beginCrop(String path) {

//        File crop = new File(mActivity.getFilesDir(),path);
        File crop = new File(getRealFilePath(path));
        Uri source =  FileProvider.getUriForFile(mActivity,FILE_PROVIDER,crop);

        File output = new File(mActivity.getFilesDir(), "images/crop.jpg");
        outputImagePath = output.getAbsolutePath();
        Uri destination = FileProvider.getUriForFile(mActivity,FILE_PROVIDER,output);

        if(isFreeCrop){
            Crop.of(source, destination).start(mActivity);
        }else if(isSpecifyRadio){
            Crop.of(source, destination).withAspect(radioX,radioY).start(mActivity);
        }else{
            Crop.of(source, destination).asSquare().start(mActivity);
        }
    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == mActivity.RESULT_OK && result != null) {
            if (onDealImageListener != null) {
                Crop.getOutput(result).getPath();
                BitmapUtil.ratingImageAndSave(getRealFilePath(imagePath));
                int[] arr = getImageWidthAndHeight(outputImagePath);
                onDealImageListener.onDealSingleImageComplete(getImage(outputImagePath, arr[0], arr[1]));
            }
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(mActivity, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 获取图片的实体
     *
     * @param path
     * @param w
     * @param h
     * @return
     */
    private Photo getImage(String path, int w, int h) {
        Photo photo = new Photo();
        photo.setWidth(w);
        photo.setHeight(h);
        photo.setUrl(path);
        return photo;
    }

    private Uri getUri() {

//        //兼容性控制
//        File file;
//        if (Build.VERSION.SDK_INT > 8) {
////            file = mActivity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//            file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
//        } else {
//            file = new File(Environment.getExternalStorageDirectory(), AppConfig.IMAGE_PATH);
//        }
//
//        if (!file.exists()) { // 创建目录
//            file.mkdirs();
//        }
//
//
//        String name = UUID.randomUUID() + ".png";
//        File file1 = new File(file, name);
//        imagePath = file1.getAbsolutePath();
//        Uri uri = Uri.fromFile(file1);
//        return uri;

        String path = "images/default.jpg";
        File dir = new File(mActivity.getFilesDir().getAbsoluteFile() + File.separator + "images");
        if (!dir.exists()) { // 创建目录
            dir.mkdirs();
        }
        File file = new File(mActivity.getFilesDir(), path);
        outputImagePath = file.getAbsolutePath();
        Uri uri = FileProvider.getUriForFile(mActivity, FILE_PROVIDER, file);
        imagePath = path;
        return uri;
    }

    /**
     * 获取目标图片的宽高
     *
     * @return
     */
    private int[] getImageWidthAndHeight(String imagePath) {

        imagePath = getRealFilePath(imagePath);
//        Log.e("getImageWidthAndHeight","imagePath:" + imagePath);

        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, opts);

        boolean rotate = BitmapUtil.isPictureRotate(imagePath);
        int width = rotate ? opts.outHeight : opts.outWidth;
        int height = rotate ? opts.outWidth : opts.outHeight;

        return new int[]{width, height};
    }

    /**
     * 获取真实路径
     * @param path
     * @return
     */
    private String getRealFilePath(String path){

        File file = new File(path);
        if(!file.exists()){
            file = new File(mActivity.getFilesDir(),path);
            if(file.exists()){
                path = file.getAbsolutePath();
            }
        }

        return path;
    }
}
