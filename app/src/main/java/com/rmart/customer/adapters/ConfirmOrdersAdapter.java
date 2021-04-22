package com.rmart.customer.adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.rmart.R;
import com.rmart.RMartApplication;
import com.rmart.baseclass.CallBackInterface;
import com.rmart.baseclass.Constants;
import com.rmart.baseclass.views.CustomLoadingDialog;
import com.rmart.customer.models.AddToCartResponseDetails;
import com.rmart.customer.models.ContentModel;
import com.rmart.customer.models.CustomerProductsDetailsUnitModel;
import com.rmart.customer.models.ProductInCartDetailsModel;
import com.rmart.customer.models.ProductInCartResponse;
import com.rmart.customer.models.ShoppingCartResponseDetails;
import com.rmart.customer.shops.home.model.ProductData;
import com.rmart.customer.shops.products.repositories.ProductsRepository;
import com.rmart.profile.model.MyProfile;
import com.rmart.utilits.HttpsTrustManager;
import com.rmart.utilits.RetrofitClientInstance;
import com.rmart.utilits.UpdateCartCountDetails;
import com.rmart.utilits.Utils;
import com.rmart.utilits.services.CustomerProductsService;

import org.jetbrains.annotations.NotNull;

import java.net.SocketTimeoutException;
import java.util.List;
import java.util.Locale;

/**
 * Created by Satya Seshu on 12/09/20.
 */
public class ConfirmOrdersAdapter extends RecyclerView.Adapter<ConfirmOrdersAdapter.ViewHolder> {

