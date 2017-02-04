package settings;

import dialogs.ComplexConfirm;
import dialogs.ErrorMessage;
import backup.Back;
import backup.PurgeData;
import backup.Restore;
import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import logs.LogStatus;
import main.ExDetails;
import main.MainWindow;


public class SysSettings extends Application
{

	Stage window;
	Label ltime, lrevert, lstyle, lrestore, lpurge, lbackup, lclose;
	Scene scene;
	Text sysSet, theme, systems;
	@Override
	public void start(Stage s) throws Exception {
		
		window=s;
		window= new Stage(StageStyle.UNDECORATED);
		window.setTitle("System Settings");
		window.initModality(Modality.APPLICATION_MODAL);
		window.getIcons().add(new Image("/pic/slogo.png"));
		window.setResizable(false);
		
		Image imgback = new Image(ExDetails.class.getResourceAsStream("/pic3/RStudio.png"));
		ImageView ivback= new ImageView(imgback);
		lbackup= new Label("", ivback);
		lbackup.setText("\n\n\n\nBackup Database");
		lbackup.setAlignment(Pos.CENTER_LEFT);
		lbackup.setCursor(Cursor.HAND);
		lbackup.setPadding(new Insets(1,1,1,5));
		lbackup.setMinWidth(240);
		lbackup.setMinHeight(120);
		rotateHer(lbackup, ivback);
		lbackup.setOnMouseClicked(e -> {
			try {
				new Back().start(new Stage());
			} catch (Exception e1) {
				ErrorMessage.display("Launch error", e1.getMessage()+" launch error");
				e1.printStackTrace();
			}
		});
		
		Image imgclose = new Image(ExDetails.class.getResourceAsStream("/pic3/logout-1-512.png"));
		ImageView ivclose= new ImageView(imgclose);
		lclose= new Label("", ivclose);
		lclose.setText("\n\n\n\nExit Settings");
		lclose.setAlignment(Pos.CENTER_LEFT);
		lclose.setCursor(Cursor.HAND);
		lclose.setPadding(new Insets(1,1,1,5));
		lclose.setMinWidth(240);
		lclose.setMinHeight(120);
		rotateHer(lclose, ivclose);
		lclose.setOnMouseClicked(e -> {
			window.close();
		});
		
		Image imgtime = new Image(ExDetails.class.getResourceAsStream("/pic3/free-time-tracking-software.png"));
		ImageView ivtime= new ImageView(imgtime);
		ltime= new Label("", ivtime);
		ltime.setText("\n\n\n\nTrack User Log-in ");
		ltime.setAlignment(Pos.CENTER_LEFT);
		ltime.setCursor(Cursor.HAND);
		ltime.setPadding(new Insets(1,1,1,5));
		ltime.setMinWidth(240);
		ltime.setMinHeight(120);
		rotateHer(ltime, ivtime);
		ltime.setOnMouseClicked(e -> {
			try {
				new LogStatus().start(new Stage());
			} catch (Exception e1) {
				ErrorMessage.display("Launch Error", "Error occured during launch of program");
				e1.printStackTrace();
			};
			
		});
		
		Image imgpurge = new Image(ExDetails.class.getResourceAsStream("/pic3/recycle_bin_PNG9111.png"));
		ImageView ivpurge= new ImageView(imgpurge);
		lpurge= new Label("", ivpurge);
		lpurge.setText("\n\n\n\nPurge Database Records");
		lpurge.setAlignment(Pos.CENTER_LEFT);
		lpurge.setCursor(Cursor.HAND);
		lpurge.setPadding(new Insets(1,1,1,5));
		lpurge.setMinWidth(240);
		lpurge.setMinHeight(120);
		rotateHer(lpurge, ivpurge);
		lpurge.setOnMouseClicked(e -> {
			deleteProgram();
		});
		
		Image imgrest = new Image(ExDetails.class.getResourceAsStream("/pic3/System Restore.png"));
		ImageView ivrest= new ImageView(imgrest);
		lrestore= new Label("", ivrest);
		lrestore.setText("\n\n\n\nRestore Database");
		lrestore.setAlignment(Pos.CENTER_LEFT);
		lrestore.setCursor(Cursor.HAND);
		lrestore.setPadding(new Insets(1,1,1,5));
		lrestore.setMinWidth(240);
		lrestore.setMinHeight(120);
		rotateHer(lrestore, ivrest);
		lrestore.setOnMouseClicked(e -> {
			try {
				new Restore().start(new Stage());
			} catch (Exception e1) {
				ErrorMessage.display("Launch Error", e1.getMessage()+" /nError occured during launching application");
				e1.printStackTrace();
			}
		});
		
		Image imgstyle = new Image(ExDetails.class.getResourceAsStream("/pic3/style.png"));
		ImageView ivstyle= new ImageView(imgstyle);
		lstyle= new Label("", ivstyle);
		lstyle.setText("\n\n\n\nChange Theme");
		lstyle.setAlignment(Pos.CENTER_LEFT);
		lstyle.setCursor(Cursor.HAND);
		lstyle.setPadding(new Insets(1,1,1,5));
		lstyle.setMinWidth(240);
		lstyle.setMinHeight(120);
		rotateHer(lstyle, ivstyle);
		lstyle.setOnMouseClicked(e -> {
			setUserAgentStylesheet(STYLESHEET_CASPIAN);
		});
		
		Image imgrev = new Image(ExDetails.class.getResourceAsStream("/pic3/Undo.png"));
		ImageView ivrev =new ImageView(imgrev);
		lrevert= new Label("", ivrev);
		lrevert.setText("\n\n\n\nRevert Theme");
		lrevert.setAlignment(Pos.CENTER_LEFT);
		lrevert.setCursor(Cursor.HAND);
		lrevert.setPadding(new Insets(1,1,1,5));
		lrevert.setMinWidth(240);
		lrevert.setMinHeight(120);
		rotateHer(lrevert, ivrev);
		lrevert.setOnMouseClicked(e -> {
			setUserAgentStylesheet(STYLESHEET_MODENA);
		});

		sysSet= new Text("Settings");
		sysSet.setFont(Font.font("Andalus", 14));
		theme= new Text("Change Theme");
		theme.setFont(Font.font("Andalus", 14));
		systems= new Text("System Settings");
		systems.setId("systitle");
		
		HBox lay1= new HBox(8);
		lay1.getChildren().addAll(lrestore, lclose);
		lay1.setPadding(new Insets(4,0,0,0));
		HBox lay2= new HBox(8);
		lay2.getChildren().addAll(lbackup, ltime, lpurge);
		
		HBox lay3= new HBox(8);
		lay3.getChildren().addAll(lstyle, lrevert);
		lay3.setPadding(new Insets(3,0,0,0));
		
		VBox layall= new VBox(8);
		layall.getChildren().addAll(systems, sysSet, lay2, lay1, theme, lay3);
		layall.setPadding(new Insets(15,15,10,20));
		
		scene= new Scene(layall, 855,660);
		scene.getStylesheets().add(SysSettings.class.getResource("settings.css").toExternalForm());
		window.setScene(scene);
		window.setY(25);
		window.setX(252);
		window.show();
		
		 if(MainWindow.ladmins.getText().equals("User"))
	 		{
	 			lbackup.setVisible(false);
	 			lrestore.setVisible(false);
	 			lpurge.setVisible(false);
	 		}
	}
	public void rotateHer(Label labelHer, ImageView iv)
	{
		RotateTransition rotation = new RotateTransition(Duration.seconds(2.3), iv);
		rotation.setCycleCount(Animation.INDEFINITE);
		rotation.setByAngle(360);

		iv.setTranslateZ(iv.getBoundsInLocal().getWidth() / 2.0);
		iv.setRotationAxis(Rotate.Y_AXIS);
		
		labelHer.setOnMouseEntered(e ->
		{ 
			rotation.play();
			iv.setRotate(180);
		});
		labelHer.setOnMouseExited(e ->
		{ 
			rotation.pause();
			iv.setRotate(0);
		});
	}
	private void deleteProgram()
	{
		boolean result = ComplexConfirm.display("Purge Databases", " Are you sure you want to completely clear the database? "
				+ " \n If no press No otherwise press Yes \n Press Backup to first Backup the Database");
		if(result)
		{	
			new PurgeData().deleteLog();
		}
	}
	public static void main(String[] args)
	{
		launch(args);
	}
}