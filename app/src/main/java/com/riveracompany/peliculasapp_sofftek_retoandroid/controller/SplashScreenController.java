package com.riveracompany.peliculasapp_sofftek_retoandroid.controller;

import android.content.Context;
import android.widget.ImageView;

import com.riveracompany.peliculasapp_sofftek_retoandroid.R;
import com.riveracompany.peliculasapp_sofftek_retoandroid.utils.Utils;

public class SplashScreenController {
    private final Context context;
    private final Utils utils;

    public SplashScreenController(Context context) {
        this.context = context;
        this.utils = new Utils(context);
    }

    //
    public void startLogoAnimation(ImageView imageView) {
        utils.startImageAnimation(imageView, context.getString(R.string.anim_fade_in));
    }
}
