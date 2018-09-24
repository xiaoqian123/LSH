package action;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import bean.Imageinfo;

public interface ServerAction {
	public List<Imageinfo> SelectImage(Imageinfo Image,String sign) throws SQLException;
	public void SaveImage(Imageinfo Image);
	public void DelImage(String id);
	public Imageinfo DownLoad(String pid) throws SQLException, IOException;
}
