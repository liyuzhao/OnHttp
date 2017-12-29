# OnHttp  [![](https://jitpack.io/v/aliletter/onhttp.svg)](https://jitpack.io/#aliletter/onhttp)
OnHttp由四部分组成，分别是Imageloader，Uploader，Downloader和Httploader。对应的功能为图片加载，文件上传，文件下载，网络请求。网络请求返回的数据可以通过设置自动转换为Json，JavaBean，File，Bitmap等。Onhttp使用简单，而且所有的网络请求均为异步请求。
## 使用说明
Imageloader可以设置加载过程中显示的图片，加载失败的图片。Uploader目前只支持普通上传，断点上传目前尚未支持。Downloader可以设置回调，监听下载进度，下载完成等。Httploader支持添加请求头，请求体，设置请求方式等。
### 代码示例
在使用之前，请初始化OnHttp。可以通过初始化配置Imageloader的缓存在本地中的位置。(OnHttp.initDefault())
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
## 如何配置
将本仓库引入你的项目:
### Step 1. 添加JitPack仓库到Build文件
合并以下代码到项目根目录下的build.gradle文件的repositories尾。[点击查看详情](https://github.com/aliletter/CarouselBanner/blob/master/root_build.gradle.png)

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
### Step 2. 添加依赖
合并以下代码到需要使用的application Module的dependencies尾。[点击查看详情](https://github.com/aliletter/CarouselBanner/blob/master/application_build.gradle.png)
```Java
	dependencies {
	  ...
          compile 'com.github.aliletter:onhttp:onhttp:v1.3.0'
	}
```	
如果你需要配合Rxjava2使用，请按照以下方式合并.
```Java
	dependencies {
	  ...
          compile 'com.github.aliletter:onhttp:onhttp:v1.3.0'
          compile 'com.github.aliletter:onhttp:rxhttp:v1.3.0'
          compile "io.reactivex.rxjava2:rxjava:2.1.8"

	}
```	
### Step 3. 添加权限
合并以下代码到应用的AndroidManifest.xml的manifest标签中。[点击查看详情](https://github.com/aliletter/OnHttp/blob/master/androimanifest.png)
```Java
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.INTERNET" />
```
## 感谢浏览
如果你有任何疑问，请加入QQ群，我将竭诚为你解答。欢迎Star和Fork本仓库，当然也欢迎你关注我。
<br>
![Image Text](https://github.com/aliletter/CarouselBanner/blob/master/qq_group.png)
