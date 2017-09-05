package com.leeorz.lib.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.View;

import com.leeorz.lib.app.AppConfig;
import com.orhanobut.logger.Logger;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * bitmap帮助类
 */
public class BitmapUtil {

    private final static int DES_WIDTH = 50;

    /**
     * @param context 上下文
     * @param rid     缩放的本地资源id
     * @return
     */
    public static Bitmap scaleBitmap(Context context, int rid) {
        Bitmap bitmap;
        BitmapFactory.Options options = new BitmapFactory.Options();
        //这里为ture，则下面bitmap为空（节省内存），并把bitmap对应资源的宽和高分别保存在options.outWidth和options.outHeight中
        options.inJustDecodeBounds = true;
        options.inPurgeable = true;
        bitmap = BitmapFactory.decodeResource(context.getResources(), rid, options);
        int outWidth = options.outWidth;
        int outHeight = options.outHeight;
        if (outWidth >= DES_WIDTH) {
            options.inSampleSize = outWidth / DES_WIDTH;//注意：这里的缩放等级必须是2的倍数（不够会就近取2的倍数值）
        }
        //注意：设置好缩放后，前面的true需要设置为false，不然bitmap一直为空
        options.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeResource(context.getResources(), rid, options);
        bitmap.recycle();
        return bitmap;
    }

    /**
     * @param context 上下文
     * @param rid     缩放的本地资源id
     * @return
     */
    public static Bitmap scaleBitmap(Context context, int rid, int dip) {
        Bitmap bitmap;
        BitmapFactory.Options options = new BitmapFactory.Options();
        //这里为ture，则下面bitmap为空（节省内存），并把bitmap对应资源的宽和高分别保存在options.outWidth和options.outHeight中
        options.inJustDecodeBounds = true;
        bitmap = BitmapFactory.decodeResource(context.getResources(), rid, options);
        int outWidth = options.outWidth;
        int outHeight = options.outHeight;
        //获取屏幕的宽高
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;    //得到宽度
        int height = dm.heightPixels;  //得到高度

        if (dip > 0 && outWidth >= dip) {
            options.inSampleSize = outWidth / dip;//注意：这里的缩放等级必须是2的倍数（不够会就近取2的倍数值）
        } else {
            options.inSampleSize = outWidth / width;//注意：这里的缩放等级必须是2的倍数（不够会就近取2的倍数值）
        }
        //注意：设置好缩放后，前面的true需要设置为false，不然bitmap一直为空
        options.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeResource(context.getResources(), rid, options);

        return bitmap;
    }

    /**
     * @param context  上下文
     * @param imageUri 图片路径
     * @return
     */
    public static Bitmap scaleBitmap(Context context, String imageUri) {
        Bitmap bitmap;
        BitmapFactory.Options options = new BitmapFactory.Options();
        //这里为ture，则下面bitmap为空（节省内存），并把bitmap对应资源的宽和高分别保存在options.outWidth和options.outHeight中
        options.inJustDecodeBounds = true;
        bitmap = BitmapFactory.decodeFile(imageUri, options);
        int outWidth = options.outWidth;
        int outHeight = options.outHeight;
        if (outWidth >= DES_WIDTH) {
            options.inSampleSize = outWidth / DES_WIDTH;//注意：这里的缩放等级必须是2的倍数（不够会就近取2的倍数值）
        }
        //注意：设置好缩放后，前面的true需要设置为false，不然bitmap一直为空
        options.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(imageUri, options);

        return bitmap;
    }

