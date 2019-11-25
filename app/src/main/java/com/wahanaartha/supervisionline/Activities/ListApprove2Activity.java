package com.wahanaartha.supervisionline.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.wahanaartha.supervisionline.Adapter.ListAdapater;
import com.wahanaartha.supervisionline.Connect.API;
import com.wahanaartha.supervisionline.Fragment.ToApprovedFragment;
import com.wahanaartha.supervisionline.Model.AddPica;
import com.wahanaartha.supervisionline.Model.ListApprove;
import com.wahanaartha.supervisionline.Model.LoginUser;
import com.wahanaartha.supervisionline.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.wahanaartha.supervisionline.Activities.LoginActivity.MY_LOGIN_PREF;
import static com.wahanaartha.supervisionline.Activities.LoginActivity.MY_LOGIN_PREF_KEY;

public class ListApprove2Activity extends AppCompatActivity{

    private Toolbar toolbar;
    private ListApprove listApprove;

    public static final String ID = "id";

    @BindView(R.id.titleSearch) TextView title;
    @BindView(R.id.select)
    TextView btnSelect;
    @BindView(R.id.deselect)
    TextView btnDeselect;
    @BindView(R.id.btnApprove)
    TextView btnApprove;
    @BindView(R.id.btnNotApprove)
    TextView btnNotApprove;
    @BindView(R.id.lvApprove)
    ListView listView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipe;

    private ArrayList<ListApprove> modelArrayList;
    private ListAdapater listAdapater;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_approve);

        ButterKnife.bind(this);

        toolbar = findViewById(R.id.toolbar);
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


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(ListApprove2Activity.this, ApproveActivity.class);
                intent.putExtra(ID, modelArrayList.get(position).getId());
                Toast.makeText(ListApprove2Activity.this, ""+modelArrayList.get(position).getId(), Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });



        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    modelArrayList = getModel(true);
                    listAdapater = new ListAdapater(ListApprove2Activity.this,modelArrayList);
                    listView.setAdapter(listAdapater);
                    btnDeselect.setVisibility(View.VISIBLE);
                    btnSelect.setVisibility(View.GONE);

            }
        });

        btnDeselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                modelArrayList = getModel(false);
                listAdapater = new ListAdapater(ListApprove2Activity.this,modelArrayList);
                listView.setAdapter(listAdapater);
                btnDeselect.setVisibility(View.GONE);
                btnSelect.setVisibility(View.VISIBLE);

            }
        });

        btnApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            for (int i = 0; i < ListAdapater.modelArrayList.size(); i++){
                if(ListAdapater.modelArrayList.get(i).getSelectAll()) {

                    ProgressDialog progressDialog = new ProgressDialog(ListApprove2Activity.this);
                    progressDialog.setMessage("Saving");
                    progressDialog.show();

                    LoginUser savedUser = new Gson()
                            .fromJson(ListApprove2Activity
                                    .this.getSharedPreferences(MY_LOGIN_PREF, Context.MODE_PRIVATE)
                                    .getString(MY_LOGIN_PREF_KEY, ""), LoginUser.class);

                    String userID = savedUser.getId();
                    String id = ListAdapater.modelArrayList.get(i).getId();

                    API.editApprove(id,userID).enqueue(new Callback<AddPica>() {
                        @Override
                        public void onResponse(Call<AddPica> call, Response<AddPica> response) {

                            if (response.code() == 200) {
                                progressDialog.dismiss();
                                Toast.makeText(ListApprove2Activity.this, "success", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(ListApprove2Activity.this, "failed", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<AddPica> call, Throwable t) {
                            Toast.makeText(ListApprove2Activity.this, "gagal", Toast.LENGTH_SHORT).show();
                        }
                    });

                    finish();
                }
//                else {
//                    Toast.makeText(ListApprove2Activity.this, "Please Checked the Item", Toast.LENGTH_SHORT).show();
//                }
            }
            }
        });

        btnNotApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = 0; i < ListAdapater.modelArrayList.size(); i++){
                    if(ListAdapater.modelArrayList.get(i).getSelectAll()) {

                        ProgressDialog progressDialog = new ProgressDialog(ListApprove2Activity.this);
                        progressDialog.setMessage("Saving");
                        progressDialog.show();

                        LoginUser savedUser = new Gson()
                                .fromJson(ListApprove2Activity
                                        .this.getSharedPreferences(MY_LOGIN_PREF, Context.MODE_PRIVATE)
                                        .getString(MY_LOGIN_PREF_KEY, ""), LoginUser.class);

                        String userID = savedUser.getId();
                        String id = ListAdapater.modelArrayList.get(i).getId();

                        API.editNotApprove(id,userID).enqueue(new Callback<AddPica>() {
                            @Override
                            public void onResponse(Call<AddPica> call, Response<AddPica> response) {

                                if (response.code() == 200) {
                                    progressDialog.dismiss();
                                    Toast.makeText(ListApprove2Activity.this, "success", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(ListApprove2Activity.this, "failed", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<AddPica> call, Throwable t) {
                                Toast.makeText(ListApprove2Activity.this, "gagal", Toast.LENGTH_SHORT).show();
                            }
                        });

                        finish();
                    }
//                    else {
//                        Toast.makeText(ListApprove2Activity.this, "Please Checked the Item", Toast.LENGTH_SHORT).show();
//                    }
                }
            }
        });

        getData();
    }

    public void  getData(){
        String tglSurvey = getIntent().getStringExtra(ToApprovedFragment.Companion.getTGL_SURVEY());
        String noDlr = getIntent().getStringExtra(ToApprovedFragment.Companion.getNO_DLR());
        String idSa = getIntent().getStringExtra(ToApprovedFragment.Companion.getID_SA());
        API.getListApproveDetail(idSa,tglSurvey,noDlr).enqueue(new Callback<ArrayList<ListApprove>>() {
            @Override
            public void onResponse(Call<ArrayList<ListApprove>> call, Response<ArrayList<ListApprove>> response) {
                modelArrayList = response.body();

                modelArrayList = getModel(false);

                listAdapater = new ListAdapater(ListApprove2Activity.this,modelArrayList);

                listView.setAdapter(listAdapater);

            }

            @Override
            public void onFailure(Call<ArrayList<ListApprove>> call, Throwable t) {
                Toast.makeText(ListApprove2Activity.this, "gagal", Toast.LENGTH_SHORT).show();
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
