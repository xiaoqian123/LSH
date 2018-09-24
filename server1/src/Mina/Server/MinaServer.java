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
//		   // �������ʵ����������Ϊһ������,������Static��final���η�
//	    private static final MinaServer instance = new MinaServer();  
//
//	    public static MinaServer getInstancei() {  
//	        return instance;  
//	    }  
//	 
    public  boolean Start(String ip,int port) {
    	
    	
    	
    	
        //  �ڷ������˴���һ���������ӵĽ����� ����tcp/ip  
        acceptor = new NioSocketAcceptor();  
          
        System.out.println(ip);
        //  �󶨵Ķ˿�  
        address = new InetSocketAddress(ip, port);  
          
        //   ��ȡ��������  
        DefaultIoFilterChainBuilder chain = acceptor.getFilterChain();  
         
        //  �����־������  
        chain.addLast("logger", new LoggingFilter());  
//        chain.addLast("codec",
//        		new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));		//���͵Ķ�����������кŶ��� 
        //�������Զ���ı��������  
       chain.addLast("mycodec", new ProtocolCodecFilter(new MyCoderFactory()));  
        
        //  ������ݴ���Ĵ�����  
        acceptor.setHandler(new ServerHandler());  
          
        //  ����������Ϣ������       
        acceptor.getSessionConfig().setReadBufferSize(100);       
        acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);   
          
        //  �󶨷������˿ڡ�  
        try {
			acceptor.bind(address);
			 System.out.println("��������ʼ�� "+ port+ " �˿ڼ���.......");
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("����ʧ��");			
			e.printStackTrace();
			return false;
		}   
    }
    
    public void Close()
    {
    	acceptor.unbind();
    	acceptor=null;
    	address=null;
    	System.out.println("�رճɹ�");
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
        	System.out.println("���� 0,��رշ�����");
        	Scanner out = new Scanner(System.in);
        	int a= out.nextInt();
        	if(a==0)
        	{
        		minaServer.Close();
        	}
        }
    }
}
