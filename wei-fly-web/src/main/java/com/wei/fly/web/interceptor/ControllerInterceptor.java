package com.wei.fly.web.interceptor;

import com.alibaba.fastjson.JSON;
import com.wei.fly.interfaces.annotation.RequiredParam;
import com.wei.fly.interfaces.response.Result;
import com.wei.fly.util.ParameterUtil;
import com.wei.fly.util.ReflectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Controller拦截器
 */
@Aspect
@Component
@Slf4j
public class ControllerInterceptor {


    @Pointcut("execution(* com.wei.fly.web.controller.*.*(..))")
    private void excudeController() {
    }

    @Around("excudeController()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Throwable oe = null;
        Object request = null;
        Object response = null;
        String methodName = point.getSignature().getName();
        long startMs = System.currentTimeMillis();

        // 获取非get请求参数
        Object[] args = point.getArgs();
        if (ArrayUtils.isNotEmpty(args)) {
            for (Object obj : args) {
                if (!(obj instanceof HttpServletRequest || obj instanceof HttpServletResponse)) {
                    request = obj;
                    break;
                }
            }
        }

        try {
            // 必须参数校验
            Result<Object> responseEntity = checkRequiredParam(request);
            if (!"200".equals(String.valueOf(responseEntity.getStatus()))) {
                response = responseEntity;
                return response;
            }

            response = point.proceed();
            return response;
        } catch (Exception e) {
            oe = e;
            if (e instanceof InvocationTargetException) {
                oe = ((InvocationTargetException) e).getTargetException();
            }

            Result<Object> responseEntity = new Result<Object>();
            responseEntity.setStatus(5000);
            responseEntity.setMessage("服务端未知异常");
            response = responseEntity;


            return response;
        } finally {
            if (oe != null) {
                log.error(String.format("Request %s execute exception", methodName), oe);
                log.error(String.format("method=%s, times=%sms, req=%s, resp=%s", methodName, ((System.currentTimeMillis() - startMs)), JSON.toJSONString(request), JSON.toJSONString(response)));
            } else {
                log.info(String.format("method=%s, times=%sms, req=%s, resp=%s", methodName, ((System.currentTimeMillis() - startMs)), JSON.toJSONString(request), JSON.toJSONString(response)));
            }
        }
    }

    private Result<Object> checkRequiredParam(Object request ) {
        try {
            Pair<Boolean, String> pair =  checkPostRequiredParam(request);
            if (!pair.getPair1()) {
                Result<Object> response = new Result<>();
                response.setStatus(4999);
                response.setMessage( String.format("服务端参数校验异常：parameter %s is empty", pair.getPair2()));
                return response;
            }
        } catch (Exception e) {
            log.error("RequiredParam check failed", e);
            Result<Object> response = new Result<>();
            response.setStatus(5000);
            response.setMessage("服务端参数校验时异常");
            return response;
        }
        return new Result<>();
    }



    private static Pair<Boolean, String> checkPostRequiredParam(Object request) throws Exception {
        if (request != null) {
            List<Field> fields = ReflectionUtil.getFieldsWithAnnotation(request.getClass(), RequiredParam.class);
            for (Field f : fields) {
                if (ParameterUtil.hasNullOrBlank(f.get(request))) {
                    return new Pair<>(false, f.getName());
                }
            }
        }

        return new Pair<>(true, null);
    }


    private static class Pair<E, V> {

        private E pair1;

        private V pair2;

        public Pair(E pair1, V pair2) {
            this.pair1 = pair1;
            this.pair2 = pair2;
        }

        public E getPair1() {
            return pair1;
        }

        public V getPair2() {
            return pair2;
        }

    }

}
