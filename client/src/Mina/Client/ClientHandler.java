package Mina.Client;

import java.io.File;

import javax.naming.Context;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import Mina.Coder.MyDecoder;
import actiom.impl.ClientActionImpl;
import bean.Bucket;
import bean.ImageData;
import bean.Imageinfo;
import bean.Message;
import bean.Parent_object;

public class ClientHandler  extends IoHandlerAdapter{
	ClientActionImpl clientActionImpl = new ClientActionImpl();
	test instancei = test.getInstancei();
	  // 当一个客端端连结到服务器后
    @Override
    public void sessionOpened(IoSession session) throws Exception {
    	instancei.contrview.setconnectText("已连接到服务器!");
    }
 
    // 当一个客户端关闭时
    @Override
    public void sessionClosed(IoSession session) {
        System.out.println("I'm Client &&  I closed!");
        instancei.contrview.setconnectText("已断开服务器!");
    }
    // 当信息已经传送给客户端后触发此方法.
    public void messageSent(IoSession session, Object message) {
    	
        System.out.println("信息已经传送给客户端");
      //  instancei.contrview.setconnectText("信息已经传送到服务器");
 
    }
    // 当服务器端发送的消息到达时:
    @Override
    public void messageReceived(IoSession session, Object message)
            throws Exception {
    	MyDecoder.isexecute=true;
    	
    	Parent_object ho = (Parent_object) message;
    	switch(ho.getOperation()){
    		
			case 1:			//直接上传
			{
				Message mess = (Message) message;
				Upload();
				System.out.println(mess.getMessage());
				instancei.contrview.setStaText("已上传成功");
			}				
			break;			//发送序列
			case 2:
			{
				
				Message mess = (Message) message;				
				System.out.println(mess.getMessage());
				PreCompare();
				break;
			}
			case 3:				//比较
			{
				ImageData mess = (ImageData) message;
				Compare(mess.getBimage());
				test.t=true;
			}break;
			case 4:									//查询到相似图片
			{
				cycleDownload(session, message);
				
			}break;
			case 5:
			{
				instancei.next();  			//发送下一张图片
			}break;
			case 6:									//查询相似图片无一张
			{
				System.out.println("查询相似图片无一张");//---------------------------------------------------------------待------
				instancei.contrview.setStaText("没有相似的图片");
			}break;
    	}


    }
    public void Upload()
    {
    	test.getInstancei().SentImage();
    }
    public void PreCompare()
    {
    	test.getInstancei().PreCompare();
    }
    public void Compare(byte [] number)
    {
    	instancei.contrview.setStaText("正在上传序列");
    	test.getInstancei().Compare(number);
    }

    int count=0;
    public void cycleDownload(IoSession session, Object message)
    {

    	 ImageData image = (ImageData) message;
		 String path = clientActionImpl.GetImageDecode(image,"1");	
		 test.getInstancei().contrview.setList(path);								//将路径保存
		 int number = image.getObjectlength();
		int tnum= number+1;
		 if(instancei.printsimnum)
		 {
			 count=tnum;
			 System.out.println("查询出相同图片"+tnum+"个,正在下载请等待...");
			 instancei.contrview.setStaText("查询出相同图片"+tnum+"个,正在下载请等待...");
			 instancei.printsimnum=false;
		 }
		 if(number!=0)
		 {
			 Message mess = new Message();
			 mess.setMessage(String.valueOf(number));
			 mess.setOperation(5);				//循环上传
			 session.write(mess);
		 }
			 else{
			//instancei.contrview.dispy();
				 instancei.contrview.setStaText("查询完毕");//------------------------------------------激活对话框显示图片------------
				 instancei.contrview.selectdisplay("查询完毕,相同图片"+count+"个");
				 Message mess = new Message();
				 mess.setMessage("0");				//无实际意义
				 mess.setOperation(6);				//终止上传
				 session.write(mess);
		 }
    }
}
