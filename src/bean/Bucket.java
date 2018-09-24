package bean;
/**
 * 
 * @桶类与图片类 多对多
 *
 */
public class Bucket extends Parent_object{
	private int id;				
	private String pid;  //对应图片的编号
	private String bid;	//对应图片的桶号
	public Bucket(String string, String string2) {
		// TODO Auto-generated constructor stub
		pid=string;
		bid=string2;
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
	public String getBid() {
		return bid;
	}
	public void setBid(String bid) {
		this.bid = bid;
	}

	
}
