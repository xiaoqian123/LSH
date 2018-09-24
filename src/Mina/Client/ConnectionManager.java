package Mina.Client;

import java.lang.ref.WeakReference;
import java.net.InetSocketAddress;

import javax.naming.Context;
import javax.print.attribute.standard.Compression;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import Mina.Coder.MyCoderFactory;



/**
 * ��װ��Connection disConnection ������������
 * @author Administrator
 *
 */
public class ConnectionManager {
	
	private ConnectionConfig mConifg;				
	private NioSocketConnector mConnection;
	private IoSession mSession;
	private InetSocketAddress mAddress;
	
	public ConnectionManager(ConnectionConfig Conifg)
	{
		this.mConifg= Conifg;
		init();
	}
	
	private void init()
	{
		mAddress = new InetSocketAddress(mConifg.getIp(),mConifg.getPort());
		mConnection= new NioSocketConnector();				 // ����һ��tcp/ip ����
		mConnection.getSessionConfig().setReadBufferSize(mConifg.getReadBufferSize());
		mConnection.getFilterChain().addLast("logger", new LoggingFilter());		//��־������
//		mConnection.getFilterChain().addLast("codec",
//	        		new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));		//���͵Ķ�����������кŶ���
		mConnection.getFilterChain().addLast("mycodec",new ProtocolCodecFilter(new MyCoderFactory()));  
		mConnection.setHandler(new ClientHandler());
	}
	
	
	/**
	 * ������ȡ�������������
	 * @return
	 */
	public boolean connect()
	{
		try{
			ConnectFuture con = mConnection.connect(mAddress);
			con.awaitUninterruptibly();
			mSession= con.getSession();
			
		}catch(Exception e)
		{
			return false;
		}
		return mSession ==null? false:true;
		
	}
	public IoSession getSession()
	{
		return mSession;
	}
	/*
	 * �Ͽ�����
	 */
	public void disConnection()
	{
		mConnection.dispose();
		mConnection=null;
		mSession=null;
		mAddress=null;
	}
}
