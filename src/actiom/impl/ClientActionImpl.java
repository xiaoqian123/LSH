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
	Imageinfo hsbean = new Imageinfo();				//��ʱ����
	Similarity similarity = new Similarity();
	/**
	 * GetImageInfo ���ش�����ͼƬ��Ϣ
	 * @param str��ͼƬ·��������
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
	 * �ϴ�ǰ��ͼ���Ԥ���� ���� ��װ 
	 */
	public Imageinfo PreUpload(File file)
	{
			Imageinfo getImageEncode = GetImageEncode(file);
			return getImageEncode;
		
	}
	/**
	 * ������֤ͼƬ�Ƿ����
	 * @param i ���飬�û����ѡ������Ȼ��Լ��ܺ��������ȡ ƥ��
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
		System.out.println("�ͻ��˻�ȡ�ɹ�");
		return bytePart;
	}
	
	/**
	 * ����ͼƬ���ܺ����Ϣ
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
	 * ��ͼƬ���н��ܣ�����ͼƬ
	 * @param ImageCode  isSave(Ϊ1����ͼƬ��Ϊ0�����棬����ͼƬ�ֽ�)
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