    /**
     * @param context  上下文
     * @param imageUri 图片路径
     * @return
     */
    public static Bitmap scaleBitmapDefault(Context context, String imageUri) {
        Bitmap bitmap;
        BitmapFactory.Options options = new BitmapFactory.Options();
        //这里为ture，则下面bitmap为空（节省内存），并把bitmap对应资源的宽和高分别保存在options.outWidth和options.outHeight中
//		options.inJustDecodeBounds = true;
//		bitmap = BitmapFactory.decodeFile(imageUri, options);
//		int outWidth = options.outWidth;
//		int outHeight = options.outHeight;
//		if(outWidth >= DES_WIDTH){
//			options.inSampleSize = outWidth/DES_WIDTH;//注意：这里的缩放等级必须是2的倍数（不够会就近取2的倍数值）
//		}
        //注意：设置好缩放后，前面的true需要设置为false，不然bitmap一直为空
//		options.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(imageUri, options);

        return bitmap;
    }


    /**
     * @param context  上下文
     * @param imageUri 图片路径
     * @return
     */
    public static Bitmap scaleBitmap(Context context, String imageUri, int dip) {
        Bitmap bitmap;
        BitmapFactory.Options options = new BitmapFactory.Options();
        //这里为ture，则下面bitmap为空（节省内存），并把bitmap对应资源的宽和高分别保存在options.outWidth和options.outHeight中
        options.inJustDecodeBounds = true;
        bitmap = BitmapFactory.decodeFile(imageUri, options);
        int outWidth = options.outWidth;
        int outHeight = options.outHeight;

        //获取屏幕的宽高
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;    //得到宽度
        int height = dm.heightPixels;  //得到高度

        if (dip > 0 && outWidth >= dip) {
            options.inSampleSize = outWidth / dip;//注意：这里的缩放等级必须是2的倍数（不够会就近取2的倍数值）
        } else {
            options.inSampleSize = outWidth / width;//注意：这里的缩放等级必须是2的倍数（不够会就近取2的倍数值）
        }
        //注意：设置好缩放后，前面的true需要设置为false，不然bitmap一直为空
        options.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(imageUri, options);

        return bitmap;
    }


    /**
     * @param imageFilePath      图片文件的路径
     * @param imageUpperLimitPix 返回的bitmap宽或高的最高像素
     * @return
     */
    public static Bitmap decodeFile(String imageFilePath, int imageUpperLimitPix) {
        try {
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(imageFilePath, o);
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp / 2 < imageUpperLimitPix && height_tmp / 2 < imageUpperLimitPix)
                    break;
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeFile(imageFilePath, o2);
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 获取bitmap宽高像素值
     *
     * @param path
     * @return
     */
    public static int[] getBitmapPxSize(String path) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, opts);
        opts.inSampleSize = 1;
        opts.inJustDecodeBounds = false;
        Bitmap mBitmap = BitmapFactory.decodeFile(path, opts);
        int width = opts.outWidth;
        int height = opts.outHeight;
        return new int[]{width, height};
    }

    /**
     * @param path
     * @param targetWidth
     * @return
     */
    public static int[] getFullScreenBitmapSize(String path, int targetWidth) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        int tempheight = options.outHeight;
        int tempWidth = options.outWidth;

//		Logger.d("-------", "__width:" + tempWidth);
//		Logger.d("-------", "__height:" + tempheight);
        double d = tempWidth / (targetWidth * 1.0);
//		if(width < Configure.screenWidth){
        tempWidth = targetWidth;
//		}
        tempheight = (int) (tempheight / d);
