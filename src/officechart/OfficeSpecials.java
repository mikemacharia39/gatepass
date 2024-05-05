package officechart;

import dialogs.ErrorMessage;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class OfficeSpecials extends Application {

    Stage window;
    Scene scene;
    Label labelbar, labelpie;
    Text text1, text2;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage s) throws Exception {

        window = s;
        window = new Stage(StageStyle.UTILITY);
        window.getIcons().add(new Image("/pic/slogo.png"));
        window.setResizable(false);
        window.setTitle("Chart Reports");
        window.initModality(Modality.APPLICATION_MODAL);
        Image img1 = new Image(OfficeSpecials.class.getResourceAsStream("/pic/Bar-chart.png"));
        ImageView ivimg1 = new ImageView(img1);
        labelbar = new Label("", ivimg1);
        labelbar.setMinWidth(130);
        labelbar.setMinHeight(130);
        labelbar.setAlignment(Pos.CENTER);
        labelbar.setOnMouseClicked(e -> {
            try {
                new Officebar().start(new Stage());

            } catch (Exception e1) {
                ErrorMessage.display("Launch Error", "Some error occured when launching application");
                e1.printStackTrace();
            }
        });
        text2 = new Text("Click to View Graphs");
        text2.setFont(Font.font("Anadus", 19));
        Image img3 = new Image(OfficeSpecials.class.getResourceAsStream("/pic/pie_chart.png"));
        ImageView ivimg3 = new ImageView(img3);
        ivimg3.setFitWidth(90);
        ivimg3.setFitHeight(90);
        labelpie = new Label("", ivimg3);
        labelpie.setMinWidth(130);
        labelpie.setMinHeight(130);
        labelpie.setAlignment(Pos.CENTER);
        labelpie.setOnMouseClicked(e -> {
            try {
                new OfficePie().start(new Stage());
            } catch (Exception e1) {
                ErrorMessage.display("Launch Error", "Some error occured when launching application");
                e1.printStackTrace();
            }
        });


        text1 = new Text("Office-Access Graph Representations");
        text1.setId("myText");
        VBox lay1 = new VBox(15);
        lay1.getChildren().addAll(text2, labelbar, labelpie);
        lay1.setAlignment(Pos.CENTER);

        HBox laybac = new HBox();
        laybac.getChildren().addAll(text1);
        laybac.setAlignment(Pos.CENTER_RIGHT);

        HBox lay1acc = new HBox(55);
        lay1acc.getChildren().addAll(lay1, laybac);
        lay1acc.setPadding(new Insets(10, 10, 10, 10));

        scene = new Scene(lay1acc, 1050, 500);
        window.setScene(scene);
        scene.getStylesheets().add(OfficeSpecials.class.getResource("special.css").toExternalForm());
        window.setX(290);
        window.setY(30);
        window.show();
    }
}
