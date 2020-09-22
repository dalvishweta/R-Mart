package com.rmart.profile.views;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.rmart.R;

public class AddressViewHolder extends RecyclerView.ViewHolder {
    AppCompatTextView primary, address;
    ImageView edit;
    public AddressViewHolder(@NonNull View itemView) {
        super(itemView);
        primary = itemView.findViewById(R.id.is_primary);
        address = itemView.findViewById(R.id.customer_address);
        edit = itemView.findViewById(R.id.edit);
    }
}
