package com.rmart.customerservice.mobile.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.rmart.R;
import com.rmart.customerservice.mobile.models.ContactDisply;

import java.util.ArrayList;
import java.util.List;

public class AllContactsAdapter extends RecyclerView.Adapter<AllContactsAdapter.ViewHolder>{

    private LayoutInflater layoutInflater;
    public List<ContactDisply> cont;
    ContactDisply list;
    private ArrayList<ContactDisply> arraylist;
    boolean checked = false;
    View vv;


    public AllContactsAdapter(LayoutInflater inflater, List<ContactDisply> items) {
        this.layoutInflater = inflater;
        this.cont = items;
        this.arraylist = new ArrayList<ContactDisply>();
        this.arraylist.addAll(cont);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = layoutInflater.inflate(R.layout.single_contact_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        list = cont.get(position);
        String name = (list.getContactName());

        holder.title.setText(name);
        holder.phone.setText(list.getContactNumber());

    }

    @Override
    public int getItemCount() {
        return cont.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView phone;
        public LinearLayout contact_select_layout;

        public ViewHolder(View itemView) {
            super(itemView);
            this.setIsRecyclable(false);
            title = (TextView) itemView.findViewById(R.id.name);
            phone = (TextView) itemView.findViewById(R.id.no);
            contact_select_layout = (LinearLayout) itemView.findViewById(R.id.contact_select_layout);

        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

}