package action;

import java.io.File;
import java.io.IOException;





import bean.ImageData;
import bean.Imageinfo;

public interface ClientAction {
	public  Imageinfo GetImageInfo(File file) throws IOException;
	public String GetImageDecode(ImageData enimage,String isSave);
	public Imageinfo PreUpload(File file);
	public File getFile(String str);
}
