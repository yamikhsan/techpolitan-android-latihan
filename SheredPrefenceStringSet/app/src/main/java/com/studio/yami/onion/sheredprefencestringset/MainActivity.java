package com.studio.yami.onion.sheredprefencestringset;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private Set<String> set = new HashSet<>();
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = getSharedPreferences("Prefence", MODE_PRIVATE);

        String msg;
        try{

            set = preferences.getStringSet("Set", set);
            msg = String.valueOf(set.size());

        } catch (ClassCastException e){
            msg = e.toString();
        }

        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();

    }

    public void onClick(View view) {

        set.add("Banana");
        @SuppressLint("CommitPrefEdits")
        SharedPreferences.Editor editor = preferences.edit();
        editor.putStringSet("Set", set);
        editor.apply();

    }
}
