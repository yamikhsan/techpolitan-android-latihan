package com.yami.studio.banana.customerapp.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.yami.studio.banana.customerapp.R;
import com.yami.studio.banana.customerapp.activity.DisconnectActivity;
import com.yami.studio.banana.customerapp.adapter.MainAdapter;
import com.yami.studio.banana.customerapp.entity.ListProduct;
import com.yami.studio.banana.customerapp.network.NetworkStatus;

import static com.yami.studio.banana.customerapp.activity.DisconnectActivity.EXTRA_DISCONNECT;

public class MainActivity extends AppCompatActivity {

    private MainViewModel model;
    private MainAdapter adapter;

    private SwipeRefreshLayout refreshLayout;
    private ProgressBar progressMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        RecyclerView rvMain = findViewById(R.id.rv_main);
        adapter = new MainAdapter();
        rvMain.setAdapter(adapter);
        rvMain.setLayoutManager(new GridLayoutManager(this, 2));

        model = new ViewModelProvider(this).get(MainViewModel.class);
        getData();

        refreshLayout = findViewById(R.id.swipe_main);
        refreshLayout.setColorSchemeResources(R.color.colorBlue);
        refreshLayout.setOnRefreshListener(refreshListener);

        progressMain = findViewById(R.id.progress_main);

    }

    SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            getData();
        }
    };

    private void getData() {

        model.getProductData().observe(this, new Observer<NetworkStatus>() {
            @Override
            public void onChanged(@Nullable NetworkStatus networkStatus) {
                if (networkStatus != null) {

                    progressMain.setVisibility(View.GONE);
                    String res = networkStatus.getResponse();

                    if (networkStatus.isStatus()) {
                        Gson gson = new Gson();
                        ListProduct data = gson.fromJson(res, ListProduct.class);
                        adapter.setProducts(data.getData());
                    } else {
                        Intent i = new Intent(getApplicationContext(), DisconnectActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.putExtra(EXTRA_DISCONNECT, res);
                        startActivity(i);
                    }

                    refreshLayout.setRefreshing(false);
                }
            }
        });

    }

}
