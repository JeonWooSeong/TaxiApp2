package com.example.wooseonge.taxiapp.server;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.wooseonge.taxiapp.bean.Body;
import com.example.wooseonge.taxiapp.bean.Header;
import com.example.wooseonge.taxiapp.bean.ResultDefault;
import com.example.wooseonge.taxiapp.server.constants.ParameterConstants;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Created by KSE_NOTEBOOK on 2017-02-15.
 */

public class TaxiActivity extends AppCompatActivity {
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    public Map<String, String> apiCode;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }
    protected void apiCode(String apiValue) {
        apiCode = new HashMap<>();
        apiCode.put("apiKey", apiValue);
    }

    private Hashtable<String, String> mParameter;
    protected Gson mGson;

    protected void addParameter(String key, String value) {
        if (null == mParameter) {
            mParameter = new Hashtable<>();
        }
        mParameter.put(key, value);
    }

    protected void initParameter() {
        if (null == mParameter) {
            mParameter = new Hashtable<>();
        }
        this.mParameter.clear();
    }

    protected void requestHandle(final int opCode, final int funcCode) {
        this.addParameter("device", "android");
        this.addParameter("deviceCode", "android");
        this.addParameter("opCode", String.valueOf(opCode));
        this.addParameter("funcCode", funcCode + "");

        Log.d("request", "====================================================");
        Log.d("request", "parameter : " + mParameter);
        Log.d("request", "====================================================");

        HttpRequstor requstor = new HttpRequstor();
        requstor.execute(opCode, funcCode);
    }

    private void requestResult(String result) {
        try {
            if (null != result && result.length() > 0) {
                if (null == mGson) {
                    mGson = new GsonBuilder().setPrettyPrinting().create();
                }

                ResultDefault resultDefault = mGson.fromJson(result, ResultDefault.class);
                Log.d("request", "resultDefault : " + resultDefault);
                Body body = resultDefault.getBody();
                Log.d("request", "body json : " + body);
                this.response(resultDefault.getHeader(), body);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void response(Header header, Body body) {
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Taxi Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    private class HttpRequstor extends AsyncTask<Object, Void, String> {

        @Override
        protected String doInBackground(Object[] params) {
            try {
                String url = ParameterConstants.getServerUrl();
                int opCode = (Integer) params[0];

                if (apiCode.get("apiKey").equals("appVersion")) {
                    url += ParameterConstants.Common.OPCODE_COMMON;
                    url += ParameterConstants.Common.URI_APPVERSION;
                }

                Log.d("server url", url);

                HttpClient client = new DefaultHttpClient();
                HttpPost post = new HttpPost(url);

                List<NameValuePair> list = new ArrayList<NameValuePair>();

                Enumeration<String> keys = mParameter.keys();
                while (keys.hasMoreElements()) {
                    String key = keys.nextElement();
                    list.add(new BasicNameValuePair(key, mParameter.get(key)));
                }

                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, HTTP.UTF_8);
                post.setEntity(entity);
                HttpResponse responsePost = client.execute(post);
                HttpEntity resEntity = responsePost.getEntity();

                if (resEntity != null) {
                    String result = EntityUtils.toString(resEntity);
                    Log.i("RESPONSE", result);
                    return result;
                }

            } catch (ClientProtocolException e) {

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            requestResult(result);
        }
    }
}