//package application;
//	
//import java.net.InetAddress;
//import java.net.URL;
//import java.net.UnknownHostException;
//import java.util.ResourceBundle;
//
//import javafx.application.Application;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.fxml.Initializable;
//import javafx.stage.Stage;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.TextField;
//import javafx.scene.layout.BorderPane;
//
//
//public class Main extends Application{
//	
//	@Override
//	public void start(Stage primaryStage) {
//		try {
//			Parent root = FXMLLoader.load(getClass()
//                    .getResource("/application/serverFxml.fxml"));
//			primaryStage.setTitle("·þÎñÆ÷");
//			primaryStage.setScene(new Scene(root));	
//			primaryStage.show();
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
//
//	}
//
//	
//	public static void main(String[] args) {
//		launch(args);
//	}
//
//}
