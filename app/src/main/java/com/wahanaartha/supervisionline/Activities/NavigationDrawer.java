package com.wahanaartha.supervisionline.Activities;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wahanaartha.supervisionline.Fragment.BantuanFragment;
import com.wahanaartha.supervisionline.Fragment.BerandaFragment;
import com.wahanaartha.supervisionline.Fragment.BerandaFragmentAdmin;
import com.wahanaartha.supervisionline.Fragment.BerandaFragmentSpv;
import com.wahanaartha.supervisionline.Fragment.HasilSupervisiFragment;
import com.wahanaartha.supervisionline.Fragment.HistoryFragment;
import com.wahanaartha.supervisionline.Fragment.IndexPicaAdminFragment;
import com.wahanaartha.supervisionline.Fragment.IndexPicaSpvFragment;
import com.wahanaartha.supervisionline.Fragment.ReportFragmentAdmin;
import com.wahanaartha.supervisionline.Model.LoginUser;
import com.wahanaartha.supervisionline.Model.db.model.Alarm;
import com.wahanaartha.supervisionline.Model.db.repository.AlarmRepository;
import com.wahanaartha.supervisionline.R;

import java.util.List;

import static com.wahanaartha.supervisionline.Activities.LoginActivity.MY_LOGIN_PREF;
import static com.wahanaartha.supervisionline.Activities.LoginActivity.MY_LOGIN_PREF_KEY;

public class NavigationDrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public String title;
    TextView nama_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        LoginUser savedUser = new Gson().fromJson(NavigationDrawer
                .this.getSharedPreferences(MY_LOGIN_PREF, Context.MODE_PRIVATE).getString(MY_LOGIN_PREF_KEY, ""), LoginUser.class);
        title = savedUser.getGroupId();
        String data_nama = savedUser.getName();

        if (title.equals("14")) {
            hideItem();
//            hideItemBeranda();

        }
//        else if(title.equals("14")){
//            hideItem();
//        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        TextView userTxt = header.findViewById(R.id.nameTxt); //your ID
        userTxt.setText(data_nama);

        //add this line to display menu1 when the activity is loaded

//        if (title.equals("1")) {
//            displaySelectedScreen(R.id.nav_hasil);
//
//        } else{
//            displaySelectedScreen(R.id.nav_beranda);
//        }

        displaySelectedScreen(R.id.nav_beranda);

        AlarmRepository alarmRepo = new AlarmRepository(this);

        new BackgroundAsyncTask(new OnBackgroundAction() {
            @Override
            public void action() {
                alarmRepo.deleteAlarm();
            }

            @Override
            public void onPostExecute() {
            }
        }).execute();
        new BackgroundAsyncTask(new OnBackgroundAction() {
            @Override
            public void action() {
                alarmRepo.getAllAlarm().observe(NavigationDrawer.this, new Observer<List<Alarm>>() {
                    @Override
                    public void onChanged(@Nullable List<Alarm> alarms) {
                    }
                });
            }

            @Override
            public void onPostExecute() {
            }
        }).execute();
    }

    public interface OnBackgroundAction {
        void action();

        void onPostExecute();
    }

    public static class BackgroundAsyncTask extends AsyncTask<Void, Void, Void> {
        OnBackgroundAction action;

        public BackgroundAsyncTask(OnBackgroundAction action) {
            this.action = action;
        }

        @Override
        protected Void doInBackground(Void... params) {
            action.action();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            action.onPostExecute();
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    private void displaySelectedScreen(int itemId) {

        Fragment fragment = null;
        switch (itemId) {
            case R.id.nav_beranda:
                if (title.equals("1")) {
//                    fragment = new BerandaFragmentAdmin();
                    fragment = new BerandaFragment();
                } else if (title.equals("12")) {

                    //for SA
                    fragment = new BerandaFragment();
                } else {
                    fragment = new BerandaFragmentSpv();
                }
                break;
            case R.id.nav_hasil:
                if (title.equals("1")) {
//                    fragment = new IndexPicaAdminFragment();
                    fragment = new HasilSupervisiFragment();
                } else if (title.equals("12")) {
                    fragment = new HasilSupervisiFragment();
                } else {
                    fragment = new IndexPicaSpvFragment();
                }
                break;
            case R.id.nav_history:
                if (title.equals("12")) {
                    fragment = new HistoryFragment();
                } else{
                    //group id 1
                    fragment = new HistoryFragment();
                }
                break;
            case R.id.nav_help:
                fragment = new BantuanFragment();
                break;
            case R.id.nav_logout: {
                SharedPreferences preferences = getSharedPreferences(MY_LOGIN_PREF, Context.MODE_PRIVATE);
                preferences.edit().remove(MY_LOGIN_PREF_KEY).apply();
                Intent i = new Intent(NavigationDrawer.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
            break;
        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    private void hideItem() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.nav_history).setVisible(false);
    }

    private void hideItemBeranda() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.nav_beranda).setVisible(false);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        displaySelectedScreen(item.getItemId());
        return true;
    }
}