package Mina.Client;

import javax.naming.Context;

public class ConnectionConfig {
	
	private Context context;
	private String ip;
	private int port;
	private int readBufferSize;
	private long connectTimeOut;
	public Context getContext() {
		return context;
	}
	public void setContext(Context context) {
		this.context = context;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public int getReadBufferSize() {
		return readBufferSize;
	}
	public void setReadBufferSize(int readBufferSize) {
		this.readBufferSize = readBufferSize;
	}
	public long getConnectTimeOut() {
		return connectTimeOut;
	}
	public void setConnectTimeOut(long connectTimeOut) {
		this.connectTimeOut = connectTimeOut;
	}
	
	
}
