package Mina.Server;

//import application.controller;

public class test {
//	static controller contrview;
	public MinaServer instanser=new  MinaServer();
	 private test() {  
	    }  
		   // �������ʵ����������Ϊһ������,������Static��final���η�
	    private static final test instance = new test();

	    public static test getInstancei() {  
	        return instance;  
	    }  
	    
//		public static void getcontrview(controller test1)
//		{
//			contrview=test1;
//			System.out.println(test1);
//		}
//		  
		
}
