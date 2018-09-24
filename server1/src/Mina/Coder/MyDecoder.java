package Mina.Coder;

import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.AttributeKey;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import Mina.Coder.MyDecoder.Context;
import bean.Comparenumber;
import bean.ImageData;
import bean.Imageinfo;
import bean.Message;
import bean.Parent_object;


/** 
 *  �������ɼ̳�ProtocolEncoderAdapter�����������ڴ���ճ������� 
 */  
public class MyDecoder extends CumulativeProtocolDecoder {  
	/** 
	 * ����ͼƬ���ݣ�����ͼƬ���ݱȽϴ�tcp�ǲ��÷ֶ�ʽ���ͣ�������Ҫ���������ݽ�����֮����ܽ��� 
	 * ����ԭ�����ȶ�ȡ�������˷������ݵ��ܳ���length��Ȼ���뵱ǰ��buff�е����ݳ���matchLength�Ƚϣ����matchLength>= 
	 * length����Ϊ���ݷ������, ��t����ǰ��buff�������������´η���buff֮ʱ�ϲ�Ϊһ��buff��Ȼ���ڰ������������ж� 
	 * @author seara 
	 */  
		private final AttributeKey CONTEXT	= new AttributeKey(this.getClass(), "context"); 
	 	public static boolean isexecute=true;
	    public boolean istime=true;
	    @Override  
	    protected boolean doDecode(IoSession session, IoBuffer buff,  
	    		ProtocolDecoderOutput out) throws Exception { 
	    	int operate=2;
	    	if(isexecute)
	    	{
	    		operate = buff.getInt();
	    		isexecute=false;
	    	}	  
	   //	operate = buff.getInt();
	   // 	System.out.println(operate);
	    	if(operate==1)					//Imageinfo
	    	{
	    		 
	    		 return ImageinfoDecode(session,buff,out);
	    	
	    	} else if(operate==2)					//ImageCode
   	        {
	    		 
	    		 return ImageCodeDecode(session,buff,out);
   	        }else if(operate==3)
   	        {
   	         
   	        	return ComparenumberDecode(session,buff,out);
   	        }else if(operate==4)
   	        {
   	        	System.out.println("��������......." + buff.remaining());
    		    CharsetDecoder charset = Charset.forName("UTF-8").newDecoder();  
    		    Message mess = new Message();
			        int messlength = buff.getInt();
			        mess.setMessage(buff.getString(messlength,charset));
			        mess.setOperation(buff.getInt()); 
			   //     buff.flip(); 
			        out.write(mess);
			       
			        return true;
   	        }
	    //	 buff.free();
	      
	        return false;// ����falseʱ���������Ͳ���ִ�н��룬����true���ڽ������  
	    }  
	    
