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
	   // �������ʵ����������Ϊһ������,������Static��final���η�
    private static final test instance = new test();  

    public static test getInstancei() {  
        return instance;  
    }  
   public boolean printsimnum=true;			//���ڴ�ӡ��һ�β�ѯ������ͼƬ����
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
			 System.out.println("���ӳɹ�");	
		//	 contrview.setconnectText("");
			 return true;
		 }
		 System.out.println("����ʧ��");
		 return false;
	}
	
	public void Close()
	{
		connectionManager.disConnection();
	}
	
		public void SentImage()					//			��ü��ܺ�����ݣ������ϴ�
		{	System.out.println("�ϴ����ܵ��ļ�");
			contrview.setStaText("�����ϴ����ܵ��ļ�...");
			Imageinfo image = clientActionImpl.PreUpload(file);				
			ImageData imageCode = new ImageData(image.getEncode());
			//ImageData msg = new ImageData(getImageBinary("0.jpg"));
			imageCode.setOperation(2);				//����ͼƬ��Ϣ
			session.write(imageCode);	
			
		}
		public void PreCompare()	//����Ƚ�����
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
			System.out.println("�յ��������ڱȽ�");
			contrview.setStaText("���ڱȽ�...");
			byte[] assure = clientActionImpl.Assure(number, file);
			System.out.println("�����Ƚϵ�����");
			 for(int j=0;j<assure.length;j++)
			  {
				  System.out.print(assure[j]);
			  }
			 System.out.println();
			 for(int j=0;j<Servernumber.length;j++)
			  {
				  System.out.print(Servernumber[j]);
			  }					
			if(clientActionImpl.sim(assure, Servernumber)=="1") 				//����ͬͼƬ
			{
				System.out.println("����ͬͼƬ�������ϴ�");
				contrview.setStaText("��ͬͼƬ�������ϴ���ͼƬ...");
				next();  			//������һ��ͼƬ
			}else														//������� ���ݿ�phase��ͬ�����Ĳ�ͬ	���ϴ�
			{
				System.out.println("û����ͬͼƬ�ϴ�");
				contrview.setStaText("�Ƚ�û����ͬͼƬ...");
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
			}	//��ȡͼƬ��Ϣ
			System.out.println("�Ի�ȡͼƬ��Ϣ�������ϴ�");
			contrview.setStaText("�Ի�ȡͼƬ��Ϣ�������ϴ�...");
			System.out.println();
			session =connectionManager.getSession();		//������Ϣ	
			getImageInfo.setSim(100);								//���ƶ�
			getImageInfo.setOperation(1);							//�ж��Ƿ��ϴ�			
			session.write(getImageInfo);		
	}
	
	public void Upload(File []file1)
	{
		filelist=file1;
		nextid=0;
		next();				//��һ�ſ�ʼ����
	}
	public void SelectSim(File file1,int simnumb) throws IOException
	{
		getImageInfo = clientActionImpl.GetImageInfo(file1);
		if(getImageInfo.equals(null))
		{
			System.out.println("����SelectSim��GetImageInfo��Ϊ��");
		}else
		{
			System.out.println("���ƾ���"+simnumb);
			System.out.println("�Ի�ȡͼƬ��Ϣ�������ϴ�");
			contrview.setStaText("���ڲ�ѯ��ȴ�...");
			System.out.println();
			session =connectionManager.getSession();		//������Ϣ	
			getImageInfo.setOperation(4);						//��ѯ
			getImageInfo.setSim(simnumb);					//���ƶ�
			session.write(getImageInfo);
		}
	
	}
	

	
	public void next()
	{				
		if(nextid<filelist.length)
			IsUplod(filelist[nextid]);
		
		else
		{
			System.out.println("����");		//---------------------------------------------------------------------------
			contrview.setStaText("�ϴ����!");
		}
		nextid++;		
		
	}
	public static void getcontrview(controller test)
	{
		contrview=test;
		System.out.println(test);
	}


	  
}