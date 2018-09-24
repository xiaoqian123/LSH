package service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.BaseDao;
import bean.Bucket;
import bean.Imageinfo;

public class BucketService {
	
	public void SaveImage(Imageinfo image) {
		// TODO Auto-generated method stub
		
		String sql="insert into Bucket (pid,bid) values(?,?)";
		BaseDao dao = new BaseDao();

		String id=image.getPid();
		
		String[] split = image.getBcid().split(",");	//��ȡ��ͼƬ��Ͱ��
		for(int i=0;i<split.length;i++)
		{
			dao.updateDB(sql, id,split[i]);
			dao.CloseDB();
		}
			
	}

	/**
	 * ����ͼƬ�����Ͱ�Ų�ѯ��Ͱ���е�ͼƬid�����з���
	 * 
	 * @param image
	 * @throws SQLException 
	 */
	public String getImageByBid(Imageinfo image) throws SQLException {
		// TODO Auto-generated method stub
		String pid="";
		String[] split = image.getBcid().split(",");	//��ȡ��ͼƬ��Ͱ��
		
		String sql = "select * from Bucket where bid=?";
		BaseDao dao = new  BaseDao();
		
		for(int i=0;i<split.length;i++)
		{
			ResultSet quertyDB = dao.quertyDB(sql, split[i]);
			while(quertyDB.next())
			{
				pid=quertyDB.getString(2)+","+pid;
			//	System.out.println(quertyDB.getString(2));
			}
			dao.CloseDB();
		}
		
		return pid;
	}
	

	public void DelImage(String id) {
		String sql="DELETE from Bucket where pid=?";
		BaseDao dao = new BaseDao();
		dao.updateDB(sql,id);
		dao.CloseDB();
	}
	 

}
