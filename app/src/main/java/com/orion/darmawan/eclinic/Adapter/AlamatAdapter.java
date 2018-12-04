package com.orion.darmawan.eclinic.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.orion.darmawan.eclinic.R;

import java.util.List;

public class AlamatAdapter extends RecyclerView.Adapter<AlamatAdapter.AlamatViewHolder>  {

    private List<Alamat> alamatList;
    private Context mCtx;

    public AlamatAdapter(Context mCtx,List<Alamat> alamatList) {
        this.alamatList = alamatList;
        this.mCtx = mCtx;
    }

    @Override
    public AlamatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_alamat,  null);
        return new AlamatViewHolder(view);
    }


    @Override
    public void onBindViewHolder(AlamatViewHolder holder, int position) {
        //getting the product of the specified position
        final Alamat almt = alamatList.get(position);
        holder.txtAlamat.setText(almt.alamat);
        holder.txtKota.setText(almt.kota);
        holder.txtKodePos.setText(almt.kode_pos);
        holder.txtLabel.setText(almt.label_alamat);
        holder.txtPenerima.setText(almt.penerima);
        holder.txtTelp.setText(almt.no_telp);
    }

    @Override
    public int getItemCount() {
        return alamatList.size();
    }

    class AlamatViewHolder extends RecyclerView.ViewHolder {
        TextView txtAlamat,txtKodePos,txtPenerima,txtTelp,txtKota,txtLabel;
        LinearLayout parentLayout;
        TextView dflBtn,ubahBtn,hapusBtn;

        public AlamatViewHolder(View itemView) {
            super(itemView);
            txtPenerima = itemView.findViewById(R.id.textViewPenerima);
            txtLabel = itemView.findViewById(R.id.textViewLabel);
            txtAlamat = itemView.findViewById(R.id.textViewAlamat);
            txtKota = itemView.findViewById(R.id.textViewKota);
            txtKodePos = itemView.findViewById(R.id.textViewKodePos);
            txtTelp = itemView.findViewById(R.id.textViewTelp);

            dflBtn = itemView.findViewById(R.id.btnDefault);
            ubahBtn = itemView.findViewById(R.id.btnUbah);
            hapusBtn = itemView.findViewById(R.id.btnHapus);
        }
    }
}
