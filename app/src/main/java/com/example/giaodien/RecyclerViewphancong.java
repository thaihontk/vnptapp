package com.example.giaodien;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewphancong extends RecyclerView.Adapter<RecyclerViewphancong.RecyclerViewHolder>{

    private List<String> mact = new ArrayList<>();
    private List<String> chitieubsc = new ArrayList<>();
    private List<String> chitieu = new ArrayList<>();
    private List<String> dathuchien = new ArrayList<>();

    public RecyclerViewphancong(List<String> mact,List<String> chitieubsc,List<String> chitieu,List<String> dathuchien) {
        this.mact = mact;
        this.chitieubsc = chitieubsc;
        this.chitieu = chitieu;
        this.dathuchien = dathuchien;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.itemphancong, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.mact.setText(mact.get(position));
        holder.chitieubsc.setText(chitieubsc.get(position));
        holder.chitieu.setText(chitieu.get(position));
        holder.dathuchien.setText(dathuchien.get(position));
    }

    @Override
    public int getItemCount() {
        return mact.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView mact,chitieubsc,chitieu,dathuchien;
        public RecyclerViewHolder(View itemView) {
            super(itemView);
            mact = (TextView) itemView.findViewById(R.id.mact);
            chitieubsc = (TextView) itemView.findViewById(R.id.chitieubsc);
            chitieu = (TextView) itemView.findViewById(R.id.chitieu);
            dathuchien = (TextView) itemView.findViewById(R.id.dathuchien);
        }
    }
}
