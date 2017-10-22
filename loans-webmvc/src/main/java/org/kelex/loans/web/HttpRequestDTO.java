package org.kelex.loans.web;

import org.kelex.loans.core.dto.RequestDTO;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by hechao on 2017/8/29.
 */
public class HttpRequestDTO<T> extends RequestDTO<T> {
    public HttpServletRequest getRequest(){
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }
}
