package service;


  
/** 
 * 字符串相似性匹配算法 
 * Created by panther on 15-7-20. 
 */  
public class similarity {  

  
    // 求余弦相似度  
    public  double sim(byte a[],byte  b[]) {
    	double pointMulti = pointMulti(a,b);
    	double sqrtMulti = sqrtMulti(a)*sqrtMulti(b);
		return pointMulti/sqrtMulti;  
    }  
  
    private  double sqrtMulti(byte num[]) {
    	double sum=0;
    	for(int i=0;i<num.length;i++)
    	{
    		sum=sum+num[i]*num[i];
    	}
    	sum=Math.sqrt(sum);
    	return sum;  
    }
		
 
     
  

  
    // 点乘法  
    private  double pointMulti(byte a[],byte b[]) 
    {
    	double sum=0;
    	for(int i=0;i<a.length;i++)
    	{
    		sum=sum+a[i]*b[i];
    	}
		return sum;  
    
    		
    }
  

  
}  