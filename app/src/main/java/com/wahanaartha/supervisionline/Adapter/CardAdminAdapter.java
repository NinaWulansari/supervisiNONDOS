package com.wahanaartha.supervisionline.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wahanaartha.supervisionline.Model.Model;
import com.wahanaartha.supervisionline.R;

import java.util.ArrayList;

public class CardAdminAdapter extends RecyclerView.Adapter {

    Context mContext;
    int total_types;
    ArrayList<String> allNames = new ArrayList<>();
    ArrayList<Integer> allProspects = new ArrayList<>();
    ArrayList<Integer> allDeals = new ArrayList<>();
    private ArrayList<Model> dataSet;


    public CardAdminAdapter(ArrayList<Model> data, Context context) {
        this.dataSet = data;
        this.mContext = context;
        total_types = dataSet.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        switch (viewType) {
            case Model.DONE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.supervisi_done, parent, false);
                return new ProspectViewHolder(view, mContext);

            case Model.NOT_DONE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.supervisi_non_done, parent, false);
                return new DealViewHolder(view);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {

        switch (dataSet.get(position).type) {
            case 0:
                return Model.DONE;
            case 1:
                return Model.NOT_DONE;
            default:
                return -1;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int listPosition) {

        Model object = dataSet.get(listPosition);
        switch (object.type) {
            case Model.DONE:
                ((ProspectViewHolder) holder).txtType.setText(object.text);
                break;

            case Model.NOT_DONE:
                ((DealViewHolder) holder).txtType.setText(object.text);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    //done
    public static class DealViewHolder extends RecyclerView.ViewHolder {

        TextView txtType;
        CardView cardView;
        TextView deal;

        public DealViewHolder(View itemView) {
            super(itemView);

            this.txtType = itemView.findViewById(R.id.type);
            this.cardView = itemView.findViewById(R.id.card_view);
        }
    }

    //not_done
    public static class ProspectViewHolder extends RecyclerView.ViewHolder {

        public View view;
        TextView txtType;
        CardView cardView;
        TextView prospect;
        Context mContext;

        public ProspectViewHolder(View itemView, Context context) {
            super(itemView);
            view = itemView;

            this.mContext = context;
            this.txtType = itemView.findViewById(R.id.type);
            this.cardView = itemView.findViewById(R.id.card_view);
            this.prospect = itemView.findViewById(R.id.prospect);

        }
    }
}