package com.yami.studio.banana.merchantapp.activity.detail;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.yami.studio.banana.merchantapp.BuildConfig;
import com.yami.studio.banana.merchantapp.R;
import com.yami.studio.banana.merchantapp.activity.disconnect.DisconnectHandle;
import com.yami.studio.banana.merchantapp.activity.form.FormActivity;
import com.yami.studio.banana.merchantapp.entity.product.Product;
import com.yami.studio.banana.merchantapp.entity.product.ProductDetail;
import com.yami.studio.banana.merchantapp.network.NetworkStatus;
import com.yami.studio.banana.merchantapp.utils.dialog.ActionDialog;
import com.yami.studio.banana.merchantapp.utils.dialog.BuildDialog;

import java.text.DecimalFormat;

import static com.yami.studio.banana.merchantapp.activity.form.FormActivity.EXTRA_ID;
import static com.yami.studio.banana.merchantapp.activity.form.FormActivity.EXTRA_TEXT;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_DATA = "EXTRA PRODUCT DATA";

    private LinearLayout layoutOption;

    private ImageView image;
    private TextView name, price, qty, category, merchant, desc;
    private ProgressBar progressImage, progressOption;

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
    }

    @Override
    protected void onStart() {
        super.onStart();
        getData();
    }

    private void getData() {

        model.getProduct(id).observe(this, new Observer<NetworkStatus>() {
            @Override
            public void onChanged(@Nullable NetworkStatus networkStatus) {

                if (networkStatus != null) {
                    String res;
                    if (networkStatus.isStatus()) {
                        res = networkStatus.getResponse();
                        Gson gson = new Gson();
                        ProductDetail productDetail = gson.fromJson(res, ProductDetail.class);
                        Product product = productDetail.getData();

                        setImage(product.getProductImage());

                        name.setText(product.getProductName());

                        DecimalFormat format = new DecimalFormat("#,###");
                        String _price = "Rp " + format.format(product.getProductPrice());
                        price.setText(_price);
                        qty.setText(product.getProductQty());

                        category.setText(product.getCategory().getCategoryName());
                        merchant.setText(product.getMerchant().getMerchantName());

                        desc.setText(product.getProductDesc());

                    } else {
                        res = networkStatus.getVolleyError().getMessage();
                        DisconnectHandle.onHandle(getApplicationContext(), res);
                    }
                }
            }
        });

    }

    private void setImage(String url) {

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

    private void init() {
        qty = findViewById(R.id.tv_detail_qty);
        name = findViewById(R.id.tv_detail_name);
        desc = findViewById(R.id.tv_detail_desc);
        price = findViewById(R.id.tv_detail_price);
        image = findViewById(R.id.img_detail);
        category = findViewById(R.id.tv_detail_category);
        merchant = findViewById(R.id.tv_detail_merchant);
        layoutOption = findViewById(R.id.layout_option);
        progressImage = findViewById(R.id.progress_detail);
        progressOption = findViewById(R.id.progress_option);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void actionDelete(View view) {
        BuildDialog.create(this, "Do you want to delete this product?", new ActionDialog() {
            @Override
            public void positiveButton() {
                deleteProduct();
            }
        });
    }

    private void deleteProduct() {
        model.delete(id).observe(this, new Observer<NetworkStatus>() {
            @Override
            public void onChanged(@Nullable NetworkStatus networkStatus) {
                layoutOption.setVisibility(View.GONE);
                progressOption.setVisibility(View.VISIBLE);
                if (networkStatus != null) {
                    if (networkStatus.isStatus()) {
                        finish();
                    } else {
                        String res = networkStatus.getVolleyError().getMessage();
                        DisconnectHandle.onHandle(getApplicationContext(), res);
                    }
                }
            }
        });
    }

    public void actionUpdate(View view) {
        Intent i = new Intent(getApplicationContext(), FormActivity.class);
        i.putExtra(EXTRA_TEXT, "Update Product");
        i.putExtra(EXTRA_ID, id);
        startActivity(i);
    }
}

