package com.hacknife.onhttp.core;

import java.util.List;
import java.util.Map;

/**
 * author  : Hacknife
 * e-mail  : 4884280@qq.com
 * github  : http://github.com/hacknife
 * project : OnHttp
 */

public interface IHeaderListener {
    void onHeader(Map<String, List<String>> headers);
}
