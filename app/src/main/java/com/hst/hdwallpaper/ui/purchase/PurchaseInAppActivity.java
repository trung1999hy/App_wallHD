package com.hst.hdwallpaper.ui.purchase;


import static com.android.billingclient.api.BillingClient.ProductType.INAPP;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.billingclient.api.AcknowledgePurchaseParams;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ConsumeParams;
import com.android.billingclient.api.ConsumeResponseListener;
import com.android.billingclient.api.ProductDetails;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.QueryProductDetailsParams;
import com.android.billingclient.api.QueryPurchasesParams;
import com.google.common.collect.ImmutableList;
import com.hst.hdwallpaper.App;
import com.hst.hdwallpaper.R;
import com.hst.hdwallpaper.data.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class PurchaseInAppActivity extends BaseFragment<PurcherListView, PurchearPresenter> implements PurchaseInAppAdapter.OnClickListener {

    private PurchaseInAppAdapter adapter;
    private BillingClient billingClient;
    private Handler handler;
    private List<ProductDetails> productDetailsList;
    private OnPurchaseResponse onPurchaseResponse;

    private ImageView imvBack;
    private LinearLayout layout;
    private RecyclerView listData;

    @Override
    public int getLayoutId() {
        return R.layout.activity_purchase_in_app;
    }

    @Override
    public PurchearPresenter initPresenter() {
        return new PurchearPresenter();
    }

    @Override
    public void onDestroyed() {

    }

    @Override
    public void onStarting() {
        listData = getRootView().findViewById(R.id.listData);
        layout = getRootView().findViewById(R.id.view);
        initViews();
    }


    private void initViews() {
        adapter = new PurchaseInAppAdapter();
        listData.setHasFixedSize(true);
        listData.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        listData.setAdapter(adapter);
        adapter.setOnClickListener(this);
        productDetailsList = new ArrayList<>();
        handler = new Handler();
        billingClient = BillingClient.newBuilder(getActivity())
                .enablePendingPurchases()
                .setListener(
                        (billingResult, list) -> {
                            if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && list != null) {
                                for (Purchase purchase : list) {
                                    verifyInAppPurchase(purchase);
                                }
                            }
                        }
                ).build();
        establishConnection();
    }

    void establishConnection() {
        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    showProducts();
                }
            }

            @Override
            public void onBillingServiceDisconnected() {
                establishConnection();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    void showProducts() {
        QueryProductDetailsParams params = QueryProductDetailsParams.newBuilder()
                .setProductList(getInAppProductList())
                .build();
        billingClient.queryProductDetailsAsync(
                params,
                (billingResult, prodDetailsList) -> {
                    // Process the result
                    productDetailsList.clear();
                    handler.postDelayed(() -> {
                        productDetailsList.addAll(prodDetailsList);
                        adapter.setData(getActivity(), productDetailsList);
                        if (prodDetailsList.size() == 0) {
                            layout.setVisibility(View.VISIBLE);
                            Toast.makeText(getActivity(), "prodDetailsList, size = 0", Toast.LENGTH_SHORT).show();
                        } else {
                            layout.setVisibility(View.GONE);
                        }
                    }, 2000);
                }
        );
    }

//    @Override
//    public void onBackPressed() {
//        try {
//            super.onBackPressed();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        finish();
//    }

    private ImmutableList<QueryProductDetailsParams.Product> getInAppProductList() {
        ImmutableList<QueryProductDetailsParams.Product> productList = ImmutableList.of(
                //Product 1
                QueryProductDetailsParams.Product.newBuilder()
                        .setProductId(IConstaint.KEY_1_COIN)
                        .setProductType(INAPP)
                        .build(),
                QueryProductDetailsParams.Product.newBuilder()
                        .setProductId(IConstaint.KEY_COIN)
                        .setProductType(INAPP)
                        .build(),


                //Product 2
                QueryProductDetailsParams.Product.newBuilder()
                        .setProductId(IConstaint.KEY_2_COIN)
                        .setProductType(INAPP)
                        .build(),

                //Product 3
                QueryProductDetailsParams.Product.newBuilder()
                        .setProductId(IConstaint.KEY_3_COIN)
                        .setProductType(INAPP)
                        .build(),

                //Product 4
                QueryProductDetailsParams.Product.newBuilder()
                        .setProductId(IConstaint.KEY_4_COIN)
                        .setProductType(INAPP)
                        .build(),

                //Product 5
                QueryProductDetailsParams.Product.newBuilder()
                        .setProductId(IConstaint.KEY_5_COIN)
                        .setProductType(INAPP)
                        .build(),

                //Product 6
                QueryProductDetailsParams.Product.newBuilder()
                        .setProductId(IConstaint.KEY_6_COIN)
                        .setProductType(INAPP)
                        .build()
        );
        return productList;
    }

    void verifyInAppPurchase(Purchase purchases) {
        AcknowledgePurchaseParams acknowledgePurchaseParams = AcknowledgePurchaseParams
                .newBuilder()
                .setPurchaseToken(purchases.getPurchaseToken())
                .build();
// lắng nghe trả về trạng thái mua
        billingClient.acknowledgePurchase(acknowledgePurchaseParams, billingResult -> {
            if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                App.getInstance().setPremium(1);
                String proId = purchases.getProducts().get(0);
                int quantity = purchases.getQuantity();
                setPurchaseResponse(this::setupResult);
                onPurchaseResponse.onResponse(proId, quantity);
                allowMultiplePurchases(purchases);
                Toast.makeText(getActivity(), "verifyInAppPurchase Mua ok--> " + proId, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void allowMultiplePurchases(Purchase purchase) {
        // gọi thằng này để mua nhiều lần
        ConsumeParams consumeParams = ConsumeParams
                .newBuilder()
                .setPurchaseToken(purchase.getPurchaseToken())
                .build();
        billingClient.consumeAsync(consumeParams, new ConsumeResponseListener() {
            @Override
            public void onConsumeResponse(BillingResult billingResult, String s) {
                Toast.makeText(getActivity(), " Resume item ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClickItem(ProductDetails item) {
        launchPurchaseFlow(item);
    }

    private void launchPurchaseFlow(ProductDetails productDetails) {
        ImmutableList<BillingFlowParams.ProductDetailsParams> productDetailsParamsList =
                ImmutableList.of(
                        BillingFlowParams.ProductDetailsParams.newBuilder()
                                .setProductDetails(productDetails)
                                .build()
                );
        BillingFlowParams billingFlowParams = BillingFlowParams.newBuilder()
                .setProductDetailsParamsList(productDetailsParamsList)
                .build();
        billingClient.launchBillingFlow(getActivity(), billingFlowParams);
    }

    public void onResume() {
        super.onResume();
        billingClient.queryPurchasesAsync(
                QueryPurchasesParams.newBuilder().setProductType(INAPP).build(),
                (billingResult, list) -> {
                    if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                        for (Purchase purchase : list) {
                            if (purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED && !purchase.isAcknowledged()) {
                                verifyInAppPurchase(purchase);
                            }
                        }
                    }
                }
        );
    }

    private void setupResult(String proId, int quantity) {
        Intent intent = new Intent();
        int totalCoin = App.getInstance().getValueCoin();
        int remainCoin = totalCoin + getCoinFromKey(proId) * quantity;
        App.getInstance().setValueCoin(remainCoin);
        intent.putExtra(IConstaint.COIN_ORDER_RESULT, remainCoin + "");
        getActivity().setResult(Activity.RESULT_OK, intent);
        getActivity().runOnUiThread(getActivity()::onBackPressed);
    }

    private int getCoinFromKey(String coin) {
        switch (coin) {
            case IConstaint.KEY_COIN:
                return 25;
            case IConstaint.KEY_1_COIN:
                return 50;
            case IConstaint.KEY_2_COIN:
                return 100;
            case IConstaint.KEY_3_COIN:
                return 200;
            case IConstaint.KEY_4_COIN:
                return 400;
            case IConstaint.KEY_5_COIN:
                return 600;
            case IConstaint.KEY_6_COIN:
                return 700;
            case IConstaint.KEY_7_COIN:
                return 99;
            default:
                return 0;
        }
    }

    interface OnPurchaseResponse {
        void onResponse(String proId, int quantity);
    }

    private void setPurchaseResponse(OnPurchaseResponse onPurchaseResponse) {
        this.onPurchaseResponse = onPurchaseResponse;
    }
}
