package com.rmart.customerservice.mobile.views;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.rmart.R;
import com.rmart.customerservice.mobile.adapters.AllContactsAdapter;
import com.rmart.customerservice.mobile.models.ContactDisply;

import java.util.ArrayList;

public class Activity_SelectNumber extends AppCompatActivity {

    RecyclerView rvContacts, recent_,recent_recharge;
    private Handler mHandler;
    Cursor phones;
    ArrayList<ContactDisply> selectUsers;
    private RecyclerView.LayoutManager layoutManager;

    ProgressBar simpleProgressBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_recharge_home_new);
        mHandler = new Handler();
        selectUsers = new ArrayList<ContactDisply>();
         simpleProgressBar=(ProgressBar)findViewById(R.id.simpleProgressBar);
        rvContacts = (RecyclerView) findViewById(R.id.select_contact);
        rvContacts.setHasFixedSize(true);

        rvContacts.setLayoutManager(layoutManager);
        recent_recharge = (RecyclerView) findViewById(R.id.recent_recharge);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, 1);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {
          showAllContacts();
        }


        }

    class LoadContact extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        simpleProgressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected Void doInBackground(Void... voids) {
            // Get Contact list from Phone

            if (phones != null) {
                Log.e("count", "" + phones.getCount());
                if (phones.getCount() == 0) {

                }

                while (phones.moveToNext()) {
                    Bitmap bit_thumb = null;
                    String id = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
                    String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));


                    ContactDisply selectUser = new ContactDisply();
                    selectUser.setContactName(name);
                    selectUser.setContactNumber(phoneNumber);
                    selectUsers.add(selectUser);


                }
            } else {
                Log.e("Cursor close 1", "----------------");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            simpleProgressBar.setVisibility(View.GONE);
            LayoutInflater inflater;
            inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // sortContacts();
            int count=selectUsers.size();
            ArrayList<ContactDisply> removed=new ArrayList<>();
            ArrayList<ContactDisply> contacts=new ArrayList<>();
            for(int i=0;i<selectUsers.size();i++){
                ContactDisply inviteFriendsProjo = selectUsers.get(i);

                if(inviteFriendsProjo.getContactName().matches("\\d+(?:\\.\\d+)?")||inviteFriendsProjo.getContactName().trim().length()==0){
                    removed.add(inviteFriendsProjo);
                    Log.d("Removed Contact",new Gson().toJson(inviteFriendsProjo));
                }else{
                    contacts.add(inviteFriendsProjo);
                }
            }
            contacts.addAll(removed);
            selectUsers=contacts;
            AllContactsAdapter   adapter = new AllContactsAdapter(inflater, selectUsers);
            rvContacts.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            rvContacts.setAdapter(adapter);

        }
    }




    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                showAllContacts();
            } else {
                Toast.makeText(this, "Until you grant the permission, we canot display the names", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showAllContacts() {

        phones = getApplicationContext().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        LoadContact loadContact = new LoadContact();
        loadContact.execute();
    }
}


