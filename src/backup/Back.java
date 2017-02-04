package backup;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import dialogs.ErrorMessage;
import dialogs.SuccessMessage;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Back extends Application{

	Scene scene;
	Stage stageback;
	TextField txtpath, txtdatabase, txtusername;
	PasswordField txtpass;
	Button btnclose, btnchoose, btnbackup,  btnedit;
	Text ltitle;
	
	SimpleDateFormat dateFormat= new SimpleDateFormat("dd/MM/yyyy");
	Calendar calendar= Calendar.getInstance();
	String date=dateFormat.format(calendar.getTime());
	
	@Override
	public void start(Stage stage) throws Exception {
		
		stageback=stage;
		stageback= new Stage(StageStyle.UTILITY);
		stageback.initModality(Modality.APPLICATION_MODAL);
		stageback.setResizable(false);
		stageback.getIcons().add(new Image("/pic/slogo.png"));
		stageback.setTitle("Backup Database");
		txtdatabase= new TextField();
		txtdatabase.setText("gatepass");
		txtdatabase.setPromptText("database name");
		txtusername= new TextField();
		txtusername.setText("root");
		txtusername.setEditable(false);
		txtusername.setPromptText("database username");
		txtpass= new PasswordField();
		txtpass.setPromptText("database password");
		txtpass.setEditable(false);
		txtpath= new TextField();
		txtpath.setMinWidth(300);
		txtpath.setPromptText("select save location");
		btnchoose= new Button("Choose...");
		btnchoose.setOnAction(e -> {
			txtpath.setText(getBackUpPath());
		});
		btnclose= new Button("Close");
		btnclose.setOnAction(e -> {
			stageback.close();
		});
		btnbackup= new Button("Backup");
		btnbackup.setOnAction(e ->{
			btnBackupClicked();
		});
		btnedit =new Button("Edit");
		btnedit.setOnAction(e ->{
			txtpass.setEditable(true);
			txtusername.setEditable(true);
		});
		ltitle=new Text("Backup Database to Disk");
		ltitle.setFont(Font.font("Calibri", 15));
		ltitle.setStyle("-fx-text-fill:#0000ff;");
		Label lname=new Label("Database Name");
		Label lpass=new Label("Password");
		Label luser=new Label("Username");
		Label lpath=new Label("Save Location");
		GridPane lay= new GridPane();
		GridPane.setConstraints(lname, 0, 0);
		GridPane.setConstraints(txtdatabase, 1, 0);
		lay.setVgap(10);
		lay.setHgap(10);
		GridPane.setConstraints(luser, 0, 1);
		GridPane.setConstraints(txtusername, 1, 1);
		GridPane.setConstraints(btnedit, 2, 1);
		GridPane.setConstraints(lpass, 0, 2);
		GridPane.setConstraints(txtpass, 1, 2);
		
		GridPane.setConstraints(lpath, 0, 3);
		GridPane.setConstraints(txtpath, 1, 3);
		GridPane.setConstraints(btnchoose, 2, 3);
		lay.getChildren().addAll(lname, txtdatabase, luser, txtusername, btnedit,
				lpass, txtpass, lpath, txtpath, btnchoose);
		lay.setAlignment(Pos.CENTER);
		HBox layt= new HBox();
		layt.getChildren().add(ltitle);
		layt.setAlignment(Pos.CENTER_LEFT);
		
		HBox layb= new HBox(10);
		layb.getChildren().addAll(btnbackup,btnclose);
		layb.setAlignment(Pos.CENTER_RIGHT);
		
		BorderPane bp= new BorderPane();
		bp.setTop(layt);
		bp.setCenter(lay);
		bp.setBottom(layb);
		bp.setPadding(new Insets(10,10,10,10));
		scene= new Scene(bp, 520,250);
		stageback.setScene(scene);
		scene.getStylesheets().add(Back.class.getResource("backs.css").toExternalForm());
		stageback.show();
	}
	public static void main(String[] args)
	{
		launch(args);
	}
	
	public static String getBackUpPath() {
        String backUpPath = "";
        DirectoryChooser chooser= new DirectoryChooser();
        chooser.setTitle("Select Folder");
        chooser.setInitialDirectory(new File("C:/"));
        File files=chooser.showDialog(new Stage());
        if(files != null){
        backUpPath = files.getAbsolutePath();
        }
      return backUpPath;
}
	
	private void btnBackupClicked() {
        String backuppath=txtpath.getText();
   String Database =txtdatabase.getText();
   String Password =txtpass.getText();
   String user=txtusername.getText();
   Backup b = new Backup();
   try
  {
       if(txtusername.getText().equals("") || txtpath.getText().equals("") || txtdatabase.getText().equals(""))
       {
    	   ErrorMessage.display("Incomplete Details", "Please fill all the fields first");
       }
       else
       {
    	   byte[] data = b.getData("localhost", "3306", user,   Password, Database).getBytes();
           File filedst = new File(backuppath+"\\"+Database+".zip");
           FileOutputStream dest = new FileOutputStream(filedst);
           ZipOutputStream zip = new ZipOutputStream(
           new BufferedOutputStream(dest));
           zip.setMethod(ZipOutputStream.DEFLATED);
           zip.setLevel(Deflater.BEST_COMPRESSION);
           zip.putNextEntry(new ZipEntry(Database+".sql"));
           zip.write(data);
           zip.close();
           dest.close();
      SuccessMessage.display("Database BackUp Wizard", "Back Up Successfully for Database: " +
    		""+Database+"\n"+"On Dated: "+date);
       }
   }catch (Exception ex){
    ErrorMessage.display("Database BackUp Wizard", ex.getMessage()+" \nBack Up Failed for Database: "+Database+"\n "+"On Dated: ");
  }

    }
	
}
