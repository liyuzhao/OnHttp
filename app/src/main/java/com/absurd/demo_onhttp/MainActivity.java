package com.absurd.demo_onhttp;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.absurd.demo_onhttp.entity.UserResult;
import com.absurd.demo_onhttp.entity.Userinfo;
import com.absurd.demo_onhttp.entity.Vertification;
import com.absurd.demo_onhttp.util.Md5Util;
import com.absurd.demo_onhttp.util.RSAUtil;
import com.absurd.onhttp.OnHttp;
import com.absurd.onhttp.base.IHeaderListener;
import com.absurd.onhttp.base.IHttpListener;
import com.absurd.onhttp.base.base.IDownloadListener;
import com.absurd.onhttp.cache.BitmapCache;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
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
        BitmapCache.getInstance("/sdcard/Music/");
    }

    public void OnClick(View v) {

        //   getJSon();
        loadimg();
//        getimg();
//        getHead();
        //  updata();
        //   vertify();
        //      login();
        //updata(10016,new File("/sdcard/Music/123.png"));
        //  download();
    }

//    private void download() {
//        OnHttp.getInstance().url("http://192.168.0.121321308:6677/uploads/sublime.zip")
//                .file(new File("/sdcard/Music/classes.dex"))
//                .clazz(File.class)
//                .listener(new IHttpListener<File>() {
//                    @Override
//                    public void onSuccess(File file) {
//                        Log.v("TAG", file.getAbsolutePath());
//                    }
//
//                    @Override
//                    public void onError(int code) {
//
//                    }
//                })
//                .excute();
//    }


    private void updata(int id, File file) {
        Map<String, String> body = new HashMap<>();
        body.put("uid", id + "");

        OnHttp.getInstance()
                .url(API_UPLOAD_PICTURE)
                .body(Md5Util.mapToBody(body))
                .file(file)
                .updata(true)
                .clazz(String.class)
                .method(OnHttp.GET)
                .listener(new IHttpListener<String>() {
                    @Override
                    public void onSuccess(String respone) {
                        Log.v("TAG", "----------" + respone);
                    }

                    @Override
                    public void onError(int i) {
                        Log.v("TAG", "-----------" + i);
                    }
                })
                .excute();
    }


    private void login() {
        //appid=adroid123456city=Guilincountry=CNheadimgurl=  nickname=Alone openid= province= sex=1
        Userinfo info = new Userinfo();
        info.setAppid("adroid123456");
        info.setCity("Guilin");
        info.setCountry("CN");
        info.setHeadimgurl("http://wx.qlogo.cn/mmopen/vi_32/lQ4iaw3AW0OStGT4o6ZeK9LLwr3d9d8Qx9M4XxENiaLM2DJWvx2ClR064WK1aiaQYGP7ib2nvfHHXjcEhMb6SGgzHA/0");
        info.setOpenid("odZUJwU0vPAhmkA8XC5r3KvmtQsQ");
        info.setSex("1");
        info.setNickname("Alone");
        info.setProvince("Guangxi");
        info.setSign("1bdc6fabc42147edf1aa4bf9792aad30");
        OnHttp.getInstance().url("http://192.168.1.204/fastadmin/public/index.php/api/client/wechat")
                .clazz(String.class).method(OnHttp.POST)
                .body(info)
                .listener(new IHttpListener<String>() {
                    @Override
                    public void onSuccess(String o) {

                    }

                    @Override
                    public void onError(int code) {

                    }
                }).excute();
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
        body.put("sign", "天空一生巨响老子闪亮登场");
        body.put("nickname", "雷锋");
        body.put("avator", "");
        body.put("province", "广西");
        body.put("city", "桂林");
        OnHttp.getInstance().method(OnHttp.POST).url(Constaint.ADDUSER).body(body).excute();
    }

    private void userinfo(String cookie) {
        Map<String, String> head = new HashMap<>();
        head.put("cookie", cookie);
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

    public final static String APP_HOST_INERFACE = "http://192.168.0.108:6677";
    public final static String API_UPLOAD_PICTURE = APP_HOST_INERFACE + "/index.php/api/attachment/upload";


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


    public static void main(String[] argv) {
        Class<?> classResponse = Respone.class;
        Field[] fields = classResponse.getDeclaredFields();

//        for (Field field : fields) {
//            field.setAccessible(true);
//            System.out.print(field.getType().getName()+"\n");
//        }


        Class<?> classType = Respone.class;
//        for (Field field : classType.getDeclaredFields()) {
//            Class<?> c = field.getType(); //获得属性的类型
//            for (TypeVariable tv : c.getTypeParameters()) { //获得属性类型的泛型参数信息
//                System.out.println(tv);
//                System.out.println(tv.getBounds()[0]); //泛型的类型
//            }
//        }


        for (Field f : fields) {
            Class fieldClazz = f.getType(); // 得到field的class及类型全路径

            if (fieldClazz.isPrimitive()) continue;  //【1】 //判断是否为基本类型

            if (fieldClazz.getName().startsWith("java.lang")) continue; //getName()返回field的类型全路径；

            if (fieldClazz.isAssignableFrom(List.class)) //【2】
            {
                Type fc = f.getGenericType(); // 关键的地方，如果是List类型，得到其Generic的类型

                if (fc == null) continue;

                if (fc instanceof ParameterizedType) // 【3】如果是泛型参数的类型
                {
                    ParameterizedType pt = (ParameterizedType) fc;

                    Class genericClazz = (Class) pt.getActualTypeArguments()[0]; //【4】 得到泛型里的class类型对象。


                    System.out.print(f.getName() + "\n");
                    System.out.print(genericClazz.getName() + "\n");

                }
            }
        }


    }


}

