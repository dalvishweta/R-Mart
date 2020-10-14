package com.rmart.inventory.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.appcompat.widget.AppCompatTextView;

import com.rmart.R;

import java.util.ArrayList;

public class CustomStringAdapter extends BaseAdapter {
    private ArrayList<String> units;
    private LayoutInflater inflater;

    public CustomStringAdapter(ArrayList<String> units, Context context) {
        this.units = units;
        inflater = (LayoutInflater.from(context));
    }
    @Override
    public int getCount() {
        return units.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.unit_item_row, viewGroup, false);//set layout for displaying items
        view.findViewById(R.id.text);//get id for image view
        ((AppCompatTextView)view).setText(units.get(i));
        return view;
    }
}
