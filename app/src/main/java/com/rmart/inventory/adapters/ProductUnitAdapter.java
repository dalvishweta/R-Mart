package com.rmart.inventory.adapters;

import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rmart.R;
import com.rmart.baseclass.CallBackInterface;
import com.rmart.baseclass.Constants;
import com.rmart.customer.models.ContentModel;
import com.rmart.inventory.models.UnitObject;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

public class ProductUnitAdapter extends RecyclerView.Adapter<ProductUnitAdapter.ProductUnitViewHolder> {

    private CallBackInterface callBackInterface;
    private ArrayList<UnitObject> productUnitList;
    private boolean canDelete;

    public ProductUnitAdapter(ArrayList<UnitObject> unitObjects, CallBackInterface callBackInterface, boolean canDelete) {
        this.callBackInterface = callBackInterface;
        this.productUnitList = unitObjects;
        this.canDelete = canDelete;
    }

    @NonNull
    @Override
    public ProductUnitAdapter.ProductUnitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem;
        listItem = layoutInflater.inflate(R.layout.product_unit_base, parent, false);
        ProductUnitViewHolder productUnitViewHolder = new ProductUnitViewHolder(listItem);
        productUnitViewHolder.delete.setVisibility(canDelete ? View.VISIBLE : View.GONE);
        return productUnitViewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull ProductUnitViewHolder holder, int position) {
        UnitObject unit = productUnitList.get(position);
        holder.delete.setTag(unit);
        String html = "<strike>" + unit.getActualCost() + "</strike>";
        holder.tvActual.setText(Html.fromHtml(html));
        holder.tvFinalCost.setText(unit.getFinalCost());
        holder.tvUnitValue.setText(unit.getUnitNumber()+unit.getDisplayUnitValue());
        holder.tvOffer.setText(String.format(holder.itemView.getContext().getString(R.string.offer_single_line), unit.getDiscount() + "%"));
        /*
            holder.tvIUnitState.setText(unit.getProductStatus());
            if(unit.getProductStatus().equalsIgnoreCase("Available")) {

            }
        */
        holder.delete.setTag(position);
        if (unit.getStockID().equalsIgnoreCase("5")) {
            holder.tvIUnitState.setTextColor(holder.itemView.getContext().getColor(R.color.colorPrimary));
        } else {
            holder.tvIUnitState.setTextColor(holder.itemView.getContext().getColor(R.color.gray));
        }
        holder.tvIUnitState.setText(unit.getShortName());
    }

    @Override
    public int getItemCount() {
        return productUnitList.size();
    }

    public class ProductUnitViewHolder extends RecyclerView.ViewHolder {
        public AppCompatTextView tvIUnitState, tvUnitValue, tvFinalCost, tvActual, tvOffer;
        public AppCompatImageButton delete;

        public ProductUnitViewHolder(View listItem) {
            super(listItem);
            tvUnitValue = listItem.findViewById(R.id.sub_title_1);
            tvFinalCost = listItem.findViewById(R.id.sub_title_2);
            tvActual = listItem.findViewById(R.id.sub_title_3);
            tvOffer = listItem.findViewById(R.id.offer);
            tvIUnitState = listItem.findViewById(R.id.unit_status);
            delete = listItem.findViewById(R.id.delete);
            delete.setOnClickListener(v -> {
                int tag = (int) v.getTag();
                ContentModel contentModel = new ContentModel();
                contentModel.setStatus(Constants.TAG_DELETE);
                contentModel.setValue(productUnitList.get(tag));
                callBackInterface.callBackReceived(contentModel);
            });
        }
    }
}
