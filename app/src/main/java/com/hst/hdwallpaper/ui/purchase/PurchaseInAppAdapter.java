package com.hst.hdwallpaper.ui.purchase;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.android.billingclient.api.ProductDetails;
import com.hst.hdwallpaper.R;

import java.util.ArrayList;
import java.util.List;

public class PurchaseInAppAdapter extends RecyclerView.Adapter<PurchaseInAppAdapter.ViewHolder> {

    private OnClickListener onClickListener;
    private List<ProductDetails> productDetailsList = new ArrayList<>();
    private Context context;

    public void setData(Context context, List<ProductDetails> productDetailsList) {
        this.productDetailsList = productDetailsList;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.item_subscription, parent, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.display(productDetailsList.get(position));
    }

    @Override
    public int getItemCount() {
        return productDetailsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvSubName;
        private ConstraintLayout item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSubName = itemView.findViewById(R.id.tvSubName);
            item = itemView.findViewById(R.id.item);
        }

        public void display(ProductDetails productDetails) {
            if (productDetails == null) return;
            String price = productDetails.getOneTimePurchaseOfferDetails().getFormattedPrice();
            tvSubName.setText(setTitleValue(productDetails.getProductId(), price));
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickListener.onClickItem(productDetails);
                }
            });
        }
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        void onClickItem(ProductDetails item);
    }

    private String setTitleValue(String productId, String price) {
        switch (productId) {
            case IConstaint.KEY_COIN:
                return String.format(context.getResources().getString(R.string.message_purchase_one), price + "/25 vàng");
            case IConstaint.KEY_1_COIN:
                return String.format(context.getResources().getString(R.string.message_purchase_one), price + "/50 vàng");
            case IConstaint.KEY_2_COIN:
                return String.format(context.getResources().getString(R.string.message_purchase_one), price + "/100 vàng");
            case IConstaint.KEY_3_COIN:
                return String.format(context.getResources().getString(R.string.message_purchase_one), price + "/200 vàng");
            case IConstaint.KEY_4_COIN:
                return String.format(context.getResources().getString(R.string.message_purchase_one), price + "/400 vàng");
            case IConstaint.KEY_5_COIN:
                return String.format(context.getResources().getString(R.string.message_purchase_one), price + "/600 vàng");
            case IConstaint.KEY_6_COIN:
                return String.format(context.getResources().getString(R.string.message_purchase_one), price + "/700 vàng");
            case IConstaint.KEY_7_COIN:
                return String.format(context.getResources().getString(R.string.message_purchase_one), price + "/99 vàng");
            default:
                return String.format(context.getResources().getString(R.string.message_purchase_one), "/0 vàng");
        }
    }
}

