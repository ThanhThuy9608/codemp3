package com.example.thanhthuy.nhac_mp3;

import android.content.Context;
import android.webkit.WebView;

/**
 * Created by ThanhThuy on 26/03/2017.
 */

public class ImagerGifView extends WebView {
    public ImagerGifView (Context context, String path){
        super(context);

        loadUrl(path);
    }
}
