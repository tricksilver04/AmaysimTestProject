package com.amaysim.testproject;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.amaysim.testproject.model.Collection;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

    }

    @Override
    protected void onStart() {
        super.onStart();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadData(new LoadListener() {
                    @Override
                    public void onLoadFinish() {
                        startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                        finish();
                    }
                });
            }
        }, 3000);
    }

    interface LoadListener {
        void onLoadFinish();
    }

    private void loadData(LoadListener loadListener) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            InputStream stream = getAssets().open("collection.json");
            String str = "";
            BufferedReader bf = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
            while ((str = bf.readLine()) != null) {
                stringBuilder.append(str);
            }
            processJSON(stringBuilder.toString());
            bf.close();
            loadListener.onLoadFinish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void processJSON(String str) {
        try {
            if(Collection.listAll(Collection.class).size() > 0)
                return;

            JSONObject jsonObject = new JSONObject(str);
            Collection collection = new Collection();

            JSONObject data = jsonObject.getJSONObject("data");
            JSONObject attributes = data.getJSONObject("attributes");
            collection.setAccounts(attributes.toString());

            JSONArray includedJA = jsonObject.getJSONArray("included");
            for (int i = 0; i < includedJA.length(); i++) {
                JSONObject jo = (JSONObject) includedJA.get(i);
                String type = jo.getString("type");
                JSONObject attributesJO = jo.getJSONObject("attributes");
                if (type.equals("products")) {
                    collection.setProducts(attributesJO.toString());
                } else if (type.equals("subscriptions")) {
                    collection.setSubscription(attributesJO.toString());
                } else if (type.equals("services")) {
                    collection.setServices(attributesJO.toString());
                }
            }

            collection.save();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
