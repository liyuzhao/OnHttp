package com.absurd.demo_onhttp;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.absurd.demo_onhttp.entity.UserResult;
import com.absurd.demo_onhttp.entity.Vertification;
import com.absurd.demo_onhttp.util.RSAUtil;
import com.absurd.onhttp.OnHttp;
import com.absurd.onhttp.base.IHeaderListener;
import com.absurd.onhttp.base.IHttpListener;

import java.io.File;
import java.security.interfaces.RSAPublicKey;
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
        //  updata();
        vertify();
    }

    private void vertify() {
        OnHttp.getInstance().url(Constaint.VERTIFY).clazz(Vertification.class).listener(new IHttpListener<Vertification>() {
            @Override
            public void onSuccess(Vertification vertification) {
                Log.v("TAG", vertification.toString());
                user(vertification);
             //   adduser(vertification);
            }

            @Override
            public void onError(int code) {

            }
        }).excute();
    }

    private void adduser(Vertification vertification) {
        Map<String, String> body = new HashMap<>();
        body.put("id", vertification.getId() + "");
        RSAPublicKey pubKey = RSAUtil.getPublicKey(vertification.getModulus(), vertification.getExponent());
        body.put("userid", RSAUtil.encryptByPublicKey("456qeqwdeqwdqw123", pubKey));
        body.put("country", "China");
        body.put("gender", "1");
        body.put("sign","天空一生巨响老子闪亮登场");
        body.put("nickname","雷锋");
        body.put("avator","");
        body.put("province","广西");
        body.put("city","桂林");
        OnHttp.getInstance().method(OnHttp.POST).url(Constaint.ADDUSER).body(body).excute();
    }

    private void userinfo(String cookie){
        Map<String, String> head = new HashMap<>();
        head.put("cookie",cookie);
        OnHttp.getInstance().body(head).url(Constaint.USERINFO).method(OnHttp.GET).excute();
    }

    private void user(Vertification vertification) {
        Map<String, String> body = new HashMap<>();
        body.put("id", vertification.getId() + "");
        RSAPublicKey pubKey = RSAUtil.getPublicKey(vertification.getModulus(), vertification.getExponent());
        body.put("userid", RSAUtil.encryptByPublicKey("456qeqwdeqwdqw123", pubKey));
        OnHttp.getInstance().url(Constaint.USER).body(body).clazz(UserResult.class).method(OnHttp.POST).listener(new IHttpListener<UserResult>() {
            @Override
            public void onSuccess(UserResult user) {
                Log.v("TAG", user.toString());
                userinfo(user.getCookie());
            }

            @Override
            public void onError(int code) {

            }
        }).excute();
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

