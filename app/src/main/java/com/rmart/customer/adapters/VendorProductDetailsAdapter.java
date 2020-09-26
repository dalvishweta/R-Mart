package com.rmart.customer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.rmart.R;
import com.rmart.baseclass.CallBackInterface;
import com.rmart.baseclass.Constants;
import com.rmart.customer.models.ContentModel;
import com.rmart.customer.models.CustomerProductDetailsModel;
import com.rmart.customer.models.ProductBaseModel;
import com.rmart.utilits.RecyclerTouchListener;
import com.rmart.utilits.custom_views.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Satya Seshu on 10/09/20.
 */
public class VendorProductDetailsAdapter extends RecyclerView.Adapter<VendorProductDetailsAdapter.ItemsViewHolder> implements Filterable {

    private LayoutInflater layoutInflater;
    private List<ProductBaseModel> vendorProductsList;
    private List<ProductBaseModel> filteredListData;
    private Context context;
    private MyFilter myFilter;
    private CallBackInterface callBackListener;

    public VendorProductDetailsAdapter(Context context, List<ProductBaseModel> vendorProductsList, CallBackInterface callBackListener) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.vendorProductsList = vendorProductsList;
        filteredListData = new ArrayList<>();
        this.filteredListData.addAll(vendorProductsList);
        this.callBackListener = callBackListener;
    }

    public void updateItems(List<ProductBaseModel> listData) {
        this.vendorProductsList = listData;
        this.filteredListData.clear();
        this.filteredListData.addAll(vendorProductsList);
    }

    @NonNull
    @Override
    public ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View lItemView = layoutInflater.inflate(R.layout.vendor_product_details_list_items, parent, false);
        return new ItemsViewHolder(lItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsViewHolder holder, int position) {
        ProductBaseModel dataObject = filteredListData.get(position);
        holder.tvProductNameField.setText(dataObject.getProductCategoryName());
        holder.btnViewAllField.setTag(position);
        List<CustomerProductDetailsModel> productsList = dataObject.getProductsList();
        if (productsList != null && !productsList.isEmpty()) {
            VendorProductTypesAdapter vendorProductTypesAdapter = new VendorProductTypesAdapter(context, dataObject.getProductsList());
            holder.productsTypesListField.setAdapter(vendorProductTypesAdapter);
            holder.productsTypesListField.addOnItemTouchListener(new RecyclerTouchListener(context, position, holder.productsTypesListField, new RecyclerTouchListener.ClickListener() {
                @Override
                public void onClick(View view, int position) {
                    callBackListener.callBackReceived(productsList.get(position));
                }

                @Override
                public void onLongClick(View view, int position) {

                }
            }));
        }
    }

    @Override
    public int getItemCount() {
        return filteredListData.size();
    }

    @Override
    public Filter getFilter() {
        if (myFilter == null) {
            myFilter = new MyFilter();
        }
        return myFilter;
    }

    private class MyFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                filteredListData.clear();
                for (ProductBaseModel productBaseModel : vendorProductsList) {
                    if (productBaseModel.getProductCategoryName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        filteredListData.add(productBaseModel);
                    }
                }
            }
            results.count = filteredListData.size();
            results.values = filteredListData;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredListData = (List<ProductBaseModel>) results.values;
            notifyDataSetChanged();
        }
    }

    public class ItemsViewHolder extends RecyclerView.ViewHolder {

        RecyclerView productsTypesListField;
        TextView tvProductNameField;
        Button btnViewAllField;

        public ItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductNameField = itemView.findViewById(R.id.tv_header_title_field);
            btnViewAllField = itemView.findViewById(R.id.btn_view_all_field);
            productsTypesListField = itemView.findViewById(R.id.products_types_list_field);
            productsTypesListField.setHasFixedSize(false);
            productsTypesListField.setLayoutManager(new GridLayoutManager(context, 2));
            productsTypesListField.addItemDecoration(new SpacesItemDecoration(20));

            btnViewAllField.setOnClickListener(v -> {
                int tag = (int) v.getTag();
                ProductBaseModel selectedProductCategoryDetails = filteredListData.get(tag);
                ContentModel contentModel = new ContentModel();
                contentModel.setStatus(Constants.TAG_VIEW_ALL);
                contentModel.setValue(selectedProductCategoryDetails);
                callBackListener.callBackReceived(contentModel);
            });
        }
    }
}
