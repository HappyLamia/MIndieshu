package com.orion.darmawan.eclinic.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.orion.darmawan.eclinic.AlamatActivity;
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

        String status = almt.def;
        final String alamat = almt.alamat;
        final String kota = almt.kota;
        final String kodepos = almt.kode_pos;
        final String label = almt.label_alamat;
        final String penerima = almt.penerima;
        final String telp = almt.no_telp;

        holder.txtAlamat.setText(almt.alamat);
        holder.txtKota.setText(almt.kota);
        holder.txtKodePos.setText(almt.kode_pos);
        holder.txtLabel.setText(almt.label_alamat);
        holder.txtPenerima.setText(almt.penerima);
        holder.txtTelp.setText(almt.no_telp);
        if (status.equals("TRUE")){
            holder.dflBtn.setVisibility(View.GONE);
        }
        else{
            holder.dflBtn.setVisibility(View.VISIBLE);
        }
        holder.dflBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Key"+almt.getKeyId(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        holder.hapusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Key"+almt.getKeyId(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return alamatList.size();
    }

    class AlamatViewHolder extends RecyclerView.ViewHolder {
        TextView txtAlamat,txtKodePos,txtPenerima,txtTelp,txtKota,txtLabel;
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
