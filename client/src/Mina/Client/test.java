package Mina.Client;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;

import org.apache.mina.core.session.IoSession;

import actiom.impl.ClientActionImpl;
import application.Main;
import application.controller;
import bean.Bucket;
import bean.Comparenumber;
import bean.ImageData;
import bean.Imageinfo;
import bean.Message;
import javafx.beans.binding.When.BooleanConditionBuilder;

public class test {
	private test() {  
    }  
	   // 将自身的实例对象设置为一个属性,并加上Static和final修饰符
    private static final test instance = new test();  

    public static test getInstancei() {  
        return instance;  
    }  
   public boolean printsimnum=true;			//用于打印第一次查询出来的图片个数
    static controller contrview;
    public static boolean t =false;
	ConnectionManager connectionManager=null;
	Imageinfo getImageInfo=null;
	File file=null;
	Comparenumber number =null;
	static ClientActionImpl clientActionImpl = new ClientActionImpl();
	IoSession session=null;
	File filelist[]=null;
	
	int nextid;
	public  boolean Start(String ip,int port)
	{ 
		
		 ConnectionConfig connectionConfig = new ConnectionConfig();
		
		 connectionConfig.setIp(ip);//localhost
		 connectionConfig.setPort(port);
		 connectionConfig.setReadBufferSize(4028);
		 System.out.println(connectionConfig.getIp());
		 connectionManager = new ConnectionManager(connectionConfig);
		 boolean connect = connectionManager.connect();		
		 if(connect)
		 {
			 System.out.println("连接成功");	
		//	 contrview.setconnectText("");
			 return true;
		 }
		 System.out.println("连接失败");
		 return false;
	}
	
	public void Close()
	{
		connectionManager.disConnection();
	}
	
		public void SentImage()					//			获得加密后的数据，进行上传
		{	System.out.println("上传加密的文件");
			contrview.setStaText("正在上传加密的文件...");
			Imageinfo image = clientActionImpl.PreUpload(file);				
			ImageData imageCode = new ImageData(image.getEncode());
			//ImageData msg = new ImageData(getImageBinary("0.jpg"));
			imageCode.setOperation(2);				//保存图片信息
			session.write(imageCode);	
			
		}
		public void PreCompare()	//输入比较序列
		{
			contrview.getPreCompare();
				
		}
		public void getCompare(Comparenumber num)
		{
			number =num;
			number.setOperation(3);
			session.write(number);
		}
		public void Compare(byte [] Servernumber)
		{		
			System.out.println("收到数据正在比较");
			contrview.setStaText("正在比较...");
			byte[] assure = clientActionImpl.Assure(number, file);
			System.out.println("两个比较的数：");
			 for(int j=0;j<assure.length;j++)
			  {
				  System.out.print(assure[j]);
			  }
			 System.out.println();
			 for(int j=0;j<Servernumber.length;j++)
			  {
				  System.out.print(Servernumber[j]);
			  }					
			if(clientActionImpl.sim(assure, Servernumber)=="1") 				//有相同图片
			{
				System.out.println("有相同图片不用在上传");
				contrview.setStaText("相同图片，不在上传此图片...");
				next();  			//发送下一张图片
			}else														//特殊情况 数据库phase相同，密文不同	则上传
			{
				System.out.println("没有相同图片上传");
				contrview.setStaText("比较没有相同图片...");
				SentImage();
			}
		}

	public synchronized void IsUplod(File file1) {
		// TODO Auto-generated method stub

			file=file1;
			try {
				getImageInfo = clientActionImpl.GetImageInfo(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	//获取图片信息
			System.out.println("以获取图片信息，正在上传");
			contrview.setStaText("以获取图片信息，正在上传...");
			System.out.println();
			session =connectionManager.getSession();		//发送消息	
			getImageInfo.setSim(100);								//相似度
			getImageInfo.setOperation(1);							//判断是否上传			
			session.write(getImageInfo);		
	}
	
	public void Upload(File []file1)
	{
		filelist=file1;
		nextid=0;
		next();				//第一张开始发送
	}
	public void SelectSim(File file1,int simnumb) throws IOException
	{
		getImageInfo = clientActionImpl.GetImageInfo(file1);
		if(getImageInfo.equals(null))
		{
			System.out.println("我是SelectSim，GetImageInfo后为空");
		}else
		{
			System.out.println("相似距离"+simnumb);
			System.out.println("以获取图片信息，正在上传");
			contrview.setStaText("正在查询请等待...");
			System.out.println();
			session =connectionManager.getSession();		//发送消息	
			getImageInfo.setOperation(4);						//查询
			getImageInfo.setSim(simnumb);					//相似度
			session.write(getImageInfo);
		}
	
	}
	

	
	public void next()
	{				
		if(nextid<filelist.length)
			IsUplod(filelist[nextid]);
		
		else
		{
			System.out.println("激活");		//---------------------------------------------------------------------------
			contrview.setStaText("上传完成!");
		}
		nextid++;		
		
	}
	public static void getcontrview(controller test)
	{
		contrview=test;
		System.out.println(test);
	}


	  
}