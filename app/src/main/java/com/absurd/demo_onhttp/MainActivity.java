package com.absurd.demo_onhttp;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.absurd.onhttp.OnHttp;
import com.absurd.onhttp.base.IHttpListener;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.iv_view);
    }

    public void OnClick(View v) {
        OnHttp.getInstance().url(Constaint.PIC)
                .view(imageView)
                .excute();
    }


    private void getJSon() {
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
        body.put("name", "admin");
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Cookie", "WSJ=54654654654643213.2134658");
        OnHttp.getInstance()
                .url("http://118.123.11.98:50501/demo/user/vertify")
                .clazz(Respone.class)
                .method(OnHttp.GET)
                .body(body)
                .headers(headers)
                .listener(new IHttpListener<Respone>() {
                    @Override
                    public void onSuccess(Respone respone) {
                        Log.v("TAG", "exponent:" + respone.getExponent());
                        Log.v("TAG", "id:" + respone.getId());
                        Log.v("TAG", "modulus:" + respone.getModulus());

                    }

                    @Override
                    public void onError(int code) {

                    }
                })
                .excute();
    }
}

