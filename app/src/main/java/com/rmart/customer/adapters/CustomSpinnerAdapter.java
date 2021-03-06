package com.rmart.customer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.rmart.R;
import com.rmart.customer.models.CustomerProductsDetailsUnitModel;
import com.rmart.utilits.pojos.ProfileResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Satya Seshu on 12/09/20.
 */
public class CustomSpinnerAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private List<Object> listData;
    private boolean changeTextColor = false;
    private int whiteColor;
    private int blackColor;
    private boolean type;
    public CustomSpinnerAdapter(Context context, List<Object> listData,boolean type) {
        layoutInflater = LayoutInflater.from(context);
        this.listData = listData;
        whiteColor = ContextCompat.getColor(context, R.color.white);
        blackColor = ContextCompat.getColor(context, R.color.black);
        this.type=type;
    }

    public void changeTextColor() {
        changeTextColor = true;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            if(!type) {
                convertView = layoutInflater.inflate(R.layout.custom_text_view, viewGroup, false);
            } else {
                convertView = layoutInflater.inflate(R.layout.layout_spinerr_item_row, viewGroup, false);
            }
            holder.tvTextField = convertView.findViewById(R.id.tv_custom_text_field);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if(changeTextColor) {
            holder.tvTextField.setTextColor(whiteColor);
        } else {
            holder.tvTextField.setTextColor(blackColor);
        }
        Object dataObject = listData.get(position);
        if (dataObject instanceof CustomerProductsDetailsUnitModel) {
            CustomerProductsDetailsUnitModel unitDetails = (CustomerProductsDetailsUnitModel) dataObject;
            String unitMeasureDetails = String.format("%s %s", unitDetails.getUnitNumber(), unitDetails.getShortUnitMeasure());
            holder.tvTextField.setText(unitMeasureDetails);
        } else if (dataObject instanceof String) {
            holder.tvTextField.setText((String) dataObject);
        } else if (dataObject instanceof ProfileResponse) {
            holder.tvTextField.setText((String) ((ProfileResponse) dataObject).getFirstName()+" "+(String) ((ProfileResponse) dataObject).getLastName());
        }
        return convertView;
    }

    static class ViewHolder {
        TextView tvTextField;
    }
}
