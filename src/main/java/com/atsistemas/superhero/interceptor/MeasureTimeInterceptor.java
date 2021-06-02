package com.atsistemas.superhero.interceptor;

import com.atsistemas.superhero.annotation.MeasureTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class MeasureTimeInterceptor implements HandlerInterceptor {

    private static Logger log = LoggerFactory.getLogger(MeasureTimeInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            if (handlerMethod.getMethod().isAnnotationPresent(MeasureTime.class)) {
                log.info(((HandlerMethod)handler).getBean().getClass().getName());
                log.info(((HandlerMethod)handler).getMethod().getName());
                request.setAttribute("startTime", LocalDateTime.now());
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            if (handlerMethod.getMethod().isAnnotationPresent(MeasureTime.class)) {
                LocalDateTime startTime = (LocalDateTime) request.getAttribute("startTime");
                LocalDateTime finishTime = LocalDateTime.now();
                Duration duration = Duration.between(startTime, finishTime);
                log.info("Time:" + (duration.getNano() / 1000000) + " milliseconds");
            }
        }


    }

}
