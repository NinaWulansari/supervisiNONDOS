package com.wahanaartha.supervisionline.Activities;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wahanaartha.supervisionline.Adapter.ListApprovedAdapter;
import com.wahanaartha.supervisionline.Connect.API;
import com.wahanaartha.supervisionline.Fragment.ApprovedFragment;
import com.wahanaartha.supervisionline.Fragment.ToApprovedFragment;
import com.wahanaartha.supervisionline.Model.ListApprove;
import com.wahanaartha.supervisionline.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListApproved2Activity extends AppCompatActivity {

    private Toolbar toolbar;
    private ListApprove listApprove;


    @BindView(R.id.titleSearch)
    TextView title;
    @BindView(R.id.lvApproved)
    ListView listView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipe;

    private ArrayList<ListApprove> modelArrayList;
    private ListApprovedAdapter listApprovedAdapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_approved);

        ButterKnife.bind(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        title.setText("Appprove");
        title.setTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                getData();
                swipe.setRefreshing(false);
            }
        });

        getData();
    }

    public void  getData(){
        String tglSurvey = getIntent().getStringExtra(ApprovedFragment.Companion.getTGL_SURVEY());
        String noDlr = getIntent().getStringExtra(ApprovedFragment.Companion.getNO_DLR());
        String idSa = getIntent().getStringExtra(ApprovedFragment.Companion.getID_SA());
        API.getListApprovedDetail(idSa,tglSurvey,noDlr).enqueue(new Callback<ArrayList<ListApprove>>() {
            @Override
            public void onResponse(Call<ArrayList<ListApprove>> call, Response<ArrayList<ListApprove>> response) {
                modelArrayList = response.body();

                modelArrayList = getModel(false);

                listApprovedAdapter = new ListApprovedAdapter(ListApproved2Activity.this,modelArrayList);

                listView.setAdapter(listApprovedAdapter);


            }

            @Override
            public void onFailure(Call<ArrayList<ListApprove>> call, Throwable t) {
                Toast.makeText(ListApproved2Activity.this, "gagal", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private ArrayList<ListApprove> getModel(boolean isSelect){
        ArrayList<ListApprove> list = new ArrayList<>();
        for(int i = 0; i < modelArrayList.size(); i++){

            ListApprove model = new ListApprove();
            model.setSelectAll(isSelect);
            model.setKategori(modelArrayList.get(i).getKategori());
            model.setTitle(modelArrayList.get(i).getTitle());
            model.setId(modelArrayList.get(i).getId());
            model.setDeadline(modelArrayList.get(i).getDeadline());

            list.add(model);
        }
        return list;
    }
}
