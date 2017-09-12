package com.absurd.demo_onhttp;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.absurd.onhttp.OnHttp;
import com.absurd.onhttp.base.IHeaderListener;
import com.absurd.onhttp.base.IHttpListener;

import java.io.File;
import java.util.HashMap;
import java.util.List;
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

        //   getJSon();
        //  loadimg();
//        getimg();
//        getHead();
        updata();
    }

    private void updata() {
        OnHttp.getInstance().updata(true).file(new File("/sdcard/Music/apk.patch")).url(Constaint.PIC).excute();
    }

    private void loadimg() {

        OnHttp.getInstance().url(Constaint.PIC).id(R.mipmap.ic_launcher)
                .view(imageView)
                .excute();
    }


    private void getJSon() {

        Map<String, String> body = new HashMap<String, String>();
        body.put("name", "admin");
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Cookie", "WSJ=546546546546432132134658");
        OnHttp.getInstance()
                .url("http://118.123.11.98:50501/demo/user/vertify")
                .clazz(Respone.class)
                .method(OnHttp.POST)
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

    public void getimg() {
        OnHttp.getInstance().url(Constaint.PIC).clazz(Bitmap.class).listener(new IHttpListener<Bitmap>() {
            @Override
            public void onSuccess(Bitmap bitmap) {

            }

            @Override
            public void onError(int code) {

            }
        }).excute();
    }

    public void getHead() {
        OnHttp.getInstance()
                .url("http://118.123.11.98:50501/demo/user/vertify")
                .clazz(Respone.class)
                .method(OnHttp.POST)
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
                .headerListener(new IHeaderListener() {
                    @Override
                    public void onHeader(Map<String, List<String>> headers) {

                    }
                })
                .excute();
    }
}

