package bean;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.rmi.RemoteException;

import javax.imageio.ImageIO;

public class ImageData extends Parent_object{  
    private static final long serialVersionUID = 1L;  
    private String type ;// ��������  
    private byte[] bimage;// �����͵��ֽ�����  
    private BufferedImage image;// ���ֽ�����ת����ͼƬ�ļ�  
    private String mes;
    private int pid;			//ͼƬ����
    private int objectlength;
    
    
	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public int getObjectlength() {
		return objectlength;
	}

	public void setObjectlength(int objectlength) {
		this.objectlength = objectlength;
	}

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
	
	public ImageData() {  
    }  
  
    public ImageData(byte[] bimage) {  
        this.bimage = bimage;  
    }  
  
    public byte[] getBimage() {  
        return bimage;  
    }  
  
    public BufferedImage getImage() {  
        try {  
            if (bimage.length > 0) {  
                ByteArrayInputStream in = new ByteArrayInputStream(bimage);  
                this.image = ImageIO.read(in);  
                in.close();  
            }  
        } catch (RemoteException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return this.image;  
    }  
  
    public int getLength() {  
        return bimage.length;  
    }  
  
 
  
    public void setBimage(byte[] bimage) {  
        this.bimage = bimage;  
    }  
  

}  