    private final LayoutInflater layoutInflater;
    private ShoppingCartResponseDetails vendorShoppingCartDetails;
    private final List<ProductInCartDetailsModel> listData;
    private final CallBackInterface callBackListener;
    private final ImageLoader imageLoader;
    Context context;
    public ConfirmOrdersAdapter(ShoppingCartResponseDetails vendorShoppingCartDetails,Context context, List<ProductInCartDetailsModel> listData, CallBackInterface callBackListener) {
        layoutInflater = LayoutInflater.from(context);
        this.listData = listData;
        this.vendorShoppingCartDetails = vendorShoppingCartDetails;
        this.context = context;
        this.callBackListener = callBackListener;
        imageLoader = RMartApplication.getInstance().getImageLoader();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.confirm_order_list_items, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductInCartDetailsModel dataObject = listData.get(position);
        holder.tvProductNameField.setText(dataObject.getProductName());
        String productImageUrl = dataObject.getProductImage();
        int totalProductCartQuantity = dataObject.getTotalProductCartQty();
        holder.tvNoOfQuantityField.setText(String.valueOf(totalProductCartQuantity));

       // Double totalUnitNumbers = totalProductCartQuantity * dataObject.getUnitNumber();
       // String quantityDetails = String.format(Locale.getDefault(), "%s %s", Utils.roundOffDoubleValue(totalUnitNumbers, "0.00") , dataObject.getShortUnitMeasure());
        double totalSellingPrice = totalProductCartQuantity * dataObject.getPerProductSellingPrice();
        String sellingPrice = String.format("Rs. %s", Utils.roundOffDoubleValue(totalSellingPrice, "0.00"));
        Double totalUnitPrice = totalProductCartQuantity * dataObject.getPerProductUnitPrice();
        String unitPriceDetails = Utils.roundOffDoubleValue(totalUnitPrice, "0.00");
        String quantityPriceDetails = String.format("%s  %s",  sellingPrice, unitPriceDetails);

        SpannableString quantityPriceDetailsSpannable = new SpannableString(quantityPriceDetails);
        quantityPriceDetailsSpannable.setSpan(new StyleSpan(Typeface.BOLD), quantityPriceDetails.indexOf(sellingPrice),
                quantityPriceDetails.indexOf(sellingPrice) + sellingPrice.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        quantityPriceDetailsSpannable.setSpan(new StrikethroughSpan(), quantityPriceDetails.indexOf(unitPriceDetails),
                quantityPriceDetails.indexOf(unitPriceDetails) + unitPriceDetails.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        if(totalSellingPrice-totalUnitPrice==0){
            holder.tvQuantityPriceDetailsField.setText(sellingPrice);
        } else {
            holder.tvQuantityPriceDetailsField.setText(quantityPriceDetailsSpannable);
        }

        if (!TextUtils.isEmpty(productImageUrl)) {
            HttpsTrustManager.allowAllSSL();
            imageLoader.get(productImageUrl, ImageLoader.getImageListener(holder.ivProductImageField,
                    R.mipmap.ic_launcher, android.R.drawable
                            .ic_dialog_alert));
            holder.ivProductImageField.setImageUrl(productImageUrl, imageLoader);
        }
        holder.btnMoveToWishListField.setTag(position);
        holder.btnMoveToWishListField.setOnClickListener(v -> {
            int tag = (int) v.getTag();
            ContentModel contentModel = new ContentModel();
            contentModel.setStatus(Constants.TAG_MOVE_TO_WISH_LIST);
            contentModel.setValue(listData.get(tag));
            callBackListener.callBackReceived(contentModel);
        });
        holder.btnDeleteProductField.setTag(position);
        holder.btnDeleteProductField.setOnClickListener(v -> {
            int tag = (int) v.getTag();
            ContentModel contentModel = new ContentModel();
            contentModel.setStatus(Constants.TAG_DELETE);
            contentModel.setValue(listData.get(tag));
            callBackListener.callBackReceived(contentModel);
        });
        holder.btnMinusField.setTag(position);
        holder.btnMinusField.setOnClickListener(v -> {
            addtocart(dataObject, position,2);
        });
        holder.btnPlusField.setTag(position);
        holder.btnPlusField.setOnClickListener(v -> {
            addtocart(dataObject, position,1);
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
    public void addtocart(ProductInCartDetailsModel  productInCartDetailsModel, int position,int type){

        boolean perform= true;
        if(type==1) {
            productInCartDetailsModel.setTotalProductCartQty(productInCartDetailsModel.getTotalProductCartQty() + 1);
        }else{
            if(productInCartDetailsModel.getTotalProductCartQty()>1) {
                productInCartDetailsModel.setTotalProductCartQty(productInCartDetailsModel.getTotalProductCartQty() - 1);
            } else {
                perform =false;
            }
        }
        if(perform) {
            ProductsRepository.addToCart(context,vendorShoppingCartDetails.getVendorId(), MyProfile.getInstance(context).getUserID(), productInCartDetailsModel.getProductUnitId(), productInCartDetailsModel.getTotalProductCartQty(), "").observeForever(new Observer<AddToCartResponseDetails>() {
                @Override
                public void onChanged(AddToCartResponseDetails addToCartResponseDetails) {
                    if (addToCartResponseDetails.getStatus().equalsIgnoreCase("success")) {
                        AddToCartResponseDetails.AddToCartDataResponse addToCartDataResponse = addToCartResponseDetails.getAddToCartDataResponse();
                        if (addToCartDataResponse != null) {
                            Integer totalCartCount = addToCartDataResponse.getTotalCartCount();
                            UpdateCartCountDetails.updateCartCountDetails.onNext(totalCartCount);
                            notifyItemChanged(position);
                        } else {
                            if (type == 1) {
                                productInCartDetailsModel.setTotalProductCartQty(productInCartDetailsModel.getTotalProductCartQty() - 1);
                                notifyItemChanged(position);
                            } else {
                                productInCartDetailsModel.setTotalProductCartQty(productInCartDetailsModel.getTotalProductCartQty() + 1);
                                notifyItemChanged(position);
                            }
                        }
                        Toast.makeText(context, addToCartResponseDetails.getMsg(), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(context, addToCartResponseDetails.getMsg(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        Button btnMoveToWishListField;
        Button btnDeleteProductField;
        NetworkImageView ivProductImageField;
        TextView tvProductNameField;
        Button btnMinusField;
        Button btnPlusField;
        TextView tvNoOfQuantityField;
        TextView tvQuantityPriceDetailsField;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProductImageField = itemView.findViewById(R.id.iv_product_image_field);
            tvProductNameField = itemView.findViewById(R.id.tv_product_name_field);
            btnMinusField = itemView.findViewById(R.id.btn_minus_field);
            tvNoOfQuantityField = itemView.findViewById(R.id.tv_no_of_quantity_field);
            btnPlusField = itemView.findViewById(R.id.btn_add_field);
            btnMoveToWishListField = itemView.findViewById(R.id.btn_move_to_wish_list_field);
            btnDeleteProductField = itemView.findViewById(R.id.btn_delete_product_field);
            tvQuantityPriceDetailsField = itemView.findViewById(R.id.tv_quantity_price_details_field);
        }
    }
}
