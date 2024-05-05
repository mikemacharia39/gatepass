package dialogs;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SuccessMessage extends Application {

    static boolean answer;
    static Image ICON = new Image(SuccessMessage.class.getResourceAsStream("/pic/Green Tick 2.png"));

    public static void display(String title, String message) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setAlwaysOnTop(true);
        window.getIcons().add(new Image("/pic/slogo.png"));
        window.setTitle(title);
        Label label = new Label();
        label.setText(message);
        label.setStyle("-fx-font-size:14px;");

        ImageView imageView = new ImageView(ICON);
        imageView.setFitWidth(40);
        imageView.setFitHeight(40);
        Label labelimage = new Label("", imageView);

        // two buttons
        Button okbtn = new Button("Ok");
        okbtn.setOnAction(e -> {
            answer = false;
            window.close();
        });
        okbtn.setId("blue");
        HBox hbox = new HBox(10);
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setPadding(new Insets(10, 5, 10, 5));
        hbox.getChildren().addAll(labelimage, label);
        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER_RIGHT);
        layout.setPadding(new Insets(10, 5, 10, 5));
        layout.getChildren().addAll(hbox, okbtn);
        layout.setStyle("-fx-background-color: linear-gradient(#E4EAA2, #9CD672);");
        Scene scene = new Scene(layout);
        window.setScene(scene);
        scene.getStylesheets().add(SuccessMessage.class.getResource("confirm.css").toExternalForm());
        window.setResizable(false);
        window.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage arg0) throws Exception {
        display("", "");
    }

}