	    private boolean  ComparenumberDecode(IoSession session,IoBuffer buff,ProtocolDecoderOutput out)
	    {
	    	 if (buff.hasRemaining()) 
	   			{
	   				 CharsetDecoder charset = Charset.forName("UTF-8").newDecoder();  
	   			        System.out.println("��������......." + buff.remaining()); 
	   			      
	   			        Comparenumber number = new Comparenumber();
			   			number.setStart1(buff.getInt());
			   			number.setLenth1(buff.getInt());
			   			number.setStart2(buff.getInt());
			   			number.setLenth2(buff.getInt());
			   			number.setStart3(buff.getInt());
			   			number.setLenth3(buff.getInt());
			   			number.setOperation(buff.getInt()); 
	   			        out.write(number);
	   			        return true;
	   			}
			return false;
	    	
	    }
	    private boolean  ImageinfoDecode(IoSession session,IoBuffer buff,ProtocolDecoderOutput out) throws CharacterCodingException
	    {
   		 if (buff.hasRemaining()) 
   			{
   				 CharsetDecoder charset = Charset.forName("UTF-8").newDecoder();  
   			        System.out.println("��������......." + buff.remaining()); 
   			      
   			        Imageinfo image = new Imageinfo();
   			  //      int phaselength = buff.getInt();
   			  //      image.setPhase(buff.getString(phaselength,charset));
   			        int Bcidlength = buff.getInt();
			        image.setBcid(buff.getString(Bcidlength,charset));
			        int Imagetypelength = buff.getInt();
   			        image.setImagetype(buff.getString(Imagetypelength,charset)); 
   			        image.setSim(buff.getInt());
   			        image.setOperation(buff.getInt()); 
   			 //       buff.flip(); 
   			        out.write(image);
   			        buff.free();
   			        return true;
   			}
   		 	return false;
	       
	    }
	    private boolean  ImageCodeDecode(IoSession session,IoBuffer buff,ProtocolDecoderOutput out) throws CharacterCodingException
	    {
	    
	    	  CharsetDecoder charset = Charset.forName("UTF-8").newDecoder();  
	   	   //     System.out.println("��������......." + buff.remaining());  
	   	        // ȡ��context  
	   	        Context ctx = this.getContext(session);// ��contex��session��ȡ��  
	   	        int length = ctx.getLength();// �����ܳ���  
	   	//        System.out.println("�����ܳ���"+length);
	   	  //      System.out.println(length);
	   	        IoBuffer buffer = ctx.getBuffer();// �������ݵ�buffer  
	   	        int matchLength = ctx.getMatchLength();// Ŀǰ�Ѿ����͵����ݵ��ܳ���  
	   	   //     System.out.println("Ŀǰ�Ѿ����͵����ݵ��ܳ���  "+matchLength);
	   	        if (0 == length) {// ��һ��ȡֵ  
//	   	            String yh = buff.getString(YHConstants.LENGTH, charset);  
	   	            length = buff.getInt();	     
//	   	            int int1 = buff.getInt();
//	   	            String meg = buff.getString(int1,charset);
	   	            
	   	            ctx.setOperation(buff.getInt());	   	      
	   	            matchLength = buff.remaining();  

	   	            ctx.setLength(length); 
	   	            istime=false;
	   	        //    ctx.setMes(meg);
	   	            
	   	        } else {  
	   	            matchLength += buff.remaining();  
	   	        }  
	   	        ctx.setMatchLength(matchLength);  
	   	        if (buff.hasRemaining()) {// ���buff�л�������  
	   	            buffer.put(buff);// ��ӵ��������ݵ�buffer��  
	   	            if (matchLength >= length) {// ����Ѿ����͵����ݵĳ���>=Ŀ�����ݵĳ���,����н���  
	   	            	System.out.println("���ݵĳ���>=Ŀ�����ݵĳ���"+matchLength);
	   	            	byte[] b = new byte[length];  
	   	                // һ��Ҫ���������һ�Σ����򲻻����κ�����,��Ϊ����ִ��buffer.put(buff)ʱbuffer����ʼλ���Ѿ��ƶ������������Ҫ��buffer����ʼλ���ƶ����ʼ  
	   	                buffer.flip();  
	   	                buffer.get(b);  
	   	                ImageData image = new ImageData(b);
	   	                image.setOperation(ctx.getOperation());
	  
	   	                istime=true;
	   	                out.write(image);  
	   	                System.out.println("�������.......");  
	   	                return true;  
	   	            } else {  
	   	                ctx.setBuffer(buffer);  
	   	            }  
	   	        }
				return false;
	    }
	    
	    
	    
	    /** 
	     * ����һ���ڲ��࣬������ת��ǰ�������е�һЩ�������ݣ���Ҫ�����ڴ����ݽ��� 
	     *  
	     * @author seara 
	     *  
	     */  
	    public class Context extends Parent_object {  
	        public IoBuffer buffer;  
	        public int length = 0;  
	        public int matchLength = 0;  
	        public String yh = "";  
//	        public String mes="";
//	        
//	        public String getMes() {
//				return mes;
//			}
//
//			public void setMes(String mes) {
//				this.mes = mes;
//			}

			public Context() {  
	            this.buffer = IoBuffer.allocate(1024).setAutoExpand(true);  
	        }  
	  
	        public int getMatchLength() {  
	            return matchLength;  
	        }  
	  
	        public void setMatchLength(int matchLength) {  
	            this.matchLength = matchLength;  
	        }  
	  
	        public IoBuffer getBuffer() {  
	            return buffer;  
	        }  
	  
	        public void setBuffer(IoBuffer buffer) {  
	            this.buffer = buffer;  
	        }  
	  
	        public int getLength() {  
	            return length;  
	        }  
	  
	        public void setLength(int length) {  
	            this.length = length;  
	        }  
	  
	        public String getYh() {  
	            return yh;  
	        }  
	  
	        public void setYh(String yh) {  
	            this.yh = yh;  
	        }  
	    }  
	  
    public Context getContext(IoSession session) {  
		    
	        Context ctx = (Context) session.getAttribute(CONTEXT);  
	    	if(istime)
	    	{
	    		System.out.println("��");
	    		ctx=null;
	    		istime=false;
	    	}
	        if (ctx == null) {  
	        	System.out.println("null");
	            ctx = new Context();
	            session.setAttribute(CONTEXT, ctx);  
	        }  
	        return ctx;  
	    } 

	
}  

















