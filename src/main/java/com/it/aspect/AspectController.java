package com.it.aspect;

import com.it.entity.Logs;
import com.it.service.LogsService;
import com.it.vo.LoginUserVO;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;



@Aspect
@Component
public class AspectController {

    @Autowired
    private LogsService logsservice;

    @Pointcut("@annotation(com.it.aspect.SysLog)")
    public void logPointCut() {}


   @Around("logPointCut()")
    public Object aroundLog(ProceedingJoinPoint point) throws Throwable {

       Object proceed = point.proceed();
       saveLog(point);
       return proceed;
    }

    /**
     * 保存日志
     * @param joinPoint
     * @param
     */
    private void saveLog(ProceedingJoinPoint joinPoint) {
        // 获取签名
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        SysLog sysLog = method.getAnnotation(SysLog.class);
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String funcName = signature.getName();
        Logs logs = new Logs();
        if(sysLog != null) {
            //注解上的描述
            logs.setType(sysLog.value());
        }
        LoginUserVO user= (LoginUserVO) SecurityUtils.getSubject().getPrincipal();
        if (null!=user) {
            logs.setLtime(new Date());
            logs.setUname(user.getUser().getUsername());
            logs.setIp(request.getRemoteAddr());
            logs.setContent("执行的方法：" + funcName);
            //记录日志（姓名、操作时间、登陆ip、操作、操作类型）
            logsservice.save(logs);
        }
    }



}
