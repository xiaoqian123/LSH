package bean;

import java.security.Key;

/**
 * 
 * @ͼƬ��
 *
 */
// implements java.io.Serializable  ���л�
public class Imageinfo extends Parent_object {
	private int id;	
	private String pid;		//ͼƬ���
//	private String phase;	//��ӦͼƬ����ֵ
	private String Bcid;		//Ͱ��
	private byte[] encode=null;		//���ܺ������	,����Ҫ���浽���ݿ�
	private int enlength;		//���ܺ�����ݵĳ���
	private String imagetype;		//ͼƬ����
	private int sim;				//���ƶ�  1����ͬ 
	
	public int getSim() {
		return sim;
	}
	public void setSim(int sim) {
		this.sim = sim;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
//	public String getPhase() {
//		return phase;
//	}
//	public void setPhase(String phase) {
//		this.phase = phase;
//	}

	public String getBcid() {
		return Bcid;
	}
	public void setBcid(String bcid) {
		Bcid = bcid;
	}
	public byte[] getEncode() {
		return encode;
	}
	public void setEncode(byte[] encode) {
		this.encode = encode;
	}
	public int getEnlength() {
		return enlength;
	}
	public void setEnlength(int enlength) {
		this.enlength = enlength;
	}
	public String getImagetype() {
		return imagetype;
	}
	public void setImagetype(String imagetype) {
		this.imagetype = imagetype;
	}

	

	
}
