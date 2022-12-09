package com.electronic.controller;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;

public class BaseController {
    /**
     * @Description 获取请求参数并封装
     * @Param request
     * @Return java.util.Map<java.lang.String, java.lang.Object>
     * @Author electroNic
     * @Date 2022/11/29 4:07
     */
    protected Map<String, Object> getParams(HttpServletRequest request) {
        Enumeration<String> paramNames = request.getParameterNames();
        Map<String, Object> filters = new TreeMap();
        while (paramNames != null && paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            String[] values = request.getParameterValues(paramName);
            if (values != null && values.length != 0) {
                if (values.length > 1) {
                    filters.put(paramName, values);
                } else {
                    filters.put(paramName, values[0]);
                }
            }
        }
        if (!filters.containsKey("pageNum")) {
            filters.put("pageNum", 1);
        }
        if (!filters.containsKey("pageSize")) {
            filters.put("pageSize", 3);
        }
        return filters;
    }
}
