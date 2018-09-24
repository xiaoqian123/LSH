package Mina.Server;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import Mina.Coder.MyDecoder;
import actiom.impl.ServerActionImpl;
import bean.Bucket;
import bean.Comparenumber;
import bean.ImageData;
import bean.Imageinfo;
import bean.Message;
import bean.Parent_object;

public class ServerHandler extends IoHandlerAdapter{
//	MinaServer Server = MinaServer.getInstancei();
	 private int count = 0;
	 ServerActionImpl serverActionImpl = new ServerActionImpl();
	 Imageinfo image=null;
	 List<Imageinfo> selectImage =null;
	 Message mess = new Message();
	    // ��һ���¿ͻ������Ӻ󴥷��˷���.
	    public void sessionCreated(IoSession session) {
	        System.out.println("�¿ͻ�������");     
	    }
	 
	    // ��һ���Ͷ˶��������ʱ @Override
	    public void sessionOpened(IoSession session) throws Exception {
	        count++;
	        System.out.println("�� " + count + " �� client ��½��address�� : "
	                + session.getRemoteAddress());
//	        test.contrview.setText("�� " + count + " �� client ��½��address�� : "
//	                + session.getRemoteAddress());
	    }
	 
	    // ���ͻ��˷��͵���Ϣ����ʱ:
	    public void messageReceived(IoSession session, Object message)
	            throws Exception {
	    	System.out.println("--------------------------------------------------");
	    	MyDecoder.isexecute=true;
	    	Parent_object ho = (Parent_object) message;
	    	switch(ho.getOperation()){
	    		case 1:					//�ж��Ƿ�����ͬ��ͼƬ
	    		{
	    			System.out.println("Isupload");
	    			System.out.println("��ѯ�Ƿ�����ͬ����Ƭ");	    		
	    			IsUpload(session,message);		//��ȡ�Ƿ���Ҫ�ϴ���Ϣ	
//	    			test.contrview.setText("�ͻ��������ϴ�ͼƬ....");
	    			System.out.println("�ͻ��������ϴ�ͼƬ....");
	    		}	
	    		break; 
	    		case 2:					//ֱ���ϴ�
	    		{
	    			System.out.println("upload");
	    			Upload(session,message);
	    			image=null;
	    			selectImage=null;
	    			Next(session, message);	    			
	    		}
	    		break;
	    		case 3:					//�����û�������� ��ȡ�������� ���бȽ�
	    		{
	    			System.out.println("PreCompare");
	    			PreCompare(session,message);
	    			selectImage=null;
//	    			test.contrview.setText("���ıȽ���....");
	    			System.out.println("���ıȽ���....");
	    		}
	    			break;
	    		case 4:					//��ѯ��ͬͼƬ
	    		{
	    			SeleteImage(session,message);
	    			System.out.println("�ͻ������ڲ�ѯ��ͬ��ͼƬ....");
//	    			test.contrview.setText("�ͻ������ڲ�ѯ��ͬ��ͼƬ....");
	    		}break;
	    		case 5:
	    		{
	    			cycleDownload(session,message);
	    		}
	    		break;
	    		case 6:							//��ֹ����
	    		{
	    			selectImage=null;
	    		}
	    		break;

	    			
	    	}
	    }
	    // ����Ϣ�Ѿ����͸��ͻ��˺󴥷��˷���.
	    public void messageSent(IoSession session, Object message) {
	        System.out.println("��Ϣ�Ѿ����͸��ͻ���");
	 
	    }
	 
	    // ��һ���ͻ��˹ر�ʱ
	    public void sessionClosed(IoSession session) {
	        System.out.println("one Clinet Disconnect !");
//	        test.contrview.setText("one Clinet Disconnect !");	        
	    }
	 
	    // �����ӿ���ʱ�����˷���.
	    public void sessionIdle(IoSession session, IdleStatus status) {
	        System.out.println("���ӿ���");
	    }
	 
