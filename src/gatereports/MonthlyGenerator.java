package gatereports;

import dialogs.ErrorMessage;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MonthlyGenerator extends Application {

    static ChoiceBox<String> cdate;
    static ComboBox<String> cyear;
    Stage window;
    Scene scene;
    Label ltitle, lm, lyear;
    Button btngen, btncancel;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage dr) throws Exception {

        dr = window;
        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.getIcons().add(new Image("/pic/slogo.png"));
        ltitle = new Label("Total Monthly Attendance");
        ltitle.setFont(Font.font("Calibri", 22));
        lm = new Label("Select Month");
        lm.setFont(Font.font("Calibri", 14));
        cdate = new ChoiceBox<>();
        cdate.getItems().addAll("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12");
        cdate.setValue("1");

        lyear = new Label("Select Year");
        cyear = new ComboBox<String>();
        cyear.getItems().addAll("2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025");
        cyear.setEditable(true);
        cyear.setValue("2015");
        lyear.setFont(Font.font("Calibri", 14));

        btngen = new Button("Generate");
        btngen.setOnAction(e -> {
            try {
                new MonthlyReports().start(new Stage());
            } catch (Exception e1) {
                ErrorMessage.display("Launch Error", e1.getMessage() + " \nError launching Application");
                e1.printStackTrace();
            }
        });
        btncancel = new Button("Close");
        btncancel.setOnAction(e -> {
            window.close();
        });

        HBox lay1 = new HBox();
        lay1.getChildren().add(ltitle);
        lay1.setAlignment(Pos.CENTER_LEFT);
        lay1.setPadding(new Insets(0, 10, 0, 10));
        lay1.setBorder(new Border(new BorderStroke(Color.LIGHTGREY, null, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        //lay1.setStyle("-fx-background-color:#00aaff; -fx-background-radius:1;");
        lay1.setId("hboxcolor1");
        lay1.setPrefSize(200, 40);

        HBox lay2 = new HBox(10);
        lay2.getChildren().addAll(lm, cdate, lyear, cyear);
        lay2.setPadding(new Insets(0, 10, 0, 10));
        lay2.setAlignment(Pos.CENTER);

        HBox lay3 = new HBox(10);
        lay3.getChildren().addAll(btngen, btncancel);
        lay3.setPadding(new Insets(0, 10, 0, 10));
        lay3.setAlignment(Pos.CENTER_RIGHT);
        // lay3.setStyle("-fx-background-color:#00aaff; -fx-background-radius:1;");
        lay3.setPrefSize(200, 30);
        lay3.setId("hboxcolor1");

        VBox layall = new VBox(69);
        layall.getChildren().addAll(lay1, lay2, lay3);

        scene = new Scene(layall, 510, 250);
        scene.getStylesheets().add(DailyReports.class.getResource("reports.css").toExternalForm());
        window.setScene(scene);
        window.setResizable(false);
        window.show();
    }
}
