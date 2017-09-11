# OnHttp
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
          compile 'com.github.mr-absurd:onhttp:v1.0.3'
          
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
## Class OnHttp
![](http://i2.bvimg.com/602270/103d0108588c90cb.png)

## BitmapCache
```Java
//get size of bitmap in local device
BitmapCache.getInstance.size();
// clear all bitmap in local device
BitmapCache.getInstance.clear();
```
