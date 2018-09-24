package Mina.Coder;

import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.AttributeKey;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;







import bean.ImageData;
import bean.Imageinfo;
import bean.Message;
import bean.Parent_object;
import bean.YHConstants;

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
	    private final AttributeKey CONTEXT	= new AttributeKey(this.getClass(), "context"); ;  
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
	    	if(operate==3)
	    	{		
	    			System.out.println("��������......." + buff.remaining());
	    		    CharsetDecoder charset = Charset.forName("UTF-8").newDecoder();  
	    		    Message mess = new Message();
  			        int messlength = buff.getInt();
  			        mess.setMessage(buff.getString(messlength,charset));
  			        mess.setOperation(buff.getInt()); 
  			        out.write(mess);
  			        return true;
	   	        
	    	} else if(operate==2)
   	        {

	    		 return ImageCodeDecode(session,buff,out);
   	        }
//	    		 else if(operate==1)
//   	        {
//   	         return ImageinfoDecode(session,buff,out);
//   	        }
	    	
	      
	        return false;// ����falseʱ���������Ͳ���ִ�н��룬����true���ڽ������  
	    }  
	    private boolean  ImageinfoDecode(IoSession session,IoBuffer buff,ProtocolDecoderOutput out) throws CharacterCodingException
	    {
	   	 if (buff.hasRemaining()) 
			{
				 CharsetDecoder charset = Charset.forName("UTF-8").newDecoder();  
			        System.out.println("��������......." + buff.remaining()); 
			      
			        Imageinfo image = new Imageinfo();
			   //     int phaselength = buff.getInt();
			 //       image.setPhase(buff.getString(phaselength,charset));
			        int Bcidlength = buff.getInt();
			        image.setBcid(buff.getString(Bcidlength,charset));
			        int Imagetypelength = buff.getInt();
			        image.setImagetype(buff.getString(Imagetypelength,charset)); 			       
			        image.setOperation(buff.getInt()); 
			        out.write(image);
			        return true;
			}
	    	return false;
	    }
	    private boolean  ImageCodeDecode(IoSession session,IoBuffer buff,ProtocolDecoderOutput out) throws CharacterCodingException
	    {
	    	   CharsetDecoder charset = Charset.forName("UTF-8").newDecoder();  
	   	        System.out.println("��������......." + buff.remaining());  
	   	        // ȡ��context  
	   	        Context ctx = this.getContext(session);// ��contex��session��ȡ��  
	   	        int length = ctx.getLength();// �����ܳ���  
	   	 
	   	        IoBuffer buffer = ctx.getBuffer();// �������ݵ�buffer  
	   	        int matchLength = ctx.getMatchLength();	// Ŀǰ�Ѿ����͵����ݵ��ܳ���  
	   	    //    System.out.println("Ŀǰ�Ѿ����͵����ݵ��ܳ���  "+matchLength);
	   	        int test =  ctx.getMatchLength();	   	  
	   	        if (0 == length) {// ��һ��ȡֵ 	   	        	
	   	        	int tyeplength = buff.getInt();
	   	        	String type = buff.getString(tyeplength,charset);	   	         
	   	            length = buff.getInt();	     
	   	            ctx.pid=buff.getInt();
	   	            ctx.objectlength=buff.getInt();	  	            
	   	            ctx.setOperation(buff.getInt());	   	      
	   	            matchLength = buff.remaining();  
	   	            ctx.setType(type);  
	   	            ctx.setLength(length);  	   	    
	   	            istime=false;
	   	        } else {  
	   	            matchLength += buff.remaining();  
	   	        }  
	   	        ctx.setMatchLength(matchLength);  
	   	        if (buff.hasRemaining()) {// ���buff�л�������  
	   	            buffer.put(buff);// ��ӵ��������ݵ�buffer��  
	   	            if (matchLength >= length) {// ����Ѿ����͵����ݵĳ���>=Ŀ�����ݵĳ���,����н���  
	   	            //	System.out.println("���ݵĳ���>=Ŀ�����ݵĳ���"+matchLength);
	   	                byte[] b = new byte[length];  
	   	                // һ��Ҫ���������һ�Σ����򲻻����κ�����,��Ϊ����ִ��buffer.put(buff)ʱbuffer����ʼλ���Ѿ��ƶ������������Ҫ��buffer����ʼλ���ƶ����ʼ  
	   	                buffer.flip();  
	   	                buffer.get(b);  
	   	                ImageData image = new ImageData(b);
	   	                image.setOperation(ctx.getOperation());
	   	                image.setType(ctx.getType());
	   	                image.setObjectlength(ctx.objectlength);
	   	                image.setPid(ctx.pid);
	   	                ctx=null; 
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
	    public class Context extends Parent_object{  
	        public IoBuffer buffer;  
	        public int length = 0;  
	        public int matchLength = 0;  
	        public String type = "";  
	        public String mes="";
	        public int objectlength;
	        public int pid;
	        public String getType() {
				return type;
			}

			public void setType(String type) {
				this.type = type;
			}

			public String getMes() {
				return mes;
			}

			public void setMes(String mes) {
				this.mes = mes;
			}

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
	            ctx = new Context();	         
	            session.setAttribute(CONTEXT, ctx);  
	        }  
	        return ctx;  
	    }  
	
}  

















