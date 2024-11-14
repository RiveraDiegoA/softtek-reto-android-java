package com.riveracompany.peliculasapp_sofftek_retoandroid.controller;

import android.content.Context;
import android.os.Handler;
import android.widget.ImageView;

import com.riveracompany.peliculasapp_sofftek_retoandroid.R;
import com.riveracompany.peliculasapp_sofftek_retoandroid.utils.Utils;
import com.riveracompany.peliculasapp_sofftek_retoandroid.view.activities.LoginActivity;
import com.riveracompany.peliculasapp_sofftek_retoandroid.view.activities.SplashScreenActivity;

public class SplashScreenController {
    private static final int SCREEN_VIEW_DURATION = 2500; //2.5 seconds
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

    //
    public void startTransition() {
        new Handler().postDelayed(this::navigateToLogin, SCREEN_VIEW_DURATION);
    }

    //
    public void navigateToLogin() {
        utils.redirectActivity(LoginActivity.class);
        //
        if (context instanceof SplashScreenActivity) {
            //animate transition
            ((SplashScreenActivity) context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            //finish
            ((SplashScreenActivity) context).finish();
        }
    }
}
