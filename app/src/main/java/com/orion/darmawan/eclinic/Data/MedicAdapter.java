package com.orion.darmawan.eclinic.Data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.orion.darmawan.eclinic.Model.MedicRecord;
import com.orion.darmawan.eclinic.R;

import java.util.List;

/**
 * Created by Belal on 9/5/2017.
 */

public class MedicAdapter extends ArrayAdapter<MedicRecord> {

    //the hero list that will be displayed
    private List<MedicRecord> MedicList;

    //the context object
    private Context mCtx;

    //here we are getting the herolist and context
    //so while creating the object of this adapter class we need to give herolist and context
    public MedicAdapter(List<MedicRecord> MedicList, Context mCtx) {
        super(mCtx, R.layout.list_medic, MedicList);
        this.MedicList = MedicList;
        this.mCtx = mCtx;
    }

    //this method will return the list item
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //getting the layoutinflater
        LayoutInflater inflater = LayoutInflater.from(mCtx);

        //creating a view with our xml layout
        View listViewItem = inflater.inflate(R.layout.list_medic, null, true);

        //getting text views
        TextView textNoRm = listViewItem.findViewById(R.id.noRm);
        TextView textTglRm = listViewItem.findViewById(R.id.tglRm);

        //Getting the hero for the specified position
        MedicRecord medic  = MedicList.get(position);

        //setting hero values to textviews
        textNoRm.setText(medic.getNo());
        textTglRm.setText(medic.getTgl());

        //returning the listitem
        return listViewItem;
    }
}
