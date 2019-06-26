package com.example.giaodien;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>{

    private List<String> ngay = new ArrayList<>();
    private List<String> gio = new ArrayList<>();
    private List<String> maynghe = new ArrayList<>();
    private List<String> giay = new ArrayList<>();
    private List<String> tenvung = new ArrayList<>();
    private List<String> tien = new ArrayList<>();

    public RecyclerViewAdapter(List<String> ngay,List<String> gio,List<String> maynghe,List<String> giay,List<String> tenvung,List<String> tien) {
        this.ngay = ngay;
        this.gio = gio;
        this.maynghe = maynghe;
        this.giay = giay;
        this.tenvung = tenvung;
        this.tien = tien;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.ngay.setText(ngay.get(position));
        holder.gio.setText(gio.get(position));
        holder.maynghe.setText(maynghe.get(position));
        holder.giay.setText(giay.get(position));
        holder.tenvung.setText(tenvung.get(position));
        holder.tien.setText(tien.get(position));
    }

    @Override
    public int getItemCount() {
        return ngay.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView ngay,gio,maynghe,giay,tenvung,tien;
        public RecyclerViewHolder(View itemView) {
            super(itemView);
            ngay = (TextView) itemView.findViewById(R.id.ngay);
            gio = (TextView) itemView.findViewById(R.id.gio);
            maynghe = (TextView) itemView.findViewById(R.id.maynghe);
            giay = (TextView) itemView.findViewById(R.id.giay);
            tenvung = (TextView) itemView.findViewById(R.id.tenvung);
            tien = (TextView) itemView.findViewById(R.id.tien);
        }
    }
}
