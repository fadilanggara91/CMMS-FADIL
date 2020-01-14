package com.msadev.cmms.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.msadev.cmms.Model.MesinModel;
import com.msadev.cmms.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MesinAdapter extends ArrayAdapter<MesinModel> {

    List<MesinModel> mesinModelList;
    ArrayList<MesinModel> arrayList;
    Context context;

    public MesinAdapter(List<MesinModel> mesinModelList, Context context){
        super(context, R.layout.design_l__mesin, mesinModelList);
        this.context = context;
        this.mesinModelList = mesinModelList;
        this.arrayList = new ArrayList<MesinModel>();
        this.arrayList.addAll(mesinModelList);
    }

    @Override
    public View getView(final  int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = LayoutInflater.from(context);
        View listData = inflater.inflate(R.layout.design_l__mesin, null, true);
        MesinModel mm = mesinModelList.get(position);

        TextView tvnomesin = listData.findViewById(R.id.nomesin);
        TextView tvsite = listData.findViewById(R.id.site);
        TextView tvketerangan = listData.findViewById(R.id.keterangan);

        tvnomesin.setText(mm.getNomesin());
        tvsite.setText(mm.getSite());
        tvketerangan.setText(mm.getKeterangan());

        return listData;
    }

    public void filter(String filters){
        filters = filters.toLowerCase(Locale.getDefault());
        mesinModelList.clear();
        if (filters.length()==0){
            mesinModelList.addAll(arrayList);
        }else {
            for (MesinModel mm : arrayList){
                if (mm.getAll().toLowerCase(Locale.getDefault()).contains(filters)){
                    mesinModelList.add(mm);
                }
            }
        }
        notifyDataSetChanged();
    }

}
