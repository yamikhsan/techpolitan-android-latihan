package com.yami.studio.banana.customerapp.activity.detail;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.yami.studio.banana.customerapp.BuildConfig;
import com.yami.studio.banana.customerapp.R;
import com.yami.studio.banana.customerapp.activity.DisconnectActivity;
import com.yami.studio.banana.customerapp.entity.Product;
import com.yami.studio.banana.customerapp.entity.ProductDetail;
import com.yami.studio.banana.customerapp.network.NetworkStatus;

import java.text.DecimalFormat;

import static com.yami.studio.banana.customerapp.activity.DisconnectActivity.EXTRA_DISCONNECT;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_DATA = "EXTRA PRODUCT DATA";

    private ImageView image;
    private TextView name, price, qty, category, merchant, desc;
    private ProgressBar progressImage;

    private long id;

    private DetailViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = findViewById(R.id.toolbar_detail);
        toolbar.setNavigationIcon(R.drawable.ic_chevron);
        setSupportActionBar(toolbar);

        id = getIntent().getLongExtra(EXTRA_DATA, 0);
        init();

        model = new ViewModelProvider(this).get(DetailViewModel.class);
        getData();
    }

    private void getData(){

        model.getProduct(id).observe(this, new Observer<NetworkStatus>() {
            @Override
            public void onChanged(@Nullable NetworkStatus networkStatus) {

                if(networkStatus != null){
                    String res = networkStatus.getResponse();
                    if(networkStatus.isStatus()){

                        Gson gson = new Gson();
                        ProductDetail productDetail = gson.fromJson(res, ProductDetail.class);
                        Product product = productDetail.getData();

                        setImage(product.getProductImage());

                        name.setText(product.getProductName());
                        price.setText(toMoney(product.getProductPrice()));
                        qty.setText(String.valueOf(product.getProductQty()));

                        category.setText(product.getCategory().getCategoryName());
                        merchant.setText(product.getMerchant().getMerchantName());

                        desc.setText(product.getProductDesc());

                    }else {
                        Intent i = new Intent(getApplicationContext(), DisconnectActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.putExtra(EXTRA_DISCONNECT, res);
                        startActivity(i);
                    }
                }

            }
        });

    }

    private void setImage(String url){

        String storage_url = BuildConfig.URL + BuildConfig.STORAGE;
        Picasso.get()
                .load(storage_url + url)
                .error(R.drawable.ic_launcher_background)
                .fit()
                .into(image, new Callback() {
                    @Override
                    public void onSuccess() {
                        progressImage.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onError(Exception e) {
                        progressImage.setVisibility(View.INVISIBLE);
                        e.printStackTrace();
                    }
                });

    }

    private void init(){
        image = findViewById(R.id.img_detail);
        name = findViewById(R.id.tv_detail_name);
        price = findViewById(R.id.tv_detail_price);
        qty = findViewById(R.id.tv_detail_qty);
        category = findViewById(R.id.tv_detail_category);
        merchant = findViewById(R.id.tv_detail_merchant);
        desc = findViewById(R.id.tv_detail_desc);
        progressImage = findViewById(R.id.progress_detail);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    String toMoney(long money){
        DecimalFormat format = new DecimalFormat("#,###");
        return "Rp " + format.format(money);
    }

}
