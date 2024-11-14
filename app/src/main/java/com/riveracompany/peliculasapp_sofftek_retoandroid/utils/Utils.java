package com.riveracompany.peliculasapp_sofftek_retoandroid.utils;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputLayout;
import com.riveracompany.peliculasapp_sofftek_retoandroid.R;

public class Utils {
    private final Context context;

    public Utils(Context context) {
        this.context = context;
    }

    //VALIDATIONS
    //STRING
    public String validateStringValue(String value) {
        //
        return (value == null || value.equals("null") || value.isEmpty()) ? "" : value;
    }

    //ANIMATIONS
    //
    private Animation getAnimationByName(String animationName) {
        //
        Animation animation = null;
        animationName = validateStringValue(animationName);
        //
        switch (animationName) {
            case "fade-out":
                animation = AnimationUtils.loadAnimation(context, R.anim.fade_out);
                break;
            case "new-animation":
                break;
            default:
                animation = AnimationUtils.loadAnimation(context, R.anim.fade_in);
                break;
        }
        //
        return animation;
    }

    //
    public void startImageAnimation(ImageView imageView, String animationName) {
        //
        Animation animation = getAnimationByName(animationName);
        //
        imageView.startAnimation(animation);
    }

    //REDIRECTIONS - TRANSITIONS
    //
    public void redirectActivity(Class<?> cls) {
        //
        context.startActivity(new Intent(context, cls));
    }

    //EDIT TEXT
    //
    public void handleEditText(EditText editText, TextInputLayout textInputLayout) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = validateStringValue(s.toString());
                if (!text.isEmpty()) textInputLayout.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    //
    public void handleEditTextError(EditText editText, TextInputLayout textInputLayout, String message) {
        textInputLayout.setErrorEnabled(true);
        textInputLayout.setError(message);
        editText.requestFocus();
    }


}
