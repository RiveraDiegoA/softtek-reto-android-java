package com.riveracompany.peliculasapp_sofftek_retoandroid.callback;

public interface LoginResultCallback {
    void onLoginStart();

    void onLoginSuccess();

    void onLoginError(boolean isUsername, boolean isPassword, String message);
}
