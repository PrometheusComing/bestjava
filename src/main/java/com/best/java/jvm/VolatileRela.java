package com.best.java.jvm;

/**
 * @Author: xjxu3
 * @Date: 2020/1/17 9:08
 * @Description: Volatile相关说明
 * 作用：
 * 1.保证变量对所有线程的可见性（工作内存使用变量时，必须立刻强制从主内存获取最新值，修改后也要立刻同步回主内存）
 * 2.禁止指令重排（生成汇编层的一个指令，相当于内存屏障（重排序时不能把后面的指令重排序到内存屏障前））
 * 3.不保证原子性（比如i ++ 操作，虽然i的修改值所有线程可见，但是i ++ 本身这个操作
 * 有多条指令（单条字节码指令也未必有原子性，这里的描述时为了方便理解），不具备原子性。比如线程a，b前后拿到i的值0,a在修改i的值之前，i可能已经被b修改为1
 * .导致a又修改为1）
 * 使用场景:运算结果不依赖于变量的当前值
 *
 * 一般认为基本数据类型的读写具有原子性，long和double有非原子性协定（不过大多数虚拟机都实现了他两原子性，所以不用加volitale）
 *
 * A初始化配置后，B利用配置继续执行
 * 伪代码:
 * 线程A执行后，修改initFlag，让线程B操作
 * 1.Map map = ConfigMap()
 * 2.volatile boolean initFlag = false
 * 3.processConfig(map)
 * 4.initFlag = true
 *
 * 伪代码：
 * while(!initFlag){
 *     sleep()
 * }
 * doSomething()
 *
 * 如果initFlag不用volatile修饰，那么第四行代码就可能重排序到第三行代码之前，导致A还没有完成配置初始化，B就运行了
 * 最后报错
 *
 * 先行发生原则（happens-before）:简单理解方式，不符合的认为线程不安全
 * 1. 程序顺序规则：
 * func() {
 * int a = 1;
 * int b = a;
 * }
 * 同一个线程内，b 一定能看到 a 的变化
 *
 * 2. 监视器锁规则：
 * synchronized(obj) {
 * do something
 * }
 * 线程 A 申请锁，而在这之前的一纳秒，线程 B 正好释放了 obj 上的锁，那么 A 一定能觉察到 B 的释放
 *
 * 3. volatile 变量规则：
 * volatile a;
 * 线程对 a 的写入一定能被后面的其它线程看到，这里的后面是指时间上的后面
 *
 * 4. 中断规则：
 * t.interrupt();
 * 假如我调用这句话，那么 t 一定能觉察到自己中断状态的变化（catch 住 InterruptedException 或者 isInterrupted() 返回 true ）
 *
 * 5. 线程启动规则：
 * start() 方法是线程执行的第一个方法
 *
 * 6. 线程结束规则：
 * 当 join() 成功返回，或者 isAlive() 返回 false 时，线程所做的工作一定全都结束了
 *
 * 7. 终结器规则：
 * 对象的构造函数必须在其 finalize() 执行之前完成
 *
 * 8. 传递性
 * A先行于B,B先行于C,那么A先行于C
 */
public class VolatileRela {
}
