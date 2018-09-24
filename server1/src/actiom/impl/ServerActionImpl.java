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
	 * 用于查询图片 sign="1" 为查找一样的图片  sign="0" 查找相似图片
	 * @return	
	 * @throws SQLException 
	 */
	public List<Imageinfo>  SelectImage(Imageinfo Image,int sim) throws SQLException
	{			
		List<Imageinfo> lists = new ArrayList<Imageinfo>();
		String imageid = Bucket.getImageByBid(Image);
		if(imageid.equals(null) || imageid.equals(""))
		{
			System.out.println("没有相同的图片");
			return lists;
		}else
		{
			
			Map<String,Integer> map = Count(imageid.split(","));//去重
			System.out.println("sim:"+sim);
			 for (Entry<String, Integer> entry : map.entrySet()) {
				 System.out.println("图片ID"+entry.getKey()+"出现次数："+entry.getValue());
				 Imageinfo Image1 = imageinfoService.GetImageByid(entry.getKey());
				 if((int)(entry.getValue()*100/5)>=(sim))
				  {			
					 System.out.println(Image1.getPid()+"与"+"当前图片相同");
					 try {
							lists.add(DownLoad(Image1));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						 System.out.println("key= " + entry.getKey()+" "+(entry.getValue()*100/5)); 
				  }	else
				  {
					 
						  System.out.println(Image1.getPid()+"与"+"当前图片不相同");
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
//			System.out.println("没有相同的图片");
//			return lists;
//		}else
//		{
//			
//			String[] pid = delRepeat(imageid.split(","));//去重
//			
//			for(int i=0;i<pid.length;i++)		//根据pid查询对应的图片
//			{
//				System.out.println("图片："+pid[i]);
//				Imageinfo Image1 = imageinfoService.GetImageByid(pid[i]);
//				int dis=imageinfoService.distance(Image1.getPhase(),Image.getPhase());
//				System.out.println("距离是："+dis);
//				if(sim==1)				//查找相同
//				{
//					if(dis==0){										//获取对应服务器存储的图片
//						System.out.println(Image1.getPid()+"与"+"当前图片相同");	
//						try {
//							lists.add(DownLoad(Image1));
//						} catch (IOException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//				
//					}else
//					{
//						System.out.println(Image1.getPid()+"与"+"当前图片不相同");						
//					}
//				}else								//查找相似
//				{
//					if(dis<sim){
//						System.out.println(Image1.getPid()+"与"+"当前图片相似");	
//						try {
//							lists.add(DownLoad(Image1));
//						} catch (IOException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//
//					}else
//					{
//						System.out.println(Image1.getPid()+"与"+"当前图片不相似");						
//					}
//				}
//			}
//			return lists;
//		}
//	
//	}		
		
		
		
		

	
	/**
	 * 去掉重复的数据
	 * @param str
	 * @return
	 */
	private Map<String,Integer> Count(String [] ss)
	{
		System.out.println("相似图片ID");
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
	 * 用于保存图片
	 * 前提需要将加密后的数据先进行保存
	 * @param Image
	 * @return
	 */
	public String SaveImage(Imageinfo Image)
	{
		
		String ranDid="0";
		while(imageinfoService.IsImageByid(ranDid).equals("1"))
		{
			 ranDid = RANDid();
			 System.out.println("赋予图片ID："+ranDid);
		}
		Image.setPid(ranDid);
		try {
			if(LocalSaveImageCode(Image)!="0")			//保存本地文件
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
	 * 用于删除图片
	 * @return
	 */
	public void DelImage(String pid) 
	{
		
		imageinfoService.DelImage(pid);
		Bucket.DelImage(pid);
	}
	/**
	 * 将加密后的图片保存到当前目录下的ImageCode文件夹，并将路径返回
	 * @param image
	 * @throws IOException 
	 */
	private String LocalSaveImageCode(Imageinfo image) throws IOException
	{
		//SaveRead saveRead = new SaveRead();
		
		dir.mkdir();			//创建文件夹
		String Path = dir.getCanonicalPath()+"\\"+image.getPid()+".txt";
		byte[] encode = image.getEncode();
		if(encode != null)
		{
			saveRead.byte2type(encode, Path);
			System.out.println("本地保存成功");
			return "1";
		}else
		{
			System.out.println("没有加密文件");
			return "0";
		}
		
	}
	
/**
 * 下载图片，首先根据Pid 查询图片获取图片存储位置，然后GetLocalImageCode(),然后封装返回
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
	 * 下载使用
	 * 获取本地加密图片的byte
	 * pid 为图片pid
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
		java.util.Random random=new java.util.Random();// 定义随机类	
		return String.valueOf(random.nextInt(1000)+1); 	
	}
	
	public byte[] Assure(Comparenumber number,Imageinfo image)
	{

		try {
			String Path = dir.getCanonicalPath()+"\\"+image.getPid()+".txt";
			//byte[] ImageEncode=GetLocalImageCode(Path,0,image.getEnlength());		//将存于服务器的密文提取出来
			byte[] ImageEncode=image.getEncode();
			if(ImageEncode==null||ImageEncode.equals(null))							//服务器文件丢失
			{
				return null;
			}
			SaveRead saveRead = new SaveRead();									
			byte[] bytePart = saveRead.bytePart(number, ImageEncode);					//获取密文对应位子的数据		
//			for(int i=0;i<bytePart.length;i++)
//			{
//				System.out.print(bytePart[i]+" ");
//			}
			System.out.println("服务器获取成功");
			return bytePart;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		
	}


	
}
