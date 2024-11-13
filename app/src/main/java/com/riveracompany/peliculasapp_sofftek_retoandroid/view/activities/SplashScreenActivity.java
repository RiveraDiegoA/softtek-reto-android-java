package com.riveracompany.peliculasapp_sofftek_retoandroid.view.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.riveracompany.peliculasapp_sofftek_retoandroid.R;
import com.riveracompany.peliculasapp_sofftek_retoandroid.controller.SplashScreenController;
import com.riveracompany.peliculasapp_sofftek_retoandroid.databinding.ActivitySplashScreenBinding;

@SuppressLint("CustomSplashScreen")
public class SplashScreenActivity extends AppCompatActivity {
    private Activity activity;
    private ActivitySplashScreenBinding binding;
    private SplashScreenController splashScreenController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //
        activity = this;
        splashScreenController = new SplashScreenController(this);
        initializeView();
    }

    @Override
    protected void onDestroy() {
        if (binding != null) binding = null;
        super.onDestroy();
    }

    private void initializeView() {
        //start animation
        splashScreenController.startLogoAnimation(binding.aSpsImvLogo);
    }
}