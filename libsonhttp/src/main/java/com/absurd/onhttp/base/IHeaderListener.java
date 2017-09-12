package com.absurd.onhttp.base;

import java.util.List;
import java.util.Map;

/**
 * Author: mr-absurd
 * Github: http://github.com/mr-absurd
 * Data: 2017/9/12.
 */

public interface IHeaderListener {
    void onHeader(Map<String, List<String>> headers);
}
