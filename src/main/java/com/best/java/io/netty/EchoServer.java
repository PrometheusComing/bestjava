package com.best.java.io.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 可以参考https://segmentfault.com/a/1190000007283053系列文章
 * 总体概述:
 * 创建NioEventLoopGroup并设置关键属性，如线程数组children(数组元素是NioEventLoop，每个NioEventLoop的属性设置，如selector、executor(
 * 就是线程执行器)、taskQueue)、线程选择器chooser、线程执行器（ThreadPerTaskExecutor，可以看成是线程工厂的包装）
 * ——>创建ServerBootstrap(服务端)或者Bootstrap(客户端)，服务端绑定bossGroup和workGroup（两个都是NioEventLoopGroup）,客户端绑定workGroup
 * ——>调用channel方法创建用来生产指定channel类型（客户端服务端不同）的工厂类
 * ——>服务端bind()和客户端connect()里分别创建NioServerSocketChannel和NioSocketChannel，并完成初始化及注册
 * 其中，服务端的pipeline会加一个ServerBootstrapAcceptor用来处理客户端的连接，获取NioSocketChannel并选择workGroup的线程与之绑定，并
 * 注册read事件。bind()主要是将NioServerSocketChannel的accept事件注册到bossGroup的线程的selector上。后续连接事件到来，会获取
 * NioSocketChannel并选择workGroup的线程上的selector注册read事件。
 *
 * ps:NioEventLoop的run就是轮询操作，其中 processSelectedKeys()——>processSelectedKeysOptimized()
 * ——>processSelectedKey(k, (AbstractNioChannel) a);方法里有对各类兴趣事件的处理
 *
 *
 * 重点——>多看NioEventLoopGroup、NioEventLoop、NioSocketChannel、NioServerSocketChannel的用例图
 *
 * 服务端channel:NioServerSocketChannel,观察用例图，继承AbstractNioChannel类的属性SelectableChannel，就是jdk的channel
 * 客户端channel:NioSocketChannel,同上，所以netty的channel底层封装了jdk的channel
 * 要仔细看两个channel的用例图
 * AbstractChannel中有unsafe,pipeline,eventLoop三个属性
 *
 * 1.new NioEventLoopGroup()过程
 * NioEventLoopGroup继承父类MultithreadEventExecutorGroup的EventExecutor[] children和EventExecutorChooser两个重要属性
 * new NioEventLoopGroup()执行构造器后，在MultithreadEventExecutorGroup构造方法中会通过for循环创建NioEventLoop实例，
 * 并放进数组children中，即children[i] = NioEventLoopGroup.newChild(executor, args);其中executor未指定的情况下是new
 * ThreadPerTaskExecutor(newDefaultThreadFactory());
 * NioEventLoop继承了SingleThreadEventExecutor的Executor executor属性
 * newChild(executor, args)创建NioEventLoop实例会在构造方法中把executor变量设置为上面创建的ThreadPerTaskExecutor实例，也会
 * 在构造方法中创建selector、tailTasks、taskQueue，不过变量thread和scheduledTaskQueue还是null
 * 随后继续执行NioEventLoopGroup()构造器中的chooser = chooserFactory.newChooser(children);把线程选择器和children绑定
 * 到此为止NioEventLoopGroup创建完成，以及内部数组children中的所有NioEventLoop创建完成
 *
 * 2.boot.group(bossGroup,workGroup).channel(NioServerSocketChannel.class)
 *   bootstrap.group(group).channel(NioSocketChannel.class)
 * 无论客户端Bootstrap还是服务端的ServerBootstrap都继承了AbstractBootstrap，并且继承了属性channelFactory，这里的channel方法就是
 * 创建一个用于生产指定channel类型的工厂实例，并保存到变量channelFactory。后续其他地方会有代码用该工厂类创建需要的channel
 *
 * 3.boot.bind()
 *  boot.bind()——>AbstractBootStrap.bind()——>AbstractBootStrap.doBind(final SocketAddress localAddress)
 *  关键看doBind方法中的final ChannelFuture regFuture = AbstractBootStrap.initAndRegister();initAndRegister()中的关键在于
 *  channel = channelFactory.newChannel();init(channel);ChannelFuture regFuture = config().group().register(channel);
 *  这3行代码。
 *  channelFactory.newChannel()通过反射调用无参构造方法创建channel实例，在此是创建NioServerSocketChannel实例，调用过程
 *  见NioServerSocketChannel()这个构造方法，即
 *  public NioServerSocketChannel() {
 *         this(newSocket(DEFAULT_SELECTOR_PROVIDER));//newSocket方法返回值作为参数，继续调用有参构造
 *     }
 *  public NioServerSocketChannel(ServerSocketChannel channel) {
 *         super(null, channel, SelectionKey.OP_ACCEPT);//调用父类AbstractNioChannel的构造
 *         config = new NioServerSocketChannelConfig(this, javaChannel().socket());
 *     }
 *  会发现通过newSocket(DEFAULT_SELECTOR_PROVIDER)创建ServerSocketChannel并作为参数返回后，继续调用有参构造方法
 *
 *  protected AbstractNioChannel(Channel parent, SelectableChannel ch, int readInterestOp) {
 *         super(parent); // 调用父类AbstractChannel的构造
 *         this.ch = ch; // 把jdk的ServerSocketChannel保存到属性SelectableChannel ch中
 *         this.readInterestOp = readInterestOp;// 保存兴趣事件，这里是OP_ACCEPT
 *         try {
 *             ch.configureBlocking(false); // 设置ServerSocketChannel为非阻塞模式
 *         } catch (IOException e) {
 *            ...省略非关键代码
 *     }
 *
 *  protected AbstractChannel(Channel parent) {
 *         this.parent = parent;
 *         id = newId();
 *         // 对于NioServerSocketChannel创建AbstractNioMessageChannel的内部类NioMessageUnsafe对象
 *         // 对于NioSocketChannel创建AbstractNioByteChannel的内部类NioByteUnsafe对象
 *         unsafe = newUnsafe();
 *         pipeline = newChannelPipeline();// 创建ChannelPipeline
 *     }
 *  这个构造方法。到此为止，NioServerSocketChannel初始化完成了，可以debug后看future对象里的channel属性。ServerSocketChannel与此相似。
 *
 *  ServerBootstrap.init(channel){
 *       // 设置channel的options
 *       setChannelOptions(channel, newOptionsArray(), logger);
 *        // 设置channel的Attributes
 *       setAttributes(channel, attrs0().entrySet().toArray(EMPTY_ATTRIBUTE_ARRAY));
 *       ChannelPipeline p = channel.pipeline();
 *       // 添加一个匿名对象ChannelInitializer，属于入站处理器，这里匿名对象里的方法还没执行，只是把这个对象放进了ChannelPipeline p中
 *       // 对于Bootstrap.init(channel)，则是直接p.addLast(config.handler());
 *       // pipeline.addLast(handler);和p.addLast(config.handler());效果一样，都是把用户自定义的ChannelInitializer封装到
 *       // DefaultChannelHandlerContext后加入管道
 *       p.addLast(new ChannelInitializer<Channel>() {
 *       	   // 重写了initChannel方法
 *             public void initChannel(final Channel ch) {
 *                 final ChannelPipeline pipeline = ch.pipeline();
 *                 ChannelHandler handler = config.handler();
 *                 if (handler != null) {
 *                 	   // 添加用户配置的handler，服务端的NioServerSocketChannel一般不配置
 *                     pipeline.addLast(handler);
 *                 }
 *					// 222行pipeline.fireChannelRegistered()触发initChannel方法后，将Runnable作为task放入到taskQueue中
 *                 ch.eventLoop().execute(new Runnable() {
 *                     public void run() {
 *                     		// 添加一个ServerBootstrapAcceptor作为入站处理器，作用是等服务端监听accept事件后，会执行到
 *                     	   // 这个入站处理器，而该处理器的功能是将workGroup中的某个NioEventLoop和客户端NioSocketChannel注册绑定
 *                         pipeline.addLast(new ServerBootstrapAcceptor(
 *                                 ch, currentChildGroup, currentChildHandler, currentChildOptions, currentChildAttrs));
 *                     }
 *                 });
 *             }
 *         });
 *  }
 *
 *  config().group().register(channel);其中config()返回ServerBootstrapConfig config对象，就是在new ServerBootstrap()创建时
 *  创建的内部变量private final ServerBootstrapConfig config = new ServerBootstrapConfig(this);
 *  而config.group()返回的就是ServerBootstrap的EventLoopGroup group，也就是NioEventLoopGroup(代码中通过boot.group绑定的)
 *  而NioEventLoopGroup.register(channel)——>调用的是其父类MultithreadEventLoopGroup的register(Channel channel)，即
 *  public ChannelFuture register(Channel channel) {
 *  	   //next方法会通过线程选择器choose选择一个NioEventLoop实例返回
 *  	   //再调用NioEventLoop.register(channel)把当前的NioServerSocketChannel注册到这个NioEventLoop实例
 *         return next().register(channel);
 *     }
 *  接下来就分析NioEventLoop.register(channel)方法了
 *  NioEventLoop.register(channel)——>父类SingleThreadEventLoop.register(Channel channel)即
 *
 *     public ChannelFuture register(Channel channel) {
 *     	   // 这里的this明显就是当前调用对象，也就是NioEventLoop
 *     	   // 创建ChannelPromise，并且将当前的NioServerSocketChannel和NioEventLoop俩个对象设置进去
 *     	   // 再调用下一个register方法
 *         return register(new DefaultChannelPromise(channel, this));
 *     }
 *     public ChannelFuture register(final ChannelPromise promise) {
 *         ObjectUtil.checkNotNull(promise, "promise");
 *         // promise.channel()返回NioServerSocketChannel
 *         // unsafe()返回之前NioServerSocketChannel创建的时候生成的NioMessageUnsafe
 *         // 再调用NioMessageUnsafe.register(this, promise)，这个方法在父类AbstractUnsafe中
 *         promise.channel().unsafe().register(this, promise);
 *         return promise;
 *     }
 *     AbstractChannel的内部类AbstractUnsafe的register方法如下
 *     public final void register(EventLoop eventLoop, final ChannelPromise promise) {
 *             // 将NioServerSocketChannel的eventLoop属性设置为当前NioEventLoop实例
 *             AbstractChannel.this.eventLoop = eventLoop;
 *			   // 判断当前线程是否是NioEventLoop里绑定的线程(也就是当前执行线程是否和NioEventLoop.thread是同一个线程)
 *			   // 这里当前线程是main，所以不等
 *             if (eventLoop.inEventLoop()) {
 *                 register0(promise);
 *             } else {
 *                 try {
 *                    // 调用NioEventLoop的execute方法
 *                     eventLoop.execute(new Runnable() {
 *                         public void run() {
 *                             register0(promise);
 *                         }
 *                     });
 *                 } catch (Throwable t) {
 *                     ...
 *                 }
 *             }
 *         }
 *    NioEventLoop的父类SingleThreadEventExecutor.execute(Runnable task)——>execute(Runnable task, boolean immediate),即
 *    private void execute(Runnable task, boolean immediate) {
 *         boolean inEventLoop = inEventLoop();
 *         // 把158行的runnable作为任务放入taskQueue中
 *         addTask(task);
 *         // 明显当前线程为main，并非NioEventLoop的线程
 *         if (!inEventLoop) {
 *         	   // 启动线程
 *             startThread();
 *         }
 *     }
 *     startThread();——>doStartThread();——>ThreadPerTaskExecutor.execute(Runnable command) {
 *     	   // 创建线程并启动，这个线程将会在执行command时，设置到NioEventLoop的thread属性
 *         threadFactory.newThread(command).start();
 *     }
 *     看下doStartThread()方法主要内容(省去很多代码)
 *      private void doStartThread() {
 *         // NioEventLoop的thread目前肯定是null，因为bind过程，是创建NioServerSocketChannel的过程，关注accept兴趣事件，目前
 *         // NioEventLoop里没有线程
 *         assert thread == null;
 *         // executor引用的对象是ThreadPerTaskExecutor，里面封装了一个threadFactory，execute里会创建一个线程
 *         executor.execute(new Runnable() {
 *             public void run() {
 *                 // 把NioEventLoop的thread设置为当前执行线程，明显当前线程就是刚才threadFactory创建的线程
 *                 // 其实这里就说明了每个NioEventLoop都会封装绑定一个线程，可以把NioEventLoop看作是个线程，称作nio线程
 *                 thread = Thread.currentThread();
 *                 boolean success = false;
 *                 updateLastExecutionTime();
 *                 try {
 *                     //  nio线程执行run
 *                     SingleThreadEventExecutor.this.run();
 *                     success = true;
 *                 } catch (Throwable t) {
 *                     logger.warn("Unexpected exception from an event executor: ", t);
 *                 }
 *             }
 *         });
 *     }
 *     SingleThreadEventExecutor.this.run();——>NioEventLoop.run()这里就是nio线程轮询事件的逻辑
 *     由于服务端的channel事件才建立，还没有注册到selector上，目前肯定是没有事件的，但是当前的taskQueue却有任务，这个
 *     任务就是158行的runnable，所以nio线程将会执行里面的register0(promise);逻辑
 *	   private void register0(ChannelPromise promise) {
 *             try {
 *                 boolean firstRegistration = neverRegistered;
 *                 //通过selectionKey = javaChannel().register(eventLoop().unwrappedSelector(), 0, this);
 *                 //把当前的NioServerSocketChannel注册到NioEventLoop的selector上，兴趣事件为0，即无兴趣，并且把当前
 *                 //NioServerSocketChannel作为attachment
 *                 doRegister();
 *                 neverRegistered = false;
 *                 registered = true;
 *                 //
 *                 pipeline.invokeHandlerAddedIfNeeded();
 *                 safeSetSuccess(promise);
 *                 // 这里会调用ChannelInitializer里的initChannel方法
 *                 pipeline.fireChannelRegistered();
 *                 // 只有端口已经绑定到NioServerSocketChannel，isActive()才是true
 *                 if (isActive()) {
 *                     if (firstRegistration) {
 *                         pipeline.fireChannelActive();
 *                     } else if (config().isAutoRead()) {
 *                     	   // 客户端的NioSocketChannel的注册，就会走这里直接注册读事件
 *                         beginRead();
 *                     }
 *                 }
 *             } catch (Throwable t) {
 *             }
 *         }
 *    上述代码由nio线程执行，而main线程会在AbstractBootstrap.doBind继续执行initAndRegister();后续的代码，即
 *    private ChannelFuture doBind(final SocketAddress localAddress) {
 *         final ChannelFuture regFuture = initAndRegister();
 *         final Channel channel = regFuture.channel();
 *         // 这种情况nio线程注册完成了，调用doBind0方法来绑定端口，可以确定224行isActive()是false，不会执行pipeline.fireChannelActive()
 *         if (regFuture.isDone()) {
 *             ChannelPromise promise = channel.newPromise();
 *             // 266行wasActive是false，执行269行后，276行是true，因此执行pipeline.fireChannelActive()
 *             doBind0(regFuture, channel, localAddress, promise);
 *             return promise;
 *         } else {
 *         //这种情况是等nio线程注册完成了回调到这里，调用doBind0方法来绑定端口，可以确定224行isActive()是false，
 *  *     //不会执行pipeline.fireChannelActive()。和上面一样，只不过上面可以直接执行(毕竟已经完成了)，这里还需要使用监听器
 *             final PendingRegistrationPromise promise = new PendingRegistrationPromise(channel);
 *             regFuture.addListener(new ChannelFutureListener() {
 *                 public void operationComplete(ChannelFuture future) throws Exception {
 *                     Throwable cause = future.cause();
 *                     if (cause != null) {
 *                         promise.setFailure(cause);
 *                     } else {
 *                         promise.registered();
 *                         doBind0(regFuture, channel, localAddress, promise);
 *                     }
 *                 }
 *             });
 *             return promise;
 *         }
 *     }
 *     最终调用到AbstractChannel#AbstractUnsafe.bind方法(这里就是用NioMessageUnsafe unsafe 实例调用的)，即
 *     public final void bind(final SocketAddress localAddress, final ChannelPromise promise) {
 *     		  // wasActive取isActive()的值
 *             boolean wasActive = isActive();
 *             try {
 *                 //绑定端口
 *                 doBind(localAddress);
 *             } catch (Throwable t) {
 *                 safeSetFailure(promise, t);
 *                 closeIfClosed();
 *                 return;
 *             }
 *             // 绑定端口后，isActive()变为true
 *             if (!wasActive && isActive()) {
 *                 invokeLater(new Runnable() {
 *                     public void run() {
 *                         //最终会调用HeadContext的channelActive(ChannelHandlerContext ctx)
 *                         //里面有两个方法， ctx.fireChannelActive();和readIfIsAutoRead();
 *                         //readIfIsAutoRead();里是channel.read()，将从tail开始出站调用read，最终会调用HeadContext的read方法，执行
 * 			               //unsafe.beginRead();——>AbstractNioChannel.doBeginRead()——>获取SelectionKey后注册兴趣事件。在这里完成真正的兴趣事件
 *                         pipeline.fireChannelActive();
 *                     }
 *                 });
 *             }
 *             safeSetSuccess(promise);
 *         }
 *
 *
 */
public class EchoServer {

	private static final Logger LOG = LoggerFactory.getLogger(EchoServer.class);


	private final int port;

	public EchoServer(int port) {
		this.port = port;
	}

	public void start() throws Exception {
		EventLoopGroup bossGroup = null;
		EventLoopGroup workGroup = null;
		// 使用netty自带的线程池处理耗时业务
		EventExecutorGroup businessGroup = new DefaultEventExecutorGroup(10);
		EventExecutorGroup businessGroup2 = new DefaultEventExecutorGroup(10);
		try {
			//因为使用NIO，指定NioEventLoopGroup来接受和处理新连接
			bossGroup = new NioEventLoopGroup(1);
			workGroup = new NioEventLoopGroup();
			//创建bootstrap来启动服务器
			ServerBootstrap boot = new ServerBootstrap();
			boot.group(bossGroup,workGroup)
					//指定通道类型为NioServerSocketChannel
					.channel(NioServerSocketChannel.class)
					.localAddress(port)
					// serverSocketChannel的配置;服务端地址可以复用，如某个进程占用了80端口,然后重启进程,原来的socket1处于TIME-WAIT状态,进程启动后,
					// 使用一个新的socket2,要占用80端口,如果这个时候不设置SO_REUSEADDR=true,那么启动的过程中会报端口已被占用的异常
					.option(ChannelOption.SO_REUSEADDR,true)
					// 使用内存池
					.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
					// socketChannel的配置
					//SO_KEEPALIVE=true,是利用TCP的SO_KEEPALIVE属性,当SO_KEEPALIVE=true的时候,服务端可以探测客户端的连接
					// 是否还存活着,如果客户端因为断电或者网络问题或者客户端挂掉了等,那么服务端的连接可以关闭掉,释放资源
					.childOption(ChannelOption.SO_KEEPALIVE, true)
					//如果TCP_NODELAY没有设置为true,那么底层的TCP为了能减少交互次数,会将网络数据积累到一定的数量后,
					// 服务器端才发送出去,会造成一定的延迟。在互联网应用中,通常希望服务是低延迟的,建议将TCP_NODELAY设置为true
					.childOption(ChannelOption.TCP_NODELAY, true)
					//调用childHandler用来指定连接后调用的ChannelHandler
					.childHandler(new ChannelInitializer<Channel>() {
						//这个方法传ChannelInitializer类型的参数，ChannelInitializer是个抽象类，
						// 所以需要实现initChannel方法，这个方法就是用来设置ChannelHandler
						@Override
						protected void initChannel(Channel channel) throws Exception {
							// 将对象转为String，后续的EchoServerHandler就可以直接强转string而非ByteBuf
							channel.pipeline().addLast(new StringDecoder());
							channel.pipeline().addLast(new AuthHandler());
							channel.pipeline().addLast(businessGroup,new TimeConsumeHandler());
							channel.pipeline().addLast(businessGroup2,new TimeConsumeHandler());
//							channel.pipeline().addLast(new TimeConsumeHandler());
							channel.pipeline().addLast(new EchoServerHandler());
						}
					});
			ChannelFuture future = boot.bind().sync();
			System.out.println(EchoServer.class.getName() + " started and listen on " + future.channel().localAddress());
			future.channel().closeFuture().sync();
		} finally {
			if (bossGroup != null) {
				bossGroup.shutdownGracefully().sync();
			}
			if (workGroup != null) {
				workGroup.shutdownGracefully().sync();
			}
		}
	}

	public static void main(String[] args) {
		EchoServer server = new EchoServer(20000);
		try {
			server.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}