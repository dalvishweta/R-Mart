package com.rmart.customerservice.mobile.adapters;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.rmart.R;
import com.rmart.customerservice.mobile.listners.OnContactSelectContactListner;
import com.rmart.databinding.ContactListItemBinding;
import com.rmart.utilits.Utils;

import java.io.IOException;
import java.util.regex.Pattern;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class PhoneNumberAdapter extends BaseCursorAdapter<PhoneNumberAdapter.FriendViewHolder> {
    Context context;
    OnContactSelectContactListner onContactSelectContactListner;
    public PhoneNumberAdapter(Context context,OnContactSelectContactListner onContactSelectContactListner) {
        super(null);
        this.context=context;
        this.onContactSelectContactListner=onContactSelectContactListner;
    }
    @Override
    public FriendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ContactListItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.contact_list_item, parent, false);
        FriendViewHolder vh = new FriendViewHolder(binding);
        return vh;
    }


    @Override
    public void onBindViewHolder(FriendViewHolder holder, Cursor cursor) {
        int mColumnIndexName = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
        int mColumnIndexNumber = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
        String contactName = cursor.getString(mColumnIndexName);
        String contactNumber = cursor.getString(mColumnIndexNumber);
        holder.contactListItemBinding.name.setText(contactName);
        holder.contactListItemBinding.number.setText(contactNumber);
        if(0 == (int)holder.itemView.getTag()) {
            holder.contactListItemBinding.first.setVisibility(View.VISIBLE);
        } else {
            holder.contactListItemBinding.first.setVisibility(View.GONE);

        }
        int size=cursor.getCount()-1;
        int currunt = ((int)holder.itemView.getTag());
        if(size== currunt){
            holder.contactListItemBinding.bottom.setVisibility(View.VISIBLE);

        } else {
            holder.contactListItemBinding.bottom.setVisibility(View.GONE);

        }
        holder.contactListItemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Utils.isValidMobile(contactNumber)) {
                    onContactSelectContactListner.onSelect(contactName, contactNumber);
                } else {
                    if(contactNumber!=null && contactNumber.length()>10){
                        String contactNumber2=   phoeNumberWithOutCountryCode(contactNumber);
                        if(Utils.isValidMobile(contactNumber2)) {
                            onContactSelectContactListner.onSelect(contactName, contactNumber2);
                        } else {
                            Toast.makeText(context,"Please Select Valid Mobile Number",Toast.LENGTH_LONG).show();
                        }
                    }
                    else {
                        Toast.makeText(context,"Please Select Valid Mobile Number",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }
    public String phoeNumberWithOutCountryCode(String phoneNumber) {
        phoneNumber =  phoneNumber.replace(" ","");
        int i = phoneNumber.length()-10;
        String str_getMOBILE = phoneNumber.substring(i);
        return str_getMOBILE;
    }
    @Override
    public void swapCursor(Cursor newCursor) {
        super.swapCursor(newCursor);
    }
    class FriendViewHolder extends RecyclerView.ViewHolder {
       public ContactListItemBinding contactListItemBinding ;
        FriendViewHolder(ContactListItemBinding contactListItemBinding) {
            super(contactListItemBinding.getRoot());
            this.contactListItemBinding=contactListItemBinding;
        }
    }
}