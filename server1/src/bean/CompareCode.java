package bean;

import java.awt.image.BufferedImage;

public class CompareCode extends Parent_object {
	  public int length = 0;// �ֽ����鳤��  
	    private byte[] bimage;// �����͵��ֽ�����  
	    private BufferedImage image;// ���ֽ�����ת����ͼƬ�ļ� 
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
