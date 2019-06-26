package com.example.giaodien;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter2 extends RecyclerView.Adapter<RecyclerViewAdapter2.RecyclerViewHolder>{

    private List<String> danhthu = new ArrayList<>();
    private List<String> thue = new ArrayList<>();
    private List<String> stb = new ArrayList<>();

    public RecyclerViewAdapter2(List<String> danhthu,List<String> thue,List<String> stb) {
        this.danhthu = danhthu;
        this.thue = thue;
        this.stb = stb;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item2, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.danhthu.setText(danhthu.get(position));
        holder.thue.setText(thue.get(position));
        holder.stb.setText("Số thuê bao: "+stb.get(position));
    }

    @Override
    public int getItemCount() {
        return danhthu.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView danhthu, thue, stb;
        public RecyclerViewHolder(View itemView) {
            super(itemView);
            danhthu = (TextView) itemView.findViewById(R.id.danhthu);
            thue = (TextView) itemView.findViewById(R.id.thue);
            stb = (TextView) itemView.findViewById(R.id.stb);
        }
    }
}
