package com.wahanaartha.supervisionline.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.wahanaartha.supervisionline.Model.ListApprove;
import com.wahanaartha.supervisionline.R;

import java.util.ArrayList;

/**
 * Created by hardik on 9/1/17.
 */
public class ListAdapater  extends BaseAdapter {

    private Context context;
    public static ArrayList<ListApprove> modelArrayList;


    public ListAdapater(Context context, ArrayList<ListApprove> modelArrayList) {

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
            convertView = inflater.inflate(R.layout.activity_list_approve_row, null, true);

            holder.checkBox = (CheckBox) convertView.findViewById(R.id.chk_selected);
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

        holder.checkBox.setChecked(modelArrayList.get(position).getSelectAll());

        holder.checkBox.setTag(R.integer.btnplusview, convertView);
        holder.checkBox.setTag( position);
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                View tempview = (View) holder.checkBox.getTag(R.integer.btnplusview);
                Integer pos = (Integer)  holder.checkBox.getTag();

                if(modelArrayList.get(pos).getSelectAll()){
                    modelArrayList.get(pos).setSelectAll(false);
                }else {
                    modelArrayList.get(pos).setSelectAll(true);
                }

            }
        });

        return convertView;
    }

    private class ViewHolder {

        protected CheckBox checkBox;
        private TextView tvKategori, tvSoal, tvDeadline;

    }

}

