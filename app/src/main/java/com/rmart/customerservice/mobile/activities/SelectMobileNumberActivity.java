package com.rmart.customerservice.mobile.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.rmart.R;
import com.rmart.customerservice.mobile.adapters.PhoneNumberAdapter;
import com.rmart.customerservice.mobile.listners.OnContactSelectContactListner;
import com.rmart.databinding.ActivitySelectMobileNumberBinding;
import com.rmart.utilits.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SelectMobileNumberActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private String searchString = "";
    // Defines the array to hold values that replace the ?
    private String[] selectionArgs = { searchString };
    ActivitySelectMobileNumberBinding activitySelectMobileNumberBinding;
    private PhoneNumberAdapter cursorAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySelectMobileNumberBinding = DataBindingUtil.setContentView(this, R.layout.activity_select_mobile_number);
        activitySelectMobileNumberBinding.simpleSearchView.requestFocus();
        cursorAdapter = new PhoneNumberAdapter(this, (name, number) -> {
            Intent intent=new Intent();
            intent.putExtra("name",name);
            intent.putExtra("number",number);
            setResult(2,intent);
            finish();//finishing activity
        });
        activitySelectMobileNumberBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        // Sets the adapter for the ListView
        activitySelectMobileNumberBinding.contactListView.setAdapter(cursorAdapter);
        LoaderManager.getInstance(this).initLoader(0, null, this);
        activitySelectMobileNumberBinding.simpleSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchString = s;
                LoaderManager.getInstance(SelectMobileNumberActivity.this).restartLoader(0,null,SelectMobileNumberActivity.this);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
               searchString = s;
               LoaderManager.getInstance(SelectMobileNumberActivity.this).restartLoader(0,null,SelectMobileNumberActivity.this);
               return false;
            }
        });
        activitySelectMobileNumberBinding.newnumberlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(searchString) || Utils.isValidMobile(searchString)) {
                    Intent intent = new Intent();
                    intent.putExtra("number", searchString);
                    setResult(2, intent);
                    finish();//finishing activity
                } else {
                    Toast.makeText(SelectMobileNumberActivity.this,"Invalid Mobile Number",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id,@Nullable Bundle args) {
        String[] fieldListProjection = {
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.NORMALIZED_NUMBER,
                ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI

        };
        selectionArgs = new String[]{"%" + searchString + "%","%" + searchString + "%"};
        String i =null;
        if (TextUtils.isEmpty(searchString)) {
             i =  null;
            selectionArgs = null;
        }
        else {
             i =  ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+ " LIKE ? OR "+ContactsContract.CommonDataKinds.Phone.NUMBER+ " LIKE ?";
        }
        // Starts the query
        return new CursorLoader(
               this,
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                fieldListProjection,
                i,
                selectionArgs,
                null
        );
    }
    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader,
                               Cursor cursor) {
        cursorAdapter.swapCursor(cursor);
        if(cursor.getCount()==0 && searchString.length()>0){
                boolean isnumeric= true;
                try {
                    Long.parseLong(searchString);
                    isnumeric= false;
                } catch (Exception e){
                    isnumeric= true;

                }
            if ( isnumeric ) {
                activitySelectMobileNumberBinding.name.setText("Invalid Number");
            } else {

                activitySelectMobileNumberBinding.name.setText("New Number");
            }
            activitySelectMobileNumberBinding.newnumberlayout.setVisibility(View.VISIBLE);
            activitySelectMobileNumberBinding.number.setText(searchString);
        } else{
            activitySelectMobileNumberBinding.newnumberlayout.setVisibility(View.GONE);

        }
    }
    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        cursorAdapter.swapCursor(null);
    }
}





