package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBHelper {
	public static final String url = "jdbc:mysql://47.106.204.247:3306/LSH";//jdbc:mysql://111.230.45.164:3306/LSH
	public static final String user= "root";
	public static final String password = "root1234";//chenhan123
	public static final String name="com.mysql.jdbc.Driver";
	private Connection connection;
	public PreparedStatement prepareStatement;
	
	public DBHelper(String sql) {	
		try {

			Class.forName(name);
			System.out.println("success load!");
			connection = DriverManager.getConnection(url, user, password);
			System.out.println("connection success");

			prepareStatement = connection.prepareStatement(sql);
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		} catch (SQLException e) {

			e.printStackTrace();
			System.out.println("锟斤拷锟斤拷失锟斤拷");
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
				e.printStackTrace();
			}
		}
	}
}
