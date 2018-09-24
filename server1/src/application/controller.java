package application;
//
//import java.net.InetAddress;
//import java.net.URL;
//import java.net.UnknownHostException;
//import java.util.ResourceBundle;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//import javax.swing.JOptionPane;
//
//import Mina.Server.MinaServer;
//import Mina.Server.test;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.scene.control.Button;
//import javafx.scene.control.ListView;
//import javafx.scene.control.TextField;
//
//public class controller implements Initializable {
//	@FXML
//	private TextField ip;
//	@FXML
//	private Button closeSer;
//	@FXML
//	private Button openSer;
//	@FXML
//	private TextField port;
//	@FXML
//	private ListView displayInfo;
//	
////	MinaServer Server= MinaServer.getInstancei();
//	test instancei =test.getInstancei();
//	public void initialize(URL arg0, ResourceBundle arg1) {
//		// TODO Auto-generated method stub
//		getIp();
//		//instancei.getcontrview(this);
//		closeSer.setDisable(true);
//		setText("锟斤拷示锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷丝诤锟�");
//	}
//	
//	public void openServer(){	
//		if(check_port()==1){
//			if(instancei.instanser.Start(ip.getText(),Integer.valueOf(port.getText())))
//			{
//				setText("锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟缴癸拷,锟饺达拷锟酵伙拷锟斤拷锟斤拷锟斤拷...");
//				closeSer.setDisable(false);
//			}else
//			{
//				setText("锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷失锟斤拷,锟斤拷锟剿匡拷锟角凤拷占锟斤拷!");
//			}
//		}
//	}
//	
//	public int check_port(){
//		//锟斤拷锟斤拷锟斤拷锟斤拷1锟斤拷示锟剿口猴拷锟斤拷锟斤拷
//		//锟斤拷锟斤拷锟斤拷锟斤拷0锟斤拷示锟剿口猴拷锟斤拷锟斤拷锟斤拷
//		if(!port.getText().equals("")){
//			if(!isNumeric(port.getText()))	{
//				JOptionPane.showMessageDialog(null, "锟斤拷示:锟剿口猴拷只锟斤拷为锟斤拷锟斤拷");
//				return 0;
//			}else if(isNumeric(port.getText())){
//				int a=Integer.parseInt(port.getText());
//				while(a<1500 || a>10000){
//				JOptionPane.showMessageDialog(null, "锟斤拷示:锟剿口号凤拷围锟斤拷1500-10000锟斤拷");
//				return 0;
//				}
//			}
//		}else{
//			JOptionPane.showMessageDialog(null, "锟斤拷示:锟剿口猴拷锟斤拷锟诫不锟斤拷为锟秸ｏ拷");
//			return 0;
//		}
//		return 1;
//	
//	}
//	
//	public void closeServer()
//	{
//		instancei.instanser.Close();
//		setText("锟窖关闭凤拷锟斤拷锟斤拷!");
//		openSer.setDisable(false);
//	}
//	public boolean isNumeric(String str){ 
//		   Pattern pattern = Pattern.compile("[0-9]*"); 
//		   Matcher isNum = pattern.matcher(str);
//		   if( !isNum.matches() ){
//		       return false; 
//		   } 
//		   return true; 
//		}
//	ObservableList<String> strList = FXCollections.observableArrayList();
//	public void setText(String str){
//	
//		strList.add(str);
//		displayInfo.setItems(strList);
//		
//	}
//
//	public void getIp(){
//		
//		InetAddress ia=null;
//		
//		try {
//			ia=ia.getLocalHost();
//			ip.setText(ia.getHostAddress());
//			ip.setDisable(true);
//		} catch (UnknownHostException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//}
