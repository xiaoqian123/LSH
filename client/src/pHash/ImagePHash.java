package pHash;

import java.awt.Graphics2D;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;



import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class ImagePHash {

	 private int size = 32;
	 private int smallerSize = 8;
	 
	  public ImagePHash() {
	        initCoefficients();
	    }
	  
	    private ImagePHash(int size, int smallerSize) {
	        this.size = size;
	        this.smallerSize = smallerSize;
	        initCoefficients();
	    }
	   
//	   //汉明距离进行比较
//	 private int distance(String s1, String s2) {
//	        int counter = 0;
//	        for (int k = 0; k < s1.length();k++) {
//	            if(s1.charAt(k) != s2.charAt(k)) {
//	                counter++;
//	            }
//	        }
//	        return counter;
//	    }
	 
	 //数据的处理
	 public String getHash(FileInputStream fileInputStream) throws IOException 
	 {

		 BufferedImage img = ImageIO.read(fileInputStream);
		 img = resize(img, size, size);			//缩小尺寸
		 img = grayscale(img);					//转灰度图像
		// ImageIO.write(img, "JPEG", new File("D:/myeclipse/picture/456.jpg"));// 输出到文件流
		   double[][] vals = new double[size][size];

	        for (int x = 0; x < img.getWidth(); x++) {
	            for (int y = 0; y < img.getHeight(); y++) {
	                vals[x][y] = getBlue(img, x, y);
	            }
	        }
	        //计算DCT(离散余弦转换)
	        long start = System.currentTimeMillis();
	        double[][] dctVals = applyDCT(vals);
	       System.out.println("length1 "+dctVals.length +"length2 "+ vals.length); 
	        
	        //缩小DCT 计算平均值
	        double total = 0;

	        for (int x = 0; x < smallerSize; x++) {
	            for (int y = 0; y < smallerSize; y++) {
	                total += dctVals[x][y];
	            }
	        }
	        total -= dctVals[0][0];

	        double avg = total / (double) ((smallerSize * smallerSize) - 1);
	        
	        //计算hash值
	        String hash = "";

	        for (int x = 0; x < smallerSize; x++) {
	            for (int y = 0; y < smallerSize; y++) {
	                if (x != 0 && y != 0) {	         
	                    hash += (dctVals[x][y] > avg?"1":"0");	                
	                }
	               // System.out.println();
	            }
	        }
	        return hash;

	    }
		 
	 //缩小图片
	 private BufferedImage resize(BufferedImage image, int width,    int height) {
	        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	        Graphics2D g = resizedImage.createGraphics();
	        g.drawImage(image, 0, 0, width, height, null);
	        g.dispose();	        
	        return resizedImage;
	    }

	 private ColorConvertOp colorConvert = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
	 
	 private BufferedImage grayscale(BufferedImage img) {
	        colorConvert.filter(img, img);
	        return img;
	    }
	 
	 private static int getBlue(BufferedImage img, int x, int y) {
	        return (img.getRGB(x, y)) & 0xff;
	    }
	 
	 private double[] c=null;
	    private void initCoefficients() {
	        c = new double[size];

	        for (int i=1;i<size;i++) {
	            c[i]=1;
	        }
	        c[0]=1/Math.sqrt(2.0);       
	    }
	    
	    private double[][] applyDCT(double[][] f) {
	        int N = size;

	        double[][] F = new double[N][N];
	        for (int u=0;u<N;u++) {
	            for (int v=0;v<N;v++) {
	                double sum = 0.0;
	                for (int i=0;i<N;i++) {
	                    for (int j=0;j<N;j++) {
	                        sum+=Math.cos(((2*i+1)/(2.0*N))*u*Math.PI)*Math.cos(((2*j+1)/(2.0*N))*v*Math.PI)*(f[i][j]);
	                    }
	                }
	                sum*=((c[u]*c[v])/4.0);
	                F[u][v] = sum;
	            }
	        }
	        return F;
	    }
//	    //图片的导入
//	    public boolean imgChk(String img1, String img2, int tv){
//	        ImagePHash p = new ImagePHash();
//	        String image1;
//	        String image2;
//	        try {
//	            image1 = p.getHash(new FileInputStream(new File(img1)));
//	            image2 = p.getHash(new FileInputStream(new File(img2)));
//	            System.out.println("hash:"+image1+"length");
//	            System.out.println("hash:"+image2+"length");
//	            int dt = p.distance(image1, image2);
//	            System.out.println("["+img1 + "] : [" + img2 + "] Score is " + dt);
//	            if (dt <= tv)
//	                return true;
//	        } catch (FileNotFoundException e) {
//	            e.printStackTrace();
//	        } catch (Exception e) {
//	            e.printStackTrace();
//	        }
//	        return false;
//	    }
	    
//	    public String getHashCode(String img1) throws FileNotFoundException, IOException
//	    
//	    {
//	    	ImagePHash p = new ImagePHash();
//	    //	String Code=p.getHash();
//			return Code;
//	    	
//	    }
}
