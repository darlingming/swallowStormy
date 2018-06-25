package com.software.dm.swallow.stormy.ssm.framework;

import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FWDispatcherServlet extends DispatcherServlet {

    protected ModelAndView processHandlerException(HttpServletRequest request, HttpServletResponse response,
                                                   Object handler, Exception ex) throws Exception {
        if (ex instanceof HttpRequestMethodNotSupportedException) {
            return new ModelAndView("jumpMain");
        } else if (ex instanceof UncategorizedSQLException) {
            return new ModelAndView("jumpLogin");
        } else {
            return super.processHandlerException(request, response, handler, ex);
        }
    }
}
