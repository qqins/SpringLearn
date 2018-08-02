package edu.cqupt.eini.chapter03.aspectj.annotation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @author: Hello World
 * @date: 2018/8/2 10:09
 */
@Aspect
@Component
public class MyAspect {
    @Pointcut("execution(* edu.cqupt.eini.chapter03.jdk.*.*(..))")
    private void myPointCut() {

    }
    //前置通知
    @Before("myPointCut()")
    public void myBefore(JoinPoint joinPoint) {
        System.out.print("前置通知：模拟执行权限检查...,");
        System.out.print("目标类是：" + joinPoint.getTarget());
        System.out.println(", 被织入增强处理的目标方法为：" + joinPoint.getSignature().getName());
    }

    //后置通知
    @AfterReturning("myPointCut()")
    public void myAfterReturning(JoinPoint joinPoint) {
        System.out.print("后置通知：模拟记录日志...,");
        System.out.println("被织入增强处理的目标方法为：" + joinPoint.getSignature().getName());
    }

    /**
     * 环绕通知
     * ProceedingJoinPoint是JoinPoint子接口，表示可以执行目标方法
     * 1, 必须是Object类型的返回值
     * 2. 必须接受一个参数, 类型为ProceedingJoinPoint
     * 3. 必须throws Throwable
     */
    @Around("myPointCut()")
    public Object myAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("环绕开始: 执行目标方法之前, 模拟开启事物...");
        //执行当前目标方法
        Object obj = proceedingJoinPoint.proceed();
        System.out.println("环绕结束: 执行目标方法之后, 模拟关闭事务...");
        return obj;
    }

    //异常通知
    @AfterThrowing(value = "myPointCut()",throwing = "throwable")
    public void myAfterThrowing(JoinPoint joinPoint, Throwable throwable) {
        System.out.println("异常通知: " + "出错了" + throwable.getMessage());
    }

    //最终通知
    @After("myPointCut()")
    public void myAfter() {
        System.out.println("最终通知: 模拟方法结束后的释放资源...");
    }
}
