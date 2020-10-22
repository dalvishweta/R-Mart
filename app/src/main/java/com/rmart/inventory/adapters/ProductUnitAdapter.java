package com.rmart.inventory.adapters;

import android.graphics.Paint;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.rmart.R;
import com.rmart.baseclass.CallBackInterface;
import com.rmart.baseclass.Constants;
import com.rmart.customer.models.ContentModel;
import com.rmart.inventory.models.UnitObject;

import java.util.ArrayList;

public class ProductUnitAdapter extends RecyclerView.Adapter<ProductUnitAdapter.ProductUnitViewHolder> {

    private CallBackInterface callBackInterface;
    private ArrayList<UnitObject> productUnitList;
    private boolean canDelete;

    public ProductUnitAdapter(ArrayList<UnitObject> unitObjects, CallBackInterface callBackInterface, boolean canDelete) {
        this.callBackInterface = callBackInterface;
        this.productUnitList = unitObjects;
        this.canDelete = canDelete;
    }

    public void updateItems(ArrayList<UnitObject> unitObjects) {
        this.productUnitList = unitObjects;
    }

    @NonNull
    @Override
    public ProductUnitAdapter.ProductUnitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem;
        listItem = layoutInflater.inflate(R.layout.product_unit_base, parent, false);
        ProductUnitViewHolder productUnitViewHolder = new ProductUnitViewHolder(listItem);
        productUnitViewHolder.edit.setVisibility(canDelete ? View.VISIBLE : View.GONE);
        return productUnitViewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull ProductUnitViewHolder holder, int position) {
        UnitObject unit = productUnitList.get(position);
        String finalCost = String.format("%s Rs", unit.getFinalCost());
        holder.tvFinalCost.setText(finalCost);
        String unitValue = String.format("%s %s", unit.getUnitNumber(), unit.getDisplayUnitValue());
        holder.tvUnitValue.setText(unitValue);
        holder.tvIUnitState.setText(unit.getStockName());

        String actualCost = String.format("%s Rs", unit.getActualCost());
        holder.tvActual.setText(actualCost);
        holder.tvActual.setPaintFlags(holder.tvActual.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.tvOffer.setText(String.format(holder.itemView.getContext().getString(R.string.offer_single_line), unit.getDiscount() + "%"));

        holder.edit.setTag(position);
        if (unit.getStockID().equalsIgnoreCase("5")) {
            holder.tvIUnitState.setTextColor(holder.itemView.getContext().getColor(R.color.colorPrimary));
        } else {
            holder.tvIUnitState.setTextColor(holder.itemView.getContext().getColor(R.color.gray));
        }
    }

    @Override
    public int getItemCount() {
        return productUnitList.size();
    }

    public class ProductUnitViewHolder extends RecyclerView.ViewHolder {
        public AppCompatTextView tvIUnitState, tvUnitValue, tvFinalCost, tvActual, tvOffer;
        public AppCompatImageButton edit;

        public ProductUnitViewHolder(View listItem) {
            super(listItem);
            tvUnitValue = listItem.findViewById(R.id.sub_title_1);
            tvFinalCost = listItem.findViewById(R.id.sub_title_2);
            tvActual = listItem.findViewById(R.id.sub_title_3);
            tvOffer = listItem.findViewById(R.id.offer);
            tvIUnitState = listItem.findViewById(R.id.unit_status);
            edit = listItem.findViewById(R.id.unit_edit);
            edit.setOnClickListener(v -> {
                int tag = (int) v.getTag();
                ContentModel contentModel = new ContentModel();
                contentModel.setStatus(Constants.TAG_EDIT_UNIT);
                contentModel.setValue(productUnitList.get(tag));
                callBackInterface.callBackReceived(contentModel);
            });
        }
    }
}
