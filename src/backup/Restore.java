package backup;

import java.io.File;

import dialogs.ErrorMessage;
import dialogs.SuccessMessage;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
public class Restore extends Application{

	Stage stage;
	Scene scene;
	TextField txtpath;
	Button btnclose, btnchoose, btnrestore;
	
	public static void main(String[] args) {
		launch(args);
	}
	@Override
	public void start(Stage s) throws Exception {
		stage=s;
		stage=new Stage(StageStyle.UTILITY);
		stage.setTitle("Restore Database");
		stage.setResizable(false);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.getIcons().add(new Image("/pic/slogo.png"));
		Label lt= new Label("Restore backedup Database (hint: extract first)");
		txtpath= new TextField();
		txtpath.setMinWidth(270);
		btnchoose= new Button("Choose...");
		btnchoose.setOnAction(e ->{
			txtpath.setText(getRestore());
		});
		
		btnclose= new Button("Close");
		btnclose.setOnAction(e -> {
			stage.close();
		});
		btnrestore= new Button("Restore");
		btnrestore.setOnAction(e -> {
			setRestore();
		});
		HBox lay= new HBox(10);
		lay.getChildren().add(lt);
		HBox lay1= new HBox(10);
		lay1.getChildren().addAll(txtpath, btnchoose);
		HBox lay2= new HBox(10);
		lay2.getChildren().addAll(btnrestore, btnclose);
		lay2.setAlignment(Pos.CENTER_RIGHT);
		VBox layall= new VBox(20);
		layall.getChildren().addAll(lay,lay1,lay2);
		layall.setAlignment(Pos.CENTER);
		layall.setPadding(new Insets(15,15,15,15));
		scene= new Scene(layall, 400,150);
		stage.setScene(scene);
		scene.getStylesheets().add(Restore.class.getResource("backs.css").toExternalForm());
		stage.show();
	}
	public boolean setRestore(){
		String source=txtpath.getText();
		String dbUserName="root";
		String dbPassword="";
		   
		//String[] restoreCmd = new String[]{"C:/wamp/bin/mysql/mysql5.1.36/bin/mysql -u "+dbUserName+" -p"+dbPassword+" -e \"source "+source+"\""};
		 
        Process runtimeProcess;
        try {
        	String executeCmd = "C:/wamp/bin/mysql/mysql5.1.36/bin/mysql -u "+dbUserName+" -p "+dbPassword+" < -e" + source;
            runtimeProcess = Runtime.getRuntime().exec(executeCmd);
            int processComplete = runtimeProcess.waitFor();
 
            if (processComplete == 0) {
                SuccessMessage.display("Success", "Restored successfully!");
            } else {
            	ErrorMessage.display("Failed", "Could not restore the backup!");
            }
        } catch (Exception ex) {
        	ErrorMessage.display("Error", ex.getMessage());
            ex.printStackTrace();
        }
		return false;
	}
	public String getRestore(){
		String backuppath ="";
		File file;
		FileChooser fileChooser= new FileChooser();
		file = fileChooser.showOpenDialog(stage);
		fileChooser.setTitle("Select Photo");
        if (file != null) {
        	fileChooser.getExtensionFilters().addAll(
        		    new FileChooser.ExtensionFilter("All Images", "*.*"),
        		    new FileChooser.ExtensionFilter("SQL", "*.sql")
        );
        	
        	backuppath = file.getAbsolutePath();      
	}
		return backuppath;
	}
}