	    // ���ӿ������������׳��쳣δ������ʱ�����˷���
	    public void exceptionCaught(IoSession session, Throwable cause) {
	      //  System.out.println("���������׳��쳣");
	    	cause.printStackTrace();
	    }  
	    public  void IsUpload(IoSession session,Object message)
	    {
	    	image=(Imageinfo) message;
	    	try {
	    		long timeInMillis1= Calendar.getInstance().getTimeInMillis();
	    		selectImage = serverActionImpl.SelectImage(image, image.getSim());
		    	long timeInMillis2= Calendar.getInstance().getTimeInMillis();
		    	System.out.println("��ѯʱ��:"+((timeInMillis2-timeInMillis1)));//////////////////////////////
			
				if(selectImage.isEmpty()||selectImage.size()==0)				//û����ͬ��	��ֱ���ϴ�
				{
					System.out.println("û����ͬ��ͼƬ***");
		    		mess.setOperation(1);				//ֱ���ϴ�
		    		mess.setMessage("Upload");
		    		session.write(mess);			
				}
				else
				{
					System.out.println("����ͬ��ͼƬ���бȽ�***");
	    			mess.setOperation(2);				//��������
	    			mess.setMessage("PreCompare");
	    			session.write(mess);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	//��ѯ��û����ͬ��ͼƬ
	    }
	    public  void Upload(IoSession session,Object message)
	    {
	    	System.out.println("ֱ���ϴ�");
	    	ImageData imagecode=(ImageData) message;
	    	image.setEncode(imagecode.getBimage());
	    	image.setEnlength(imagecode.getLength());
	     	long timeInMillis = Calendar.getInstance().getTimeInMillis(); ///////////////////////////////////////////////
			System.out.println("�ϴ�����ʱ�����:"+timeInMillis);
			long timeInMillis1= Calendar.getInstance().getTimeInMillis();
	    	serverActionImpl.SaveImage(image);
	    	long timeInMillis2= Calendar.getInstance().getTimeInMillis();
	    	System.out.println("����ʱ��:"+((timeInMillis2-timeInMillis1)));//////////////////////////////
	    }
	    public  void PreCompare(IoSession session,Object message)
	    {
	    	System.out.println("ȡ�����Ķ�Ӧλ������");
	    	Comparenumber number=(Comparenumber) message;
	    	byte[] assure = serverActionImpl.Assure(number, selectImage.get(0));
	    	if(assure==null)								//������ݿ��Ӧλ�ӵ�ͼƬ�����ѱ�ɾ�� ���ȡΪnull		
			{			
				serverActionImpl.DelImage(selectImage.get(0).getPid());				//ɾ��
				System.out.println("�жϳ�û���ļ�");
				mess.setOperation(1);				//ֱ���ϴ�
	    		mess.setMessage("��������ʧ�ļ�,�����ϴ�...");
	    		session.write(mess);			
			}else
			{
		    	ImageData imageCode = new ImageData(assure); 
		    	imageCode.setObjectlength(0);  					//�����嵫����ɾ�������ܴ���
				imageCode.setOperation(3);						 //	�Ƚ�	
				imageCode.setType("test");
				imageCode.setPid(0);
				session.write(imageCode);
			}
	    }
	    
//	    public  void SeleteImage(IoSession session,Object message) throws SQLException, IOException
//	    {
//	    	image=(Imageinfo) message;
//	    	selectImage = serverActionImpl.SelectImage(image, image.getSim() );
//	    	System.out.println("����ͼƬ����"+selectImage.size());
//	    	ImageData imageData = new ImageData();
//	    	Imageinfo imageinfo = selectImage.get(selectImage.size()-1);
//	    	imageData.setBimage(imageinfo.getEncode());
//	    	imageData.setOperation(4);					//��������	    	
//	    	imageData.setPid(Integer.valueOf(imageinfo.getPid()));		
//	    	imageData.setType(imageinfo.getImagetype());
//	    	imageData.setObjectlength(selectImage.size()-1); 		//����Ҫ���صĸ���
//	    	session.write(imageData);
//		  
//	    }
	    //��ѯͼƬ
	    public  void SeleteImage(IoSession session,Object message) 
	    {	        	
			try {
				image=(Imageinfo) message;
				selectImage = serverActionImpl.SelectImage(image, image.getSim());
				if(selectImage.isEmpty()||selectImage.size()==0)				
				{
					System.out.println("û����ͬ��ͼƬ***");
		    		mess.setOperation(6);				//û������ͼƬ
		    		mess.setMessage("û����ͬ��ͼƬ");
		    		session.write(mess);			
				}
				else
				{
					System.out.println("����ͼƬ����"+selectImage.size());
					ImageData imageData = new ImageData();
					Imageinfo imageinfo = selectImage.get(selectImage.size()-1);
					imageData.setBimage(imageinfo.getEncode());
					imageData.setOperation(4);
					imageData.setPid(Integer.valueOf(imageinfo.getPid()));
					imageData.setType(imageinfo.getImagetype());
					imageData.setObjectlength(selectImage.size()-1);
					long timeInMillis = Calendar.getInstance().getTimeInMillis(); ///////////////////////////////////////////////
					System.out.println("���ؼ���ʱ�俪ʼ:"+timeInMillis);
					session.write(imageData);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	    }
	    public   void cycleDownload(IoSession session,Object message) 
	    {
	    	Message mess= (Message)message;
	    	Integer length = Integer.valueOf(mess.getMessage());
	    	System.out.println("����ͼƬ����"+length);
	    	
	    	ImageData imageData = new ImageData();
	    	Imageinfo imageinfo = selectImage.get(length-1);
	    	imageData.setBimage(imageinfo.getEncode());
	    	imageData.setOperation(4);					//��������	
	    	
	    	System.out.println("bit����"+imageinfo.getEncode().length);
	    	System.out.println("pid"+imageinfo.getPid());
	    	System.out.println("type"+imageinfo.getImagetype());
	    	
	    	imageData.setPid(Integer.valueOf(imageinfo.getPid()));		
	    	imageData.setType(imageinfo.getImagetype());
	    	imageData.setObjectlength(length-1); 		//����Ҫ���صĸ���
//	    	if(length==0)				//���
//	    	{
//	    		selectImage=null;
//	    	}
	    	session.write(imageData);
	    }
	    public void Next(IoSession session,Object message)
	    {
	    	mess.setOperation(5);				//�ϴ���һ��
    		mess.setMessage("Upload");
    		session.write(mess);	
	    }
}  

