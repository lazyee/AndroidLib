package com.leeorz.lib.utils.photo;

import java.util.List;

/**
 * Created by 嘉俊 on 2015/10/9.
 */
public interface OnDealImageListener {

    void onDealSingleImageComplete(Photo photo);
    void onDealMultiImageComplete(List<Photo> list);
}
