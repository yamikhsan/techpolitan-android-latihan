package com.yami.studio.banana.merchantapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.yami.studio.banana.merchantapp.BuildConfig;
import com.yami.studio.banana.merchantapp.R;
import com.yami.studio.banana.merchantapp.activity.detail.DetailActivity;
import com.yami.studio.banana.merchantapp.entity.product.Product;

import java.text.DecimalFormat;
import java.util.List;

import static com.yami.studio.banana.merchantapp.activity.detail.DetailActivity.EXTRA_DATA;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainHolder> {

    private List<Product> products;

    @NonNull
    @Override
    public MainHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new MainHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MainHolder holder, int position) {
        if(products != null){
            holder.onBind(products.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return products != null ? products.size() : 0;
    }

    public void setProducts(List<Product> products){
        this.products = products;
        notifyDataSetChanged();
    }

    static class MainHolder extends RecyclerView.ViewHolder {

        private Context context;
        private ImageView img;
        private TextView productName, productPrice, merchantName;
        private ProgressBar progressImage;
        private Product product;

        MainHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_product);
            productName = itemView.findViewById(R.id.tv_product_name);
            productPrice = itemView.findViewById(R.id.tv_product_price);
            merchantName = itemView.findViewById(R.id.tv_merchant_name);
            progressImage = itemView.findViewById(R.id.progress_image);

            context = itemView.getContext();

            itemView.setOnClickListener(listener);
        }

        void onBind(Product product){
            DecimalFormat format = new DecimalFormat("#,###");
            String price = "Rp " + format.format(product.getProductPrice());
            this.product = product;
            productName.setText(product.getProductName());
            productPrice.setText(price);
            merchantName.setText(product.getMerchant().getMerchantName());
            String url = BuildConfig.URL + BuildConfig.STORAGE;
            img.setClipToOutline(true);
            Picasso.get()
                    .load(url + product.getProductImage())
                    .error(R.drawable.ic_launcher_background)
                    .fit()
                    .into(img, new Callback() {
                        @Override
                        public void onSuccess() {
                            progressImage.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onError(Exception e) {
                            progressImage.setVisibility(View.INVISIBLE);
                            if(e.getMessage() != null){

                                Log.d("JsonApp", e.getMessage());

                            }
                            e.printStackTrace();
                        }
                    });
        }

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, DetailActivity.class);
                i.putExtra(EXTRA_DATA, product.getProductId());
                context.startActivity(i);
            }
        };

    }
}
