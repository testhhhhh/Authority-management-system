package com.lear.controller;

import com.lear.domain.SysLog;
import com.lear.service.ISysLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

@Component
@Aspect
public class LogAop {
    @Autowired
    HttpServletRequest request = null;
    @Autowired
    ISysLogService sysLogService;
    //访问的开始时间
    private Date visitTime;
    //访问的类
    private Class clazz;
    //访问的方法
    private Method method;

    @Pointcut("execution(* com.lear.controller.*.*(..))")
    private void pointCut(){};

    //前置通知，只要是获取开始时间，执行的类，访问的方法
    @Before("pointCut()")
    public void doBefore(JoinPoint jp) throws NoSuchMethodException {
        visitTime = new Date();//当前时间就是开始访问的时间
        clazz = jp.getTarget().getClass();//集体要访问的类对象
        String methodName = jp.getSignature().getName();//获取访问的方法的名称
        Object[] obj = jp.getArgs();//获取访问方法的参数

        //获取具体的执行方法的method对象
        if(obj==null||obj.length==0){
            method = clazz.getMethod(methodName);
        }
        else{
            Class[] classes = new Class[obj.length];
            for(int i=0;i<obj.length;i++){
                classes[i] = obj[i].getClass();
            }
            method = clazz.getMethod(methodName, classes);
        }

    }
    @After("pointCut()")
    public void doAfter(JoinPoint jp) throws Exception {
        long time = new Date().getTime() - visitTime.getTime();//获取了访问的时长
        String url="";
        //获取url
        if(clazz!=null&&method!=null&&clazz!= LogAop.class){
            //获取类上的RequestMapping的值
            RequestMapping classAnnotation = (RequestMapping) clazz.getAnnotation(RequestMapping.class);
            if(classAnnotation!=null){
                String[] classValue = classAnnotation.value();

                //获取方法上到RequestMapping的value值
                RequestMapping methodAnnotation  = method.getAnnotation(RequestMapping.class);
                if(methodAnnotation!=null){
                    String[] methodValue = methodAnnotation.value();
                    url=classValue[0]+methodValue[0];
                    //获取访问的ip
                    String ip = request.getRemoteAddr();

                    //获取操作者
                    SecurityContext context = SecurityContextHolder.getContext();//从上下文中获取当前登录的用户
//        User user1 = (User) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
                    User user = (User) context.getAuthentication().getPrincipal();
                    String userName = user.getUsername();

                    //将日志相关信息封装到SysLog对象中
                    SysLog sysLog = new SysLog();
                    sysLog.setExecutionTime(time);
                    sysLog.setUrl(url);
                    sysLog.setIp(ip);
                    sysLog.setUsername(userName);
                    sysLog.setVisitTime(visitTime);
                    sysLog.setMethod("[类名]  "+clazz.getName()+"[方法名]  "+method.getName());
                    System.out.println(sysLog);
                    //调用Service完成数据库的insert操作
                    sysLogService.save(sysLog);
                }
            }
        }


    }
}
