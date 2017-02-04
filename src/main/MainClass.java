package main;

import dialogs.Confirmation;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class MainClass extends Application{

	Stage window;
	Scene scene;
	Label lname, lallapps, lreg, lpower, lusers, lhelp, latt, lsetting;
	Text freq, systems;
	
	@Override
	public void start(Stage stage) throws Exception {
		
		
		window= stage;
		window= new Stage();
		window.setOnCloseRequest(e -> {
			e.consume();
			closeProgram();
			});
		
		Image pic = new Image(ExDetails.class.getResourceAsStream("/pic/anon.png"));
		ImageView ivpic= new ImageView(pic);
		 ivpic.setFitWidth(40);
		ivpic.setFitHeight(40);
		lname= new Label("-Mikehenry Macharia-");
		lname.setGraphic(ivpic);
		lname.setMinWidth(200);
		
		freq= new Text("Frequently Used");
		
		Image us = new Image(ExDetails.class.getResourceAsStream("/pic2/User.png"));
		ImageView ivus= new ImageView(us);
		ivus.setFitWidth(40);
		ivus.setFitHeight(40);
		lreg= new Label("Add New Users...");
		lreg.setGraphic(ivus);
		lreg.setMinWidth(200);
		//lreg.setMinHeight(40);
		
		Image re = new Image(ExDetails.class.getResourceAsStream("/pic2/reg.png"));
		ImageView ivre= new ImageView(re);
		ivre.setFitWidth(40);
		ivre.setFitHeight(40);
		lusers= new Label("Register...");
		lusers.setGraphic(ivre);
		lusers.setMinWidth(200);
		
		Image att = new Image(ExDetails.class.getResourceAsStream("/pic2/att.png"));
		ImageView ivatt= new ImageView(att);
		ivatt.setFitWidth(40);
		ivatt.setFitHeight(40);
		latt=new Label("Continuous Attendance...");
		latt.setGraphic(ivatt);
		latt.setMinWidth(200);
		
		Image apps = new Image(ExDetails.class.getResourceAsStream("/pic2/Folder.png"));
		ImageView ivapp= new ImageView(apps);
		ivapp.setFitWidth(37);
		ivapp.setFitHeight(42);
		lallapps=new Label("All Programs >>");
		lallapps.setGraphic(ivapp);
		lallapps.setMinWidth(200);
		
		
		
		lallapps.setOnMouseClicked(e ->
		{
			try {
				new ExDetails().start(new Stage());
			} catch (Exception e1) {
				e1.printStackTrace();
			} 
		});
		
		
		Image set = new Image(ExDetails.class.getResourceAsStream("/pic2/Settings.png"));
		ImageView ivset= new ImageView(set);
		ivset.setFitWidth(40);
		ivset.setFitHeight(40);
		lsetting= new Label("System Setting...");
		lsetting.setGraphic(ivset);
		lsetting.setMinWidth(200);
		
		Image ex = new Image(ExDetails.class.getResourceAsStream("/pic2/logout.png"));
		ImageView ivex= new ImageView(ex);
		ivex.setFitWidth(40);
		ivex.setFitHeight(40);
		lpower= new Label("Exit System");
		lpower.setGraphic(ivex);
		lpower.setMinWidth(200);
		
		Image hel = new Image(ExDetails.class.getResourceAsStream("/pic2/Help.png"));
		ImageView ivhe= new ImageView(hel);
		ivhe.setFitWidth(40);
		ivhe.setFitHeight(40);
		lhelp= new Label("Help...");
		lhelp.setGraphic(ivhe);
		lhelp.setMinWidth(200);
		
		systems= new Text("System");
		
		VBox stop=new VBox(20);
		stop.setAlignment(Pos.TOP_LEFT);
		stop.getChildren().addAll(lname, freq, lreg, lusers, latt, lallapps);
		
		VBox sbottom=new VBox(20);
		sbottom.setAlignment(Pos.BOTTOM_LEFT);
		sbottom.getChildren().addAll(systems, lhelp, lsetting, lpower);
		
		
		VBox side= new VBox(150);
		side.setPadding(new Insets(10,20, 10, 20));
		side.setAlignment(Pos.TOP_LEFT);
		side.getChildren().addAll(stop, sbottom);
		side.setStyle("-fx-background-color: linear-gradient(#E4EAA2, #9CD672);");
		
		HBox layall= new HBox();
		layall.getChildren().add(side);
		scene= new Scene(layall, 1000,650);
		window.setScene(scene);
		scene.getStylesheets().add(MainClass.class.getResource("mainclass.css").toExternalForm());
		window.setMaximized(true);
		window.show();
	}
	private void closeProgram()
	{
		boolean result = Confirmation.display("Exit Program", " Are you sure you want to exit the \n program? ");
		if(result)
		{
			System.exit(0);
			//window.close();
		}
	}
	public static void main(String [] args)
	{
		launch(args);
	}
}
