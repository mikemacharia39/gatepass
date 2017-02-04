package gateUsers;

import java.sql.ResultSet;

import database.DBConnect;
import dialogs.ErrorMessage;
import dialogs.SuccessMessage;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.ComboBox;
import javafx.geometry.Insets;

public class EditingClass extends Application{

	public static TextField txtname;
	Label lusername, llevel;
	Button btnsave, btnclose;
	Stage window;
	Scene scene;
	public static ComboBox<String> cuser;
	
	public void setToEdit()
	{
		window= new Stage();
		window.setTitle("Edit User");
		window.initModality(Modality.APPLICATION_MODAL);
		window.getIcons().add(new Image("/pic/slogo.png"));
		window.setResizable(false);
		lusername= new Label("Username");
		txtname= new TextField();
		txtname.setPromptText("Username");
		txtname.setEditable(false);
		txtname.setText(Users.txtname.getText());
		
		llevel= new Label("User Level");
		cuser= new ComboBox<>();
		cuser.getItems().addAll("Super User","Gate Admin", "Office Admin","User");
		cuser.setEditable(true);
		cuser.setValue(Users.txtlevel.getText());
		btnsave=new Button("Save");
		btnsave.setOnAction(e ->{
			setEdited();
		});
		btnclose=new Button("Close");
		btnclose.setOnAction( e -> {
			window.close();
		});
		
		HBox lay= new HBox(10);
		lay.getChildren().addAll(lusername, txtname);
		
		HBox lay1= new HBox(10);
		lay1.getChildren().addAll(llevel, cuser);
		
		HBox laybtn= new HBox(10);
		laybtn.getChildren().addAll(btnsave, btnclose);
		laybtn.setPadding(new Insets(0,10,0,10));
		laybtn.setAlignment(Pos.CENTER_RIGHT);
		
		VBox layall= new VBox(15);
		layall.setPadding(new Insets(15,15,5,15));
		layall.getChildren().addAll(lay,lay1, laybtn);
		layall.setStyle("-fx-background-color: linear-gradient(#E4E9A9, #9CD777);");
		scene= new Scene(layall);
		scene.getStylesheets().add(EditingClass.class.getResource("users.css").toExternalForm());
		window.setScene(scene);
		window.show();
	}
	
	@Override
	public void start(Stage editerS) throws Exception {
		
		window=editerS;
		
	}
	public static void main(String[] args){
		launch(args);
	}
	public void setEdited(){
		if(txtname.getText().equals(""))
		{
			ErrorMessage.display("Warning", "Please fill all the fields provided to proceed");
		     ResultSet rs;
		        
		        String query = "SELECT username, password from login WHERE username='" + txtname.getText() + "' AND level='" + cuser.getValue() + "'";
		        DBConnect.connect();
		        try {
		            rs = DBConnect.stmt.executeQuery(query);

		            if (rs.next())//valid
		            {
		                if (rs.getString("username")!=(txtname.getText().toString()) 
		                        && rs.getString("password")!=(cuser.getValue()) ) 
		                {
		                	ErrorMessage.display("Error", "Wrong password");
		                }
		                else
		                {
		                	
		                }
		            }
		            else
		            {
		            	
		            }
		        }
		        catch (Exception ea) 
		        {
		        	ErrorMessage.display("Error", ea.getMessage());
		            ea.printStackTrace();
		        }
		        }
        else
		    {
		   String fileUpdate="UPDATE login SET level= '"+ cuser.getValue()+"' where username='" + txtname.getText() + "'";
		        //connect to database
		   DBConnect.connect();
		  try {
			  DBConnect.stmt.execute(fileUpdate);
		      SuccessMessage.display("Success", "The User credential is updated");
		      DBConnect.closeConnection();
		      Users.table.setItems(new Users().getProduct());
		      txtname.clear();
		      cuser.setValue("User");
		  } catch (Exception e) {
			  ErrorMessage.display("Database Exception", e.getMessage());
		  }
		}   
	}
}
