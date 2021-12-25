package com.egs.bank.aop;

import com.egs.bank.annotations.AuthParam;
import com.egs.bank.model.response.EGSResponse;
import com.egs.bank.security.jwt.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Aspect
@Component
@Slf4j
@ResponseBody
public class JwtAspect {

    private final JwtUtils jwtUtils;

    public JwtAspect(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Around("execution(* com.egs.bank.controller.*.*(..))")
    public EGSResponse validateJwt(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes != null ? attributes.getRequest() : null;
        Object[] args = joinPoint.getArgs();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        var parameterAnnotations = method.getParameterAnnotations();
        int paramIndex = -1;
        for (int i = 0; i < parameterAnnotations.length; i++) {
            var ano = parameterAnnotations[i][0];
            if (ano instanceof AuthParam) {
                paramIndex = i;
                break;
            }
        }
        if (paramIndex == -1) {
            return (EGSResponse) joinPoint.proceed();
        }

        Long cardId = jwtUtils.getCardIdFromJwtToken(jwtUtils.parseJwt(request));
        args[paramIndex] = cardId;

        return (EGSResponse) joinPoint.proceed(args);
    }

}
