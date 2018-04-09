package com.blackchopper.demo_onhttp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.blackchopper.demo_onhttp.R;
import com.blackchopper.onhttp.OnHttp;
import com.blackchopper.onhttp.downloader.IDownListener;
import com.blackchopper.rxhttp.RxHttp;

import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.iv_view);
        OnHttp.initDefault(getApplicationContext());
    }


//    private void userinfo(String cookie) {
//        Map<String, String> head = new HashMap<>();
//        head.put("cookie", cookie);
//        OnHttp.getInstance().body(head).url(Constaint.USERINFO).method(OnHttp.GET).excute();
//    }
//
//    private void user(Vertification vertification) {
//        Map<String, String> body = new HashMap<>();
//        body.put("id", vertification.getId() + "");
//        RSAPublicKey pubKey = RSAUtil.getPublicKey(vertification.getModulus(), vertification.getExponent());
//        body.put("userid", RSAUtil.encryptByPublicKey("456qeqwdeqwdqw123", pubKey));
//        OnHttp.getInstance().url(Constaint.USER).body(body).clazz(UserResult.class).method(OnHttp.POST).listener(new IHttpListener<UserResult>() {
//            @Override
//            public void onSuccess(UserResult user) {
//                Log.v("TAG", user.toString());
//                userinfo(user.getCookie());
//            }
//
//            @Override
//            public void onError(int code) {
//
//            }
//        }).excute();
//    }

    public final static String APP_HOST_INERFACE = "http://192.168.0.108:6677";
    public final static String API_UPLOAD_PICTURE = APP_HOST_INERFACE + "/index.php/api/attachment/upload";


    private void loadimg() {
        OnHttp.imageLoader()
                .view(imageView)
                .url(Constaint.PIC)

                .executor();
//        OnHttp.httpBuilder()
//                .url(Constaint.PIC)
//                .method(Method.GET)
//                .clazz(Bitmap.class)
//                .listener(new IHttpListener<Bitmap>() {
//                    @Override
//                    public void onSuccess(Bitmap bitmap) {
//                        imageView.setImageBitmap(bitmap);
//                    }
//
//                    @Override
//                    public void onError(int code) {
//
//                    }
//                }).build();
        OnHttp.downLoader()
                .file(new File("/sdcard/123213123.jpg"))
                .url(Constaint.PIC)
                .listener(new IDownListener() {
                    @Override
                    public void onProgress(float progress) {
                        Log.v("TAG", "----------->>" + progress);
                    }

                    @Override
                    public void onSuccess(File file) {
                        Log.v("TAG", "----------->>" + file.getAbsolutePath());
                    }

                    @Override
                    public void onError(int code) {

                    }
                }).executor();

//        OnHttp.getInstance().url(Constaint.PIC)
//                .id(R.mipmap.ic_launcher)
//                .view(imageView)
//                .excute();

//        DisplayImageOptions options = new DisplayImageOptions.Builder()
//                .showImageOnLoading(R.mipmap.ic_launcher)
//                .cacheInMemory(true)
//                .cacheOnDisk(true)
//                .bitmapConfig(Bitmap.Config.RGB_565)
//                .build();
//        ImageLoader.getInstance().displayImage(Constaint.PIC, imageView, options);
//        OnHttp.getInstance()
//                .url(Constaint.PIC)
//                .file(new File("/sdcard/Music/12321.jpg"))
//                .listener(new IHttpListener<File>() {
//                    @Override
//                    public void onSuccess(File file) {
//                        Log.v("TAG", "-------------->>" + file.getAbsolutePath());
//                    }
//
//                    @Override
//                    public void onError(int code) {
//
//                    }
//                })
//                .excute();
    }


    private void getJSon() {

//        Map<String, String> body = new HashMap<String, String>();
//        body.put("name", "admin");
//        Map<String, String> headers = new HashMap<String, String>();
//        headers.put("Cookie", "WSJ=546546546546432132134658");
//        OnHttp.getInstance()
//                .url("http://118.123.11.98:50501/demo/user/vertify")
//                .clazz(Respone.class)
//                .method(OnHttp.POST)
//                .body(body)
//                .headers(headers)
//                .listener(new IHttpListener<Respone>() {
//                    @Override
//                    public void onSuccess(Respone respone) {
//                        Log.v("TAG", "exponent:" + respone.getExponent());
//                        Log.v("TAG", "id:" + respone.getId());
//                        Log.v("TAG", "modulus:" + respone.getModulus());
//                    }
//
//                    @Override
//                    public void onError(int code) {
//
//                    }
//                })
//                .excute();
    }

    public void getimg() {
//        OnHttp.getInstance().url(Constaint.PIC).clazz(Bitmap.class).listener(new IHttpListener<Bitmap>() {
//            @Override
//            public void onSuccess(Bitmap bitmap) {
//
//            }
//
//            @Override
//            public void onError(int code) {
//
//            }
//        }).excute();
    }

//    public void getHead() {
//        OnHttp.getInstance()
//                .url("http://118.123.11.98:50501/demo/user/vertify")
//                .clazz(Respone.class)
//                .method(OnHttp.POST)
//                .listener(new IHttpListener<Respone>() {
//                    @Override
//                    public void onSuccess(Respone respone) {
//                        Log.v("TAG", "exponent:" + respone.getExponent());
//                        Log.v("TAG", "id:" + respone.getId());
//                        Log.v("TAG", "modulus:" + respone.getModulus());
//                    }
//
//                    @Override
//                    public void onError(int code) {
//
//                    }
//                })
//                .headerListener(new IHeaderListener() {
//                    @Override
//                    public void onHeader(Map<String, List<String>> headers) {
//
//                    }
//                })
//                .excute();
//    }


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


    @Override
    public void onClick(View view) {
        // loadimg();
        Rx();
    }

    private void Rx() {
        Observable.create(new RxHttp<JSONObject>() {
            @Override
            public String url() {
                return "http://news.wsj080.com/index.php/api/user/mobileregister?sign=a3cca0946e3589f16ef6679e1bb3f5af&appid=KhIHn7pa3mEn8&passwd=1234567&passwd2=1234567&authcode=1234&nickname=哦你妹&mobile=13557131404";
            }

            @Override
            public Map<String, String> body() {
                return super.body();
            }

            @Override
            protected Map<String, String> header() {
                return super.header();
            }

            @Override
            public Class<?> response() {
                return super.response();
            }


        })
                .subscribe(new Observer<JSONObject>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(JSONObject object) {
                        Log.v("TAG", object.toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.v("TAG", e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}

