package bean;
/**
 * 
 * @Ͱ����ͼƬ�� ��Զ�
 *
 */
public class Bucket extends Parent_object{
	private int id;				
	private String pid;  //��ӦͼƬ�ı��
	private String bid;	//��ӦͼƬ��Ͱ��
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
