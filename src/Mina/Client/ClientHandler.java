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
	  // ��һ���Ͷ˶����ᵽ��������
    @Override
    public void sessionOpened(IoSession session) throws Exception {
    	instancei.contrview.setconnectText("�����ӵ�������!");
    }
 
    // ��һ���ͻ��˹ر�ʱ
    @Override
    public void sessionClosed(IoSession session) {
        System.out.println("I'm Client &&  I closed!");
        instancei.contrview.setconnectText("�ѶϿ�������!");
    }
    // ����Ϣ�Ѿ����͸��ͻ��˺󴥷��˷���.
    public void messageSent(IoSession session, Object message) {
    	
        System.out.println("��Ϣ�Ѿ����͸��ͻ���");
      //  instancei.contrview.setconnectText("��Ϣ�Ѿ����͵�������");
 
    }
    // ���������˷��͵���Ϣ����ʱ:
    @Override
    public void messageReceived(IoSession session, Object message)
            throws Exception {
    	MyDecoder.isexecute=true;
    	
    	Parent_object ho = (Parent_object) message;
    	switch(ho.getOperation()){
    		
			case 1:			//ֱ���ϴ�
			{
				Message mess = (Message) message;
				Upload();
				System.out.println(mess.getMessage());
				instancei.contrview.setStaText("���ϴ��ɹ�");
			}				
			break;			//��������
			case 2:
			{
				
				Message mess = (Message) message;				
				System.out.println(mess.getMessage());
				PreCompare();
				break;
			}
			case 3:				//�Ƚ�
			{
				ImageData mess = (ImageData) message;
				Compare(mess.getBimage());
				test.t=true;
			}break;
			case 4:									//��ѯ������ͼƬ
			{
				cycleDownload(session, message);
				
			}break;
			case 5:
			{
				instancei.next();  			//������һ��ͼƬ
			}break;
			case 6:									//��ѯ����ͼƬ��һ��
			{
				System.out.println("��ѯ����ͼƬ��һ��");//---------------------------------------------------------------��------
				instancei.contrview.setStaText("û�����Ƶ�ͼƬ");
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
    	instancei.contrview.setStaText("�����ϴ�����");
    	test.getInstancei().Compare(number);
    }

    int count=0;
    public void cycleDownload(IoSession session, Object message)
    {

    	 ImageData image = (ImageData) message;
		 String path = clientActionImpl.GetImageDecode(image,"1");	
		 test.getInstancei().contrview.setList(path);								//��·������
		 int number = image.getObjectlength();
		int tnum= number+1;
		 if(instancei.printsimnum)
		 {
			 count=tnum;
			 System.out.println("��ѯ����ͬͼƬ"+tnum+"��,����������ȴ�...");
			 instancei.contrview.setStaText("��ѯ����ͬͼƬ"+tnum+"��,����������ȴ�...");
			 instancei.printsimnum=false;
		 }
		 if(number!=0)
		 {
			 Message mess = new Message();
			 mess.setMessage(String.valueOf(number));
			 mess.setOperation(5);				//ѭ���ϴ�
			 session.write(mess);
		 }
			 else{
			//instancei.contrview.dispy();
				 instancei.contrview.setStaText("��ѯ���");//------------------------------------------����Ի�����ʾͼƬ------------
				 instancei.contrview.selectdisplay("��ѯ���,��ͬͼƬ"+count+"��");
				 Message mess = new Message();
				 mess.setMessage("0");				//��ʵ������
				 mess.setOperation(6);				//��ֹ�ϴ�
				 session.write(mess);
		 }
    }
}
