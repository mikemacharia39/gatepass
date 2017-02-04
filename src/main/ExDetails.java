package main;

import register.AddNew;
import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;


public class ExDetails extends Application{

	Stage window;
	Scene scene;
	Label label1, label2, label21, label22, label3, label4, label5, label51, label52, label53, label54, label6, label61;
	Text welText, appuser, register, entries, moreinfo;
	
	@Override
	public void start(Stage det) throws Exception {
		
		window= det;
		window= new Stage(StageStyle.UNDECORATED); //StageStyle.TRANSPARENT
		//window.initModality(Modality.APPLICATION_MODAL);
		window.setAlwaysOnTop(true);
		//in order to give them uniquity use this for example scenetitle.setId("welcome-text");
		//user, changepassword, logoff, exit
		//adding them to the database
		//workers, students, staff, visitors
		//logs status, backup database, restore db, purge db
		//continuous attendance, daily reports, monthly reports visitors reports, unidentifiable, 
		//special reports
		//available themes
		//help, about
		
		welText= new Text("All Programs");
		welText.setId("welText");
		
		appuser= new Text("Manage Users");
		appuser.setId("identify_groups");
		
		register= new Text("Register");
		register.setId("identify_groups");
		
		entries= new Text("Continuous Entry");
		entries.setId("identify_groups");
		
		moreinfo= new Text("Help");
		moreinfo.setId("identify_groups");
	
		Image us = new Image(ExDetails.class.getResourceAsStream("/pic2/User.png"));
		ImageView ivus= new ImageView(us);
		label1= new Label(" ",ivus);
		label1.setText("\n\n\n\nUsers");
		label1.setMinWidth(220);
		label1.setMinHeight(120);
		label1.setAlignment(Pos.CENTER_LEFT);
		label1.setCursor(Cursor.HAND);
		label1.setPadding(new Insets(1,1,1,5));
		rotateHer(label1,ivus);
		//in Insets top,right,bottom,left
		
		Image cp = new Image(ExDetails.class.getResourceAsStream("/pic2/changepass.png"));
		ImageView ivcp= new ImageView(cp);
		label2= new Label("",ivcp);
		label2.setText("\n\n\n\nChange Password");
		label2.setAlignment(Pos.CENTER_LEFT);
		label2.setCursor(Cursor.HAND);
		label2.setPadding(new Insets(1,1,1,5));
		label2.setMinWidth(240);
		label2.setMinHeight(120);
		rotateHer(label2,ivcp);
		
		Image lo = new Image(ExDetails.class.getResourceAsStream("/pic2/logof.png"));
		ImageView ivlo= new ImageView(lo);
		label21= new Label("", ivlo);	
		label21.setText("\n\n\n\nLogout");
		label21.setAlignment(Pos.CENTER_LEFT);
		label21.setCursor(Cursor.HAND);
		label21.setPadding(new Insets(1,1,1,5));
		label21.setMinWidth(120);
		label21.setMinHeight(120);
		rotateHer(label21,ivlo);
		
		Image ex = new Image(ExDetails.class.getResourceAsStream("/pic2/logout.png"));
		ImageView ivex= new ImageView(ex);
		label22= new Label("",ivex);
		label22.setText("\n\n\n\nExit");
		label22.setAlignment(Pos.CENTER_LEFT);
		label22.setCursor(Cursor.HAND);
		label22.setPadding(new Insets(1,1,1,5));
		label22.setMinWidth(120);
		label22.setMinHeight(120);
		rotateHer(label22,ivex);
		
		Image rnew = new Image(ExDetails.class.getResourceAsStream("/pic2/reg.png"));
		ImageView ivrnew= new ImageView(rnew);
		label3= new Label(" ", ivrnew);
		label3.setText("\n\n\n\nRegister New");
		label3.setAlignment(Pos.CENTER_LEFT);
		label3.setCursor(Cursor.HAND);
		label3.setPadding(new Insets(1,1,1,5));
		label3.setMinWidth(220);
		label3.setMinHeight(120);
		rotateHer(label3, ivrnew);
	
		label3.setOnMouseClicked( e -> {
			try {
				new AddNew().start(new Stage());
				//window.close();
			} catch (Exception e1) {				
				e1.printStackTrace();
			}
		});
		
		Image vi = new Image(ExDetails.class.getResourceAsStream("/pic2/viewr.png"));
		ImageView ivvi= new ImageView(vi);
		label4= new Label("", ivvi);
		label4.setText("\n\n\n\nView Records");
		label4.setAlignment(Pos.CENTER_LEFT);
		label4.setCursor(Cursor.HAND);
		label4.setPadding(new Insets(1,1,1,5));
		label4.setMinWidth(241);
		label4.setMinHeight(120);
		rotateHer(label4, ivvi);
		
		Image att = new Image(ExDetails.class.getResourceAsStream("/pic2/att.png"));
		ImageView ivatt= new ImageView(att);
		label5= new Label("", ivatt);
		label5.setText("\n\n\n\nContinuous\nAttendance");
		label5.setAlignment(Pos.CENTER_LEFT);
		label5.setCursor(Cursor.HAND);
		label5.setPadding(new Insets(1,1,1,5));
		label5.setMinWidth(220);
		label5.setMinHeight(120);
		rotateHer(label5, ivatt);
		
		Image dr = new Image(ExDetails.class.getResourceAsStream("/pic2/dreport.png"));
		ImageView ivdr= new ImageView(dr);
		label51= new Label("",ivdr);
		label51.setText("\n\n\n\nDaily\nReport");
		label51.setAlignment(Pos.CENTER_LEFT);
		label51.setCursor(Cursor.HAND);
		label51.setPadding(new Insets(1,1,1,5));
		label51.setMinWidth(115);
		label51.setMinHeight(120);
		rotateHer(label51, ivdr);
		
		Image mr = new Image(ExDetails.class.getResourceAsStream("/pic2/mreport.png"));
		ImageView ivmr= new ImageView(mr);
		label52= new Label("", ivmr);
		label52.setText("\n\n\n\nMonth\nReport");
		label52.setAlignment(Pos.CENTER_LEFT);
		label52.setCursor(Cursor.HAND);
		label52.setPadding(new Insets(1,1,1,5));
		label52.setMinWidth(118);
		label52.setMinHeight(120);
		rotateHer(label52, ivmr);
		
		Image vr = new Image(ExDetails.class.getResourceAsStream("/pic2/vreport.png"));
		ImageView ivvr= new ImageView(vr);
		label53= new Label(" ",ivvr);
		label53.setText("\n\n\n\n Visitor Reports");
		label53.setAlignment(Pos.CENTER_LEFT);
		label53.setCursor(Cursor.HAND);
		label53.setPadding(new Insets(1,1,1,5));
		label53.setMinWidth(220);
		label53.setMinHeight(120);
		rotateHer(label53, ivvr);
		

		Image sr = new Image(ExDetails.class.getResourceAsStream("/pic2/sreport.png"));
		ImageView ivsr= new ImageView(sr);
		label54= new Label("", ivsr);
		label54.setText("\n\n\n\nSpecial Reports");
		label54.setAlignment(Pos.CENTER_LEFT);
		label54.setCursor(Cursor.HAND);
		label54.setPadding(new Insets(1,1,1,5));
		label54.setMinWidth(240);
		label54.setMinHeight(120);
		rotateHer(label54, ivsr);
		
		Image he = new Image(ExDetails.class.getResourceAsStream("/pic2/Help.png"));
		ImageView ivhe= new ImageView(he);
		label6= new Label(" ",ivhe);
		label6.setText("\n\n\n\n\tHelp?");
		label6.setAlignment(Pos.CENTER_LEFT);
		label6.setCursor(Cursor.HAND);
		label6.setPadding(new Insets(1,1,1,5));
		label6.setMinWidth(220);
		label6.setMinHeight(120);
		rotateHer(label6, ivhe);
		
		Image ab = new Image(ExDetails.class.getResourceAsStream("/pic2/info.png"));
		ImageView ivab= new ImageView(ab);
		label61= new Label(" ",ivab);
		label61.setText("\n\n\n\n\tAbout");
		label61.setAlignment(Pos.CENTER_LEFT);
		label61.setCursor(Cursor.HAND);
		label61.setPadding(new Insets(1,1,1,5));
		label61.setMinWidth(241);
		label61.setMinHeight(120);
		rotateHer(label61, ivab);
		
		HBox lay= new HBox(10);
		lay.setPadding(new Insets(5,10,10,0));
		lay.getChildren().addAll(welText);
		
		HBox lay1= new HBox(7);
		lay1.setPadding(new Insets(10,10,10,0));
		
		lay1.getChildren().addAll(label1, label2, label21, label22);
		
		HBox lay2= new HBox(7);
		lay2.setPadding(new Insets(10,10,10,0));
		lay2.getChildren().addAll(label3, label4);
	
		
		HBox lay3= new HBox(7);
		lay3.getChildren().addAll(label5, label51, label52);
		
		HBox lay31= new HBox(7);
		lay31.getChildren().addAll(label53, label54);
		
		VBox lay3all= new VBox(7);
		lay3all.setPadding(new Insets(10,10,10,0));
		lay3all.getChildren().addAll(lay3, lay31);
		
		HBox lay4= new HBox(7);
		lay4.setPadding(new Insets(10,10,10,0));
		lay4.getChildren().addAll(label6, label61);
		
		VBox layall= new VBox(5);
		layall.setId("scroll_main");
		layall.setPadding(new Insets(5,10,10,15));
		layall.getChildren().addAll(lay,appuser, lay1, register,lay2, entries, lay3all, moreinfo, lay4);
		ScrollPane scroll =new ScrollPane();
		scroll.setContent(layall);
		scene= new Scene(scroll, 771,705); //771
		
		window.setResizable(true);
		window.setX(240);
		window.setY(23);
		window.setScene(scene);
		scene.getStylesheets().add(ExDetails.class.getResource("gatepass.css").toExternalForm());
		window.show();
        label22.setOnMouseClicked(e ->{
			
			window.close();
		});
        
		
	}
	
	public void rotateHer(Label labelHer, ImageView iv)
	{
		RotateTransition rotation = new RotateTransition(Duration.seconds(2), iv);
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
	public static void main(String[] args)
	{
		launch(args);
	}
}
