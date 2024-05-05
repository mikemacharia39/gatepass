package infos;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class About extends Application {

    Stage window;
    Scene scene;
    Label llogo, llogo2;
    Text title, message, message2, title2, title3;
    Button btnClose = new Button("Close");
    TabPane tabPane1;
    Tab tab1, tab2;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage s) throws Exception {

        window = s;
        window = new Stage();
        window.setTitle("About");
        window.setResizable(false);
        window.initModality(Modality.APPLICATION_MODAL);
        window.getIcons().add(new Image("/pic/slogo.png"));
        Image img = new Image(About.class.getResourceAsStream("/pic/meru_logo.png"));
        ImageView ivlogo = new ImageView(img);
        llogo = new Label("", ivlogo);

        title = new Text("Bio-Access version 1.0.0");
        title.setFont(Font.font("Andalus", 21));
        message = new Text("Bio-Access is a Desktop Application meant to be a means of managing and securing the\n"
                + "university's premises by putting control equipment to areas where most people try to\n"
                + "access or areas where important doucuments are kept.\n\n"
                + "Currently the program is under major construction and improvement.\n\n"
                + "The program is developed using JavaFX.\n\n"
                + "Developer: Mikehenry Macharia\n\n"
                + "(C) mikehenry");

        message.setTextAlignment(TextAlignment.CENTER);

        Image img2 = new Image(About.class.getResourceAsStream("/pic/meru_logo.png"));
        ImageView ivlogo2 = new ImageView(img2);
        llogo2 = new Label("", ivlogo2);

        title3 = new Text("Top Financers");
        Text title4 = new Text("Supervisors");

        title2 = new Text("I would like to acknowledge the following contributors:");
        title2.setFont(Font.font("Times New Roman", 16));
        message2 = new Text("Programming\n"
                + "------------------\n"
                + "Kenneth Gatobu\n"
                + "Derrick Njeru\nJames Karuri\n"
                + "John Invictus\nJeremy Mutua\n"
                + "Zack Mwangi\n\n"
                + title3.getText() + "\n"
                + "-------------------\n"
                + "Almighty God\n"
                + "My Father - Samuel Maina\n\n"
                + title4.getText() + "\n"
                + "-------------------\n"
                + "Mr. Kibaara\n"
                + "Mr. Wycliff Ronoh\n\n");

        message2.setTextAlignment(TextAlignment.CENTER);

        tabPane1 = new TabPane();
        tab1 = new Tab("About");
        tab2 = new Tab("Credits");

        tabPane1.getTabs().addAll(tab1, tab2);

        VBox lay1 = new VBox(10);
        lay1.getChildren().addAll(llogo, title);
        lay1.setAlignment(Pos.TOP_CENTER);
        lay1.setPadding(new Insets(10, 20, 0, 20));
        VBox lay2 = new VBox(10);
        lay2.getChildren().addAll(message);
        lay2.setAlignment(Pos.TOP_CENTER);
        lay2.setPadding(new Insets(10, 20, 0, 20));
        HBox laybtm = new HBox();
        laybtm.getChildren().add(btnClose);
        laybtm.setStyle("-fx-background-color:#dddddd;");
        laybtm.setPadding(new Insets(10, 10, 10, 10));
        laybtm.setAlignment(Pos.CENTER_RIGHT);
        VBox layall = new VBox(10);
        layall.getChildren().addAll(lay1, lay2);
        tab1.setContent(layall);
        tab1.setClosable(false);

        VBox layt1 = new VBox(10);
        layt1.getChildren().addAll(llogo2, title2);
        layt1.setAlignment(Pos.TOP_CENTER);
        layt1.setPadding(new Insets(10, 20, 0, 75));
        VBox layt2 = new VBox(10);
        layt2.getChildren().addAll(message2);
        layt2.setAlignment(Pos.TOP_CENTER);
        layt2.setPadding(new Insets(10, 0, 0, 40));

        VBox laytall = new VBox(10);
        laytall.getChildren().addAll(layt1, layt2);
        laytall.setAlignment(Pos.CENTER);
        tab2.setContent(new ScrollPane(laytall));
        tab2.setClosable(false);

        VBox laywhole = new VBox(10);
        laywhole.getChildren().addAll(tabPane1, laybtm);

        scene = new Scene(laywhole, 500, 400);
        scene.getStylesheets().add(About.class.getResource("info.css").toExternalForm());
        window.setScene(scene);
        window.show();

        btnClose.setOnMouseClicked(e -> {
            window.close();
        });
    }
}
