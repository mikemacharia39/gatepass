package gatereports;

import dialogs.ErrorMessage;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
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
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DailyReports extends Application {

    static DatePicker txtdate;
    static DatePicker txtdate1;
    private final String pattern = "yyyy-MM-dd";
    Stage window;
    Scene scene;
    Label ltitle, lfrom, lto;
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
        ltitle = new Label("Daily Gate Entry Records");
        ltitle.setStyle("-fx-text-fill:#FFFFFF;");
        ltitle.setFont(Font.font("Calibri", 22));
        lfrom = new Label("From Date");
        txtdate = new DatePicker();
        StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
            final DateTimeFormatter dateFormatter =
                    DateTimeFormatter.ofPattern(pattern);

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        };
        txtdate.setConverter(converter);
        txtdate.setPromptText(pattern.toLowerCase());
        txtdate.setValue(LocalDate.now());

        lto = new Label("To Date");
        txtdate1 = new DatePicker();
        txtdate1.setConverter(converter);
        txtdate1.setPromptText(pattern.toLowerCase());
        txtdate1.setValue(LocalDate.now());

        btngen = new Button("Generate");
        btngen.setOnAction(e -> {
            try {
                new DailyGenerated().start(new Stage());
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
        lay1.setPrefSize(200, 70);

        HBox lay2 = new HBox(10);
        lay2.getChildren().addAll(lfrom, txtdate, lto, txtdate1);
        lay2.setPadding(new Insets(0, 10, 0, 10));
        lay2.setAlignment(Pos.CENTER);

        HBox lay3 = new HBox(10);
        lay3.getChildren().addAll(btngen, btncancel);
        lay3.setPadding(new Insets(0, 10, 0, 10));
        lay3.setAlignment(Pos.CENTER_RIGHT);
        lay3.setId("hboxcolor1");
        lay3.setPrefSize(200, 30);

        VBox layall = new VBox(69);
        layall.getChildren().addAll(lay1, lay2, lay3);
        scene = new Scene(layall, 510, 260);
        scene.getStylesheets().add(DailyReports.class.getResource("reports.css").toExternalForm());
        window.setScene(scene);
        window.setResizable(false);
        window.show();
    }
}
