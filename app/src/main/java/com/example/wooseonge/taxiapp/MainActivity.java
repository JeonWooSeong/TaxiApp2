package com.example.wooseonge.taxiapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.wooseonge.taxiapp.bean.Body;
import com.example.wooseonge.taxiapp.bean.Header;
import com.example.wooseonge.taxiapp.server.TaxiActivity;

public class MainActivity extends TaxiActivity {
    TextView version;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        version = (TextView)findViewById(R.id.version);
//        GITHUBTEST
        //seconds
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
}
