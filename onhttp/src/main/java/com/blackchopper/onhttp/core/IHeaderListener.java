package com.blackchopper.onhttp.core;

import java.util.List;
import java.util.Map;

/**
 * author  : Black Chopper
 * e-mail  : 4884280@qq.com
 * github  : http://github.com/BlackChopper
 * project : OnHttp
 */

public interface IHeaderListener {
    void onHeader(Map<String, List<String>> headers);
}
