# OnHttp  [![](https://jitpack.io/v/aliletter/onhttp.svg)](https://jitpack.io/#aliletter/onhttp)
OnHttp is composed of four parts: Imageloader, Uploader, Downloader, and Httploader. The corresponding functions are image loading, file uploading, file downloading, and network request. The data returned by the network request can be automatically converted to Json, JavaBean, File, Bitmap, and so on. Onhttp is easy to use, and all of the network requests are asynchronous.[中文文档](https://github.com/aliletter/OnHttp/blob/master/README_CHINESE.md)
## Instruction
Imageloader can set the images that are displayed during the loading process and load the failed pictures. Uploader currently only supports ordinary uploads, and breakpoint uploads are not yet supported. Downloader can set the callback, monitor the download progress, download the completion and so on. Httploader supports the adding of the request head, the request body, the setting of the request, and so on.
### Code Sample
Before use, please initialize the OnHttp. The location of the Imageloader's cache in the local location can be configured by initialing.(OnHttp.initDefault())
```Java
        OnHttp.imageLoader()
                .view(ImageView)
                .url(String)
                .defaultId(Integer)
                .errorId(Integer)
                .executor();
```
```Java
	OnHttp.downLoader()
                .file(File) //File does not exist,this path stores the downloaded files
                .url(String)
                .listener(new IDownListener() {
                    @Override
                    public void onProgress(float progress) { 
                    }

                    @Override
                    public void onSuccess(File file) { 
                    }

                    @Override
                    public void onError(int code) {
                    }
                }).executor();
```
```Java
        OnHttp.httpLoader()
                .body(Map<String,String>)
                .clazz(Example.class)
                .header(Map<String,String>)
                .headerListener(new IHeaderListener() {
                    @Override
                    public void onHeader(Map<String, List<String>> headers) {

                    }
                })
                .listener(new IHttpListener<Example>() {

                    @Override
                    public void onSuccess(Example o) { 
                    }

                    @Override
                    public void onError(int code) {
                    }
                }).executor();
```
```Java
        OnHttp.upLoader()
                .url(String)
                .body(Map<String,String>)
                .file(File)
                .header(Map<String,String>)
                .clazz(Example.class)
                .listener(new IUpListener<Example>() {
                    @Override
                    public void onProgress(float progress) { 
                    }

                    @Override
                    public void onSuccess(Example obj) { 
                    }

                    @Override
                    public void onError(int code) { 
                    }
                }).executor();
```
## How to
To get a Git project into your build:
### Step 1. Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories.[click here for details](https://github.com/aliletter/CarouselBanner/blob/master/root_build.gradle.png)

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
### Step 2. Add the dependency
Add it in your application module build.gradle at the end of dependencies where you want to use.   [click here for details](https://github.com/aliletter/CarouselBanner/blob/master/application_build.gradle.png)
```Java
	dependencies {
	  ...
          compile 'com.github.aliletter:onhttp:v1.2.7'
	}
```	
### Step 3. Add the permission
Add it in your application AndroidManifest.xml in the manifest label.   [click here for details](https://github.com/aliletter/OnHttp/blob/master/androidmanifest.gradle.png)
```Java
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.INTERNET" />
```
## Thank you for your browsing
If you have any questions, please join the QQ group. I will do my best to answer it for you. Welcome to star and fork this repository, alse follow me.
<br>
![Image Text](https://github.com/aliletter/CarouselBanner/blob/master/qq_group.png)
