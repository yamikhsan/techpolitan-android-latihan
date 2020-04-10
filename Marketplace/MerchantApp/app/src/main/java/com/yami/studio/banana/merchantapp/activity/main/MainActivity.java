package com.yami.studio.banana.merchantapp.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.yami.studio.banana.merchantapp.R;
import com.yami.studio.banana.merchantapp.activity.form.FormActivity;
import com.yami.studio.banana.merchantapp.activity.login.LoginActivity;
import com.yami.studio.banana.merchantapp.adapter.MainAdapter;
import com.yami.studio.banana.merchantapp.entity.product.ListProduct;
import com.yami.studio.banana.merchantapp.network.NetworkStatus;

import static com.yami.studio.banana.merchantapp.activity.form.FormActivity.EXTRA_TEXT;

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

        refreshLayout = findViewById(R.id.swipe_main);
        refreshLayout.setColorSchemeResources(R.color.colorBlue);
        refreshLayout.setOnRefreshListener(refreshListener);

        progressMain = findViewById(R.id.progress_main);

        FloatingActionButton floatingCreate = findViewById(R.id.floating_create);
        floatingCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =  new Intent(getApplicationContext(), FormActivity.class);
                i.putExtra(EXTRA_TEXT, "Create Product");
                startActivity(i);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        getData();
    }

    SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            getData();
        }
    };

    private void getData() {
        model.get().observe(this, new Observer<NetworkStatus>() {
            @Override
            public void onChanged(@Nullable NetworkStatus networkStatus) {
                if (networkStatus != null) {
                    progressMain.setVisibility(View.GONE);
                    String res;
                    if (networkStatus.isStatus()) {
                        res = networkStatus.getResponse();
                        Gson gson = new Gson();
                        ListProduct data = gson.fromJson(res, ListProduct.class);
                        adapter.setProducts(data.getData());
                    } else {
//                        res = networkStatus.getVolleyError().getMessage();
//                        DisconnectHandle.onHandle(getApplicationContext(), res);
                        toLogin();
                    }
                    refreshLayout.setRefreshing(false);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logout){
            toLogin();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void toLogin(){
        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(i);
        finish();
    }
}
