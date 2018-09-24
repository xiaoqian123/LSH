package action;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import bean.ImageData;
import bean.Imageinfo;

public interface ServerAction {
	public List<Imageinfo> SelectImage(Imageinfo Image,int sign) throws SQLException;
	public String SaveImage(Imageinfo Image);
	public void DelImage(String id);
	public Imageinfo DownLoad(Imageinfo pid) throws SQLException, IOException;
}
