package com.rmart.wallet.billing_history.adapter;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rmart.BR;
import com.rmart.R;
import com.rmart.databinding.BillingHistoryLayoutBinding;
import com.rmart.wallet.billing_history.models.Datum;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class BillHistoryAdapter extends RecyclerView.Adapter<BillHistoryAdapter.BillingViewHolder> {
    ArrayList<Datum> datum;
    Context context;

    public BillHistoryAdapter(Context context, ArrayList<Datum> datum) {
        this.datum = datum;
        this.context = context;
    }

    @NonNull
    @Override
    public BillingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BillingHistoryLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.billing_history_layout, parent, false);
        BillingViewHolder vh = new BillingViewHolder(binding); // pass the view to View Holder
        return vh;

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull BillingViewHolder holder, int position) {
        if (holder instanceof BillingViewHolder) {
            BillingViewHolder itemViewHolder = (BillingViewHolder) holder;
            final Datum bill_details = datum.get(position);
            itemViewHolder.bind(bill_details);
            String[] date = bill_details.getCreatedDate().split(" ");
            itemViewHolder.binding.dateTextView.setText(date[0]);
            if (bill_details.getWalletStatus().equalsIgnoreCase("DB")){
                itemViewHolder.binding.debitTextView.setText(bill_details.getAmt());
                itemViewHolder.binding.creditTextView.setText("-");
            }
            else {
                itemViewHolder.binding.creditTextView.setText(bill_details.getAmt());
                itemViewHolder.binding.debitTextView.setText("-");
            }

            itemViewHolder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            holder.binding.detailsTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("GETDATA",bill_details.getCreatedDate());
                    Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.bill_history_dialog);
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.setCancelable(false);
                    TextView date = dialog.findViewById(R.id.date_text);
                    TextView service = dialog.findViewById(R.id.service_text);
                    TextView debit = dialog.findViewById(R.id.debit_text);
                    TextView credit = dialog.findViewById(R.id.credit_text);
                    TextView balance = dialog.findViewById(R.id.balance_text);
                    TextView ok = dialog.findViewById(R.id.ok_textView);
                    String[] date1 = bill_details.getCreatedDate().split(" ");
                    date.setText(date1[0]);
                    service.setText(bill_details.getServiceName());
                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                    if (bill_details.getWalletStatus().equalsIgnoreCase("DB")){
                        debit.setText(bill_details.getAmt());
                        credit.setText("-");
                    }
                    else {
                        credit.setText(bill_details.getAmt());
                        debit.setText("-");
                    }
                    balance.setText(bill_details.getAmtAfterTrans());
                    dialog.show();
                }
            });

        }
        }


    @Override
    public int getItemCount() {
        return datum != null ? datum.size() : 0;
    }


    public class BillingViewHolder extends RecyclerView.ViewHolder {
        BillingHistoryLayoutBinding binding;

        public BillingViewHolder(BillingHistoryLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Object obj) {
          binding.setVariable(BR.BillingHistory,obj);
          binding.executePendingBindings();
        }
    }
}
