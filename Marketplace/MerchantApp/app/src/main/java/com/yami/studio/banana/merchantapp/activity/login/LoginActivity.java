package com.yami.studio.banana.merchantapp.activity.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.yami.studio.banana.merchantapp.activity.disconnect.DisconnectHandle;
import com.yami.studio.banana.merchantapp.activity.main.MainActivity;
import com.yami.studio.banana.merchantapp.activity.register.RegisterActivity;
import com.yami.studio.banana.merchantapp.databinding.ActivityLoginBinding;
import com.yami.studio.banana.merchantapp.entity.Token;
import com.yami.studio.banana.merchantapp.entity.error.Error;
import com.yami.studio.banana.merchantapp.entity.error.ResponseError;
import com.yami.studio.banana.merchantapp.network.NetworkStatus;
import com.yami.studio.banana.merchantapp.utils.TokenManager;

import java.util.HashMap;

import static com.yami.studio.banana.merchantapp.utils.TokenManager.PREFERENCES;

public class LoginActivity extends AppCompatActivity {

    private final String REMEMBER_ME = "REMEMBER ME";
    private final String EMAIL = "email";
    private final String PASSWORD = "password";

    private HashMap<String, String> params = new HashMap<>();
    private ActivityLoginBinding bind;
    private LoginViewModel model;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());

        model = new ViewModelProvider(this).get(LoginViewModel.class);
        preferences = getSharedPreferences(PREFERENCES, MODE_PRIVATE);

        boolean status = preferences.getBoolean(REMEMBER_ME, false);
        bind.checkboxRememberMe.setChecked(status);
        if(status){
            String email = preferences.getString(EMAIL, "");
            String password = preferences.getString(PASSWORD, "");
            bind.inputEmail.setText(email);
            bind.inputPassword.setText(password);
        }
    }

    private void getForm(){
        String email = bind.inputEmail.getText().toString();
        String password = bind.inputPassword.getText().toString();

        params.put(EMAIL, email);
        params.put(PASSWORD, password);
    }

    public void onRegister(View view) {
        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);
        finish();
    }

    public void rememberMe(){
        boolean status = bind.checkboxRememberMe.isChecked();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(REMEMBER_ME, status);
        if(status){
            editor.putString(EMAIL, params.get(EMAIL));
            editor.putString(PASSWORD, params.get(PASSWORD));
        }
        editor.apply();
    }

    public void onLogin(View view) {
        getForm();

        model.get(params).observe(this, new Observer<NetworkStatus>() {
            @Override
            public void onChanged(@Nullable NetworkStatus networkStatus) {
                if(networkStatus != null){
                    String res;
                    if(networkStatus.isStatus()){

                        res = networkStatus.getResponse();
                        Token token = new Gson().fromJson(res, Token.class);
                        TokenManager.getInstance(preferences).savePreferences(token);

                        rememberMe();

                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                        finish();

                    } else {

                        VolleyError volleyError = networkStatus.getVolleyError();

                        if (volleyError.networkResponse.statusCode == 404) {
                            byte[] data = volleyError.networkResponse.data;
                            res = new String(data);
                            ResponseError responseError = new Gson().fromJson(res, ResponseError.class);
                            Error error = responseError.getError();

                            checkFrom(bind.inputEmail, error.getEmail());
                            checkFrom(bind.inputPassword, error.getPassword());

                        } else {
                            res = volleyError.getMessage();
                            DisconnectHandle.onHandle(getApplicationContext(), res);
                        }

                    }
                }
            }
        });
    }

    private void checkFrom(EditText editText, String error) {
        if (!error.isEmpty()) {
            editText.setError(error);
        }
    }
}
