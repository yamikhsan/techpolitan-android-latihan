package com.yami.studio.banana.merchantapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.yami.studio.banana.merchantapp.entity.Token;

import static android.content.Context.MODE_PRIVATE;

public class TokenManager {
    private static TokenManager instance;
    private static SharedPreferences preferences;

    public static final String PREFERENCES = "TOKEN PREFERENCES";

    private final String TOKEN_TYPE = "TOKEN TYPE";
    private final String EXPIRES_IN = "EXPIRES IN";
    private final String ACCESS_TOKEN = "ACCESS TOKEN";
    private final String REFRESH_TOKEN = "REFRESH TOKEN";

    private TokenManager(SharedPreferences pref){
        preferences = pref;
    }

    public static synchronized TokenManager getInstance(SharedPreferences preferences){
        if(instance == null){
            instance = new TokenManager(preferences);
        }
        return instance;
    }

    public static synchronized TokenManager getInstance(Context context){
        if(instance == null){
            SharedPreferences preferences = context.getSharedPreferences(PREFERENCES, MODE_PRIVATE);
            instance = new TokenManager(preferences);
        }
        return instance;
    }

    public void savePreferences(Token token){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(TOKEN_TYPE, token.getToken_type());
        editor.putLong(EXPIRES_IN, token.getExpires_in());
        editor.putString(ACCESS_TOKEN, token.getAccess_token());
        editor.putString(REFRESH_TOKEN, token.getRefresh_token());
        editor.apply();
    }

//    public void deletePreferences(){
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.remove(TOKEN_TYPE);
//        editor.remove(EXPIRES_IN);
//        editor.remove(ACCESS_TOKEN);
//        editor.remove(REFRESH_TOKEN);
//        editor.apply();
//    }

//    public Token getToken(){
//        return new Token(
//                preferences.getString(TOKEN_TYPE, ""),
//                preferences.getLong(EXPIRES_IN, 0),
//                preferences.getString(ACCESS_TOKEN, ""),
//                preferences.getString(REFRESH_TOKEN, "")
//        );
//    }

    public String getAccessToken(){
        return preferences.getString(TOKEN_TYPE, "") + " " + preferences.getString(ACCESS_TOKEN, "");
    }
}
