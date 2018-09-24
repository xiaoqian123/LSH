package bean;

import java.security.Key;

/**
 * 
 * @图片类
 *
 */
// implements java.io.Serializable  序列化
public class Imageinfo extends Parent_object {
	private int id;	
	private String pid;		//图片编号
//	private String phase;	//对应图片特征值
	private String Bcid;		//桶号
	private byte[] encode=null;		//加密后的数据	,不需要保存到数据库
	private int enlength;		//加密后的数据的长度
	private String imagetype;		//图片类型
	private int sim;				//相似度  1是相同 
	
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
