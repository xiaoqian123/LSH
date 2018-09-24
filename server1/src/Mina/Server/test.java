package Mina.Server;

//import application.controller;

public class test {
//	static controller contrview;
	public MinaServer instanser=new  MinaServer();
	 private test() {  
	    }  
		   // 将自身的实例对象设置为一个属性,并加上Static和final修饰符
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
