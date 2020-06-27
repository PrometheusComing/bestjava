package com.best.java.aop;

import com.best.java.annotation.MyAnno;
import com.best.java.controller.Animal;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


/**
 * @Author: xjxu3
 * @Date: 2019/12/9 15:48
 * @Description:
 * 执行顺序
 * Around --> Before --> Around --> After --> AfterReturning --> AfterThrowing
 * 原理：将建言转换成各个MethodInterceptor mi，职责链中按照after,around，before的顺序放入对应的mi
 * 也就是 after的大致内容是 {1 mi.proceed();2 sout}  sout在这里代表各个拦截器增强的内容,mi.proceed()表示调用下一个拦截器,没拦截器就
 *       around为        {3 sout;4 mi.proceed();5 sout}                                                直接调用目标方法
 *       before为        {6 sout;7 mi.proceed();}
 *       目标方法内容为    {8 sout}  这里sout就是目标方法的原始内容
 *       执行顺序就是 1->3->4->6->7->8->5->2
 *       注意：around里的pjp.proceed其实就是mi.proceed()
 */
@Aspect
@Component
// 配置在application.properties中，true则有效
 @ConditionalOnProperty(value="method.interceptor")
public class MyAspect {

	/**
	 * 这句话是方法切入点
	 * 1 execution (* com.best.java..*.*(..)))
	 * 2 execution ： 表示执行
	 * 3 第一个*号 : 表示返回值类型， *可以是任意类型
	 * 4 io.mykit.annotation.spring.aop.service.impl : 代表扫描的包
	 * 5 .. : 代表其底下的子包也进行拦截
	 * 6 第二个*号 : 代表对哪个类进行拦截，*代表所有类
	 * 7 第三个*号 : 代表方法  *代表任意方法
	 * 8 (..) : 代表方法的参数有无都可以
	 */
//	@Pointcut(value = "execution(* com.best.java.controller.*.*(..))")
//	public void methodIntercept() {
//	}

//	@Pointcut("@annotation(com.best.java.annotation.MyAnno)")
	@Pointcut("@within(com.best.java.annotation.MyAnno)")
	public void methodIntercept() {
	}

	@Around(value = "methodIntercept() && @within(myAnnos)")
	public Object doAround(ProceedingJoinPoint pjp, MyAnno myAnnos) throws Throwable {
		Object[] objects = pjp.getArgs();
		for (Object o : objects) {
			if (o instanceof Animal) {
				Animal pageObj = (Animal) o;
				pageObj.setName("intercept dog");
			}
		}
		Object o = pjp.proceed(objects);
//		System.out.println("it is Around before");
//		long begin = System.nanoTime();
//		Object o = pjp.proceed();
//		long end = System.nanoTime();
//		System.out.println(pjp.toShortString() + "use" + (end - begin) / 1000000 + "ms");
//		System.out.println("it is Around after");
		return o;
	}

	//在某连接点（JoinPoint）之前执行的通知，但这个通知不能阻止连接点前的执行
	@Before(value = "methodIntercept()&& @annotation(myAnnos)")
	public void beforeAction(JoinPoint joinPoint,MyAnno myAnnos) {
		Object[] objects = joinPoint.getArgs();
		for (Object o : objects) {
			if (o instanceof Animal) {
				Animal pageObj = (Animal) o;
				pageObj.setName("intercept dog");
			}
		}
	}
//
//	//当某连接点退出的时候执行的通知（不论是正常返回还是异常退出）
//	@After(value = "methodIntercept()&& @annotation(MyAnno)")
//	public void afterAction(JoinPoint joinPoint) {
//		System.out.println("it is after");
//
//	}
//
//	//在某连接点正常完成后执行的通知，不包括抛出异常的情况。
//	@AfterReturning("methodIntercept() && @annotation(MyAnno)")
//	public void afterReturn(JoinPoint joinPoint){
//		System.out.println("it is AfterReturning");
//	}
//	//在方法抛出异常退出时执行的通知
//	@AfterThrowing(pointcut="methodIntercept()&& @annotation(MyAnno)", throwing="ex")
//	public void afterThrow(JoinPoint joinPoint, Exception ex){
//		System.out.println("it is AfterThrowing");
//	}

}
