package com.rmart.customer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rmart.R;
import com.rmart.baseclass.CallBackInterface;
import com.rmart.utilits.pojos.AddressResponse;

import java.util.List;

/**
 * Created by Satya Seshu on 07/09/20.
 */
public class ChangeAddressAdapter extends RecyclerView.Adapter<ChangeAddressAdapter.ViewHolder> {

    private List<AddressResponse> listData;
    private LayoutInflater layoutInflater;
    private CallBackInterface callBackListener;

    public ChangeAddressAdapter(Context context, List<AddressResponse> listData, CallBackInterface callBackListener) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(context);
        this.callBackListener = callBackListener;
    }

    public void updateItems(List<AddressResponse> listData) {
        this.listData = listData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.change_address_list_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AddressResponse addressResponse = listData.get(position);
        holder.tvAddressField.setText(new StringBuilder().append(addressResponse.getAddress()).append(addressResponse.getCity()).
                append(addressResponse.getState()).append(addressResponse.getPinCode()));

        holder.ivAddressCheckableField.setImageResource(addressResponse.getIsActive() == 1 ? R.drawable.ic_checked : R.drawable.ic_un_checked);
        holder.ivAddressCheckableField.setTag(position);
        holder.ivAddressCheckableField.setOnClickListener(v -> {
            int tag = (int) v.getTag();
            AddressResponse selectedAddress = listData.get(tag);
            if (selectedAddress.getIsActive() == 0) {
                callBackListener.callBackReceived(selectedAddress);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivAddressCheckableField;
        TextView tvAddressField;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivAddressCheckableField = itemView.findViewById(R.id.iv_address_checkable_field);
            tvAddressField = itemView.findViewById(R.id.tv_address_field);
        }
    }
}
