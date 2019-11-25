package com.wahanaartha.supervisionline.Adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wahanaartha.supervisionline.Model.HistoryIndex;
import com.wahanaartha.supervisionline.R;

import java.util.ArrayList;
import java.util.List;

public class ListHistoryAdapter extends RecyclerView.Adapter<ListHistoryAdapter.HistoryViewHolder> {
    private List<HistoryIndex> dataSet;

    public ListHistoryAdapter(ArrayList<HistoryIndex> tempData) {
        dataSet = tempData;
    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.content_history, parent, false);

        return new HistoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HistoryViewHolder holder, int position) {
        HistoryIndex historyIndex = dataSet.get(position);
        holder.dealer.setText(historyIndex.getNamaDlr());
        holder.tgl_supervisi.setText(historyIndex.getTglSupervisi());
        holder.type.setText(historyIndex.getType());
    }

    @Override
    public int getItemCount() {
        if (dataSet == null) {
            return 0;
        } else {
            return dataSet.size();
        }
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder {
        public TextView type, dealer, tgl_supervisi;
        public CardView st_supervisi;

        public HistoryViewHolder(View itemView) {
            super(itemView);
            st_supervisi = itemView.findViewById(R.id.approve);
            type = itemView.findViewById(R.id.type);
            tgl_supervisi = itemView.findViewById(R.id.tgl_supervisi);
            dealer = itemView.findViewById(R.id.nama_dealer);
        }
    }
}
