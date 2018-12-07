package com.orion.darmawan.eclinic.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.orion.darmawan.eclinic.AtmActivity;
import com.orion.darmawan.eclinic.R;

import java.util.List;

public class AtmAdapter  extends RecyclerView.Adapter<AtmAdapter.AtmViewHolder>{

    private Context mCtx;
    private List<Atm> atmList;

    public AtmAdapter(Context mCtx, List<Atm> atmList) {
        this.mCtx = mCtx;
        this.atmList = atmList;
    }

    @Override
    public AtmViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.rekening_list,  null);
        return new AtmViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AtmViewHolder holder, int position) {
        final Atm atm = atmList.get(position);
        holder.txtName.setText(atm.name);
        holder.txtBank.setText(atm.bank);
        holder.txtRekening.setText(atm.no_rek);
    }

    @Override
    public int getItemCount() {
        return atmList.size();
    }

    public class AtmViewHolder extends RecyclerView.ViewHolder {
         TextView txtName,txtBank,txtRekening,btnUbah,btnHapus;

        public AtmViewHolder(View itemView){
            super(itemView);
            txtName = itemView.findViewById(R.id.textViewNama);
            txtBank = itemView.findViewById(R.id.textViewBank);
            txtRekening = itemView.findViewById(R.id.textViewRekening);
            btnUbah = itemView.findViewById(R.id.btnUbah);
            btnHapus = itemView.findViewById(R.id.btnHapus);
        }
    }
}
