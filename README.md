# OnHttp  [![](https://jitpack.io/v/aliletter/onhttp.svg)](https://jitpack.io/#aliletter/onhttp)
OnHttp is an Android-based web framework that supports Get and Post, which is easy to use. You only need to provide Url and JavaBean, it can be in the main thread to return a JavaBean object, it can automatically load pictures and download files.
# How to
To get a Git project into your build:
## Step 1. Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
## Step 2. Add the dependency

	dependencies {
          compile 'com.github.aliletter:onhttp:v1.0.5'
          
	}
  
## Step 3. Add the permission
---
```Java
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.INTERNET" />
```
---
# Instruction
## BitmapCache
```Java
//get size of bitmap in local device
BitmapCache.getInstance().size();
// clear all bitmap in local device
BitmapCache.getInstance().clear();
// set cache location 
BitmapCache.getInstance(String path);
```
## Code 
---
```Java
	// response automatically convert to javabean
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
```
---
```Java
	// response automatically convert to Bitmap
        OnHttp.getInstance().url(Constaint.PIC).clazz(Bitmap.class).listener(new IHttpListener<Bitmap>() {
            @Override
            public void onSuccess(Bitmap bitmap) {
                
            }

            @Override
            public void onError(int code) {

            }
        }).excute();
```
---
```Java
  OnHttp.getInstance().url(Constaint.PIC)
                .view(imageView)
                .excute();
```
---
```Java
 OnHttp.getInstance().url(Constaint.PIC)
 		//The image is not loaded successfully before the resource image appears, this method is optional
 		.id(R.mipmap.ic_launcher)
                .view(imageView)
                .excute();
```
---
```Java
	/Add the headerListener method to get the response header
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

```
