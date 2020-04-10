package com.yami.studio.banana.merchantapp.activity.register;

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
import com.yami.studio.banana.merchantapp.activity.login.LoginActivity;
import com.yami.studio.banana.merchantapp.activity.main.MainActivity;
import com.yami.studio.banana.merchantapp.databinding.ActivityRegisterBinding;
import com.yami.studio.banana.merchantapp.entity.Token;
import com.yami.studio.banana.merchantapp.entity.error.Error;
import com.yami.studio.banana.merchantapp.entity.error.ResponseError;
import com.yami.studio.banana.merchantapp.network.NetworkStatus;
import com.yami.studio.banana.merchantapp.utils.TokenManager;

import java.util.HashMap;

import static com.yami.studio.banana.merchantapp.utils.TokenManager.PREFERENCES;

public class RegisterActivity extends AppCompatActivity {

    private HashMap<String, String> params = new HashMap<>();

    private ActivityRegisterBinding bind;

    private RegisterViewModel model;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());

        model = new ViewModelProvider(this).get(RegisterViewModel.class);
        preferences = getSharedPreferences(PREFERENCES, MODE_PRIVATE);
    }

    private void getForm() {
        String isMerchant = "1";
        String merchantName = bind.inputMerchantName.getText().toString();
        String firstName = bind.inputFirstName.getText().toString();
        String lastName = bind.inputLastName.getText().toString();
        String email = bind.inputEmail.getText().toString();
        String password = bind.inputPassword.getText().toString();
        String confirmPassword = bind.inputConfirmPassword.getText().toString();

        merchantName = merchantName.isEmpty() ? "Sayur Shop" : merchantName;


        params.put("first_name", firstName);
        params.put("last_name", lastName);
        params.put("email", email);
        params.put("password", password);
        params.put("confirm_password", confirmPassword);
        params.put("is_merchant", isMerchant);
        params.put("merchant_name", merchantName);
    }

    public void onRegister(View view) {
        getForm();
        model.post(params).observe(this, new Observer<NetworkStatus>() {
            @Override
            public void onChanged(@Nullable NetworkStatus networkStatus) {
                if (networkStatus != null) {
                    String res;
                    if (networkStatus.isStatus()) {
                        res = networkStatus.getResponse();
                        Token token = new Gson().fromJson(res, Token.class);
                        TokenManager.getInstance(preferences).savePreferences(token);

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

                            checkFrom(bind.inputFirstName, error.getFirst_name());
                            checkFrom(bind.inputLastName, error.getLast_name());
                            checkFrom(bind.inputEmail, error.getEmail());
                            checkFrom(bind.inputPassword, error.getPassword());
                            checkFrom(bind.inputConfirmPassword, error.getConfirm_password());
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

    public void onLogin(View view) {
        intentLogin();
    }

    private void intentLogin() {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        finish();
    }

}
