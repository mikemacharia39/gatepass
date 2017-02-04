package dialogs;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.geometry.Insets;

public class Confirmation extends Application{
	
	static boolean answer;
	
	static Image ICON = new Image(Confirmation.class.getResourceAsStream("/pic2/Help.png"));
	
	public static boolean display(String title, String message)
	{
		Stage window= new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.getIcons().add(new Image("/pic/slogo.png"));
		window.setTitle(title);
		//window.setAlwaysOnTop(true);
		Label label= new Label();
		label.setText(message);
		label.setStyle("-fx-font-size:14px;");
		label.setAlignment(Pos.CENTER_LEFT);
		
		ImageView imageView = new ImageView(ICON);
		imageView.setFitWidth(35);
		imageView.setFitHeight(35);
        Label labelimage = new Label("",imageView);
		// two buttons
		Button yesbtn= new Button("Yes");
		yesbtn.setId("red");
		Button nobtn= new Button("No");
		nobtn.setId("green");
		
		yesbtn.setOnAction(e -> {
			answer= true;
			window.close();
		});
		
		nobtn.setOnAction(e -> {
			answer= false;
			window.close();
		});
		
		HBox hbox= new HBox(10);
		hbox.setAlignment(Pos.CENTER_RIGHT);
		hbox.setPadding(new Insets(0,5,0,5));
		hbox.getChildren().addAll(yesbtn, nobtn);
		
		HBox layout= new HBox(5);
		layout.setPadding(new Insets(10,5,10,5));
		layout.getChildren().addAll(labelimage,label);
		
		VBox layout2= new VBox(10);
		layout2.setPadding(new Insets(10,10,10,10));
		layout2.getChildren().addAll(layout, hbox);
		layout2.setStyle("-fx-background-color: linear-gradient(#E4EAA2, #9CD672);");
		Scene scene= new Scene(layout2);
		scene.getStylesheets().add(Confirmation.class.getResource("confirm.css").toExternalForm());
		window.setScene(scene);
		window.setResizable(false);
		window.showAndWait();
		
		return answer;
	}
	public static void main(String [] args)
	{
		launch(args);
	}

	@Override
	public void start(Stage arg0) throws Exception {
		
	}

}
