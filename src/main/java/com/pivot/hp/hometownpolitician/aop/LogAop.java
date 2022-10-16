package com.pivot.hp.hometownpolitician.aop;

import graphql.com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Aspect
@Component
public class LogAop {

    @Around("within(com.pivot.hp.hometownpolitician.controller..*)")
    public Object log(ProceedingJoinPoint pjp) throws Throwable {
        Long start = System.currentTimeMillis();
        Object returnObj = pjp.proceed();
        Long end = System.currentTimeMillis();
        logRequest(pjp);
        logResponse(pjp, returnObj);
        logSummary(pjp, start, end);
        return returnObj;
    }

    private void logRequest(JoinPoint joinPoint) {
        log.info("REQUEST of {} ({})\n-> {}", getDeclaringTypeName(joinPoint), getMethod(joinPoint).getName(), getParams(joinPoint));
    }

    private void logResponse(JoinPoint joinPoint, Object returnObj) {
        log.info("RESPONSE of {} ({})\n-> {}", getDeclaringTypeName(joinPoint), getMethod(joinPoint).getName(), getReturnObj(returnObj));
    }

    private void logSummary(JoinPoint joinPoint, Long start, Long end) {
        log.info("SUMMARY of {} ({})\n-> {}", getDeclaringTypeName(joinPoint), getMethod(joinPoint).getName(), getSummary(start, end));
    }

    private Method getMethod(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        return signature.getMethod();
    }

    private String getDeclaringTypeName(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        return signature.getDeclaringTypeName();
    }

    private String getParams(JoinPoint joinPoint) {
        Stream<Object> stream = Arrays.stream(joinPoint.getArgs());
        if (joinPoint.getArgs().length == 0) {
            return "No Parameter";
        }
        return "[\n" + getParamsInternal(stream) + "\n]";
    }

    private String getParamsInternal(Stream<Object> stream) {
        return stream.map(e -> {
            Object[] values = new Object[1];
            values[0] = e;
            return String.format("\t%s -> %s", e.getClass().getSimpleName(), Joiner.on(",").join(values));
        }).collect(Collectors.joining(",\n"));
    }

    private Object getReturnObj(Object returnObj) {
        Object[] values = new Object[1];
        values[0] = returnObj;
        String obj = String.format("\t%s -> %s", returnObj.getClass().getSimpleName(), Joiner.on(",").join(values));
        return "{\n" + obj + "\n}";
    }

    private Object getSummary(Long start, Long end) {
        return "{\n" + getSummaryInternal(start, end) + "\n}";
    }

    private Object getSummaryInternal(Long start, Long end) {
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return new Summary(req, start, end);
    }

    static class Summary {
        String addr;
        String uri;
        String method;
        Long latency;

        public Summary(HttpServletRequest req, Long start, Long end) {
            addr = getIpAddress(req);
            uri = getUri(req);
            method = getMethod(req);
            latency = end - start;
        }

        private String getIpAddress(HttpServletRequest req) {
            Optional<String> ip = Optional.ofNullable(req.getHeader("X-FORWARDED-FOR"));
            return ip.orElse(req.getRemoteAddr());
        }

        private String getUri(HttpServletRequest req) {
            Optional<String> uri = Optional.ofNullable(req.getRequestURI());
            return uri.orElse("");
        }

        private String getMethod(HttpServletRequest req) {
            Optional<String> method = Optional.ofNullable(req.getMethod());
            return method.orElse("");
        }

        public String toString() {
            return "\taddr -> " + addr + "\n\turi -> " + uri + "\n\tmethod -> " + method + "\n\tlatency -> " + Long.toString(latency);
        }
    }

}
