package com.riveracompany.peliculasapp_sofftek_retoandroid.view.activities;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.riveracompany.peliculasapp_sofftek_retoandroid.R;
import com.riveracompany.peliculasapp_sofftek_retoandroid.callback.LoginResultCallback;
import com.riveracompany.peliculasapp_sofftek_retoandroid.controller.LoginController;
import com.riveracompany.peliculasapp_sofftek_retoandroid.databinding.ActivityLoginBinding;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity implements LoginResultCallback {
    private static final int VALIDATION_VIEW_DURATION = 2500; //2.5 seconds
    private ActivityLoginBinding binding;
    private LoginController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.a_lgn_main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        controller = new LoginController(this, this);
        initializeView();
    }

    private void initializeView() {
        //
        controller.utils.handleEditText(binding.aLgnEdtUsername, binding.aLgnTilUsername);
        controller.utils.handleEditText(binding.aLgnEdtPassword, binding.aLgnTilPassword);
        //
        binding.aLgnBtnLogin.setOnClickListener(v -> {
            String username = Objects.requireNonNull(binding.aLgnEdtUsername.getText()).toString();
            String password = Objects.requireNonNull(binding.aLgnEdtPassword.getText()).toString();
            controller.handleLogin(username, password);
        });
    }

    @Override
    public void onLoginStart() {
        //
        handleValidateView(R.drawable.ic_search_activity, getString(R.string.login_validate), getString(R.string.login_validate_desc), true);
        //
        binding.aLgnLytContent.setVisibility(View.GONE);
        binding.aLgnLytValidate.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoginSuccess() {
        //
        handleValidateView(R.drawable.ic_verified_user, getString(R.string.login_success), getString(R.string.login_success_desc), false);
    }

    @Override
    public void onLoginError(boolean isUsername, boolean isPassword, String message) {
        //
        if (!isUsername)
            controller.utils.handleEditTextError(binding.aLgnEdtUsername, binding.aLgnTilUsername, message);
        else if (!isPassword)
            controller.utils.handleEditTextError(binding.aLgnEdtPassword, binding.aLgnTilPassword, message);
        else {
            //
            handleValidateView(R.drawable.ic_error, getString(R.string.login_error), message, false);
            //
            new Handler().postDelayed(() -> {
                binding.aLgnLytValidate.setVisibility(View.GONE);
                binding.aLgnLytContent.setVisibility(View.VISIBLE);
            }, VALIDATION_VIEW_DURATION);
        }
    }

    private void handleValidateView(int icon, String title, String desc, boolean isVisibleProgressBar) {
        //
        binding.aLgnLytValidateImv.setImageResource(icon);
        binding.aLgnLytValidateTxvTitle.setText(title);
        binding.aLgnLytValidateTxvDesc.setText(desc);
        binding.aLgnLytValidatePb.setVisibility(isVisibleProgressBar ? View.VISIBLE : View.INVISIBLE);
    }
}