package com.ssz.blog.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @author sushuaizhen
 * @date 2020/7/23
 */
@Aspect
@Component
public class LogAspect {

    private  final Logger logger = LoggerFactory.getLogger(this.getClass());
    //表示切面   execution规定这个切面拦截哪些类
    //第一个*表示不管公有还是私有都要拦截   web里所有的东西都被拦截
    @Pointcut("execution(* com.ssz.blog.web.*.*(..))")
    public void log(){

    }

    @Before("log()")
    public void doBefore(JoinPoint jointPoint){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        String classMethod = jointPoint.getSignature().getDeclaringTypeName()+"."+ jointPoint.getSignature().getName();
        Object[] args = jointPoint.getArgs();

        RequestLog requestLog = new RequestLog(url,ip, classMethod,args);
        logger.info("Request: {}", requestLog);
    }

    @After("log()")
    public void doAfter(){
        logger.info("========doAfter==========");
    }

    @AfterReturning(returning = "result", pointcut = "log()")
    public void doAfterReturn(Object result){
        logger.info("result: {}" ,result);
    }

    private class RequestLog {
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;

        public RequestLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "RequestLog{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }
}
