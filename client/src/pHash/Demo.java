
package pHash;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;





import bean.Bucket;
import bean.Imageinfo;



public class Demo {

	public static void main(String[] args) throws FileNotFoundException, IOException {
			FileInputStream fileInputStream ;
			Imageinfo hsbean = new Imageinfo();
			String hash;		//pHash后的代码
			ImagePHash p = new ImagePHash();
		   LSH lsh= new LSH();
		//   D:/myeclipse/picture/1.jpg
//	       String imagePath = "D:/myeclipse/picture/Pic_Safe/";
////	       for(int i=0;i<1;i++)
////	       {
//	    	   fileInputStream = new FileInputStream(new File(imagePath+"3.jpg"));
//	    	   hash = p.getHash(fileInputStream);
//	    	   System.out.println("phash:"+hash);
//	    	   FileInputStream fileInputStream2 = new FileInputStream(new File(imagePath+"4.jpg"));
//	    	   hash = p.getHash(fileInputStream2);
//	    	   System.out.println("phash:"+hash);
//	    	//   p.imgChk(imagePath+"449.jpeg",imagePath+"810.jpeg",10);

	    	 //  hsbean.setPhase(phase);
	    //	   System.out.print("桶号:"+hsbean.getBcid());
	    //	   System.out.println();
//	       }		       
	       System.out.println();

	       
	   /*   概率
	    * double counta=0,countb=0;;
	    *  for(int i=0;i<49;i++)
	       {
	    		   if(Code[0].charAt(i)=='1' && Code[2].charAt(i)=='1')
	    		   {
	    			   counta++;
	    		   }else
	    		   if(Code[0].charAt(i) != Code[2].charAt(i))
	    		   {
	    			   countb++;
	    		   }
	    	   
	       }
	       System.out.println(counta/(counta +countb));*/
	      


	}
}
