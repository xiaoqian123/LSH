package Mina.Client;

import java.net.InetSocketAddress;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class MinaClient {
	 public  void Start(String[] args) {
	        // ����һ��tcp/ip ����
//	        NioSocketConnector connector = new NioSocketConnector();
//	 
//	        /*---------�����ַ���---------*/
//	        // //�����������ݵĹ�����
//	        // DefaultIoFilterChainBuilder chain = connector.getFilterChain();
//	        // // �趨�����������һ��һ��(/r/n)�Ķ�ȡ����
//	        // chain.addLast("myChin", new ProtocolCodecFilter(
//	        // new TextLineCodecFactory()));
//	        /*---------���ն���---------*/
//	        // �����������ݵĹ�����
//	        DefaultIoFilterChainBuilder chain = connector.getFilterChain();
//	        // �趨������������Զ���Ϊ��λ��ȡ����
//	        ProtocolCodecFilter filter = new ProtocolCodecFilter(
//	                new ObjectSerializationCodecFactory());
//	        // �趨�������˵���Ϣ������:һ��SamplMinaServerHandler����,
//	        chain.addLast("objectFilter",filter);
//	 
//	        // �趨�������˵���Ϣ������:һ�� SamplMinaServerHandler ����,
//	        connector.setHandler(new ClientHandler());
//	        // Set connect timeout.
//	        connector.setConnectTimeoutCheckInterval(30);
//	        // ���ᵽ������:
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
