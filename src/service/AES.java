package service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;






import bean.ImageData;
import bean.Imageinfo;

public class AES {
	   private static final String KEY_ALGORITHM = "AES";
	   private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";//Ĭ�ϵļ����㷨
	   private  Imageinfo enImage = new Imageinfo();				//ֻ�洢�˼�����Ϣ�ͼ��ܳ���	��ʱ����
	   private  byte[] initSecretKey() {

	       //��������ָ���㷨��Կ�������� KeyGenerator ����
	       KeyGenerator kg = null;
	       try {
	           kg = KeyGenerator.getInstance(KEY_ALGORITHM);
	       } catch (NoSuchAlgorithmException e) {
	           e.printStackTrace();
	           return new byte[0];
	       }
	       //��ʼ������Կ��������ʹ�����ȷ������Կ��С
	       //AES Ҫ����Կ����Ϊ 128
	       kg.init(128);
	       //����һ����Կ
	       SecretKey  secretKey = kg.generateKey();
	       return secretKey.getEncoded();
	   }

	   private  Key toKey(byte[] key){
	       //������Կ
	       return new SecretKeySpec(key, KEY_ALGORITHM);
	   }

	   private  byte[] encrypt(byte[] data,Key key) throws Exception{
	       return encrypt(data, key,DEFAULT_CIPHER_ALGORITHM);
	   }

	   private  byte[] encrypt(byte[] data,byte[] key) throws Exception{
	       return encrypt(data, key,DEFAULT_CIPHER_ALGORITHM);
	   }

	   private  byte[] encrypt(byte[] data,byte[] key,String cipherAlgorithm) throws Exception{
	       //��ԭ��Կ
	       Key k = toKey(key);
	       return encrypt(data, k, cipherAlgorithm);
	   }

	   private  byte[] encrypt(byte[] data,Key key,String cipherAlgorithm) throws Exception{
	       //ʵ����
	       Cipher cipher = Cipher.getInstance(cipherAlgorithm);
	       //ʹ����Կ��ʼ��������Ϊ����ģʽ
	       cipher.init(Cipher.ENCRYPT_MODE, key);
	       //ִ�в���
	       return cipher.doFinal(data);
	   }

	   private  byte[] decrypt(byte[] data,byte[] key) throws Exception{
	       return decrypt(data, key,DEFAULT_CIPHER_ALGORITHM);
	   }

	   private  byte[] decrypt(byte[] data,Key key) throws Exception{
	       return decrypt(data, key,DEFAULT_CIPHER_ALGORITHM);
	   }

	   private  byte[] decrypt(byte[] data,byte[] key,String cipherAlgorithm) throws Exception{
	       //��ԭ��Կ
	       Key k = toKey(key);
	       return decrypt(data, k, cipherAlgorithm);
	   }

	   private  byte[] decrypt(byte[] data,Key key,String cipherAlgorithm) throws Exception{
	       //ʵ����
	       Cipher cipher = Cipher.getInstance(cipherAlgorithm);
	       //ʹ����Կ��ʼ��������Ϊ����ģʽ
	       cipher.init(Cipher.DECRYPT_MODE, key);
	       //ִ�в���
	       return cipher.doFinal(data);
	   }

	   private  String  showByteArray(byte[] data){
	       if(null == data){
	           return null;
	       }
	       StringBuilder sb = new StringBuilder("{");
	       for(byte b:data){
	           sb.append(b).append(",");
	       }
	       sb.deleteCharAt(sb.length()-1);
	       sb.append("}");
	       return sb.toString();
	   }
	   private  void byte2image(byte[] data,String path){
			    if(data.length<3||path.equals("")) return;//�ж������byte�Ƿ�Ϊ��
			    try{
			    FileImageOutputStream imageOutput = new FileImageOutputStream(new File(path));//��������
			    imageOutput.write(data, 0, data.length);//��byteд��Ӳ��
			    imageOutput.close();
			    System.out.println("Make Picture success,Please find image in " + path);
			    } catch(Exception ex) {
			      System.out.println("Exception: " + ex);
			      ex.printStackTrace();
			    }
			  }
		  
		  private  byte[] image2byte(File fileIn) throws IOException	  
		  {
			  BufferedImage image = ImageIO.read(fileIn);  //��ȡ1.gif������  
			  ByteArrayOutputStream out = new ByteArrayOutputStream();  
			  boolean flag = ImageIO.write(image, "png", out);  
			  byte[] byteArray = out.toByteArray();
			  return byteArray;
		  }
		  private Key getKey()
		  {
			  byte[] key ={11,57,91,-22,-74,-118,29,-18,106,23,111,31,103,-127,-4,39};
			   //������Կ
			return toKey(key);
		  }
//		  private  void byte2type(byte[] data,String path)
//		  {
//			 if(data.length<3||path.equals("")) return;//�ж������byte�Ƿ�Ϊ��
//			 try {
//				DataOutputStream d1= new DataOutputStream(new FileOutputStream(path));
//				d1.write(data, 0, data.length);
//				d1.close();
//			} catch (FileNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//					  
//		  }
//		  private   byte[] type2byte(String path,int length) throws IOException
//		  {
//			  byte[] b = new byte[length];
//			  DataInputStream d1= new DataInputStream(new FileInputStream(path));
//			  d1.read(b, 0, length);
//			  return b;
//		  }
		  
		  public Imageinfo Encode(File file)
		  {
			  Key key = getKey();
			  try {
				byte[] image2byte = image2byte(file);	//��ȡͼƬ
				byte[] encryptData = encrypt(image2byte, key);	//���ݼ��� 
			//	int enlength = encryptData.length;				//���ܺ�ĳ���
				enImage.setEncode(encryptData);
				return enImage;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}	//��ȡͼƬ 
		  }
		  public String Decode(ImageData image,String isSave)
		  {
			  byte[] encode = image.getBimage();
			  if(encode==null)				//��������û�д�ͼƬ
			  {
				  return null;
			  }
			  Key key = getKey();
			  try {			
				  byte[] decryptData = decrypt(encode, key);       	//���ݽ��� 
//				  if(isSave!="1")		//������
//				  {
//					  return decryptData;
//				  }else						//����
//				  {
					  File dir = new File("ImageLoad");
					  dir.mkdir();			//�����ļ���
					  String path=dir.getCanonicalPath()+"\\"+image.getPid()+"."+image.getType();
					  byte2image(decryptData,path);
					  return path;
	//			  }
				
			} catch ( Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			} //��ȡ
		  }
}
