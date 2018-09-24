package Mina.Coder;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

/** 
 *  ����빤�� 
 */  
public class MyCoderFactory implements ProtocolCodecFactory{  
      
    private MyDecoder decoder;  
    private MyEncoder encoder;  
    
    
    public MyCoderFactory() {  
        this.decoder = new MyDecoder();  
        this.encoder = new MyEncoder();  
        
    }  
  
    @Override  
    public ProtocolDecoder getDecoder(IoSession session) throws Exception {  
        return decoder;  
    }  
  
    @Override  
    public ProtocolEncoder getEncoder(IoSession session) throws Exception {  
        return encoder;  
    }  
  
}  