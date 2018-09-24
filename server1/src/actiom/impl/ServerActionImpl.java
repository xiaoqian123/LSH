package actiom.impl;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import service.BucketService;
import service.ImageinfoService;
import service.SaveRead;
import action.ServerAction;
import bean.Comparenumber;
import bean.ImageData;
import bean.Imageinfo;

public class ServerActionImpl implements ServerAction{
	BucketService Bucket=new BucketService();
	ImageinfoService imageinfoService = new ImageinfoService();
	SaveRead saveRead = new SaveRead();
	File dir = new File("ImageCode");
	/**
	 * ���ڲ�ѯͼƬ sign="1" Ϊ����һ����ͼƬ  sign="0" ��������ͼƬ
	 * @return	
	 * @throws SQLException 
	 */
	public List<Imageinfo>  SelectImage(Imageinfo Image,int sim) throws SQLException
	{			
		List<Imageinfo> lists = new ArrayList<Imageinfo>();
		String imageid = Bucket.getImageByBid(Image);
		if(imageid.equals(null) || imageid.equals(""))
		{
			System.out.println("û����ͬ��ͼƬ");
			return lists;
		}else
		{
			
			Map<String,Integer> map = Count(imageid.split(","));//ȥ��
			System.out.println("sim:"+sim);
			 for (Entry<String, Integer> entry : map.entrySet()) {
				 System.out.println("ͼƬID"+entry.getKey()+"���ִ�����"+entry.getValue());
				 Imageinfo Image1 = imageinfoService.GetImageByid(entry.getKey());
				 if((int)(entry.getValue()*100/5)>=(sim))
				  {			
					 System.out.println(Image1.getPid()+"��"+"��ǰͼƬ��ͬ");
					 try {
							lists.add(DownLoad(Image1));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						 System.out.println("key= " + entry.getKey()+" "+(entry.getValue()*100/5)); 
				  }	else
				  {
					 
						  System.out.println(Image1.getPid()+"��"+"��ǰͼƬ����ͬ");
						  System.out.println("key= " + entry.getKey()+" "+(entry.getValue()*100/5)); 
				  }
			  }			 
			return lists;
		}
	
	}
//	public List<Imageinfo>  SelectImage(Imageinfo Image,int sim) throws SQLException
//	{			
//		List<Imageinfo> lists = new ArrayList<Imageinfo>();
//		String imageid = Bucket.getImageByBid(Image);
//		if(imageid.equals(null) || imageid.equals(""))
//		{
//			System.out.println("û����ͬ��ͼƬ");
//			return lists;
//		}else
//		{
//			
//			String[] pid = delRepeat(imageid.split(","));//ȥ��
//			
//			for(int i=0;i<pid.length;i++)		//����pid��ѯ��Ӧ��ͼƬ
//			{
//				System.out.println("ͼƬ��"+pid[i]);
//				Imageinfo Image1 = imageinfoService.GetImageByid(pid[i]);
//				int dis=imageinfoService.distance(Image1.getPhase(),Image.getPhase());
//				System.out.println("�����ǣ�"+dis);
//				if(sim==1)				//������ͬ
//				{
//					if(dis==0){										//��ȡ��Ӧ�������洢��ͼƬ
//						System.out.println(Image1.getPid()+"��"+"��ǰͼƬ��ͬ");	
//						try {
//							lists.add(DownLoad(Image1));
//						} catch (IOException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//				
//					}else
//					{
//						System.out.println(Image1.getPid()+"��"+"��ǰͼƬ����ͬ");						
//					}
//				}else								//��������
//				{
//					if(dis<sim){
//						System.out.println(Image1.getPid()+"��"+"��ǰͼƬ����");	
//						try {
//							lists.add(DownLoad(Image1));
//						} catch (IOException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//
//					}else
//					{
//						System.out.println(Image1.getPid()+"��"+"��ǰͼƬ������");						
//					}
//				}
//			}
//			return lists;
//		}
//	
//	}		
		
		
		
		

	
	/**
	 * ȥ���ظ�������
	 * @param str
	 * @return
	 */
	private Map<String,Integer> Count(String [] ss)
	{
		System.out.println("����ͼƬID");
		for(int i=0;i<ss.length;i++)
		{
			System.out.print(ss[i]+",");
		}
		System.out.println();
		Map<String,Integer> map=new HashMap<String,Integer>();
		
		for (int i = 0; i < ss.length; i++) {
			String temp=ss[i];
			int count=0;
			for (int j = 0; j < ss.length; j++) {
				String temp2=ss[j];
				if(temp.equals(temp2)){
					count++;
				}
				map.put(ss[i], count);	
			}
		}
		return map;
		
		
	}
	/**
	 * ���ڱ���ͼƬ
	 * ǰ����Ҫ�����ܺ�������Ƚ��б���
	 * @param Image
	 * @return
	 */
	public String SaveImage(Imageinfo Image)
	{
		
		String ranDid="0";
		while(imageinfoService.IsImageByid(ranDid).equals("1"))
		{
			 ranDid = RANDid();
			 System.out.println("����ͼƬID��"+ranDid);
		}
		Image.setPid(ranDid);
		try {
			if(LocalSaveImageCode(Image)!="0")			//���汾���ļ�
			{
				imageinfoService.SaveImage(Image);	
				Bucket.SaveImage(Image);
				return "1";
			}else
			{
				return "0";
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	/**
	 * ����ɾ��ͼƬ
	 * @return
	 */
	public void DelImage(String pid) 
	{
		
		imageinfoService.DelImage(pid);
		Bucket.DelImage(pid);
	}
	/**
	 * �����ܺ��ͼƬ���浽��ǰĿ¼�µ�ImageCode�ļ��У�����·������
	 * @param image
	 * @throws IOException 
	 */
	private String LocalSaveImageCode(Imageinfo image) throws IOException
	{
		//SaveRead saveRead = new SaveRead();
		
		dir.mkdir();			//�����ļ���
		String Path = dir.getCanonicalPath()+"\\"+image.getPid()+".txt";
		byte[] encode = image.getEncode();
		if(encode != null)
		{
			saveRead.byte2type(encode, Path);
			System.out.println("���ر���ɹ�");
			return "1";
		}else
		{
			System.out.println("û�м����ļ�");
			return "0";
		}
		
	}
	
/**
 * ����ͼƬ�����ȸ���Pid ��ѯͼƬ��ȡͼƬ�洢λ�ã�Ȼ��GetLocalImageCode(),Ȼ���װ����
 * @param pid
 * @return
 * @throws SQLException 
 * @throws IOException 
 */
	public Imageinfo DownLoad(Imageinfo pid) throws SQLException, IOException
	{
		String Path = dir.getCanonicalPath()+"\\"+pid.getPid()+".txt";
		pid.setEncode(GetLocalImageCode(Path,0,pid.getEnlength()));
		return pid;
		
		
	}
	
//	public ImageData DownLoad(Imageinfo image) throws SQLException, IOException
//	{
//		String Path = dir.getCanonicalPath()+"\\"+image.getPid()+".txt";
//		ImageData imageData = new ImageData(GetLocalImageCode(Path,0,image.getEnlength()));
//		
//		return imageData;
//		
//		
//	}
	/**
	 * ����ʹ��
	 * ��ȡ���ؼ���ͼƬ��byte
	 * pid ΪͼƬpid
	 * @param i 
	 * @return
	 * @throws IOException 
	 * @throws SQLException 
	 */
	private byte[] GetLocalImageCode(String path, int start,int Enlength) throws IOException, SQLException
	{		 
		byte[] type2byte = saveRead.type2byte(path,start, Enlength);
		return type2byte;	
	}
	
	public String RANDid()
	{
		java.util.Random random=new java.util.Random();// ���������	
		return String.valueOf(random.nextInt(1000)+1); 	
	}
	
	public byte[] Assure(Comparenumber number,Imageinfo image)
	{

		try {
			String Path = dir.getCanonicalPath()+"\\"+image.getPid()+".txt";
			//byte[] ImageEncode=GetLocalImageCode(Path,0,image.getEnlength());		//�����ڷ�������������ȡ����
			byte[] ImageEncode=image.getEncode();
			if(ImageEncode==null||ImageEncode.equals(null))							//�������ļ���ʧ
			{
				return null;
			}
			SaveRead saveRead = new SaveRead();									
			byte[] bytePart = saveRead.bytePart(number, ImageEncode);					//��ȡ���Ķ�Ӧλ�ӵ�����		
//			for(int i=0;i<bytePart.length;i++)
//			{
//				System.out.print(bytePart[i]+" ");
//			}
			System.out.println("��������ȡ�ɹ�");
			return bytePart;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		
	}


	
}
