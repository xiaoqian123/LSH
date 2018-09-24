package pHash;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;



public class test {

	public  void haha() {
		//String hash = getFeatureValue("3.jpg");
		String hash1 = getPhash(new File("4.jpg"));
	//	System.out.println(hash);
		System.out.println(hash1+" "+hash1.length());
	//	System.out.println(calculateSimilarity(hash, hash1));
	}

		public  String getPhash(File File) {
		// ��С�ߴ磬��ɫ��
		int[][] grayMatrix = getGrayPixel(File, 32, 32);
		// ����DCT
		grayMatrix = DCT(grayMatrix, 32);
		// ��СDCT������ƽ��ֵ
		int[][] newMatrix = new int[8][8];
		double average = 0;
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				newMatrix[i][j] = grayMatrix[i][j];
				average += grayMatrix[i][j];
			}
		}
		average /= 64.0;
		// ����hashֵ
		String hash = "";
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				if(newMatrix[i][j] < average){
					hash += '0';
				}
				else{
					hash += '1';
				}
			}
		}
		return hash;
	}

    public  int[][] getGrayPixel(File File , int width, int height) {
		BufferedImage bi = null;
		try {
			bi = resizeImage(File, width, height, BufferedImage.TYPE_INT_RGB);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		int minx = bi.getMinX();
		int miny = bi.getMinY();
		int[][] matrix = new int[width - minx][height - miny];
		for (int i = minx; i < width; i++) {
			for (int j = miny; j < height; j++) {
				int pixel = bi.getRGB(i, j);
				int red = (pixel & 0xff0000) >> 16;
				int green = (pixel & 0xff00) >> 8;
				int blue = (pixel & 0xff);
				int gray = (int) (red * 0.3 + green * 0.59 + blue * 0.11);
				matrix[i][j] = gray;
			}
		}
		return matrix;
	}

    public  BufferedImage resizeImage(File srcFile, int width, int height, int imageType)
			throws IOException {
		BufferedImage srcImg = ImageIO.read(new FileInputStream(srcFile));
		BufferedImage buffImg = null;
		buffImg = new BufferedImage(width, height, imageType);
		buffImg.getGraphics().drawImage(srcImg.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
		return buffImg;
	}
	
	/**
	 * ���ڼ���pHash�����ƶ�<br>
	 * ���ƶ�Ϊ1ʱ��ͼƬ������
	 * @param str1
	 * @param str2
	 * @return
	 */
	public  double calculateSimilarity(String str1, String str2) {
		int num = 0;
		for(int i = 0; i < 64; i++){
			if(str1.charAt(i) == str2.charAt(i)){
				num++;
			}
		}
		return ((double)num) / 64.0;
	}

	/**
	 * ��ɢ���ұ任
	 * @author luoweifu 
	 * 
	 * @param pix
	 *            ԭͼ������ݾ���
	 * @param n
	 *            ԭͼ��(n*n)�ĸ߻��
	 * @return �任��ľ�������
	 */
	public  int[][] DCT(int[][] pix, int n) {
		double[][] iMatrix = new double[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				iMatrix[i][j] = (double) (pix[i][j]);
			}
		}
		double[][] quotient = coefficient(n); // ��ϵ������
		double[][] quotientT = transposingMatrix(quotient, n); // ת��ϵ������

		double[][] temp = new double[n][n];
		temp = matrixMultiply(quotient, iMatrix, n);
		iMatrix = matrixMultiply(temp, quotientT, n);
		
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				pix[i][j] = (int) (iMatrix[i][j]);
			}
		}
		return pix;
	}

	/**
	 * ����ɢ���ұ任��ϵ������
	 * @author luoweifu 
	 * 
	 * @param n
	 *            n*n����Ĵ�С
	 * @return ϵ������
	 */
	private  double[][] coefficient(int n) {
		double[][] coeff = new double[n][n];
		double sqrt = 1.0 / Math.sqrt(n);
		for (int i = 0; i < n; i++) {
			coeff[0][i] = sqrt;
		}
		for (int i = 1; i < n; i++) {
			for (int j = 0; j < n; j++) {
				coeff[i][j] = Math.sqrt(2.0 / n) * Math.cos(i * Math.PI * (j + 0.5) / (double) n);
			}
		}
		return coeff;
	}

	/**
	 * ����ת��
	 * @author luoweifu 
	 * 
	 * @param matrix
	 *            ԭ����
	 * @param n
	 *            ����(n*n)�ĸ߻��
	 * @return ת�ú�ľ���
	 */
	private  double[][] transposingMatrix(double[][] matrix, int n) {
		double nMatrix[][] = new double[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				nMatrix[i][j] = matrix[j][i];
			}
		}
		return nMatrix;
	}

	/**
	 * �������
	 * @author luoweifu 
	 * 
	 * @param A
	 *            ����A
	 * @param B
	 *            ����B
	 * @param n
	 *            ����Ĵ�Сn*n
	 * @return �������
	 */
	private  double[][] matrixMultiply(double[][] A, double[][] B, int n) {
		double nMatrix[][] = new double[n][n];
		int t = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				t = 0;
				for (int k = 0; k < n; k++) {
					t += A[i][k] * B[k][j];
				}
				nMatrix[i][j] = t;
			}
		}
		return nMatrix;
	}
}
