package com.best.java.pattern.proxy;

import org.springframework.cglib.core.DebuggingClassWriter;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @Author: xjxu3
 * @Date: 2019/4/9 17:36
 * @Description:
 *
 * 通过asm技术创建代理类，代理类继承被代理类，并包含MethodInterceptor引用，所有非final方法都被继承，调用代理类的方法时，会进入
 * MethodInterceptor的intercept方法，并把代理对象，方法对象，方法参数，方法代理对象传入执行
 * 部分代理类的代码：
 *   final void CGLIB$test$2() {
 *           super.test();
 *       }
 *
 *   public final void test() {
 *           //MethodInterceptor引用
 *           MethodInterceptor var10000 = this.CGLIB$CALLBACK_0;
 *           if (var10000 == null) {
 *               CGLIB$BIND_CALLBACKS(this);
 *               var10000 = this.CGLIB$CALLBACK_0;
 *           }
 *
 *           if (var10000 != null) {
 *               var10000.intercept(this, CGLIB$test$2$Method, CGLIB$emptyArgs, CGLIB$test$2$Proxy);
 *           } else {
 *               super.test();
 *           }
 *       }
 *
 * Cglib动态代理执行代理方法效率之所以比JDK的高是因为Cglib采用了FastClass机制，它的原理简单来说就是：为代理类和被代理类各生成一个Class，
 * 这个Class会为代理类或被代理类的方法分配一个index(int类型)。
 * 这个index当做一个入参，FastClass就可以直接定位要调用的方法直接进行调用，这样省去了反射调用，所以调用效率比JDK动态代理通过反射调用高
 *
 * 创建MethodProxy
 *    Class c1, 被代理对象
 *    Class c2, 代理对象
 *    String desc, 参数列表描述
 *    String name1, 被代理方法
 *    String name2,代理方法
 *  public static MethodProxy create(Class c1, Class c2, String desc, String name1, String name2) {
 *         MethodProxy proxy = new MethodProxy();
 *         //创建方法签名
 *         proxy.sig1 = new Signature(name1, desc);
 *         proxy.sig2 = new Signature(name2, desc);
 *         //创建createInfo
 *         proxy.createInfo = new CreateInfo(c1, c2);
 *         return proxy;
 *     }
 *
 * MethodProxy类的部分方法
 *
 * public Object invokeSuper(Object obj, Object[] args) throws Throwable {
 *         try {
 *             //初始化，创建了两个FastClass类对象，并根据原来的方法签名得到方法索引
 *             init();
 *             //这个对象持有两个FastClass类对象和方法的索引
 *             FastClassInfo fci = fastClassInfo;
 *             //调用了代理对象FastClass的invoke方法
 *             return fci.f2.invoke(fci.i2, obj, args);
 *         } catch (InvocationTargetException e) {
 *             throw e.getTargetException();
 *         }
 *     }
 *     private void init(){
 *         if (fastClassInfo == null){
 *             synchronized (initLock){
 *                 if (fastClassInfo == null){
 *                     CreateInfo ci = createInfo;
 *                     FastClassInfo fci = new FastClassInfo();
 *                     //helper方法用ASM框架去生成了两个FastClass类
 *                     fci.f1 = helper(ci, ci.c1);
 *                     fci.f2 = helper(ci, ci.c2);
 *                     fci.i1 = fci.f1.getIndex(sig1);
 *                     fci.i2 = fci.f2.getIndex(sig2);
 *                     fastClassInfo = fci;
 *                     createInfo = null;
 *                 }
 *             }
 *         }
 *     }
 * private static class FastClassInfo{
 *     FastClass f1;//被代理对象FastClass
 *     FastClass f2;//代理对象FastClass
 *     int i1;//被代理test方法的索引
 *     int i2; //代理test方法的索引
 * }
 */
public class EnhancerDemo {

	public void test() {
		System.out.println("EnhancerDemo test()");
	}

	private static class MethodInterceptorImpl implements MethodInterceptor {

		@Override
		//o表示代理对象
		//method表示被代理方法
		//objects表示要被代理方法的参数
		//methodProxy表示要触发父类的方法代理对象
		public Object intercept(Object obj, Method method, Object[] args,
								MethodProxy proxy) throws Throwable {
			System.out.println("Before invoke " + method);
			// 最终调用的是代理类的fastclass的invoke方法，在方法中通过匹配方法索引，直接调用该方法
			Object result = proxy.invokeSuper(obj, args);
			//proxy.invoke(obj, args)其实就是调用的被代理类的fastclass
//			Object result = proxy.invoke(obj, args);
			System.out.println("After invoke" + method);
			return result;
		}
	}

	public static void main(String[] args) {
		//代理类class文件存入本地磁盘
		System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "C:\\Users\\prometheus\\Desktop");
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(EnhancerDemo.class);
		enhancer.setCallback(new MethodInterceptorImpl());
		EnhancerDemo demo = (EnhancerDemo) enhancer.create();
		demo.test();
	}
}
