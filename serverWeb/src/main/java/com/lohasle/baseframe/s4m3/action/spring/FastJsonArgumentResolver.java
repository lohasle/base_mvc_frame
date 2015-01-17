package com.lohasle.baseframe.s4m3.action.spring;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lohasle.baseframe.s4m3.action.spring.Bean.FastJson;
import com.lohasle.baseframe.s4m3.action.spring.Bean.PageParam;
import com.lohasle.baseframe.s4m3.action.spring.Bean.JSONObjectWrapper;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by fule on 14-6-7.
 *
 */
public class FastJsonArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(FastJson.class) != null;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {

        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        // content-type不是json的不处理
  /*      if (!request.getContentType().contains("application/json")) {
            return null;
        }*/

        BufferedReader reader = request.getReader();
        StringBuilder sb = new StringBuilder();

        char[] buf = new char[1024];
        int rd;
        while((rd = reader.read(buf)) != -1){
            sb.append(buf, 0, rd);
        }

        // 利用fastjson转换为对应的类型
        if(JSONObjectWrapper.class.isAssignableFrom(parameter.getParameterType())){
            JSONObjectWrapper jsonObjectWrapper = new JSONObjectWrapper(JSON.parseObject(sb.toString()));
            JSONObject jsonObject = jsonObjectWrapper.getJSONObject();

            Type genType = parameter.getGenericParameterType();
            Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
            //设置分页模型
            PageParam pageParam = new PageParam();
            pageParam.setQryParams(((Class<Object>) params[0]).newInstance()); //先设置一个obj
            if(jsonObject!=null){
                if(jsonObject.containsKey("pageNum")){
                    pageParam.setPageSize(jsonObject.getInteger("pageSize"));
                    pageParam.setPageNum(jsonObject.getInteger("pageNum"));
                    pageParam.setQryParams(jsonObject.getObject("qryParams", (Class<Object>) params[0]));
                    pageParam.setJsonObject(jsonObject);
                    return pageParam;
                }else{
                    return jsonObjectWrapper;
                }
            }else {
                return pageParam;
            }
        } else {
            return JSON.parseObject(sb.toString(), parameter.getParameterType());
        }
    }
}

