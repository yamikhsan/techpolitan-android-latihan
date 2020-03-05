package com.yami.studio.banana.merchantapp.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.yami.studio.banana.merchantapp.R;
import com.yami.studio.banana.merchantapp.activity.register.RegisterActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void onRegister(View view) {
        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);
        finish();
    }
}
