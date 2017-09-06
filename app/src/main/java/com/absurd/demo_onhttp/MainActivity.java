package com.absurd.demo_onhttp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.absurd.onhttp.OnHttp;
import com.absurd.onhttp.base.IHttpListener;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    public void OnClick(View v) {
        IHttpListener<Respone> listener = new IHttpListener<Respone>() {
            @Override
            public void onSuccess(Respone respone) {
                Log.v("TAG", "exponent:" + respone.getExponent());
                Log.v("TAG", "id:" + respone.getId());
                Log.v("TAG", "modulus:" + respone.getModulus());

            }

            @Override
            public void onError(int code) {

            }
        };
        Map<String, String> body = new HashMap<String, String>();
        body.put("id", 1 + "");
        body.put("name", "admin");
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Cookie", "WSJ=54654654654643213.2134658");
        OnHttp.getInstance()
                .url("http://118.123.11.98:50501/demo/user/vertify")
                .clazz(Respone.class)
                .method(OnHttp.GET)
                .body(body)
                .headers(headers)
                .listener(listener)
                .excute();
    }
}

