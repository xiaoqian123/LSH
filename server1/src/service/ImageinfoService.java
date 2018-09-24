package service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.BaseDao;
import bean.Imageinfo;

public class ImageinfoService {
	
	public void SaveImage(Imageinfo image)
	{
		System.out.println("±£´æÍ¼Æ¬");
		String sql="insert into Image (pid,Bcid,enlength,imagetype) values(?,?,?,?)";
		BaseDao dao = new BaseDao();
		dao.updateDB(sql,image.getPid(),image.getBcid(),image.getEnlength(),image.getImagetype());
		dao.CloseDB();	
	}

	public void DelImage(String id) {
		// TODO Auto-generated method stub
		String sql="DELETE from Image where pid=?";
		BaseDao dao = new BaseDao();
		dao.updateDB(sql,id);
		dao.CloseDB();
	}
	
	public List<Imageinfo> getImages()
	{
		return null;
		
	}

	/*
	 * ÅÐ¶ÏÍ¼Æ¬IDÊÇ·ñ´æÔÚ
	 */
	public String  IsImageByid(String ranDid)  {
		// TODO Auto-generated method stub
		String sql = "select * from Image where pid=?";
		BaseDao dao = new  BaseDao();
		ResultSet quertyDB = dao.quertyDB(sql, ranDid);
		String resut = null;
		
		try {
			
			if(quertyDB.next())
			{
				resut="1";
			}else
			{
				resut="0";
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dao.CloseDB();
		return resut;

		
		
	}


	public Imageinfo GetImageByid(String pid) throws SQLException {
		// TODO Auto-generated method stub
		Imageinfo  image = null;
		String sql = "select * from Image where pid=?";
		BaseDao dao = new  BaseDao();
		ResultSet quertyDB = dao.quertyDB(sql, pid);
		while(quertyDB.next())
		{		
			image =new  Imageinfo();
			image.setPid(quertyDB.getString(2));
		//	image.setPhase(quertyDB.getString(3));
			image.setEnlength(Integer.parseInt(quertyDB.getString(4)));
			image.setImagetype(quertyDB.getString(5));
		//	image.setImagename(quertyDB.getString(4));		
		}
		dao.CloseDB();
		return image;
	}
	
	/**
	 * ººÃ÷¾àÀëµÄ±È½Ï
	 * @param s1
	 * @param s2
	 * @return
	 */
	 public int distance(String s1, String s2) {
	        int counter = 0;
	        for (int k = 0; k < s1.length();k++) {
	            if(s1.charAt(k) != s2.charAt(k)) {
	                counter++;
	            }
	        }
	        return counter;
	    }

	
}
