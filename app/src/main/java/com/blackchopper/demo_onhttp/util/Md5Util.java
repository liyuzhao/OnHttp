package com.blackchopper.demo_onhttp.util;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * author  : Black Chopper
 * e-mail  : 4884280@qq.com
 * github  : http://github.com/BlackChopper
 * project : OnHttp
 */

public class Md5Util {

    public static String decode(String content) {
        try {

            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] bs = digest.digest(content.getBytes());
            String hexString = "";
            for (byte b : bs) {
                int temp = b & 255;
                if (temp < 16 && temp >= 0) {
                    hexString = hexString + "0" + Integer.toHexString(temp);
                } else {
                    hexString = hexString + Integer.toHexString(temp);
                }
            }
            return hexString;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }


    public static String mapToSign(Map<String, String> map) {

        Map<String, String> treemap = new TreeMap<String, String>(new Comparator<String>() {
            @Override
            public int compare(String s, String t1) {
                return s.compareTo(t1);
            }
        });
        treemap.putAll(map);
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> entry : treemap.entrySet()) {
            builder.append(entry.getKey()).append("=").append(entry.getValue());
        }
        return decode(builder.toString() + APPKEY);
    }

    public final static String APPID = "adroid123456";
    public final static String APPKEY = "d0649a7e47f2a65ffbc9a95e7673c2b6";

    public static Map<String, String> mapToBody(Map<String, String> info) {
        info.put("appid", APPID);
        Map<String, String> treemap = new TreeMap<String, String>(new Comparator<String>() {
            @Override
            public int compare(String s, String t1) {
                return s.compareTo(t1);
            }
        });
        treemap.putAll(info);
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> entry : treemap.entrySet()) {
            builder.append(entry.getKey()).append("=").append(entry.getValue());
        }
        info.put("sign", Md5Util.decode(builder.toString() + APPKEY));
        return info;
    }
}
