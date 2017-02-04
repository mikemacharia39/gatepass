package login;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import main.MainWindow;
import database.DBConnect;
import dialogs.ErrorMessage;
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

public class Login extends Application{

	Stage window;
	Scene scene, scenesub;
	Label laccname, lpass, lnewacc, lnewacc2, cright;
	public static TextField txtname;
	PasswordField txtpass;
	Label limg;
	HBox hBox;
	String selecteduser;
	public static Label labeladmins;
	Image img= new Image(Login.class.getResourceAsStream("/pic/meru_logo.png"));
	ImageView ivl= new ImageView(img);
	
	Button btnsign, btnclose;
	Text errormessage;
	
	SimpleDateFormat dateformat1=new SimpleDateFormat("EEEE dd/MM/yyyy "+" hh:mm:ss aa");
    Calendar calendar1=Calendar.getInstance();
    public static String d1; 
	
	public String role;
	@Override
	public void start(Stage login) throws Exception {
		
		window= login;
		window= new Stage();
		window.setResizable(false);
		window.getIcons().add(new Image("/pic/slogo.png"));
		limg= new Label();
		limg.setGraphic(ivl);
		
		d1=dateformat1.format(calendar1.getTime());
		
		labeladmins= new Label();
		laccname= new Label("Account Username");
		laccname.setStyle("-fx-text-fill:black;");
		laccname.setFont(Font.font("Calibri Light",14));
		
		Image imgname= new Image(Login.class.getResourceAsStream("/pic2/User.png"));
		ImageView ivname= new ImageView(imgname);
		Label lname= new Label();
		lname.setGraphic(ivname);
		txtname= new TextField();
		txtname.setPromptText("-Account Username-");
		txtname.setMaxSize(450, 100);
		txtname.setMinHeight(30);
		
		lpass= new Label("Password");
		lpass.setStyle("-fx-text-fill:black;");
		lpass.setFont(Font.font("Calibri Light",14));
		
		txtpass= new PasswordField();
		txtpass.setPromptText("-Account password-");
		txtpass.setMaxSize(450, 100);
		txtpass.setMinHeight(30);
		
		btnclose=new Button("Exit");
		btnclose.setOnAction(e -> {
			window.close();
		});
		btnsign= new Button("Sign in");
		btnsign.setMinSize(90, 30);
		btnsign.setOnAction(e -> {
			openMainPage();
		});
		lnewacc= new Label("You want to access the system ? ");
		lnewacc.setStyle("-fx-text-fill: #000000");
		lnewacc.setFont(Font.font("Calibri Light",15));
		
		lnewacc2= new Label("Create a new account...");
		lnewacc2.setStyle("-fx-text-fill: #4994FF; -fx-font-weight:bold;");
		lnewacc2.setFont(Font.font("Calibri",16));
		lnewacc2.setCursor(Cursor.HAND);
		lnewacc2.setOnMouseClicked(e ->{
			try {
				new LoginClass().start(new Stage());
				window.close();
			} catch (Exception e1) {
				ErrorMessage.display("Launch Error", "Error during launch of program");
				e1.printStackTrace();
			}
		});
		cright= new Label("@ 2016 -Company-");
		
		errormessage= new Text("");
		errormessage.setId("errormessage");
		errormessage.setText("Invalid Username and/or Password, try again");
		errormessage.setVisible(false);
		
		VBox laylog= new VBox(10);
		laylog.getChildren().addAll(limg);
		laylog.setAlignment(Pos.CENTER);
		laylog.setPadding(new Insets(0,0,10,0));
		
		HBox lerror= new HBox();
		lerror.getChildren().add(errormessage);
		lerror.setAlignment(Pos.CENTER_LEFT);
		
		HBox layacc= new HBox(5);
		layacc.setAlignment(Pos.CENTER);
		layacc.getChildren().addAll(lnewacc, lnewacc2);
		
		VBox layright= new VBox(40);
		layright.getChildren().addAll(layacc, cright);
		layright.setAlignment(Pos.CENTER);
		layright.setPadding(new Insets(30,0,0,0));
		
		VBox lay1= new VBox(7);
		lay1.getChildren().addAll(laccname, txtname);
		
		VBox lay2= new VBox(7);
		lay1.getChildren().addAll(lpass, txtpass);
		
		VBox lay12= new VBox(10);
		lay12.getChildren().addAll(lay1, lay2);
		
		HBox laysign= new HBox(10);
		laysign.getChildren().addAll(lerror, btnsign, btnclose);
		laysign.setAlignment(Pos.BOTTOM_RIGHT);
		
		VBox layall = new VBox(5);
		layall.getChildren().addAll(laylog, lay12, laysign, layright);
		layall.setPadding(new Insets(50,80,10,80));
		
		
		scene= new Scene(layall,610,430);
		window.setScene(scene);
		window.setTitle("Account Login");
		scene.getStylesheets().add(Login.class.getResource("Login.css").toExternalForm());
		window.show();
	}
	public static void main(String[] args)
	{
		launch(args);
	}
	
	public void openMainPage()
	{
		ResultSet rs= null;
		String user = txtname.getText();
        String pass = txtpass.getText();
        String notime="00:00";
        int no = 0;
        
        if(txtname.getText().equals("") || txtpass.getText().equals(""))
		{
		   ErrorMessage.display("Alert", "Please Fill all the Fields First in order to proceed");
		           }
		else{
        String query = "SELECT username, password, level from login WHERE username= '" + user + "'AND password = '" + pass + "'";
        DBConnect.connect();
        try {
            rs = DBConnect.stmt.executeQuery(query);
            if (rs.next())
            {	
                if (rs.getString("username").equals(txtname.getText().toString()) && rs.getString("password").equals(txtpass.getText().toString())) 	
                {
                	try {
                		window.close();
						new MainWindow().start(new Stage());
					} catch (Exception e) {
						e.printStackTrace();
					}
                	role= rs.getString("level");
                	String query2= "INSERT INTO logs values('"+no+"', '"+user+"', '"+role+"', '"+d1+"', '"+notime+"' )";
                	DBConnect.stmt.execute(query2);
                	errormessage.setVisible(false);
                }
                else if(rs.getString("username").equals(txtname.getText().toString()) && rs.getString("password")!=txtpass.getText().toString())
                {
                	errormessage.setText("Invalid Password, check your password");
                	errormessage.setVisible(true);
                }
                else {
                	
                	errormessage.setVisible(true);
                    txtpass.requestFocus();
                }
            }
            else {
            	errormessage.setVisible(true);
                txtpass.requestFocus();
            }
            DBConnect.closeConnection();
        } //end of try
        catch (SQLException e1) 
    {
        	ErrorMessage.display("Launching Error", "Database Communications Link Failure");
		e1.printStackTrace();
	}
		}
	}
	public static int countRows(Connection conn) throws SQLException {
	    // select the number of rows in the table
	    ResultSet rs = null;
	    int rowCount = -1;
	    try {
	      DBConnect.stmt = conn.createStatement();
	      rs = DBConnect.stmt.executeQuery("SELECT COUNT(*) FROM login");
	      // get the number of rows from the result set
	      rs.next();
	      rowCount = rs.getInt(1);
	    } finally {
	      rs.close();
	      DBConnect.stmt.close();
	    }
	    return rowCount;
	  }
}
