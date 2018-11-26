package com.orion.darmawan.eclinic.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.orion.darmawan.eclinic.R;

import java.util.List;

public class VoucherAdapter extends RecyclerView.Adapter<VoucherAdapter.VoucherViewHolder> {
    //this context we will use to inflate the layout
    private Context mCtx;

    //we are storing all the products in a list
    private List<Voucher> voucherList;

    //getting the context and product list with constructor
    public VoucherAdapter(Context mCtx, List<Voucher> voucherList) {
        this.mCtx = mCtx;
        this.voucherList = voucherList;
    }

    @Override
    public VoucherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_voucher, null);
        return new VoucherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VoucherViewHolder holder, int position) {
        //getting the product of the specified position
        Voucher voucher = voucherList.get(position);

        //binding the data with the viewholder views
        holder.textViewQty.setText("Qty : "+String.valueOf(voucher.getQty()));
        holder.textViewId.setText("ID : "+voucher.getIdvoucher());
        holder.textViewType.setText("Tipe : "+voucher.getType());
        holder.textViewTitle.setText(voucher.getNamavoucher());
        holder.textViewValue.setText(String.valueOf(voucher.getNilai()));
        holder.textViewExpDate.setText(voucher.getExpdate());
    }


    @Override
    public int getItemCount() {
        return voucherList.size();
    }


    class VoucherViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewValue, textViewId, textViewQty, textViewType, textViewExpDate;

        public VoucherViewHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewValue = itemView.findViewById(R.id.textViewValue);
            textViewType = itemView.findViewById(R.id.textViewType);
            textViewId = itemView.findViewById(R.id.textViewID);
            textViewQty = itemView.findViewById(R.id.textViewQty);
            textViewExpDate = itemView.findViewById(R.id.textViewExpDate);
        }
    }
}
