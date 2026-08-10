package com.mayank.reports_management.logging;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class ApiLoggingInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) {

        log.info(
                "REQUEST -> Method: {}, URI: {}, QueryParams: {}",
                request.getMethod(),
                request.getRequestURI(),
                request.getQueryString()
        );

        return true;
    }

    @Override
    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            Exception ex) {

        log.info(
                "RESPONSE -> Status: {}, URI: {}",
                response.getStatus(),
                request.getRequestURI()
        );

        if (ex != null) {
            log.error(
                    "ERROR -> URI: {}, Message: {}",
                    request.getRequestURI(),
                    ex.getMessage(),
                    ex
            );
        }
    }
}