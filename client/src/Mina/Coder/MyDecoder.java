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
 *  解码器可继承ProtocolEncoderAdapter，但它不利于处理粘包的情况 
 */  
public class MyDecoder extends CumulativeProtocolDecoder {  

	/** 
	 * 接收图片数据，由于图片数据比较大，tcp是采用分段式发送，所有需要等所有数据接收完之后才能解码 
	 * 解码原理：首先读取服务器端发送数据的总长度length，然后与当前的buff中的数据长度matchLength比较，如果matchLength>= 
	 * length则认为数据发送完毕, 否則将当前的buff保存起来，在下次发送buff之时合并为一个buff，然后在按照以上条件判断 
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
	    			System.out.println("继续解码......." + buff.remaining());
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
	    	
	      
	        return false;// 返回false时，解码器就不会执行解码，返回true是在解码完成  
	    }  
	    private boolean  ImageinfoDecode(IoSession session,IoBuffer buff,ProtocolDecoderOutput out) throws CharacterCodingException
	    {
	   	 if (buff.hasRemaining()) 
			{
				 CharsetDecoder charset = Charset.forName("UTF-8").newDecoder();  
			        System.out.println("继续解码......." + buff.remaining()); 
			      
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
	   	        System.out.println("继续解码......." + buff.remaining());  
	   	        // 取出context  
	   	        Context ctx = this.getContext(session);// 将contex从session中取出  
	   	        int length = ctx.getLength();// 数据总长度  
	   	 
	   	        IoBuffer buffer = ctx.getBuffer();// 保存数据的buffer  
	   	        int matchLength = ctx.getMatchLength();	// 目前已经发送的数据的总长度  
	   	    //    System.out.println("目前已经发送的数据的总长度  "+matchLength);
	   	        int test =  ctx.getMatchLength();	   	  
	   	        if (0 == length) {// 第一次取值 	   	        	
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
	   	        if (buff.hasRemaining()) {// 如果buff中还有数据  
	   	            buffer.put(buff);// 添加到保存数据的buffer中  
	   	            if (matchLength >= length) {// 如果已经发送的数据的长度>=目标数据的长度,则进行解码  
	   	            //	System.out.println("数据的长度>=目标数据的长度"+matchLength);
	   	                byte[] b = new byte[length];  
	   	                // 一定要添加以下这一段，否则不会有任何数据,因为，在执行buffer.put(buff)时buffer的起始位置已经移动到最后，所有需要将buffer的起始位置移动到最开始  
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
	   	                System.out.println("解码完成.......");
	   	                  	               
	   	                return true;  
	   	            } else {  
	   	                ctx.setBuffer(buffer);  
	   	            }  
	   	        }
				return false;
	    }
	    /** 
	     * 定义一个内部类，用来封转当前解码器中的一些公共数据，主要是用于大数据解析 
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
	    		System.out.println("空");
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

















