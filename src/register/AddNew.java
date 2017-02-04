package register;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class AddNew extends Application{

	Scene scene;
	Stage window;
	Label lreg, lsel;
	Button btclose, btnnext;
	RadioButton rbStudent, rbLec, rbOthers, rbVisitor;//rbothers -- subordinate staff --stoked
	@Override
	public void start(Stage s) throws Exception {
	
		window= s;
		window= new Stage(StageStyle.UNDECORATED);
		window.initModality(Modality.APPLICATION_MODAL);
		window.getIcons().add(new Image("/pic/slogo.png"));
		//window.setAlwaysOnTop(true);
		lreg= new Label("Register a New Person");
		lreg.setId("uppername");
		
		lsel= new Label("Select the criteria you want to use to register new persons.");
		lsel.setFont(Font.font("Calibri", 15));
		
		rbStudent= new RadioButton("New Students");
		
		rbLec= new RadioButton("New Lecturers");
		rbOthers= new RadioButton("New Sub-ordinate staff");
		
		rbVisitor= new RadioButton("New Visitor");
		
		ToggleGroup tg= new ToggleGroup();
		rbStudent.setToggleGroup(tg);
		rbLec.setToggleGroup(tg);
		rbOthers.setToggleGroup(tg);
		rbVisitor.setToggleGroup(tg);
		
		btclose=new Button("Close");
		btclose.setOnAction(e -> {
			window.close();
		});
		Tooltip tp = new Tooltip("Select an option from the above choices...");
		Tooltip.install(btnnext, tp);
		
		btnnext= new Button("Next");
		//btnnext.setStyle("-fx-background-color:#4994DE; -fx-text-fill:white; -fx-font-size:16; -fx-font-family: Calibri;");
		btnnext.setOnAction(e ->{
			if(rbStudent.isSelected())
			{
				try {
					new NewStudent().start(new Stage());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			if(rbLec.isSelected())
			{
				try {
					new NewLecturer().start(new Stage());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			if(rbOthers.isSelected())
			{
				try {
					new NewSub().start(new Stage());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			if(rbVisitor.isSelected())
			{
				try {
					new NewVisitor().start(new Stage());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		HBox layb= new HBox(10);
		layb.getChildren().addAll(btnnext, btclose);
		layb.setPadding(new Insets(10, 20, 0, 0));
		layb.setAlignment(Pos.CENTER_RIGHT);
		
		VBox layreg= new VBox(20);
		layreg.getChildren().addAll(lreg, lsel);
		layreg.setAlignment(Pos.CENTER);
		
		VBox layrb= new VBox(10);
		layrb.getChildren().addAll(rbStudent, rbLec, rbOthers, rbVisitor, layb);
		layrb.setPadding(new Insets(30, 0, 0, 20));
		
		VBox layall= new VBox(10);
		layall.getChildren().addAll(layreg, layrb);
		layall.setPadding(new Insets(20, 30, 10, 30));
		layall.setStyle("-fx-background-color: linear-gradient(#E4E9A9, #9CD777);");
		scene= new Scene(layall, 550, 290);
		scene.getStylesheets().add(NewStudent.class.getResource("newPeople.css").toExternalForm());
		window.setScene(scene);
		window.show();
	}

	public static void main(String[] args)
	{
		launch(args);
	}
}
