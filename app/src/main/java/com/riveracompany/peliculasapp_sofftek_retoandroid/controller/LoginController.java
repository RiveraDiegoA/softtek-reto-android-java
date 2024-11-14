package com.riveracompany.peliculasapp_sofftek_retoandroid.controller;

import android.content.Context;
import android.os.Handler;

import com.riveracompany.peliculasapp_sofftek_retoandroid.R;
import com.riveracompany.peliculasapp_sofftek_retoandroid.callback.LoginResultCallback;
import com.riveracompany.peliculasapp_sofftek_retoandroid.utils.Utils;
import com.riveracompany.peliculasapp_sofftek_retoandroid.view.activities.LoginActivity;
import com.riveracompany.peliculasapp_sofftek_retoandroid.view.activities.MoviesActivity;

public class LoginController {
    private static final int VALIDATION_VIEW_DURATION = 1500; //1.5 seconds
    private static final int SCREEN_VIEW_DURATION = 1000; //1 second
    private final Context context;
    private final LoginResultCallback callback;
    public Utils utils;

    public LoginController(Context context, LoginResultCallback callback) {
        this.context = context;
        this.callback = callback;
        this.utils = new Utils(context);
    }

    //
    public void handleLogin(String username, String password) {
        //
        username = utils.validateStringValue(username);
        password = utils.validateStringValue(password);
        //
        boolean isUsername = !username.isEmpty();
        boolean isPassword = !password.isEmpty();
        String errorMessage = !isUsername ? context.getString(R.string.username_error_empty) : !isPassword ? context.getString(R.string.password_error_empty) : context.getString(R.string.login_error_desc);
        //
        if (!isUsername || !isPassword) callback.onLoginError(isUsername, isPassword, errorMessage);
        else {
            callback.onLoginStart();
            //
            boolean isValidUsername = username.equals(context.getString(R.string.username_example));
            boolean isValidPassword = password.equals(context.getString(R.string.password_example));
            //
            new Handler().postDelayed(() -> {
                if (isValidUsername && isValidPassword) callback.onLoginSuccess();
                else callback.onLoginError(true, true, errorMessage);
            }, VALIDATION_VIEW_DURATION);
        }

    }

    //
    public void startTransition() {
        new Handler().postDelayed(this::navigateToMovies, SCREEN_VIEW_DURATION);
    }

    //
    private void navigateToMovies() {
        utils.redirectActivity(MoviesActivity.class);
        //
        if (context instanceof LoginActivity) {
            //animate transition
            ((LoginActivity) context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            //finish
            ((LoginActivity) context).finish();
        }
    }
}
