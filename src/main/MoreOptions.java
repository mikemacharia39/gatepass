package main;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MoreOptions extends Application {

    Stage window;
    Scene scene;
    ListView<String> lv;
    Button btnselect, button;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        window = stage;
        window = new Stage(StageStyle.TRANSPARENT);
        window.initModality(Modality.APPLICATION_MODAL);
        lv = new ListView<String>();
        lv.getItems().addAll("Daily Reports", "Monthly Reports", "Special Reports");
        lv.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);


        button = new Button("Close");

        button.setOnAction(e -> {
            window.close();
        });

        btnselect = new Button("Open ");
        btnselect.setOnAction(e -> {
            if (lv.getSelectionModel().getSelectedItem().equals("Daily Reports")) {
                btnselect.setText("Daily Reports");
                System.out.println("Daily Reports");
            }
            if (lv.getSelectionModel().getSelectedItem().equals("Monthly Reports")) {
                btnselect.setText("Monthly Reports");
                System.out.println("Monthly Reports");
            }
            if (lv.getSelectionModel().getSelectedItem().equals("Special Reports")) {
                btnselect.setText("Special Reports");
                System.out.println("Special Reports");
            } else {
                btnselect.setText("Nothing selected");
            }
        });

        lv.setOnMouseClicked(e -> {
            if (lv.getSelectionModel().getSelectedItem().equals("Daily Reports")) {
                btnselect.setText("Daily Reports");
                System.out.println("Daily Reports");
            }
            if (lv.getSelectionModel().getSelectedItem().equals("Monthly Reports")) {
                btnselect.setText("Monthly Reports");
                System.out.println("Monthly Reports");
            }
            if (lv.getSelectionModel().getSelectedItem().equals("Special Reports")) {
                btnselect.setText("Special Reports");
                System.out.println("Special Reports");
            }
        });
        button.setOnAction(e -> {
            window.close();
        });
        HBox lay1 = new HBox();
        lay1.getChildren().add(lv);
        HBox laybtn = new HBox(50);
        laybtn.getChildren().addAll(btnselect, button);
        laybtn.setAlignment(Pos.BOTTOM_LEFT);
        VBox layall = new VBox(3);
        layall.getChildren().addAll(lay1, laybtn);
        scene = new Scene(layall, 250, 150);
        scene.getStylesheets().add(ExDetails.class.getResource("options.css").toExternalForm());
        window.setScene(scene);
        window.setX(700);
        window.setY(300);
        window.show();
    }
}
