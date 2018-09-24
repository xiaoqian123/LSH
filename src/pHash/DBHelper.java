package pHash;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBHelper {
	public static final String url="jdbc:MySQL://localhost:3306/ssh";
	public static final String user= "root";
	public static final String password = "lu123";
	public static final String name="com.mysql.jdbc.Driver";
	private Connection connection;
	public PreparedStatement prepareStatement;
	public DBHelper(String sql) {
		
		try {
			//ʹ�ù����࣬����ͨ��class.forname����
			Class.forName(name);
			System.out.println("success load!");
			connection = DriverManager.getConnection(url, user, password);
			System.out.println("connection success");
			//׼����һ����ִ�е����
			prepareStatement = connection.prepareStatement(sql);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("����ʧ��");
		}
		
		
	}
	
	public void close()
	{
		if(this.connection!=null)
		{
			try {
				this.connection.close();
				this.prepareStatement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
