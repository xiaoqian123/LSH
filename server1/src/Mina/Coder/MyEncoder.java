package Mina.Coder;

import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import bean.ImageData;
import bean.Imageinfo;
import bean.Message;

public class MyEncoder extends ProtocolEncoderAdapter  {
	
	   public void encode(IoSession session, Object message,  
	            ProtocolEncoderOutput out) throws Exception {  
		   
		   if(message instanceof Message) {
			   MessageEncode(message,out); 
			   
		   }else if(message instanceof ImageData)
		   {
			   ImageCodeEncode(message,out);  
		   }	        
	    }  
//	   private void  ImageinfoEncode( Object message,ProtocolEncoderOutput out) throws CharacterCodingException
//	   {
//		   Charset charset = Charset.forName("UTF-8");	   
//		   Imageinfo image = (Imageinfo) message;  
//		 	 IoBuffer buffer = IoBuffer.allocate(1024).setAutoExpand(true); 
//		 	byte[] phase = image.getPhase().getBytes(charset);	
//		 	byte[] Bcid = image.getBcid().getBytes(charset);	
//		 	byte[] Imagetype = image.getImagetype().getBytes(charset);		
//		 	
//		    buffer.putInt(1);			//���ڽ����ж�
//		 	buffer.putInt(phase.length);
//		 	buffer.put(phase);
//		 	buffer.putInt(Bcid.length);
//		 	buffer.put(Bcid);
//		 	buffer.putInt(Imagetype.length);
//		 	buffer.put(Imagetype);
//		 	buffer.putInt(image.getOperation());
//		 	
//		 	buffer.flip();  
//		 	out.write(buffer);  
//		 	buffer.free();
//	   }
	   private void  MessageEncode( Object message,ProtocolEncoderOutput out) throws CharacterCodingException
	   {
		   Charset charset = Charset.forName("UTF-8");
	   
			Message mes = (Message) message;  
	        IoBuffer buffer = IoBuffer.allocate(100).setAutoExpand(true); 
	        
	    	byte[] mess = mes.getMessage().getBytes(charset);	
	    	
	        buffer.putInt(3);			//�����ж�
	        
	        buffer.putInt(mess.length);
		 	buffer.put(mess);
		 	buffer.putInt(mes.getOperation());
	
	        buffer.flip();  
	        out.write(buffer);  
	        buffer.free();  
	   }
	   private void  ImageCodeEncode( Object message,ProtocolEncoderOutput out) throws CharacterCodingException
	   {
		   Charset charset = Charset.forName("UTF-8");
	       // CharsetEncoder charsetEncoder = charset.newEncoder(); 
	        ImageData image = (ImageData) message;  
	        IoBuffer buffer = IoBuffer.allocate(2048).setAutoExpand(true); 
	        byte[] Bcid = image.getBimage();
	        byte[] type = image.getType().getBytes(charset);	
	  
	        buffer.putInt(2);			//�����ж�
	        System.out.println(Bcid.length);
	        buffer.putInt(type.length);
		 	buffer.put(type);						// ������������  

	        buffer.putInt(image.getLength());// �����ֽ�������ܳ��ȣ�������ʱʹ��   
//	        System.out.println("����"+image.getLength()); 
	        
	        buffer.putInt(image.getPid());
	        buffer.putInt(image.getObjectlength());
	        buffer.putInt(image.getOperation());
	        
	        buffer.put(Bcid);// �����ֽ�����  	        
	        
	        buffer.flip();  
	        out.write(buffer); 	        
	        buffer.free();  
	   }
	   
	}


