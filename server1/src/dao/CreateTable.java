package dao;

import java.sql.SQLException;

public class CreateTable {
	public static void main(String[] args) {
		creatTableImage();
		creatTableBucket();
	}

	private static void creatTableImage() {
		// TODO Auto-generated method stub
		String sql  = "create table Image(id int not null auto_increment,pid varchar(10),Bcid varchar(50),enlength int,imagetype varchar(10), primary key(id)) default charset=utf8;";
		DBHelper db = new DBHelper(sql);
		try {
			//执锟斤拷sql锟斤拷锟�
			db.prepareStatement.executeUpdate();
			System.out.println("create success");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			db.close();
		}
		
	}
	private static void creatTableBucket() {
		// TODO Auto-generated method stub
		
		String sql  = "create table Bucket(id int not null auto_increment,pid  varchar(50),bid varchar(200), primary key(id)) default charset=utf8;";
		DBHelper db = new DBHelper(sql);
		try {
			//执锟斤拷sql锟斤拷锟�
			db.prepareStatement.executeUpdate();
			System.out.println("create success");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			db.close();
		}
		
	}
}