package login;

import java.sql.SQLException;

import database.DBConnect;
import dialogs.ErrorMessage;
import dialogs.SuccessMessage;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.text.Text;

public class LoginClass extends Application{

	Stage window;
	Scene scene, scenesub;
	Label laccname, lpass, lpass2, lnewacc, lnewacc2, cright;
	public static TextField txtname;
	PasswordField txtpass, txtpass2;
	Label limg;
	HBox hBox;
	String selecteduser;
	public static Label labeladmins;
	Image img= new Image(LoginClass.class.getResourceAsStream("/pic/meru_logo.png"));
	ImageView ivl= new ImageView(img);
	
	Text renew= new Text("Create a New Account");
	Button btnsave, btnclose;
	Text errormessage;
	public String role;
	@Override
	public void start(Stage login) throws Exception {
		
		window= login;
		window= new Stage();
		window.setResizable(false);
		window.getIcons().add(new Image("/pic/slogo.png"));
		limg= new Label();
		limg.setGraphic(ivl);
		
		labeladmins= new Label();
		laccname= new Label("Account Username");
		laccname.setStyle("-fx-text-fill:black;");
		laccname.setFont(Font.font("Calibri Light",14));
		
		txtname= new TextField();
		txtname.setPromptText("-Username-");
		txtname.setMaxSize(450, 100);
		txtname.setMinHeight(30);
		
		lpass= new Label("Password");
		lpass.setStyle("-fx-text-fill:black;");
		lpass.setFont(Font.font("Calibri Light",14));
		
		txtpass= new PasswordField();
		txtpass.setPromptText("-Account password-");
		txtpass.setMaxSize(450, 100);
		txtpass.setMinHeight(30);
		
		lpass2= new Label("Confirm Password");
		lpass2.setStyle("-fx-text-fill:black;");
		lpass2.setFont(Font.font("Calibri Light",14));
		
		txtpass2= new PasswordField();
		txtpass2.setPromptText("-Confirm password-");
		txtpass2.setMaxSize(450, 100);
		txtpass2.setMinHeight(30);
		
		btnclose=new Button("<< Back");
		btnclose.setOnAction(e -> {
			try {
				new Login().start(new Stage());
				window.close();
			} catch (Exception e1) {
				ErrorMessage.display("Launch Error", "Error during launch of program");
				e1.printStackTrace();
			}
		});
		btnsave= new Button("Save");
		btnsave.setMinSize(90, 30);
		btnsave.setOnAction(e -> {
			saveAccount();
		});
		lnewacc= new Label("You want to access system ? ");
		lnewacc.setStyle("-fx-text-fill: #000000");
		lnewacc.setFont(Font.font("Calibri Light",15));
		
		lnewacc2= new Label("See how to.");
		lnewacc2.setStyle("-fx-text-fill: #4994FF; -fx-font-weight:bold;");
		lnewacc2.setFont(Font.font("Calibri",16));
		lnewacc2.setCursor(Cursor.HAND);
		lnewacc2.setOnMouseClicked(e ->{
			
		});
		cright= new Label("@ 2016 -Company-");
		
		errormessage= new Text("");
		errormessage.setId("errormessage");
		errormessage.setText("Invalid Username and/or Password, try again");
		errormessage.setVisible(false);
		
		renew.setFont(Font.font(17));
		VBox laylog= new VBox(10);
		laylog.getChildren().addAll(limg, renew);
		laylog.setAlignment(Pos.CENTER);
		laylog.setPadding(new Insets(0,0,10,0));
		
		HBox lerror= new HBox();
		lerror.getChildren().add(errormessage);
		lerror.setAlignment(Pos.CENTER_LEFT);
		
		
		VBox layright= new VBox(40);
		layright.getChildren().addAll(cright);
		layright.setAlignment(Pos.CENTER);
		layright.setPadding(new Insets(30,0,0,0));
		
		VBox lay1= new VBox(7);
		lay1.getChildren().addAll(laccname, txtname);
		
		VBox lay2= new VBox(7);
		lay2.getChildren().addAll(lpass, txtpass);
		
		VBox lay3= new VBox(7);
		lay3.getChildren().addAll(lpass2, txtpass2);
		
		VBox lay12= new VBox(10);
		lay12.getChildren().addAll(lay1, lay2, lay3);
		
		HBox laysign= new HBox(10);
		laysign.getChildren().addAll(lerror, btnsave, btnclose);
		laysign.setAlignment(Pos.BOTTOM_RIGHT);
		
		VBox layall = new VBox(5);
		layall.getChildren().addAll(laylog, lay12, laysign, layright);
		layall.setPadding(new Insets(25,80,10,80));
		
		
		scene= new Scene(layall,610,430);
		window.setScene(scene);
		window.setTitle("Account Login");
		scene.getStylesheets().add(LoginClass.class.getResource("Login.css").toExternalForm());
		window.show();
	}
	public static void main(String[] args)
	{
		launch(args);
	}
	
	public void saveAccount()
	{
		
		if(txtname.getText().equals(""))
		{
			ErrorMessage.display("Blank", "Enter your username to proceed");
		}
		else if(txtname.getText().length() < 5)
		{
			ErrorMessage.display("Too Short", "Your username is too short, must be a minimum of 5 characters");
		}
		else if(txtpass.getText().equals("") || txtpass2.getText().equals(""))
		{
			ErrorMessage.display("Blank", "Please enter your passwords to proceed");
		}
		else if(txtpass.getText().length() <6 ||  txtpass2.getText().length() <6)
		{
			ErrorMessage.display("Too Short", "Such short passwords. Password must be a minimum of 6 characters");
		}
		else if(!txtpass.getText().matches(txtpass2.getText()))
		{
			ErrorMessage.display("Mismatch", "Passwords don't match, re-entry");
		}
		else
		{
			String query="INSERT INTO login VALUES('"+txtname.getText().toString()+"', '"+txtpass.getText().toString()+"', 'User');";
			try {
				DBConnect.connect();
				DBConnect.stmt.execute(query);
				
				SuccessMessage.display("Success", "Weldone "+txtname.getText()+". Your account has been created, proceed. \nYou will Login with minimal privilages "
						+ "until further \nproceessing.");
				
				txtname.setText("");
				txtpass.setText("");
				txtpass2.setText("");
			} catch (SQLException se) {
	        //JOptionPane.showMessageDialog(NewUser.this, se.getMessage(), "DataBase exception",
	            //    JOptionPane.WARNING_MESSAGE);
	    	ErrorMessage.display("Database exception", se.getMessage()+"Error");
	    } 
		
	    catch (Exception e) {
	    	ErrorMessage.display("Error", e.getMessage() +" error has occured, please try again");
		      e.printStackTrace();
		   
		}
			
			
		}
		
	}
}
