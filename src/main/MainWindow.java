package main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Date;




import office.OfficeRecords;
import officeReports.DailyReportso;
import officeReports.MonthlyGeneratoro;
import officeReports.OfficePersonalReport;
import officechart.OfficeSpecials;
import officeentry.Office_Entry;
import officereg.AddOfficeStaffs;
import changepass.ChangePassword;
import infos.About;
import infos.Help;
import gateUsers.Users;
import gatereports.DailyReports;
import gatereports.MonthlyGenerator;
import gatereports.PersonalReports;
import gatereports.SpecialReports;
import database.DBConnect;
import dialogs.Confirmation;
import dialogs.ErrorMessage;
import attendance.AllAttendance;
import register.AddNew;
import reports.ChooseOne;
import reports.VisitorsReports;
import settings.SysSettings;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;
import login.Login;

public class MainWindow extends Application{
	
	Stage window;
	Scene scene;
	private Rectangle2D boxBounds = new Rectangle2D(600,600,870,700);
	 private VBox bottomPane;
	 private VBox layall;
	 VBox layoffice;
	 private Rectangle clipRect;
	 private Timeline timelineUp;
	 private Timeline timelineDown, timelineoffice;
	 Label welc;
	 Text myclock;
	 Image img= new Image(MainWindow.class.getResourceAsStream("/pic/meru_logo.png"));
	 ImageView ivt= new ImageView(img);
	 
	 ScrollPane sc = new ScrollPane();
	 Label label1, label2, label21, label22, label3, label4, label5, label51, label52, label53, label54, label6, label61, tpic;
	 Text welText, appuser, register, entries, moreinfo;
	 
	public static Label lname;
	Label lallapps;
	Label lreg;
	Label lpower;
	Label lusers, lgsearch, losearch;
	Label lhelp;
	Label latt;
	Label lsetting;
	 Text freq, systems;
	 public static Text ladmins;
	//for office
	Label loreg, loexit, lologout, loview, loatt, loallapps, ldaygen, lmongen, lspege;
	Text reports, registration, exito, weloText;
	
	/*Creating Context Menus
	final ContextMenu contextMenu = new ContextMenu();
	MenuItem daily = new MenuItem("Daily Reports");
	MenuItem monthly = new MenuItem("Monthly Reports");
	MenuItem special = new MenuItem("Special Reports");
	
	*/
	 
	@Override
	public void start(Stage shelp) throws Exception {
		
		window= shelp;
		window= new Stage();
		window.getIcons().add(new Image("/pic/slogo.png"));
		HBox root = new HBox();
		  root.autosize();
		  scene = new Scene(root, 820,690);
		  scene.getStylesheets().add(ExDetails.class.getResource("gatepass.css").toExternalForm());
		  window.setOnCloseRequest(e -> {
			  e.consume();
			  closeProgram();
			  
		  });
		  window.setTitle("Main GatePass System");
		  window.setScene(scene);
		  window.setMaximized(true);
		  window.show();
		     
		  configureBox(root);
	}
	
