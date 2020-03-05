package com.yami.studio.banana.merchantapp.activity.form;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.google.gson.Gson;
import com.yami.studio.banana.merchantapp.BuildConfig;
import com.yami.studio.banana.merchantapp.R;
import com.yami.studio.banana.merchantapp.activity.disconnect.DisconnectHandle;
import com.yami.studio.banana.merchantapp.utils.dialog.ActionDialog;
import com.yami.studio.banana.merchantapp.utils.dialog.BuildDialog;
import com.yami.studio.banana.merchantapp.entity.category.Category;
import com.yami.studio.banana.merchantapp.entity.category.ListCategory;
import com.yami.studio.banana.merchantapp.entity.product.Product;
import com.yami.studio.banana.merchantapp.entity.product.ProductDetail;
import com.yami.studio.banana.merchantapp.network.NetworkStatus;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FormActivity extends AppCompatActivity {

    public static final String EXTRA_TEXT = "form Activity EXTRA TEXT";
    public static final String EXTRA_ID = "form Activity EXTRA ID";
    private final int PICK_IMAGE_REQUEST = 1;

    private EditText etName, etPrice, etQty, etDesc;
    private ImageView imageView;
    private Spinner spinCategory;
    private TextView tvTitle;
    private Button btnAction;
    private ScrollView scrollForm;
    private ProgressBar progressForm;

    private List<Category> categories;
    Gson gson = new Gson();

    private FormViewModel model;

    private String name, qty, price, desc, productImage;

    private long id;
    private int method;

    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        init();
        getCategories();

        scrollForm.setVisibility(View.VISIBLE);
        getExtraData();
    }

    private void getExtraData(){
        String res = getIntent().getStringExtra(EXTRA_TEXT);
        id = getIntent().getLongExtra(EXTRA_ID, 0);

        if(res != null){
            tvTitle.setText(res);
            btnAction.setText(res);
        }
    }

    private void checkId(){
        if(id > 0){
            method = Request.Method.PUT;
            url += BuildConfig.PRODUCT + id + BuildConfig.UPDATE;
            getProduct();
        } else {
            method = Request.Method.POST;
            url += BuildConfig.PRODUCTS;
        }
    }

    private void getProduct(){
        model.get(id).observe(this, new Observer<NetworkStatus>() {
            @Override
            public void onChanged(@Nullable NetworkStatus networkStatus) {
                if(networkStatus != null){
                    String res;
                    if(networkStatus.isStatus()){
                        res = networkStatus.getResponse();
                        gson = new Gson();
                        ProductDetail productDetail = gson.fromJson(res, ProductDetail.class);
                        Product product = productDetail.getData();
                        setForm(product);
                    } else {
                        res = networkStatus.getVolleyError().getMessage();
                        DisconnectHandle.onHandle(getApplicationContext(), res);
                    }
                }
            }
        });
    }

    private void setForm(Product product){
        etName.setText(product.getProductName());
        etQty.setText(product.getProductQty());
        etPrice.setText(String.valueOf(product.getProductPrice()));
        etDesc.setText(product.getProductDesc());

        int position = getCategory(product.getCategory());
        spinCategory.setSelection(position);

        String _url = BuildConfig.URL + BuildConfig.STORAGE;
        Glide.with(getApplicationContext())
                .load(_url + product.getProductImage())
                .override(imageView.getWidth())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
    }

    private int getCategory(Category category){
        int res = -1;
        for(int i=0; i<categories.size(); i++){
            if(category.getCategoryId() == categories.get(i).getCategoryId()){
                res = i;
                break;
            }
        }
        return res;
    }

    private void setSpinner() {
        ArrayAdapter<Category> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinCategory.setAdapter(adapter);
    }

    private void getCategories(){
        model.get().observe(this, new Observer<NetworkStatus>() {
            @Override
            public void onChanged(@Nullable NetworkStatus networkStatus) {
                if(networkStatus != null){
                    if(networkStatus.isStatus()){
                        String res = networkStatus.getResponse();
                        ListCategory category = gson.fromJson(res, ListCategory.class);
                        categories.addAll(category.getData());
                        setSpinner();
                        checkId();
                    }
                }
            }
        });
    }

    private void init(){
        etName = findViewById(R.id.input_item_name);
        etPrice = findViewById(R.id.input_item_price);
        etQty = findViewById(R.id.input_item_qty);
        etDesc = findViewById(R.id.input_item_desc);

        tvTitle = findViewById(R.id.tv_title_form);
        btnAction = findViewById(R.id.btn_form);

        scrollForm = findViewById(R.id.scroll_form);
        progressForm = findViewById(R.id.progress_form);

        spinCategory = findViewById(R.id.category_dropdown);
        imageView = findViewById(R.id.image_form_file);

        model = new ViewModelProvider(this).get(FormViewModel.class);
        categories = new ArrayList<>();

        gson = new Gson();
        url = BuildConfig.URL;
    }

    private void validationForm(){
        name = etName.getText().toString();
        price = etPrice.getText().toString();
        qty = etQty.getText().toString();
        desc = etDesc.getText().toString();

        if(!name.isEmpty() && !price.isEmpty() && !qty.isEmpty() && !desc.isEmpty()){
            BuildDialog.create(this, "Do you want to make a new product?", new ActionDialog() {
                @Override
                public void positiveButton() {
                    actionPost();
                }
            });
        } else {
            showError(etName);
            showError(etPrice);
            showError(etQty);
            showError(etDesc);
        }
    }

    private void actionPost(){
        scrollForm.setVisibility(View.GONE);
        progressForm.setVisibility(View.VISIBLE);

        int categoryId = spinCategory.getSelectedItemPosition();
        Category category = categories.get(categoryId);

        Product product = new Product();
        product.setProductName(name);
        product.setProductPrice(Long.parseLong(price));
        product.setProductQty(Long.parseLong(qty));
        product.setProductDesc(desc);
        product.setMerchantId(getMerchantId());
        product.setCategoryId(category.getCategoryId());
        product.setProductImage(productImage);

        String _json = gson.toJson(product);

        try {
            JSONObject json = new JSONObject(_json);
            model.post(method, json, url).observe(this, new Observer<NetworkStatus>() {
                @Override
                public void onChanged(@Nullable NetworkStatus networkStatus) {

                    if(networkStatus != null){
                        if(networkStatus.isStatus()){
                            finish();
                        } else {
                            String res = networkStatus.getResponse();
                            DisconnectHandle.onHandle(getApplicationContext(), res);
                        }
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private long getMerchantId(){
        Random random = new Random();
        return random.nextInt(3) + 1;
    }

    private void showError(EditText et){
        String res = et.getText().toString();
        if(res.isEmpty()){
            et.setError("Can Not Empty");
        }
    }

    public void chooseImage(View view){
        showFileChooser();
    }

    public void handleClick(View view){
        validationForm();
    }

    private void showFileChooser() {
        Intent pickImageIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickImageIntent.putExtra("aspectX", 1);
        pickImageIntent.putExtra("aspectY", 1);
        pickImageIntent.putExtra("scale", true);
        pickImageIntent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        startActivityForResult(pickImageIntent, PICK_IMAGE_REQUEST);
    }

    // get result image from intent above
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                InputStream is = getContentResolver().openInputStream(filePath);
                Bitmap bitmap = BitmapFactory.decodeStream(is);
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //encoding image to string
                productImage = getStringImage(bitmap); // call getStringImage() method below this code
                Log.d("image",productImage);

                Glide.with(getApplicationContext())
                        .load(bitmap)
                        .override(imageView.getWidth())
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(imageView);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 70, baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }
}
