package dialogs;

import backup.Back;
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

public class ComplexConfirm extends Application {

    static boolean answer;

    static Image ICON = new Image(ComplexConfirm.class.getResourceAsStream("/pic2/Help.png"));

    public static boolean display(String title, String message) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.getIcons().add(new Image("/pic/slogo.png"));
        window.setTitle(title);
        Label label = new Label();
        label.setText(message);
        label.setStyle("-fx-font-size:14px;");

        ImageView imageView = new ImageView(ICON);
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        Label labelimage = new Label("", imageView);
        // two buttons
        Button yesbtn = new Button("Yes");
        yesbtn.setId("red");
        Button nobtn = new Button("No");
        nobtn.setId("blue");
        Button ynbtn = new Button("Backup");
        ynbtn.setId("green");
        yesbtn.setOnAction(e -> {
            answer = true;
            SuccessMessage.display("Success", "Your tables have been cleared");
            window.close();
        });

        nobtn.setOnAction(e -> {
            answer = false;
            window.close();
        });

        ynbtn.setOnAction(e -> {
            try {
                window.close();
                new Back().start(new Stage());
            } catch (Exception e1) {
                ErrorMessage.display("Launch Error", e1.getMessage() + " /nError occured during launching application");
                e1.printStackTrace();
            }
        });

        HBox hbox = new HBox(10);
        hbox.setAlignment(Pos.CENTER_RIGHT);
        hbox.setPadding(new Insets(0, 5, 0, 5));
        hbox.getChildren().addAll(yesbtn, ynbtn, nobtn);

        HBox layout = new HBox(5);
        layout.setPadding(new Insets(10, 5, 10, 5));
        layout.getChildren().addAll(labelimage, label);

        VBox layout2 = new VBox(10);
        layout2.setPadding(new Insets(10, 5, 10, 5));
        layout2.getChildren().addAll(layout, hbox);
        layout2.setStyle("-fx-background-color: linear-gradient(#E4EAA2, #9CD672);");
        Scene scene = new Scene(layout2);
        window.setScene(scene);
        window.setResizable(false);
        scene.getStylesheets().add(ComplexConfirm.class.getResource("confirm.css").toExternalForm());
        window.showAndWait();

        return answer;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage arg0) throws Exception {

    }

}
