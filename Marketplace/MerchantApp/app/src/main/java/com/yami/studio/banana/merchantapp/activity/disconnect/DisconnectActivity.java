package com.yami.studio.banana.merchantapp.activity.disconnect;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.yami.studio.banana.merchantapp.R;
import com.yami.studio.banana.merchantapp.activity.main.MainActivity;

public class DisconnectActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    public static final String EXTRA_DISCONNECT = "EXTRA DISCONNECT";

    private SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disconnect);

        TextView textView = findViewById(R.id.tv_disconnect);
        String msg = getIntent().getStringExtra(EXTRA_DISCONNECT);
        if(msg != null){
            textView.setText(msg);
        }

        refreshLayout = findViewById(R.id.swipe_disconnect);
        refreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorBlue);
        refreshLayout.setOnRefreshListener(this);

    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(false);
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        }, 1000);
    }
}
