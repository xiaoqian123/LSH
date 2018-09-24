package service;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import bean.Comparenumber;
import bean.Imageinfo;

public class SaveRead {
	  public static void byte2type(byte[] data,String path)
	  {
		 if(data.length<3||path.equals("")) return;//判断输入的byte是否为空
		 try {
			DataOutputStream d1= new DataOutputStream(new FileOutputStream(path));
			d1.write(data, 0, data.length);
			d1.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				  
	  }
	  
	  public static  byte[] type2byte(String path,int start,int length) 
	  {
		  byte[] b = new byte[length];
		  DataInputStream d1;
		try {
			d1 = new DataInputStream(new FileInputStream(path));
			System.out.println("有文件");
			d1.read(b, start, length);
			 return b;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("没有文件");
			return null;
		}
		
		
	  }
	  public static byte[]	bytePart(Comparenumber number,byte[] imageEncode)	
	  {
		  int size[]={number.getStart1(),number.getStart2(),number.getStart3()};
		  int length[]={number.getLenth1(),number.getLenth2(),number.getLenth3()};		  
		  int sumlength=number.getLenth1()+number.getLenth2()+number.getLenth3();
		  int lengthsize[]={0,number.getLenth1(),number.getLenth1()+number.getLenth2(),sumlength};
		  byte [] test = new byte[sumlength];
		  for(int i=0;i<size.length;i++)
		  {
			  System.out.println("开始位子:"+size[i]+"长度"+length[i]);
			  System.arraycopy(imageEncode, size[i], test, lengthsize[i], length[i]);
		  }
		  for(int j=0;j<test.length;j++)
		  {
			  System.out.print(test[j]);
		  }
		  System.out.println();
		  return test;
		  
	  }
	  
}
