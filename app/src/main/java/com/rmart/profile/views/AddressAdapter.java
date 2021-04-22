package com.rmart.profile.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rmart.R;
import com.rmart.profile.model.MyProfile;

public class AddressAdapter extends RecyclerView.Adapter<AddressViewHolder> {
    private View.OnClickListener listener;
    Context context;
    public AddressAdapter(Context context,View.OnClickListener listener) {
        this.listener = listener;
        this.context = context;
    }
    @NonNull
    @Override
    public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.address_list_item, parent, false);
        return new AddressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressViewHolder holder, int position) {
        /*if() {

        }*/
        StringBuffer address = new StringBuffer();
        address.append(MyProfile.getInstance(holder.itemView.getContext()).getAddressResponses().get(position).getAddress());
        address.append("\n");
        address.append(MyProfile.getInstance(holder.itemView.getContext()).getAddressResponses().get(position).getCity()).append(", ").append(MyProfile.getInstance(holder.itemView.getContext()).getAddressResponses().get(position).getState());
        address.append("\n");
        address.append(MyProfile.getInstance(holder.itemView.getContext()).getAddressResponses().get(position).getPinCode());
        holder.edit.setTag(position);
        holder.primary.setVisibility(View.GONE);
        holder.edit.setOnClickListener(listener);
        holder.address.setText(address);

        ;
    }

    @Override
    public int getItemCount() {
        return MyProfile.getInstance(context).getAddressResponses().size();
    }
}
