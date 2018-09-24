package actiom.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;








import pHash.ImagePHash;
import pHash.test;
import pHash.LSH;
import service.AES;
import service.SaveRead;
import service.Similarity;
import action.ClientAction;
import bean.Comparenumber;
import bean.ImageData;
import bean.Imageinfo;



public class ClientActionImpl implements ClientAction{
	ImagePHash p = new ImagePHash();
	LSH lsh= new LSH();
	Imageinfo hsbean = new Imageinfo();				//临时对象
	Similarity similarity = new Similarity();
	/**
	 * GetImageInfo 返回处理后的图片信息
	 * @param str是图片路径加名称
	 * @return
	 * @throws IOException 
	 */
	public  Imageinfo GetImageInfo(File file) throws IOException
	{
		
		FileInputStream imageIS = new FileInputStream(file);
		String type = file.toURL().openConnection().getContentType().split("/")[1];
		hsbean.setImagetype(type);
		String phash = p.getHash(imageIS);
  	   lsh.getminHashCode(phash);  
  	   String bucketcid = lsh.getBucket();
  	//   hsbean.setPhase(phash);	 	   
  	   hsbean.setBcid(bucketcid); 	    
  	 //  System.out.println(hsbean.getPhase());
  	   System.out.println(hsbean.getBcid());
		return hsbean;
		
	}
	
	/**
	 * 上传前对图像的预处理 加密 封装 
	 */
	public Imageinfo PreUpload(File file)
	{
			Imageinfo getImageEncode = GetImageEncode(file);
			return getImageEncode;
		
	}
	/**
	 * 用来保证图片是否存在
	 * @param i 数组，用户随机选的数字然后对加密后的数据提取 匹配
	 * @param file 
	 */
	public byte[] Assure(Comparenumber number,File file)
	{
		
		Imageinfo ImageEncode = GetImageEncode(file);
		SaveRead saveRead = new SaveRead();
		byte[] bytePart = saveRead.bytePart(number, ImageEncode.getEncode());
//		for(int i=0;i<bytePart.length;i++)
//		{
//			System.out.print(bytePart[i]+" ");
//		}
		System.out.println("客户端获取成功");
		return bytePart;
	}
	
	/**
	 * 返回图片加密后的信息
	 * @param imageIS
	 * @return
	 */
	private Imageinfo GetImageEncode(File file)
	{
		AES aes = new AES();
		Imageinfo encode = aes.Encode(file);
		return encode;	
	}
	/**
	 * 对图片进行解密，返回图片
	 * @param ImageCode  isSave(为1保存图片，为0不保存，返回图片字节)
	 * @return
	 */
	public String GetImageDecode(ImageData enimage,String isSave)
	{
		AES aes = new AES();
		String path = aes.Decode(enimage,isSave);
		return path;
	}


	public String sim(byte a[],byte  b[])
	{
		
		double sim = similarity.sim(a,b);
		if((int)sim==1)			
		{
			return "1";
		}else
		{
			return "0";
		}
		
	}
	public File getFile(String str)
	{
		return  new File(str);
		
	}

	

}