//		Logger.d("-------", "d:" + d);
//		Logger.d("-------", "width:" + tempWidth);
//		Logger.d("-------", "height:" + tempheight);
        return new int[]{tempWidth, tempheight};
    }

    /**
     * Drawable转bitmap
     *
     * @param d
     * @return
     */
    public static Bitmap drawable2bitmap(Drawable d) {

        return d != null ? ((BitmapDrawable) d).getBitmap() : null;
    }

    public static Bitmap layerdrawable2bitmap(Drawable drawable) {
        Bitmap bitmap_util;
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Config.ARGB_8888 : Config.RGB_565;
        bitmap_util = Bitmap.createBitmap(w, h, config);
        // 注意，下面三行代码要用到，否在在View或者surfaceview里的canvas.drawBitmap会看不到图
        Canvas canvas = new Canvas(bitmap_util);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap_util;
    }

    /**
     * bitmap转drawable
     *
     * @param b
     * @return
     */
    public static Drawable bitmap2drawable(Bitmap b) {
        return b != null ? (Drawable) new BitmapDrawable(b) : null;
    }

    /**
     * 创建带倒影的Bitmap
     *
     * @param originalImage 原bitmap
     * @param instanceColor 原图与倒影之间的颜色值，传null时默认为白色
     * @return bitmap
     * @author leeib
     */
    public static Bitmap createReflectedBitmap(Bitmap originalImage, Integer instanceColor) {
        final int reflectionGap = 4;

        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);

        Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0, height / 2, width, height / 2, matrix, false);

        Bitmap bitmapWithReflection = Bitmap.createBitmap(width, (height + height / 5), Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmapWithReflection);

        canvas.drawBitmap(originalImage, 0, 0, null);

        Paint deafaultPaint = new Paint();
        if (instanceColor != null) {
            deafaultPaint.setColor(instanceColor);
        } else {
            // 绘制的正方形为白色
            deafaultPaint.setColor(Color.WHITE);
        }

        canvas.drawRect(0, height, width, height + reflectionGap, deafaultPaint);

        canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);

        Paint paint = new Paint();
        LinearGradient shader = new LinearGradient(0, originalImage.getHeight(), 0, bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff, 0x00ffffff,
                TileMode.CLAMP);

        paint.setShader(shader);

        paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));

        canvas.drawRect(0, height, width, bitmapWithReflection.getHeight() + reflectionGap, paint);
        return bitmapWithReflection;
    }

    /**
     * 创建带圆角的Bitmap
     *
     * @param bitmap 要转换的Bitmap
     * @param pixels 圆角的大小
     * @return
     */
    public static Bitmap createRoundCornerBitmap(Bitmap bitmap, int pixels) {

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);

        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;

        final Paint paint = new Paint();

        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        final RectF rectF = new RectF(rect);

        final float roundPx = pixels;

        paint.setAntiAlias(true);

        canvas.drawARGB(0, 0, 0, 0);

        paint.setColor(color);

        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));

        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;

    }

    /**
     * 图片与边框组合
     *
     * @param bm  原图片
     * @param res 边框资源，size为8，分别对应左上、左、左下、下、右下、右、右上、上，其中四角传0表示忽略对应的边框绘制
     * @return
     */
    public static Bitmap createFrameBitmap(Bitmap bm, int[] res, Context context) {
        Bitmap bmp = decodeBitmap(res[1], context);
        Bitmap bmp1 = decodeBitmap(res[3], context);
        // 边框的宽高
        final int smallW = bmp.getWidth();
        final int smallH = bmp1.getHeight();

        // 原图片的宽高
        final int bigW = bm.getWidth();
        final int bigH = bm.getHeight();

        int wCount = (int) Math.ceil(bigW * 1.0 / smallW);
        int hCount = (int) Math.ceil(bigH * 1.0 / smallH);

        // 组合后图片的宽高
        // int newW = (wCount + 2) * smallW;
        // int newH = (hCount + 2) * smallH;
        int newW = bigW + 2;
        int newH = bigH + 2;

        // 重新定义大小
        Bitmap newBitmap = Bitmap.createBitmap(newW, newH, Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        Paint p = new Paint();
        p.setColor(Color.TRANSPARENT);
        canvas.drawRect(new Rect(0, 0, newW, newH), p);

        Rect rect = new Rect(smallW, smallH, newW - smallW, newH - smallH);
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        canvas.drawRect(rect, paint);

        // 绘原图
        canvas.drawBitmap(bm, (newW - bigW - 2 * smallW) / 2 + smallW, (newH - bigH - 2 * smallH) / 2 + smallH, null);
        // 绘边框
        // 绘四个角
        int startW = newW - smallW;
        int startH = newH - smallH;
        if (res[0] != 0) {
            Bitmap leftTopBm = decodeBitmap(res[0], context); // 左上角
            canvas.drawBitmap(leftTopBm, 0, 0, null);

            leftTopBm.recycle();
            leftTopBm = null;

        }

        if (res[2] != 0) {
            Bitmap leftBottomBm = decodeBitmap(res[2], context); // 左下角
            canvas.drawBitmap(leftBottomBm, 0, startH, null);
            leftBottomBm.recycle();
            leftBottomBm = null;
        }

        if (res[4] != 0) {
            Bitmap rightBottomBm = decodeBitmap(res[4], context); // 右下角
            canvas.drawBitmap(rightBottomBm, startW, startH, null);
            rightBottomBm.recycle();
            rightBottomBm = null;
        }

        if (res[6] != 0) {
            Bitmap rightTopBm = decodeBitmap(res[6], context); // 右上角
            canvas.drawBitmap(rightTopBm, startW, 0, null);
            rightTopBm.recycle();
            rightTopBm = null;
        }

        // 绘左右边框
        Bitmap leftBm = decodeBitmap(res[1], context);
        Bitmap rightBm = decodeBitmap(res[5], context);
        for (int i = 0, length = hCount; i < length; i++) {
            int h = smallH * (i + 1);
            canvas.drawBitmap(leftBm, 0, h, null);
            canvas.drawBitmap(rightBm, startW, h, null);
        }

        leftBm.recycle();
        leftBm = null;
        rightBm.recycle();
        rightBm = null;

        // 绘上下边框
        Bitmap bottomBm = decodeBitmap(res[3], context);
        Bitmap topBm = decodeBitmap(res[7], context);
        for (int i = 0, length = wCount; i < length; i++) {
            int w = smallW * (i + 1);
            canvas.drawBitmap(bottomBm, w, startH, null);
            canvas.drawBitmap(topBm, w, 0, null);
        }

        bottomBm.recycle();
        bottomBm = null;
        topBm.recycle();
        topBm = null;

        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();

        return newBitmap;
    }

    public static Bitmap decodeBitmap(int res, Context context) {
        return BitmapFactory.decodeResource(context.getResources(), res);
    }

    /**
     * 截取图片的中间的200X200的区域
     *
     * @param bm
     * @return
     */
    public static Bitmap cropCenter(Bitmap bm, int width, int height) {
        if (bm == null) {
            return bm;
        }
        int startWidth = (bm.getWidth() - width) / 2;
        int startHeight = ((bm.getHeight() - height) / 2);
        Rect src = new Rect(startWidth, startHeight, startWidth + width, startHeight + height);
        return cropBitmap(bm, src);
    }

    /**
     * 剪切图片
     *
     * @param bmp 被剪切的图片
     * @param src 剪切的位置
     * @return 剪切后的图片
     */
    public static Bitmap cropBitmap(Bitmap bmp, Rect src) {
        int width = src.width();
        int height = src.height();
        Rect des = new Rect(0, 0, width, height);
        Bitmap croppedImage = Bitmap.createBitmap(width, height, Config.RGB_565);
        Canvas canvas = new Canvas(croppedImage);
        canvas.drawBitmap(bmp, src, des, null);
        return croppedImage;
    }

    /**
     * 调整Bitmap的大小,对变形没有保证
     *
     * @param bm
     * @param newHeight
     * @param newWidth
     * @param context
     * @return
     */
    public static Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth, Context context) {

        int width = bm.getWidth();
        int height = bm.getHeight();

        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        // create a matrix for the manipulation
        Matrix matrix = new Matrix();

        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);

        // recreate the new Bitmap
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);

        return resizedBitmap;
    }

    /**
     * 图片保存
     *
     * @param bmp
     */
    public static String saveImage(Bitmap bmp, String filepath) {
        File file = new File(filepath);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file.getAbsolutePath();
    }

    /**
     * 保存图片
     *
     * @param context
     * @param bmp
     * @param isBroad 是否通知相册
     * @return
     */
    public static String saveImage(Context context, Bitmap bmp, boolean isBroad) {
        // 首先保存图片
        File appDir = new File(AppConfig.IMAGE_PATH);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        if (isBroad) {
            try {
                MediaStore.Images.Media.insertImage(context.getContentResolver(),
                        file.getAbsolutePath(), fileName, null);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            // 最后通知图库更新
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getAbsolutePath())));
        }

        return file.getAbsolutePath();

    }

    /**
     * 图片保存到相册
     *
     * @param context
     * @param bmp
     */
    public static String saveImageToGallery(Context context, Bitmap bmp) {
        return saveImage(context, bmp, true);
    }

    /**
     * 保存图片
     *
     * @param context
     * @param bmp
     * @return
     */
    public static String saveImage(Context context, Bitmap bmp) {
        return saveImage(context, bmp, false);
    }

    /**
     * 保存图片
     *
     * @param fileName
     * @param bitmap
     * @return
     */
    public static void saveBitmap(String fileName, Bitmap bitmap) {
        // 首先保存图片
        File appDir = new File(AppConfig.IMAGE_PATH);
        if (!appDir.exists()) {
            appDir.mkdirs();
        }
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            Logger.v("save image", "success, path :" + file.getAbsolutePath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存图片
     *
     * @param context
     * @param view
     * @return
     */
    public static void saveBitmap(Context context, View view) {
        saveImageToGallery(context, convertViewToBitmap(view));
    }

    /**
     * 在控件中获取bitmap
     *
     * @param view
     * @return
     */
    public static Bitmap convertViewToBitmap(View view) {
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        view.setDrawingCacheEnabled(false);
        return bitmap;
    }


    /**
     * 获取options，用于获取宽度
     *
     * @param path
     * @return
     */
    public static BitmapFactory.Options getBitmapOptions(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options); // 此时返回的bitmap为null
        return options;
    }

    /**
     * 获取options
     *
     * @param context
     * @param resId
     * @return
     */
    public static BitmapFactory.Options getBitmapOptions(Context context, int resId) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), resId, options);
        return options;
    }

    /**
     * 计算simpleSize
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // 源图片的高度和宽度
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            // 计算出实际宽高和目标宽高的比率
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
            // 一定都会大于等于目标的宽和高。
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    public static Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        if (baos.toByteArray().length / 1024 > 100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, 60, baos);//这里压缩options%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    /**
     * 质量压缩
     *
     * @param bitmap
     * @return
     */
    public static String qualityCompress(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if (bitmap.getByteCount() / 1024 > 1024) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
            baos.reset();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);//这里压缩60%，把压缩后的数据存放到baos中
        }
        //设置文件名
        byte[] bitmapBytes = baos.toByteArray();
        Logger.v("bitmap size", "size :" + baos.size());
        String newPicName = StrUtil.getMD5(bitmapBytes) + ".jpg";
        File f = new File(AppConfig.IMAGE_PATH, newPicName);
        if (f.exists()) {
            f.delete();
        }

        //字节流写入文件流
        try {
            FileOutputStream fos = new FileOutputStream(f);
            baos.writeTo(fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return f.getAbsolutePath();
    }

    /**
     * 比例压缩
     *
     * @param srcPath
     * @return
     */
    public static Bitmap scaleCompress(String srcPath) {
        BitmapFactory.Options options = getBitmapOptions(srcPath);
//        options.inJustDecodeBounds = true;
//        BitmapFactory.decodeFile(srcPath, options);
//        options.inJustDecodeBounds = false;
//        int w = options.outWidth;
//        int h = options.outHeight;
//        float hh = 1280f;
//        float ww = 960f;
//        int be = 1;//be=1表示不缩放
//        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
//            be = (int) (options.outWidth / ww);
//        } else if (w < h && h > hh) {//如果高度高的话根据高度固定大小缩放
//            be = (int) (options.outHeight / hh);
//        }
//        if (be <= 0)
//            be = 1;
        options.inSampleSize = calculateInSampleSize(options, 720, 960);//设置缩放比例，最大需要2.63mb
        return BitmapFactory.decodeFile(srcPath, options);
    }

    /**
     * 压缩
     *
     * @param srcPath
     * @return
     */
    public static String compress(String srcPath) {
        printPictureParameter(srcPath);
        File file = new File(srcPath);
        String newPath;
        if (file.length() < 1024 * 1024) {//图片size小于1m
            newPath = copyToTmpPicture(srcPath);
        } else {
            newPath = qualityCompress(scaleCompress(srcPath));
        }
        printPictureParameter(newPath);
        return ratingImage(readPictureDegree(srcPath), newPath);
    }

    public static String copyToTmpPicture(String srcPath) {
        File fileSrc = new File(srcPath);
        String newPicName = StrUtil.getMd5ByFile(fileSrc) + ".jpg";
        File f = new File(AppConfig.IMAGE_PATH, newPicName);
        if (f.exists()) {
            f.delete();
        }
        fileChannelCopy(fileSrc, f);
        return f.getAbsolutePath();
    }

    /**
     * 使用文件通道的方式复制文件
     *
     * @param s 源文件
     * @param t 复制到的新文件
     */
    public static void fileChannelCopy(File s, File t) {
        FileInputStream fi = null;
        FileOutputStream fo = null;
        FileChannel in = null;
        FileChannel out = null;
        try {
            fi = new FileInputStream(s);
            fo = new FileOutputStream(t);
            in = fi.getChannel();//得到对应的文件通道
            out = fo.getChannel();//得到对应的文件通道
            in.transferTo(0, in.size(), out);//连接两个通道，并且从in通道读取，然后写入out通道
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fi.close();
                in.close();
                fo.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 打印文件参数
     *
     * @param path
     */
    private static void printPictureParameter(String path) {
        BitmapFactory.Options options = getBitmapOptions(path);
        File file = new File(path);
        Logger.v("Picture Parameter", "Picture path: " + path + ", width: " + options.outWidth + ", height: " + options.outHeight + ", size: " + file.length());
    }

    public static long getBitmapsize(Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            return bitmap.getByteCount();
        }
        // Pre HC-MR1
        return bitmap.getRowBytes() * bitmap.getHeight();
    }

    /**
     * 读取照片exif信息中的旋转角度
     *
     * @param path 照片路径
     * @return角度
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 判断图片是否旋转
     *
     * @param path
     * @return
     */
    public static boolean isPictureRotate(String path) {
        int degree = readPictureDegree(path);
        return degree == 90 || degree == 270;
    }

    /**
     * @param angle
     * @param bitmap
     * @return
     * @desc <pre>旋转图片</pre>
     * @author Weiliang Hu
     * @date 2013-9-18
     */
    public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        // 旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        System.out.println("angle2=" + angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        bitmap.recycle();
        return resizedBitmap;
    }

    /**
     * 选择图片并保存到相册
     *
     * @param angle
     * @param filePath
     * @return
     */
    public static String ratingImage(int angle, String filePath) {
        Logger.v("ratingImage", "rating angle:" + angle + ", filePath:" + filePath);
        if (filePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(filePath);//根据Path读取资源图片
            if (angle != 0) {
                // 下面的方法主要作用是把图片转一个角度，也可以放大缩小等
                Matrix m = new Matrix();
                int width = bitmap.getWidth();
                int height = bitmap.getHeight();
                m.setRotate(angle); // 旋转angle度
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height,
                        m, true);// 从新生成图片
                return saveImage(bitmap, filePath);
            }
        }
        return filePath;
    }

    /**
     * 旋转图片并保存到相册
     *
     * @param filePath
     * @return
     */
    public static String ratingImageAndSave(String filePath) {
        return ratingImage(readPictureDegree(filePath), filePath);
    }

}
