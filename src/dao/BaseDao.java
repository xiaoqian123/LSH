package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BaseDao {
	public final String name = "com.mysql.jdbc.Driver";
	public final String url = "jdbc:mysql://localhost:3306/ssh";
	public final String user = "root";
	public final String password = "lu123";
	private Connection connection;
	private PreparedStatement prepareStatement;
	private Connection opeanCon;
	private ResultSet executeQuery;

	public Connection opeanCon() {
		try {
			Class.forName(name);
			connection = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			return connection;
		}
	}

	/**
	 * Object...objects 锟斤拷锟斤拷锟斤拷锟侥诧拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷涂锟斤拷锟叫达拷锟斤拷锟� 锟斤拷删锟斤拷
	 */

	public void updateDB(String sql, Object... objects) {
		opeanCon = this.opeanCon();
		if (opeanCon != null) {
			// 锟斤拷锟斤拷sql锟斤拷锟�
			try {
				prepareStatement = opeanCon.prepareStatement(sql);
				for (int i = 0; i < objects.length; i++) {
					this.prepareStatement.setObject(i + 1, objects[i]);
				}
				this.prepareStatement.executeUpdate();
				System.out.println("updata success!");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.out.println("connection mysql fail!");
		}
	}

	/**
	 * 锟斤拷询
	 * 
	 * @param sql
	 * @param objects
	 * @return
	 */
	public ResultSet quertyDB(String sql, Object... objects) {

		opeanCon = this.opeanCon();
		if (opeanCon != null) {
			try {
				prepareStatement = opeanCon.prepareStatement(sql);
				for (int i = 0; i < objects.length; i++) {
					//System.out.println(objects[i]);
					this.prepareStatement.setObject(i + 1,objects[i] );
				}
				executeQuery = prepareStatement.executeQuery();
				//System.out.println(executeQuery.next());
				System.out.println("select success!");
				
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return executeQuery;

	}

	/*
	 * public ResultSet quertyDB(String sql, Object...objects) { opeanCon=
	 * this.opeanCon(); if(opeanCon!=null) { try { prepareStatement=
	 * opeanCon.prepareStatement(sql); for(int i=0;i<objects.length;i++) {
	 * this.prepareStatement.setObject(i+1, i); } ResultSet executeQuery =
	 * prepareStatement.executeQuery(); return executeQuery; } catch
	 * (SQLException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } } return null; }
	 */

	public void CloseDB() {
		if (opeanCon != null) {
			try {
				opeanCon.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (prepareStatement != null) {
			try {
				prepareStatement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (executeQuery != null) {
			executeQuery = null;
		}
	}
}
