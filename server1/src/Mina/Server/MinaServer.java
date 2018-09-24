package Mina.Server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Scanner;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;

import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import Mina.Coder.MyCoderFactory;



public class MinaServer {
	/**
     * @param args
     */
	
	 IoAcceptor acceptor ;
	 SocketAddress address ;
	 
//	 private MinaServer() {  
//	    }  
//		   // 将自身的实例对象设置为一个属性,并加上Static和final修饰符
//	    private static final MinaServer instance = new MinaServer();  
//
//	    public static MinaServer getInstancei() {  
//	        return instance;  
//	    }  
//	 
    public  boolean Start(String ip,int port) {
    	
    	
    	
    	
        //  在服务器端创建一个监听连接的接收器 基于tcp/ip  
        acceptor = new NioSocketAcceptor();  
          
        System.out.println(ip);
        //  绑定的端口  
        address = new InetSocketAddress(ip, port);  
          
        //   获取过滤器链  
        DefaultIoFilterChainBuilder chain = acceptor.getFilterChain();  
         
        //  添加日志过滤器  
        chain.addLast("logger", new LoggingFilter());  
//        chain.addLast("codec",
//        		new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));		//发送的对象必须是序列号对象 
        //　配置自定义的编解码器　  
       chain.addLast("mycodec", new ProtocolCodecFilter(new MyCoderFactory()));  
        
        //  添加数据处理的处理器  
        acceptor.setHandler(new ServerHandler());  
          
        //  进行配置信息的设置       
        acceptor.getSessionConfig().setReadBufferSize(100);       
        acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);   
          
        //  绑定服务器端口　  
        try {
			acceptor.bind(address);
			 System.out.println("服务器开始在 "+ port+ " 端口监听.......");
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("开启失败");			
			e.printStackTrace();
			return false;
		}   
    }
    
    public void Close()
    {
    	acceptor.unbind();
    	acceptor=null;
    	address=null;
    	System.out.println("关闭成功");
    }
//	public static void getcontrview(controller test)
//	{
//		contrview=test;
//		System.out.println(test);
//	}

    public static void main(String args[]){
    	MinaServer minaServer = new MinaServer();
    	if(args[0].equals("1"))
    	{  		
        	minaServer.Start(args[1], Integer.valueOf(args[2]));
        	System.out.println("输入 0,则关闭服务器");
        	Scanner out = new Scanner(System.in);
        	int a= out.nextInt();
        	if(a==0)
        	{
        		minaServer.Close();
        	}
        }
    }
}
