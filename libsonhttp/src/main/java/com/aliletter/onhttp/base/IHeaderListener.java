package com.aliletter.onhttp.base;

import java.util.List;
import java.util.Map;

/**
 * Author: aliletter
 * Github: http://github.com/aliletter
 * Data: 2017/9/12.
 */

public interface IHeaderListener {
    void onHeader(Map<String, List<String>> headers);
}
