package Mina.Client;

import java.net.InetSocketAddress;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class MinaClient {
	 public  void Start(String[] args) {
	        // 创建一个tcp/ip 连接
//	        NioSocketConnector connector = new NioSocketConnector();
//	 
//	        /*---------接收字符串---------*/
//	        // //创建接收数据的过滤器
//	        // DefaultIoFilterChainBuilder chain = connector.getFilterChain();
//	        // // 设定这个过滤器将一行一行(/r/n)的读取数据
//	        // chain.addLast("myChin", new ProtocolCodecFilter(
//	        // new TextLineCodecFactory()));
//	        /*---------接收对象---------*/
//	        // 创建接收数据的过滤器
//	        DefaultIoFilterChainBuilder chain = connector.getFilterChain();
//	        // 设定这个过滤器将以对象为单位读取数据
//	        ProtocolCodecFilter filter = new ProtocolCodecFilter(
//	                new ObjectSerializationCodecFactory());
//	        // 设定服务器端的消息处理器:一个SamplMinaServerHandler对象,
//	        chain.addLast("objectFilter",filter);
//	 
//	        // 设定服务器端的消息处理器:一个 SamplMinaServerHandler 对象,
//	        connector.setHandler(new ClientHandler());
//	        // Set connect timeout.
//	        connector.setConnectTimeoutCheckInterval(30);
//	        // 连结到服务器:
//	        ConnectFuture cf = connector.connect(new InetSocketAddress("localhost",
//	                9988));
//	        // Wait for the connection attempt to be finished.
//	        cf.awaitUninterruptibly();
//	        cf.getSession().getCloseFuture().awaitUninterruptibly();
//	        connector.dispose();
		 test instancei = test.getInstancei();
		 System.out.println(instancei);
	    }
	 
	 public void send()
	 {
		 
	 }
}
