package com.example.wooseonge.taxiapp.activity;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.example.wooseonge.taxiapp.R;
import com.example.wooseonge.taxiapp.bean.Body;
import com.example.wooseonge.taxiapp.bean.Header;
import com.example.wooseonge.taxiapp.fragment.SearchFragment;
import com.example.wooseonge.taxiapp.server.TaxiActivity;
//import android.support.design.widget.NavigationView;

public class MainActivity extends TaxiActivity {
    TextView version;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        getFragmentManager().beginTransaction().replace(R.id.maincontainer,new SearchFragment(),"SearchFragment").commit();

        version = (TextView)findViewById(R.id.version);

        doAppVersion();
    }
    public void doAppVersion(){
        super.apiCode("appVersion");
        initParameter();
        addParameter("deviceCode","ANDROID");
        requestHandle(0,0);
    }
    @Override
    public void response(Header header , Body body){
        if(super.apiCode.get("apiKey").equals("appVersion")){
            if(header.getMessage().equals("success.")){
                Log.d("android Version", body.getAppVersion().getDeviceName());
                version.setText(body.getAppVersion().getDeviceName());
            }
        }
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
