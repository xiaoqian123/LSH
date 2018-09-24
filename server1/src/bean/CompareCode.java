package bean;

import java.awt.image.BufferedImage;

public class CompareCode extends Parent_object {
	  public int length = 0;// 字节数组长度  
	    private byte[] bimage;// 待发送的字节数组  
	    private BufferedImage image;// 将字节数组转换成图片文件 
		public int getLength() {
			return length;
		}
		public void setLength(int length) {
			this.length = length;
		}
		public byte[] getBimage() {
			return bimage;
		}
		public void setBimage(byte[] bimage) {
			this.bimage = bimage;
		}
		public BufferedImage getImage() {
			return image;
		}
		public void setImage(BufferedImage image) {
			this.image = image;
		}
	    
}
