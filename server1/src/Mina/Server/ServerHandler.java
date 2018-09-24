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
	    // 当一个新客户端连接后触发此方法.
	    public void sessionCreated(IoSession session) {
	        System.out.println("新客户端连接");     
	    }
	 
	    // 当一个客端端连结进入时 @Override
	    public void sessionOpened(IoSession session) throws Exception {
	        count++;
	        System.out.println("第 " + count + " 个 client 登陆！address： : "
	                + session.getRemoteAddress());
//	        test.contrview.setText("第 " + count + " 个 client 登陆！address： : "
//	                + session.getRemoteAddress());
	    }
	 
	    // 当客户端发送的消息到达时:
	    public void messageReceived(IoSession session, Object message)
	            throws Exception {
	    	System.out.println("--------------------------------------------------");
	    	MyDecoder.isexecute=true;
	    	Parent_object ho = (Parent_object) message;
	    	switch(ho.getOperation()){
	    		case 1:					//判断是否有相同的图片
	    		{
	    			System.out.println("Isupload");
	    			System.out.println("查询是否有相同的照片");	    		
	    			IsUpload(session,message);		//获取是否需要上传信息	
//	    			test.contrview.setText("客户机正在上传图片....");
	    			System.out.println("客户机正在上传图片....");
	    		}	
	    		break; 
	    		case 2:					//直接上传
	    		{
	    			System.out.println("upload");
	    			Upload(session,message);
	    			image=null;
	    			selectImage=null;
	    			Next(session, message);	    			
	    		}
	    		break;
	    		case 3:					//根据用户随机序列 获取本地密文 进行比较
	    		{
	    			System.out.println("PreCompare");
	    			PreCompare(session,message);
	    			selectImage=null;
//	    			test.contrview.setText("密文比较中....");
	    			System.out.println("密文比较中....");
	    		}
	    			break;
	    		case 4:					//查询相同图片
	    		{
	    			SeleteImage(session,message);
	    			System.out.println("客户机正在查询相同的图片....");
//	    			test.contrview.setText("客户机正在查询相同的图片....");
	    		}break;
	    		case 5:
	    		{
	    			cycleDownload(session,message);
	    		}
	    		break;
	    		case 6:							//终止下载
	    		{
	    			selectImage=null;
	    		}
	    		break;

	    			
	    	}
	    }
	    // 当信息已经传送给客户端后触发此方法.
	    public void messageSent(IoSession session, Object message) {
	        System.out.println("信息已经传送给客户端");
	 
	    }
	 
	    // 当一个客户端关闭时
	    public void sessionClosed(IoSession session) {
	        System.out.println("one Clinet Disconnect !");
//	        test.contrview.setText("one Clinet Disconnect !");	        
	    }
	 
	    // 当连接空闲时触发此方法.
	    public void sessionIdle(IoSession session, IdleStatus status) {
	        System.out.println("连接空闲");
	    }
	 
	    // 当接口中其他方法抛出异常未被捕获时触发此方法
	    public void exceptionCaught(IoSession session, Throwable cause) {
	      //  System.out.println("其他方法抛出异常");
	    	cause.printStackTrace();
	    }  
	    public  void IsUpload(IoSession session,Object message)
	    {
	    	image=(Imageinfo) message;
	    	try {
	    		long timeInMillis1= Calendar.getInstance().getTimeInMillis();
	    		selectImage = serverActionImpl.SelectImage(image, image.getSim());
		    	long timeInMillis2= Calendar.getInstance().getTimeInMillis();
		    	System.out.println("查询时间:"+((timeInMillis2-timeInMillis1)));//////////////////////////////
			
				if(selectImage.isEmpty()||selectImage.size()==0)				//没有相同的	则直接上传
				{
					System.out.println("没有相同的图片***");
		    		mess.setOperation(1);				//直接上传
		    		mess.setMessage("Upload");
		    		session.write(mess);			
				}
				else
				{
					System.out.println("有相同的图片进行比较***");
	    			mess.setOperation(2);				//发送序列
	    			mess.setMessage("PreCompare");
	    			session.write(mess);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	//查询有没有相同的图片
	    }
	    public  void Upload(IoSession session,Object message)
	    {
	    	System.out.println("直接上传");
	    	ImageData imagecode=(ImageData) message;
	    	image.setEncode(imagecode.getBimage());
	    	image.setEnlength(imagecode.getLength());
	     	long timeInMillis = Calendar.getInstance().getTimeInMillis(); ///////////////////////////////////////////////
			System.out.println("上传加密时间结束:"+timeInMillis);
			long timeInMillis1= Calendar.getInstance().getTimeInMillis();
	    	serverActionImpl.SaveImage(image);
	    	long timeInMillis2= Calendar.getInstance().getTimeInMillis();
	    	System.out.println("保存时间:"+((timeInMillis2-timeInMillis1)));//////////////////////////////
	    }
	    public  void PreCompare(IoSession session,Object message)
	    {
	    	System.out.println("取出密文对应位子数据");
	    	Comparenumber number=(Comparenumber) message;
	    	byte[] assure = serverActionImpl.Assure(number, selectImage.get(0));
	    	if(assure==null)								//如果数据库对应位子的图片密文已被删除 则获取为null		
			{			
				serverActionImpl.DelImage(selectImage.get(0).getPid());				//删除
				System.out.println("判断出没有文件");
				mess.setOperation(1);				//直接上传
	    		mess.setMessage("服务器丢失文件,重新上传...");
	    		session.write(mess);			
			}else
			{
		    	ImageData imageCode = new ImageData(assure); 
		    	imageCode.setObjectlength(0);  					//无意义但不能删除否则不能传送
				imageCode.setOperation(3);						 //	比较	
				imageCode.setType("test");
				imageCode.setPid(0);
				session.write(imageCode);
			}
	    }
	    
//	    public  void SeleteImage(IoSession session,Object message) throws SQLException, IOException
//	    {
//	    	image=(Imageinfo) message;
//	    	selectImage = serverActionImpl.SelectImage(image, image.getSim() );
//	    	System.out.println("相似图片个数"+selectImage.size());
//	    	ImageData imageData = new ImageData();
//	    	Imageinfo imageinfo = selectImage.get(selectImage.size()-1);
//	    	imageData.setBimage(imageinfo.getEncode());
//	    	imageData.setOperation(4);					//下载相似	    	
//	    	imageData.setPid(Integer.valueOf(imageinfo.getPid()));		
//	    	imageData.setType(imageinfo.getImagetype());
//	    	imageData.setObjectlength(selectImage.size()-1); 		//还需要下载的个数
//	    	session.write(imageData);
//		  
//	    }
	    //查询图片
	    public  void SeleteImage(IoSession session,Object message) 
	    {	        	
			try {
				image=(Imageinfo) message;
				selectImage = serverActionImpl.SelectImage(image, image.getSim());
				if(selectImage.isEmpty()||selectImage.size()==0)				
				{
					System.out.println("没有相同的图片***");
		    		mess.setOperation(6);				//没有相似图片
		    		mess.setMessage("没有相同的图片");
		    		session.write(mess);			
				}
				else
				{
					System.out.println("相似图片个数"+selectImage.size());
					ImageData imageData = new ImageData();
					Imageinfo imageinfo = selectImage.get(selectImage.size()-1);
					imageData.setBimage(imageinfo.getEncode());
					imageData.setOperation(4);
					imageData.setPid(Integer.valueOf(imageinfo.getPid()));
					imageData.setType(imageinfo.getImagetype());
					imageData.setObjectlength(selectImage.size()-1);
					long timeInMillis = Calendar.getInstance().getTimeInMillis(); ///////////////////////////////////////////////
					System.out.println("下载加密时间开始:"+timeInMillis);
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
	    	System.out.println("相似图片个数"+length);
	    	
	    	ImageData imageData = new ImageData();
	    	Imageinfo imageinfo = selectImage.get(length-1);
	    	imageData.setBimage(imageinfo.getEncode());
	    	imageData.setOperation(4);					//下载相似	
	    	
	    	System.out.println("bit个数"+imageinfo.getEncode().length);
	    	System.out.println("pid"+imageinfo.getPid());
	    	System.out.println("type"+imageinfo.getImagetype());
	    	
	    	imageData.setPid(Integer.valueOf(imageinfo.getPid()));		
	    	imageData.setType(imageinfo.getImagetype());
	    	imageData.setObjectlength(length-1); 		//还需要下载的个数
//	    	if(length==0)				//清空
//	    	{
//	    		selectImage=null;
//	    	}
	    	session.write(imageData);
	    }
	    public void Next(IoSession session,Object message)
	    {
	    	mess.setOperation(5);				//上传下一张
    		mess.setMessage("Upload");
    		session.write(mess);	
	    }
}  

