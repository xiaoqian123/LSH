package application;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import Mina.Client.test;
import actiom.impl.ClientActionImpl;
import bean.Comparenumber;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/*
 * ��֤ѡ���䣺check
 * 
 */

public class controller implements Initializable {
	@FXML
	private Button closeSer;
	@FXML
	private Button queryButton;
	@FXML
	private Button updataPic;
	@FXML
	private Button conSerButton;
	private File[] imageFile;
	@FXML
	private ImageView picture;
	@FXML
	private ScrollPane ScrollPane;
	@FXML
	private TabPane	 tabPane;
	@FXML
	private Label sss;
//	@FXML 
//	private AnchorPane previewPic;
	@FXML
	private AnchorPane picPanel;
	@FXML	
	private Tab check;
	@FXML	
	private TextField t1;
	@FXML	
	private TextField t2;
	@FXML	
	private TextField t3;
	@FXML	
	private TextField tt1;
	@FXML	
	private TextField tt2;
	@FXML	
	private TextField tt3;
	@FXML
	private Tab brow;
	boolean isup=true;
	@FXML
	private ListView connectmes;
	@FXML
	private  ProgressIndicator progress;
	@FXML
	private ListView appSta;
	@FXML	
	private TextField ip;
	@FXML	
	private TextField port;
	@FXML
	private AnchorPane con;
	@FXML
	private Button connectButton;
	@FXML
	private Button selectButton;
	@FXML	
	private TextField sim;
	int picwite=1;
	List<String> st=new ArrayList<String>();
	test instancei = test.getInstancei();
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		instancei.getcontrview(this);
		brow.setDisable(true);
		closeSer.setDisable(true);
		updataPic.setDisable(true);
		setconnectText("��ʾ�������������ip�Ͷ˿ں����ӷ�����\n");
	}
	int m = 1,n=2;
	int id=0;
	long starttime ;

	
	/*
	 * ���ͼƬ�����أ�
	 * 
	 */
	ObservableList<String> conn = FXCollections.observableArrayList();
	ObservableList<String> strList = FXCollections.observableArrayList();

	public void selectpicture() throws FileNotFoundException{
		//imageFile=null;
		starttime = System.currentTimeMillis();  
		getPicPanel().getChildren().clear();
		picwite=1;m = 1;n=2;id=0;
		JFileChooser fileChooser=new JFileChooser();   //�����ļ�ѡ����
		fileChooser.setMultiSelectionEnabled(true);
		javax.swing.filechooser.FileFilter filter=new FileNameExtensionFilter("ͼ���ļ���JPG/GIF/BMP��", 
				"JPG","JPEG","GIF","BMP");		//����������
		fileChooser .setFileFilter(filter);		//���ù�����
		int flag=fileChooser.showOpenDialog(null);  //��ʾ�򿪶Ի���
		if(flag==JFileChooser.APPROVE_OPTION)
		{		
			imageFile=fileChooser.getSelectedFiles();
			System.out.println(imageFile.length);
//			for(int i=0;i<imageFile.length;i++)
//			{
				if(imageFile.equals(null)||imageFile==null)
				{
					System.out.println("û��ѡ��ͼƬ");
				}else					
				{
					queryButton.setDisable(false);//ͼƬѡ��ɹ�֮�󣬽���ѯ��ť��Ϊ����
					updataPic.setDisable(false);//ͼƬѡ��ɹ�֮�󣬽��ϴ���ť��Ϊ����
					displayPicture(imageFile[id]);
					
					
				}
//				
//			}		
		}

		
	}
	public void next()
	{
		id++;
		if(id<imageFile.length)
		{
			try {
				displayPicture(imageFile[id]);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else
		{
			long currentTimeMillis = System.currentTimeMillis(); 
			System.out.println(currentTimeMillis-starttime);
		}
		 
			
	}
	
	public void setconnectText(String str){
		
		conn.add(str);
		connectmes.setItems(conn);	
	}
	public void setStaText(String str){		
		strList.add(str);
		appSta.setItems(strList);	
	}
	
	public void connectServer()
	{
		if(check_ip_port()==1 && isIP()){				//���ip�Ͷ˿ں�
			String Ip = ip.getText();
			Integer Port = Integer.valueOf(port.getText());
			
			if(instancei.Start(Ip, Port))
			{
				setconnectText("�����������ɹ�,�ȴ��ͻ�������...\n");//!!!!!!!!!!!!!!!!!!!!
				brow.setDisable(false);//�������ѡ�����
				conSerButton.setDisable(true);	//�������ӷ�����������
				closeSer.setDisable(false);//�رշ�������ť����
				tabPane.getSelectionModel().select(brow);// ѡ���ת
				
	
			}else
			{
				 setconnectText("����ʧ�ܣ�����IP��port�Ƿ���ȷ��");
			}
		}	
		
		
		
	}
	/*
	 * �ж�ip
	 */
	 public boolean isIP()  
     {  
		 String addr =ip.getText();
         if(addr.length() < 7 || addr.length() > 15 || "".equals(addr))  
         {  
        	 setconnectText("������ip��ʽ����");
             return false;  
         }  
         /** 
          * �ж�IP��ʽ�ͷ�Χ 
          */  
         String rexp = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";  

         Pattern pat = Pattern.compile(rexp);    

         Matcher mat = pat.matcher(addr);    

         boolean ipAddress = mat.find();  
         System.out.println(ipAddress);
         return ipAddress;  
     }  
	//���ip�Ͷ˿ں�
	public int check_ip_port(){
		/*
		 * ����ֵΪ0��ʾ����Ϊ��
		 * -1 ��ʾ�˿ںŲ�Ϊ����
		 * 1����
		 * ע�ͣ�����ipֻ�Ǽ�����Ƿ�Ϊ��
		 */
		
		if(ip.getText().equals("") ||port.getText().equals("")){
			setconnectText("��ʾ�����벻��Ϊ��\n");
			return 0;
		}else if(!isNumeric(port.getText())){
			setconnectText("��ʾ������Ķ˿ں�����ֻ��������\n");
				return -1;
				
		}
		return 1;
	}
	 private InputStream resize(File image, int width,    int height) {
		 	BufferedImage img = null;
			try {
				if(image.equals(null)|| image==null)
				{
					System.out.println("�ļ�Ϊ��");
				}else
				{
					img = ImageIO.read(new FileInputStream(image));
				}			
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	        Graphics2D g = resizedImage.createGraphics();
	        g.drawImage(img, 0, 0, width, height, null);
	        g.dispose();
	        
	        ByteArrayOutputStream os = new ByteArrayOutputStream();  
	        try {
				ImageIO.write(resizedImage, "gif", os);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
	        InputStream is = new ByteArrayInputStream(os.toByteArray()); 
	        return is;
	    }
	public void closeClient()
	{
		instancei.Close();
		conSerButton.setDisable(false);
	}
	//Imageinfo getImageInfo =null;
	//static ClientActionImpl clientActionImpl = new ClientActionImpl();
	
	
	
	public void test(){
		//tabPane.getSelectionModel().select(brow);// ѡ���ת
		check();
	}

	/*
	 * �������
	 * ����ֵΪ1��ʾ����
	 * ����ֵΪ0��ʾ����
	 */
	public int check(){
		if(ip.getText().equals("")||port.getText().equals("")){
			JOptionPane.showMessageDialog(null, "��ʾ�����벻��Ϊ��");
			return 0;
		}
		else{
			if(!isNumeric(port.getText())){
				JOptionPane.showMessageDialog(null, "��ʾ���˿ں�������������������");
				return 0;
			}
			else{
				return 1;
			}
		}
	}
	
	/*
	 * �ϴ��¼�
	 */
	public void sendPicture(){
		instancei.Upload(imageFile);
	}
	/*
	 * ��ѯͼƬ
	 */
	public void queryPicture() {
		if(check_sim() ==1){
				if(imageFile.length==1)
				{
//					Integer valueOf = Integer.valueOf(sim.getText());
					if(isNumeric(sim.getText()))
					{
						try {
							st=new ArrayList<String>();			//���
							instancei.printsimnum=true;
							instancei.SelectSim(imageFile[0], Integer.valueOf(sim.getText()));
							
						} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}		
					}else
					{
						setStaText("���������ƾ���:1~50");
					}
				}else
				{
					
					setStaText("��ѡ��һ��ͼƬ");
				}
				
		}
		
		
		
	}
	public int check_sim(){
		if(!sim.getText().equals("")){
			if(isNumeric(sim.getText())){
				if(Integer.parseInt(sim.getText()) <=0 ||Integer.parseInt(sim.getText())>100){
					setStaText("���ƶ���������ֻ������1��100��");
				}			
			}else{
				setStaText("���ƶ�����������������֣�");
				return 0;
			}
			
		}else{
			setStaText("���ƶȲ���Ϊ�գ����������ƶȣ�");
			return 0;
		}
		return 1;
			
	}
	
	/*
	 * ��ʾͼƬ
	 * displayPicture
	 */
	public void displayPicture(File path) throws FileNotFoundException{
		
		ImageView pic = new ImageView();
		if(picwite%2==1)
		{
			pic.setLayoutX(30);
			pic.setLayoutY(25+(picwite-m)*160);
			picwite++;
			m++;
		}else
		{
			pic.setLayoutX(60+180);
			pic.setLayoutY(25+(picwite-n)*160);
			picwite++;
			n++;
		}
		pic.setFitHeight(153);
		pic.setFitWidth(176);
	
		//pic.setImage(new Image(resize(path, 176, 153)));
		pic.setImage(new Image(new FileInputStream(path)));
		getPicPanel().getChildren().add(pic);		
		next();			
	}
	/*
	 * ��֤ȷ����ť�¼�
	 * 
	 */
	Comparenumber comparenumber = new Comparenumber();
	public void check_button(){
		
		checkAllTextField();//У������
		
		comparenumber.setStart1(Integer.valueOf(t1.getText()));
		comparenumber.setStart2(Integer.valueOf(t2.getText()));
		comparenumber.setStart3(Integer.valueOf(t3.getText()));
		comparenumber.setLenth1(Integer.valueOf(tt1.getText()));
		comparenumber.setLenth2(Integer.valueOf(tt2.getText()));
		comparenumber.setLenth3(Integer.valueOf(tt3.getText()));
		check.setDisable(true);
		tabPane.getSelectionModel().select(brow);// ѡ���ת
		instancei.getCompare(comparenumber);
		check.setDisable(true);
	}
	/*
	 * ��������������ݽ���У��
	 */
	public void checkAllTextField(){
		
		//11
		if(isCheckText() ==1){
			if(checkTextField(t1.getText())==1){			
				JOptionPane.showMessageDialog(null, "��ʾ�� ���ڵ�һ�е�"+1+"����������");
				return ;
			}
			else if(checkTextField(t1.getText())==2){
				JOptionPane.showMessageDialog(null, "��ʾ�� ���ڵ�һ�е�"+1+"������(0-4000)������");
				return ;
			}
			//12
				if(checkTextField2(tt1.getText())==1){
				JOptionPane.showMessageDialog(null, "��ʾ�� ���ڵڶ��е�"+1+"����������");
				return ;
			}
			else if(checkTextField2(tt1.getText())==2){
				JOptionPane.showMessageDialog(null, "��ʾ�� ���ڵڶ��е�"+1+"������(0-50)������");
				return ;
			}
			//21
				if(checkTextField(t2.getText())==1){			
					JOptionPane.showMessageDialog(null, "��ʾ�� ���ڵ�һ�е�"+2+"����������");
					return ;
				}
				else if(checkTextField(t2.getText())==2){
					JOptionPane.showMessageDialog(null, "��ʾ�� ���ڵ�һ�е�"+2+"������(0-4000)������");
					return ;
				}
			//22
				if(checkTextField2(tt2.getText())==1){
					JOptionPane.showMessageDialog(null, "��ʾ�� ���ڵڶ��е�"+2+"����������");
					return ;
				}
				else if(checkTextField2(tt2.getText())==2){
					JOptionPane.showMessageDialog(null, "��ʾ�� ���ڵڶ��е�"+2+"������(0-50)������");
					return ;
				}
			//31
				if(checkTextField(t3.getText())==1){			
					JOptionPane.showMessageDialog(null, "��ʾ�� ���ڵ�һ�е�"+3+"����������");
					return ;
				}
				else if(checkTextField(t3.getText())==2){
					JOptionPane.showMessageDialog(null, "��ʾ�� ���ڵ�һ�е�"+3+"������(0-4000)������");
					return ;
				}
			//32
				if(checkTextField2(tt3.getText())==1){
					JOptionPane.showMessageDialog(null, "��ʾ�� ���ڵڶ��е�"+3+"����������");
					return ;
				}
				else if(checkTextField2(tt3.getText())==2){
					JOptionPane.showMessageDialog(null, "��ʾ�� ���ڵڶ��е�"+3+"������(0-50)������");
					return ;
				}
		}
	}
	 public int isCheckText(){
		 if(t1.getText().equals("")|| t2.getText().equals("")|| t3.getText().equals("")|| 
				 tt1.getText().equals("")|| tt2.getText().equals("")|| tt3.getText().equals("")){
			JOptionPane.showMessageDialog(null, "��ʾ���벻��Ϊ�գ�");
			return 0;
		 }
		return 1;
	 }
	/*
	 * �ı���������֤
	 * ��һ��
	 */
	public int checkTextField(String str){
		/*
		 * ����0��������ȷ
		 * ����1������Ĳ���ȫΪ����
		 * ����2����������ֲ��ٷ�Χ��
		 */
		if(!str.equals("")){
			if(!isNumeric(str))	{
				return 1;
			}else if(isNumeric(str)){
				int a=Integer.parseInt(str);
				while(a<0 || a>4000){
					return 2;
				}
			}
		}else{
			//JOptionPane.showMessageDialog(null, "��ʾ���벻��Ϊ�գ�");
			
		}
		return 0;
	}
	/*
	 * �ڶ�����֤
	 */
	public int checkTextField2(String str){
		/*
		 * ����0��������ȷ
		 * ����1������Ĳ���ȫΪ����
		 * ����2����������ֲ��ٷ�Χ��
		 */
		if(!str.equals("")){
			if(!isNumeric(str))	{
				return 1;
			}else if(isNumeric(str)){
				int a=Integer.parseInt(str);
				while(a<0 || a>50){				
					return 2;
				}
			}
		}else{
			JOptionPane.showMessageDialog(null, "��ʾ���벻��Ϊ�գ�");
			return 0;
		}
		return 0;
	}
	/*
	 * �ж��Ƿ�Ϊ����
	 */
	public boolean isNumeric(String str){ 
		   Pattern pattern = Pattern.compile("[0-9]*"); 
		   Matcher isNum = pattern.matcher(str);
		   if( !isNum.matches() ){
		       return false; 
		   } 
		   return true; 
		}
	
	public File [] simfile;
	
	
	public AnchorPane getPicPanel() {
		return picPanel;
	}
	public void setPicPanel(AnchorPane picPanel) {
		this.picPanel = picPanel;
	}

	
	/**
	 *����ͼƬ�������ʾ
	 */
	
	

	public void setList(String path) {
		// TODO Auto-generated method stub
		
		System.out.println(path);
		st.add(path);
	}
	
	public void dispy() {
		// TODO Auto-generated method stub
		getPicPanel().getChildren().clear();
		picwite=1;m = 1;n=2;id=0;
		  for(String tmp:st){
			  displaydown(new File(tmp));
	        }	 
	}
	
	public void  displaydown(File path){
		ImageView pic = new ImageView();
		if(picwite%2==1)
		{
			pic.setLayoutX(30);
			pic.setLayoutY(25+(picwite-m)*160);
			picwite++;
			m++;
		}else
		{
			pic.setLayoutX(60+180);
			pic.setLayoutY(25+(picwite-n)*160);
			picwite++;
			n++;
		}
		pic.setFitHeight(153);
		pic.setFitWidth(176);

		//pic.setImage(new Image(resize(path, 176, 153)));
		try {
			pic.setImage(new Image(new FileInputStream(path)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getPicPanel().getChildren().add(pic);	
	}
	public void getPreCompare() {
		// TODO Auto-generated method stub
		check.setDisable(false);
		tabPane.getSelectionModel().select(check);// ѡ���ת		
	}
	
	//!!!!!!!!!!!!!!!!!!!!!!
	public void selectdisplay(String tip)
	{
		int res= JOptionPane.showConfirmDialog(null, tip, "��ʾ",
				JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
		if (res == JOptionPane.YES_OPTION){
			getPicPanel().getChildren().clear();
			picwite=1;m = 1;n=2;id=0;
			  for(String tmp:st){
				  displaydown(new File(tmp));
		        }	
		}
		
	}
}
