package com.dtcookie.util;

import javax.servlet.http.HttpServletRequest;

@FunctionalInterface
public interface HttpGetter {
    String doGet(HttpServletRequest req) throws Exception;
}
