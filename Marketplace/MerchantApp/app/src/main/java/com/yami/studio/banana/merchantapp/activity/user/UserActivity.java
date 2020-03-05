package com.yami.studio.banana.merchantapp.activity.user;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;
import com.yami.studio.banana.merchantapp.activity.disconnect.DisconnectHandle;
import com.yami.studio.banana.merchantapp.databinding.ActivityUserBinding;
import com.yami.studio.banana.merchantapp.entity.Token;
import com.yami.studio.banana.merchantapp.entity.user.GetUser;
import com.yami.studio.banana.merchantapp.network.NetworkStatus;
import com.yami.studio.banana.merchantapp.utils.TokenManager;

import static com.yami.studio.banana.merchantapp.utils.TokenManager.PREFERENCES;

public class UserActivity extends AppCompatActivity {

    private ActivityUserBinding bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityUserBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());

        UserViewModel model = new ViewModelProvider(this).get(UserViewModel.class);

        SharedPreferences preferences = getSharedPreferences(PREFERENCES, MODE_PRIVATE);
        Token token = TokenManager.getInstance(preferences).getToken();
        String _token = token.getToken_type() + " " + token.getAccess_token();
        model.get(_token).observe(this, new Observer<NetworkStatus>() {
            @Override
            public void onChanged(@Nullable NetworkStatus networkStatus) {
                if(networkStatus != null){
                    String res;
                    if(networkStatus.isStatus()){
                        res = networkStatus.getResponse();
                        GetUser getUser = new Gson().fromJson(res, GetUser.class);
//                        bind.tvUserName.setText();
                    } else {
                        res = networkStatus.getVolleyError().getMessage();
                        DisconnectHandle.onHandle(getApplicationContext(), res);
                    }
                }
            }
        });
    }

}
