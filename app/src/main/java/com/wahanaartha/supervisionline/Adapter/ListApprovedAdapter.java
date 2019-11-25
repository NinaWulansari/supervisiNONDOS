package com.wahanaartha.supervisionline.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.wahanaartha.supervisionline.Model.ListApprove;
import com.wahanaartha.supervisionline.R;

import java.util.ArrayList;

public class ListApprovedAdapter extends BaseAdapter {
    private Context context;
    public static ArrayList<ListApprove> modelArrayList;


    public ListApprovedAdapter(Context context, ArrayList<ListApprove> modelArrayList) {

        this.context = context;
        this.modelArrayList = modelArrayList;

    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return modelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return modelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder(); LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_list_approved_row, null, true);

            holder.tvKategori = (TextView) convertView.findViewById(R.id.text_kategori_app);
            holder.tvSoal = (TextView) convertView.findViewById(R.id.text_question_app);
            holder.tvDeadline = (TextView) convertView.findViewById(R.id.deadline);


            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }

        holder.tvKategori.setText(modelArrayList.get(position).getKategori());
        holder.tvSoal.setText(modelArrayList.get(position).getTitle());
        holder.tvDeadline.setText(modelArrayList.get(position).getDeadline());

        return convertView;
    }

    private class ViewHolder {

        private TextView tvKategori, tvSoal, tvDeadline;

    }
}