	 private void configureBox(HBox root) {
		  StackPane container = new StackPane();
		  //container.setPrefHeight(700);
		  container.setPrefSize(boxBounds.getWidth(), boxBounds.getHeight());
		  container.setStyle("-fx-border-width:0px;-fx-border-style:solid;-fx-border-color:#999999;");
		
		  // First PANE 
		  //ivt.setFitWidth(150);
		  //ivt.setFitHeight(150);
		  tpic= new Label();
		  tpic.setId("meru_logo");
		  tpic.setGraphic(ivt);
		  welc= new Label();
		  welc.setText("BIOMETRIC SECURITY CHECK FOR \nGATE PASS AND ACCESS TO EXAMINATIONS OFFICE \nFOR MERU UNIVERSITY");
		  welc.setId("meru_logo");
		  welc.setTextAlignment(TextAlignment.CENTER);
		  
		  VBox vcont= new VBox(15);
		  //vcont.setAlignment(Pos.CENTER);
		  vcont.getChildren().addAll(welc);
		  vcont.setPadding(new Insets(35,10,5,10));
		  
		  myclock= new Text(); 
		  myclock.setId("lview");
		  myclock.setFont(Font.font("Calibri", 17));
			final Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {  
			     @Override  
			     public void handle(ActionEvent event) {  
			    	 myclock.setText(DateFormat.getDateTimeInstance().format(new Date()));
			     }  
			}));  
			timeline.setCycleCount(Animation.INDEFINITE);  
			timeline.play();
		  
		  HBox layclock= new HBox();
		  layclock.getChildren().add(myclock);
		  layclock.setAlignment(Pos.TOP_RIGHT);
		  layclock.setPadding(new Insets(0,10,10,10));
		  
		  bottomPane = new VBox(10);
		  bottomPane.setAlignment(Pos.TOP_CENTER);
		  bottomPane.getChildren().addAll(layclock,tpic, vcont);
		  bottomPane.setPadding(new Insets(20,10,5,0));
		  bottomPane.setStyle("-fx-background-color: linear-gradient(#E4EAA2, #9CD672)");
		  
		  // 2nd PANE 
		  
		  welText= new Text("Gate Access Programs");
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
			label1.setOnMouseClicked(e -> {
				try {
					new Users().start(new Stage());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			});
			
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
			label2.setOnMouseClicked(e -> {
				try {
					new ChangePassword().start(new Stage());
				} catch (Exception e1) {
					ErrorMessage.display("Launch Error", "Program has problems");
					e1.printStackTrace();
				}
			});
			
			Image lo = new Image(ExDetails.class.getResourceAsStream("/pic3/logout-1-512.png"));
			ImageView ivlo= new ImageView(lo);
			label21= new Label("", ivlo);	
			label21.setText("\n\n\n\nLogout");
			label21.setAlignment(Pos.CENTER_LEFT);
			label21.setCursor(Cursor.HAND);
			label21.setPadding(new Insets(1,1,1,3));
			label21.setMinWidth(120);
			label21.setMinHeight(120);
			rotateHer(label21,ivlo);
			label21.setOnMouseClicked(e -> {
				try {
					
					String fileUpdate="UPDATE logs SET name= '" + lname.getText()
		           			+ "', signedout ='" +myclock.getText()+"'"   
		                     + "where signedin='" + Login.d1 + "'";
		        //connect to database
					DBConnect.connect();
					try {
						DBConnect.stmt.execute(fileUpdate);
					} catch (SQLException e1) {
						ErrorMessage.display("Auto Logs Error", ""+e1.getMessage());
						e1.printStackTrace();
					}
					window.close();
					new Login().start(new Stage());
					Login.txtname.setText(lname.getText());
				} catch (Exception e1) {
					ErrorMessage.display("Launch Error", "Something wrong happened when starting program\n consult Admin");
					e1.printStackTrace();
				}
			});
			
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
			
			Image rgs = new Image(ExDetails.class.getResourceAsStream("/pic/myfinger.png"));
			ImageView ivrgs= new ImageView(rgs);
			lgsearch= new Label(" ", ivrgs);
			lgsearch.setText("\n\n\n\nAuto Search...");
			lgsearch.setAlignment(Pos.CENTER_LEFT);
			lgsearch.setCursor(Cursor.HAND);
			lgsearch.setPadding(new Insets(1,1,1,5));
			lgsearch.setMinWidth(220);
			lgsearch.setMinHeight(120);
			rotateHer(lgsearch, ivrgs);
		
			lgsearch.setOnMouseClicked( e -> {
				try {
					 new PersonalReports().start(new Stage());
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
			label5.setOnMouseClicked(e -> {
				try {
					new AllAttendance().start(new Stage());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			});
			
			Image dr = new Image(ExDetails.class.getResourceAsStream("/pic2/dreport.png"));
			ImageView ivdr= new ImageView(dr);
			label51= new Label("",ivdr);
			label51.setText("\n\n\n\nDaily Report");
			label51.setAlignment(Pos.CENTER_LEFT);
			label51.setCursor(Cursor.HAND);
			label51.setPadding(new Insets(1,1,1,5));
			label51.setMinWidth(240);
			label51.setMinHeight(120);
			rotateHer(label51, ivdr);
			
			Image mr = new Image(ExDetails.class.getResourceAsStream("/pic2/mreport.png"));
			ImageView ivmr= new ImageView(mr);
			label52= new Label("", ivmr);
			label52.setText("\n\n\n\nMonth Report");
			label52.setAlignment(Pos.CENTER_LEFT);
			label52.setCursor(Cursor.HAND);
			label52.setPadding(new Insets(1,1,1,5));
			label52.setMinWidth(240);
			label52.setMinHeight(120);
			rotateHer(label52, ivmr);
			
			Image vr = new Image(ExDetails.class.getResourceAsStream("/pic2/vreport.png"));
			ImageView ivvr= new ImageView(vr);
			label53= new Label(" ",ivvr);
			label53.setText("\n\n\n\n Visitor Reports");
			label53.setAlignment(Pos.CENTER_LEFT);
			label53.setCursor(Cursor.HAND);
			label53.setPadding(new Insets(1,1,1,5));
			label53.setMinWidth(240);
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
			
			label6.setOnMouseClicked(e ->{
				try {
					new Help().start(new Stage());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			});
			
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
			lay31.getChildren().addAll(lgsearch, label53, label54);
			
			VBox lay3all= new VBox(7);
			lay3all.setPadding(new Insets(10,10,10,0));
			lay3all.getChildren().addAll(lay3, lay31);
			
			HBox lay4= new HBox(7);
			lay4.setPadding(new Insets(10,10,10,0));
			lay4.getChildren().addAll(label6, label61);
			
			layall= new VBox(5);
			layall.setId("scroll_main");
			layall.setPadding(new Insets(5,10,10,15));
			layall.getChildren().addAll(lay,appuser, lay1, register,lay2, entries, lay3all, moreinfo, lay4);
			
			
			//office pane
			
			Image ioreg  = new Image(ExDetails.class.getResourceAsStream("/pic2/User.png"));
			ImageView ivoreg= new ImageView(ioreg);
			loreg= new Label(" ", ivoreg);	
			loreg.setText("\n\n\n\n Register");
			loreg.setAlignment(Pos.CENTER_LEFT);
			loreg.setCursor(Cursor.HAND);
			loreg.setPadding(new Insets(1,1,1,5));
			loreg.setMinWidth(240);
			loreg.setMinHeight(120);
			rotateHer(loreg,ivoreg);
			
			Image ioexit  = new Image(ExDetails.class.getResourceAsStream("/pic2/logout.png"));
			ImageView ivoexit= new ImageView(ioexit);
			loexit= new Label(" ", ivoexit);	
			loexit.setText("\n\n\n\n Exit");
			loexit.setAlignment(Pos.CENTER_LEFT);
			loexit.setCursor(Cursor.HAND);
			loexit.setPadding(new Insets(1,1,1,5));
			loexit.setMinWidth(240);
			loexit.setMinHeight(120);
			rotateHer(loexit,ivoexit);
			
			Image ioatt  = new Image(ExDetails.class.getResourceAsStream("/pic/attendance.png"));
			ImageView ivoatt= new ImageView(ioatt);
			loatt= new Label("", ivoatt);	
			loatt.setText("\n\n\n\nContinuous entry");
			loatt.setAlignment(Pos.CENTER_LEFT);
			loatt.setCursor(Cursor.HAND);
			loatt.setPadding(new Insets(1,1,1,5));
			loatt.setMinWidth(240);
			loatt.setMinHeight(120);
			rotateHer(loatt,ivoatt);
			
			Image igenrep  = new Image(ExDetails.class.getResourceAsStream("/pic2/dreport.png"));
			ImageView ivogenrep= new ImageView(igenrep);
			ldaygen= new Label(" ", ivogenrep);	
			ldaygen.setText("\n\n\n\n Daily Reports");
			ldaygen.setAlignment(Pos.CENTER_LEFT);
			ldaygen.setCursor(Cursor.HAND);
			ldaygen.setPadding(new Insets(1,1,1,5));
			ldaygen.setMinWidth(240);
			ldaygen.setMinHeight(120);
			rotateHer(ldaygen,ivogenrep);
			
			Image isrep  = new Image(ExDetails.class.getResourceAsStream("/pic2/mreport.png"));
			ImageView ivsrep= new ImageView(isrep);
			lmongen= new Label(" ", ivsrep);	
			lmongen.setText("\n\n\n\n Month\nReports");
			lmongen.setAlignment(Pos.CENTER_LEFT);
			lmongen.setCursor(Cursor.HAND);
			lmongen.setPadding(new Insets(1,1,1,5));
			lmongen.setMinWidth(120);
			lmongen.setMinHeight(120);
			rotateHer(lmongen,ivsrep);
			
			Image imrep  = new Image(ExDetails.class.getResourceAsStream("/pic2/sreport.png"));
			ImageView ivmrep= new ImageView(imrep);
			lspege= new Label(" ", ivmrep);	
			lspege.setText("\n\n\n\n Special Reports");
			lspege.setAlignment(Pos.CENTER_LEFT);
			lspege.setCursor(Cursor.HAND);
			lspege.setPadding(new Insets(1,1,1,5));
			lspege.setMinWidth(240);
			lspege.setMinHeight(120);
			rotateHer(lspege,ivmrep);
			
			Image ros = new Image(ExDetails.class.getResourceAsStream("/pic/myfinger.png"));
			ImageView ivos= new ImageView(ros);
			losearch= new Label(" ", ivos);
			losearch.setText("\n\n\n\nSearch User");
			losearch.setAlignment(Pos.CENTER_LEFT);
			losearch.setCursor(Cursor.HAND);
			losearch.setPadding(new Insets(1,1,1,5));
			losearch.setMinWidth(240);
			losearch.setMinHeight(120);
			rotateHer(losearch, ivos);
		
			losearch.setOnMouseClicked( e -> {
				try {
					new OfficePersonalReport().start(new Stage());
					//window.close();
				} catch (Exception e1) {				
					e1.printStackTrace();
				}
			});
			
			
			Image ioview  = new Image(ExDetails.class.getResourceAsStream("/pic2/viewr.png"));
			ImageView ivoview= new ImageView(ioview);
			loview= new Label(" ", ivoview);	
			loview.setText("\n\n\n\n View Reports");
			loview.setAlignment(Pos.CENTER_LEFT);
			loview.setCursor(Cursor.HAND);
			loview.setPadding(new Insets(1,1,1,5));
			loview.setMinWidth(240);
			loview.setMinHeight(120);
			rotateHer(loview,ivoview);
			
			 weloText= new Text("Office Utilities");
			weloText.setId("welText");
				
			reports= new Text("Reports");
			registration= new Text("Registration");
			exito= new Text("More (S & E)");
			
			//  loview logenreports;
			
			HBox laywel= new HBox(10);
			laywel.setPadding(new Insets(5,10,10,0));
			laywel.getChildren().addAll(weloText);
			
			HBox lay11= new HBox(7);
			lay11.getChildren().addAll(loreg, loatt, ldaygen);
			lay11.setPadding(new Insets(10,10,10,0));
			
			HBox lay12= new HBox(7);
			lay12.getChildren().addAll(loview, lmongen, lspege);
			lay12.setPadding(new Insets(10,10,10,0));
			
			HBox lay13= new HBox(7);
			lay13.getChildren().addAll(losearch, loexit);
			lay13.setPadding(new Insets(10,10,10,0));
			
			layoffice= new VBox(7);
			layoffice.setPadding(new Insets(10,10,10,20));
			layoffice.getChildren().addAll(laywel, registration, lay11, reports, lay12, exito, lay13);
			layoffice.setId("scroll_main");
			
			container.getChildren().addAll(bottomPane, layall, layoffice);
			setAnimation();
			sc.setContent(container);
			sc.setId("scroll_main");
			root.setStyle("-fx-background-color: linear-gradient(#E4EAA2, #9CD672)");
			root.getChildren().addAll(getActionPane(),sc);
		  
		 }
	 
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	 private void setAnimation(){
		  // Initially hiding the Top Pane
		  clipRect = new Rectangle();
		        clipRect.setWidth(boxBounds.getWidth());
		  clipRect.setHeight(0);
		  clipRect.translateYProperty().set(boxBounds.getWidth());
		  layall.setClip(clipRect);
		  layall.translateYProperty().set(-boxBounds.getWidth());
		  
		  layoffice.translateYProperty().set(-boxBounds.getWidth());
		  
		  // Animation for bouncing effect.
		  final Timeline timelineBounce = new Timeline();
		  timelineBounce.setCycleCount(2);
		  timelineBounce.setAutoReverse(true);
		  final KeyValue kv1 = new KeyValue(clipRect.heightProperty(), (boxBounds.getHeight()-15));
		  final KeyValue kv2 = new KeyValue(clipRect.translateYProperty(), 15);
		  final KeyValue kv3 = new KeyValue(layall.translateYProperty(), -15);
		  final KeyFrame kf1 = new KeyFrame(Duration.millis(100), kv1, kv2, kv3);
		  timelineBounce.getKeyFrames().add(kf1);
		  
		  // Event handler to call bouncing effect after the scroll down is finished.
		  EventHandler onFinished = new EventHandler() {
					@Override
					public void handle(Event event) {
						timelineBounce.play();
					}
		        };
		
		        
		        timelineDown = new Timeline();
		        timelineUp = new Timeline();
		        timelineoffice= new Timeline();
		       
		        // Animation for scroll down.
		  timelineDown.setCycleCount(1);
		  timelineDown.setAutoReverse(true);
		  final KeyValue kvDwn1 = new KeyValue(clipRect.heightProperty(), boxBounds.getWidth());
		  final KeyValue kvDwn2 = new KeyValue(clipRect.translateYProperty(), 0);
		  final KeyValue kvDwn3 = new KeyValue(layall.translateYProperty(), 0);
		  final KeyValue kvDwn4 = new KeyValue(layoffice.translateYProperty(), -boxBounds.getHeight()-190);
		  final KeyFrame kfDwn = new KeyFrame(Duration.millis(1000), onFinished, kvDwn1, kvDwn2, kvDwn3, kvDwn4);
		  timelineDown.getKeyFrames().add(kfDwn);
		  
		  // Animation for scroll up.
		  timelineUp.setCycleCount(1); 
		  timelineUp.setAutoReverse(true);
		  final KeyValue kvUp1 = new KeyValue(clipRect.heightProperty(), 0);
		  final KeyValue kvUp2 = new KeyValue(clipRect.translateYProperty(), boxBounds.getHeight());
		  final KeyValue kvUp3 = new KeyValue(layall.translateYProperty(), -boxBounds.getHeight()-190);
		  final KeyValue kvUp4 = new KeyValue(layoffice.translateYProperty(), -boxBounds.getHeight()-190);
		  final KeyFrame kfUp = new KeyFrame(Duration.millis(1000), kvUp1, kvUp2, kvUp3, kvUp4);
		  timelineUp.getKeyFrames().add(kfUp);
		  
		  //Animation for the scrollside
		  timelineoffice.setCycleCount(1);
		  timelineoffice.setAutoReverse(true);
		  final KeyValue kvside1 = new KeyValue(clipRect.heightProperty(), boxBounds.getWidth());
		  final KeyValue kvside2 = new KeyValue(clipRect.translateYProperty(), 0);
		  final KeyValue kvside3 = new KeyValue(layoffice.translateYProperty(), 0);
		  final KeyValue kvside4 = new KeyValue(layall.translateYProperty(), -boxBounds.getHeight());
		  final KeyFrame kfside = new KeyFrame(Duration.millis(1000), kvside1, kvside2, kvside3, kvside4);
		  timelineoffice.getKeyFrames().add(kfside);
		  
		 }
		 
		 private VBox getActionPane(){
			 Image pic = new Image(ExDetails.class.getResourceAsStream("/pic/anon.png"));
				ImageView ivpic= new ImageView(pic);
				 ivpic.setFitWidth(40);
				ivpic.setFitHeight(40);
				lname= new Label();
				lname.setText(Login.txtname.getText());
				lname.setGraphic(ivpic);
				lname.setMinWidth(200);
				
				ladmins= new Text();
				lname.setId("ladmins");
				ResultSet rs= null;
				String query = "SELECT username, level from login WHERE username= '" + lname.getText() + "'";
		        DBConnect.connect();
		        try {
		            rs = DBConnect.stmt.executeQuery(query);
		            if (rs.next())
		            {	
		            	if (rs.getString("username").equals(lname.getText().toString())) {
		            	ladmins.setText(rs.getString("level"));
		            	}
		            }
		        
		        }
		        catch (Exception e) {
					e.printStackTrace();
				}
				
		        
				freq= new Text("Frequently Used");
				
				Image us = new Image(ExDetails.class.getResourceAsStream("/pic2/User.png"));
				ImageView ivus= new ImageView(us);
				ivus.setFitWidth(40);
				ivus.setFitHeight(40);
				lreg= new Label("Add New Users...");
				lreg.setGraphic(ivus);
				lreg.setMinWidth(200);
				//lreg.setMinHeight(40);
				lreg.setOnMouseClicked(e -> {
					try {
						new Users().start(new Stage());
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				});
				
				Image re = new Image(ExDetails.class.getResourceAsStream("/pic2/reg.png"));
				ImageView ivre= new ImageView(re);
				ivre.setFitWidth(40);
				ivre.setFitHeight(40);
				lusers= new Label("Register...");
				lusers.setGraphic(ivre);
				lusers.setMinWidth(200);
				lusers.setOnMouseClicked(e ->{
					try {
						new AddNew().start(new Stage());
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				});
				Image att = new Image(ExDetails.class.getResourceAsStream("/pic2/att.png"));
				ImageView ivatt= new ImageView(att);
				ivatt.setFitWidth(40);
				ivatt.setFitHeight(40);
				latt=new Label("Continuous Entry...");
				latt.setGraphic(ivatt);
				latt.setMinWidth(200);
				latt.setOnMouseClicked(e -> {
					try {
						new AllAttendance().start(new Stage());
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				});
				
				Image apps = new Image(ExDetails.class.getResourceAsStream("/pic/menu.png"));
				ImageView ivapp= new ImageView(apps);
				ivapp.setFitWidth(40);
				ivapp.setFitHeight(40);
				lallapps=new Label("Gate Programs>>");
				lallapps.setGraphic(ivapp);
				lallapps.setMinWidth(200);
				
				Image oapps = new Image(ExDetails.class.getResourceAsStream("/pic/offp.png"));
				ImageView ivoapp= new ImageView(oapps);
				ivoapp.setFitWidth(40);
				ivoapp.setFitHeight(40);
				loallapps=new Label("Office Utilities>>");
				loallapps.setGraphic(ivoapp);
				loallapps.setMinWidth(200);
				
				Image set = new Image(ExDetails.class.getResourceAsStream("/pic2/Settings.png"));
				ImageView ivset= new ImageView(set);
				ivset.setFitWidth(40);
				ivset.setFitHeight(40);
				lsetting= new Label("System Setting...");
				lsetting.setGraphic(ivset);
				lsetting.setMinWidth(200);
				
				lsetting.setOnMouseClicked(e -> {
					try {
						new SysSettings().start(new Stage());
					} catch (Exception e1) {
						ErrorMessage.display("Launch Error", "Failure to launch program");
						e1.printStackTrace();
					}
				});
				
				Image ex = new Image(ExDetails.class.getResourceAsStream("/pic2/logout.png"));
				ImageView ivex= new ImageView(ex);
				ivex.setFitWidth(40);
				ivex.setFitHeight(40);
				lpower= new Label("Exit System");
				lpower.setGraphic(ivex);
				lpower.setMinWidth(200);
				
				lpower.setOnMouseClicked(e -> {
					
					String fileUpdate="UPDATE logs SET name= '" + lname.getText()
		           			+ "', signedout ='" +myclock.getText()+"'"   
		                     + "where signedin='" + Login.d1 + "'";
		        //connect to database
					DBConnect.connect();
					try {
						DBConnect.stmt.execute(fileUpdate);
					} catch (SQLException e1) {
						ErrorMessage.display("Auto Logs Error", ""+e1.getMessage());
						e1.printStackTrace();
					}
					
					window.close();
				});
				
				loreg.setOnMouseClicked(e -> {
					try {
						new AddOfficeStaffs().start(new Stage());
					} catch (Exception e1) {
						ErrorMessage.display("Launch Error", "Failure to launch program");
						e1.printStackTrace();
					}
				});
				Image hel = new Image(ExDetails.class.getResourceAsStream("/pic2/Help.png"));
				ImageView ivhe= new ImageView(hel);
				ivhe.setFitWidth(40);
				ivhe.setFitHeight(40);
				lhelp= new Label("Help...");
				lhelp.setGraphic(ivhe);
				lhelp.setMinWidth(200);
				lhelp.setOnMouseClicked(e ->{
					try {
						new Help().start(new Stage());
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				});
				systems= new Text("System");
				
				VBox lusad= new VBox(5);
				lusad.getChildren().addAll(lname, ladmins);
				
				VBox stop=new VBox(20);
				stop.setAlignment(Pos.TOP_LEFT);
				stop.getChildren().addAll(lusad, freq, lreg, lusers, latt, lallapps, loallapps);
				
				VBox sbottom=new VBox(20);
				sbottom.setAlignment(Pos.BOTTOM_LEFT);
				sbottom.getChildren().addAll(systems, lhelp, lsetting, lpower);
				
				VBox side= new VBox(80);
				side.setPadding(new Insets(23,20, 27, 20));
				side.setAlignment(Pos.TOP_LEFT);
				side.getChildren().addAll(stop, sbottom);
				side.setStyle("-fx-background-color: linear-gradient(#E4E9A9, #9CD777);");
				//side.setStyle("-fx-background-color:#9CD567");
				
				lname.setId("forSide"); 
				lallapps.setId("forSide"); 
				loallapps.setId("forSide"); 
				lreg.setId("forSide"); 
				lpower.setId("forSide"); 
				lusers.setId("forSide"); 
				lhelp.setId("forSide"); 
				latt.setId("forSide"); 
				lsetting.setId("forSide"); 
				
				VBox layall= new VBox();
				
				lallapps.setOnMouseClicked(e -> {
					timelineDown.play();
			
				});
				
				loallapps.setOnMouseClicked(e -> {
					timelineoffice.play();
			
				});
				
				label22.setOnMouseClicked(e -> {
					timelineUp.play();
			
				});
				loexit.setOnMouseClicked(e -> {
					timelineUp.play();
			
				});
				label51.setOnMouseClicked(e -> {
					try {
						new DailyReports().start(new Stage());
					} catch (Exception e1) {
						ErrorMessage.display("Launch Error","Application launch failure");
						e1.printStackTrace();
					}
				});
				label52.setOnMouseClicked(e -> {
					try {
						new MonthlyGenerator().start(new Stage());
					} catch (Exception e1) {
						ErrorMessage.display("Launch Error","Application launch failure");
						e1.printStackTrace();
					}
				});
				label4.setOnMouseClicked(e -> {
					try {
						new ChooseOne().start(new Stage());
					} catch (Exception e1) {
						ErrorMessage.display("Launch Error","Application launch failure");
						e1.printStackTrace();
					}
				});
				label53.setOnMouseClicked(e -> {
					try {
						new VisitorsReports().start(new Stage());
					} catch (Exception e1) {
						ErrorMessage.display("Launch Error","Application launch failure");
						e1.printStackTrace();
					}
				});
				
				loatt.setOnMouseClicked(e -> {
					try {
						new Office_Entry().start(new Stage());
					} catch (Exception e1) {
						ErrorMessage.display("Launch Error","Application launch failure");
						e1.printStackTrace();
					}
				});
				
				label54.setOnMouseClicked(e -> {
					try {
						new SpecialReports().start(new Stage());
					} catch (Exception e1) {
						ErrorMessage.display("Launch Error","Application launch failure");
						e1.printStackTrace();
					}
				});
				loview.setOnMouseClicked(e -> {
					try {
						new OfficeRecords().start(new Stage());
					} catch (Exception e1) {
						ErrorMessage.display("Launch Error","Application launch failure");
						e1.printStackTrace();
					}
				});
				
				lspege.setOnMouseClicked(e -> {
					try {
						new OfficeSpecials().start(new Stage());
					} catch (Exception e1) {
						ErrorMessage.display("Launch Error","Application launch failure");
						e1.printStackTrace();
					}
				});
				ldaygen.setOnMouseClicked(e -> {
					try {
						new DailyReportso().start(new Stage());
					} catch (Exception e1) {
						ErrorMessage.display("Launch Error","Application launch failure");
						e1.printStackTrace();
					}
				});
				
				lmongen.setOnMouseClicked(e -> {
					try {
						new MonthlyGeneratoro().start(new Stage());
					} catch (Exception e1) {
						ErrorMessage.display("Launch Error","Application launch failure");
						e1.printStackTrace();
					}
				});
				
				label61.setOnMouseClicked(e -> {
					try {
						new About().start(new Stage());
					} catch (Exception e1) {
						ErrorMessage.display("Launch Error", "Program launch error");
						e1.printStackTrace();
					}
				});
				 if(ladmins.getText().equals("User"))
			        {
					 	lreg.setVisible(false);
					 	label1.setVisible(false);
			        	lusers.setVisible(false);
			        	label3.setVisible(false);
			        	loatt.setVisible(false);
						loreg.setVisible(false);
						label3.setVisible(false);
						label5.setVisible(false);
						losearch.setVisible(false);
						lgsearch.setVisible(false);
						latt.setVisible(false);
			        }
				 if(ladmins.getText().equals("Gate Admin"))
				 {
					 lreg.setVisible(false);
					 label1.setVisible(false);
					 loatt.setVisible(false);
					 loreg.setVisible(false);
					 losearch.setVisible(false);
					 
				 }
				 if(ladmins.getText().equals("Office Admin"))
				 {
					 lreg.setVisible(false);
					 label1.setVisible(false);
					 label3.setVisible(false);
					 label5.setVisible(false);
					 lgsearch.setVisible(false);
				 }
				 
				layall.getChildren().add(side);
				layall.setPadding(new Insets(0,0, 10, 0));
				layall.setMinWidth(250);
				return layall;
		 }
	
	public static void main(String[] args) {
		  launch(args);
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
	private void closeProgram()
	{
		boolean result = Confirmation.display("Exit Program", " Are you sure you want to exit the \n program? ");
		if(result)
		{
			String fileUpdate="UPDATE logs SET name= '" + lname.getText()
           			+ "', signedout ='" +myclock.getText()+"'"   
                     + "where signedin='" + Login.d1 + "'";
        //connect to database
			DBConnect.connect();
			try {
				DBConnect.stmt.execute(fileUpdate);
			} catch (SQLException e1) {
				ErrorMessage.display("Auto Logs Error", ""+e1.getMessage());
				e1.printStackTrace();
			}
			System.exit(0);
			//window.close();
		}
	}
}


