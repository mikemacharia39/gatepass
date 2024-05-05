package gatereports;

import database.DBConnect;
import dialogs.ErrorMessage;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.controlsfx.control.TextFields;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class DailyGenerated extends Application {

    final Calendar cal = Calendar.getInstance();
    TableView<Reports> table;
    TableColumn<Reports, String> namecol, admcol, timecol, datecol, typecol;
    Stage stage;
    Scene scene;
    Label lname, lclock, ltitle;
    TextField txtsearch;
    Menu filem;
    MenuItem iprint, iexport, iclose;
    DateFormat format = DateFormat.getInstance();
    VBox myPrint;
    HBox lay1, lay3;
    ObservableList<Reports> att = FXCollections.observableArrayList();
    Image img = new Image(DailyGenerated.class.getResourceAsStream("/pic/slogo.png"));
    ImageView imgv = new ImageView(img);

    public static void main(String[] args) {
        launch(args);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void start(Stage manual) throws Exception {

        stage = manual;
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.getIcons().add(new Image("/pic/slogo.png"));
        stage.setTitle("Bio-Access");
        lname = new Label("", imgv);
        lname.setText("Biometric Security Gate Access Records");
        lname.setFont(Font.font("Calibri", 20));
        ltitle = new Label("Daily Generated Gate Access Reports From " + DailyReports.txtdate.getValue().toString() + " To "
                + DailyReports.txtdate1.getValue().toString());
        ltitle.setFont(Font.font("Calibri", 20));
        lclock = new Label();
        lclock.setFont(Font.font("Calibri", 20));
        final Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                lclock.setText(DateFormat.getDateTimeInstance().format(new Date()));
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        txtsearch = new TextField();
        initFilter();

        table = new TableView<>();
        filem = new Menu("File");
        iprint = new MenuItem("Print");

        iexport = new MenuItem("Export to Excel...");
        iclose = new MenuItem("Close");
        iclose.setOnAction(e -> {
            stage.close();
        });
        filem.getItems().addAll(iprint, new SeparatorMenuItem(), iclose);
        MenuBar bar = new MenuBar();
        bar.getMenus().add(filem);

        typecol = new TableColumn<>("Type");
        typecol.setMinWidth(130);
        typecol.setCellValueFactory(new PropertyValueFactory<>("type"));

        namecol = new TableColumn<>("First Name");
        namecol.setMinWidth(230);
        namecol.setCellValueFactory(new PropertyValueFactory<>("name"));

        admcol = new TableColumn<>("School Number");
        admcol.setMinWidth(240);
        admcol.setCellValueFactory(new PropertyValueFactory<>("adm"));

        timecol = new TableColumn<>("Time");
        timecol.setMinWidth(170);
        timecol.setCellValueFactory(new PropertyValueFactory<>("timein"));

        datecol = new TableColumn<>("Date");
        datecol.setMinWidth(220);
        datecol.setCellValueFactory(new PropertyValueFactory<>("date"));

        table.getColumns().addAll(namecol, admcol, typecol, timecol, datecol);
        table.setItems(getAtt());
        att = getAtt();
        table.setItems(FXCollections.observableArrayList(att));

        lay1 = new HBox(400);
        lay1.getChildren().addAll(lname, lclock);
        lay1.setPadding(new Insets(10, 10, 0, 10));
        HBox lay2 = new HBox(5);
        lay2.getChildren().addAll(txtsearch);
        lay2.setAlignment(Pos.CENTER);

        lay3 = new HBox(10);
        lay3.getChildren().addAll(ltitle);
        lay3.setAlignment(Pos.CENTER);

        myPrint = new VBox(10);
        myPrint.getChildren().addAll(lay1, lay3, table);

        VBox layall = new VBox(10);
        layall.getChildren().addAll(myPrint, lay2);
        //layall.setPadding(new Insets(0,0,10,0));
        //layall.setId("hboxcolor1");
        iprint.setOnAction(e -> {
            print();
        });
        BorderPane laymall = new BorderPane();
        laymall.setTop(bar);
        laymall.setCenter(layall);

        scene = new Scene(laymall, 1000, 520);
        scene.getStylesheets().add(DailyReports.class.getResource("reports.css").toExternalForm());
        stage.setX(270);
        stage.setY(28);
        stage.setScene(scene);
        stage.showAndWait();

    }

    public ObservableList<Reports> getAtt() {
        ObservableList<Reports> list = FXCollections.observableArrayList();
        String sql = "SELECT first_name, school_id, type, time_in, date FROM  gateentry"
                + " WHERE date BETWEEN '" + DailyReports.txtdate.getValue().toString() + "'"
                + " AND '" + DailyReports.txtdate1.getValue().toString() + "'";
        try {
            DBConnect.connect();
            ResultSet rec = DBConnect.stmt.executeQuery(sql);
            while ((rec != null) && (rec.next())) {
                String name = (rec.getString("first_name"));
                String adm = (rec.getString("school_id"));
                String type = (rec.getString("type"));
                String timein = (rec.getString("time_in"));
                String date = (rec.getString("date"));

                list.add(new Reports(name, adm, type, timein, date));
            }
            rec.close();
        } catch (SQLException ea) {
            ErrorMessage.display("Launching Error", ea.getMessage() + "Database Communications Link Failure");
            ea.printStackTrace();
        } catch (Exception e) {
            ErrorMessage.display("Launching Error", e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public void print() {
        Printer printer = Printer.getDefaultPrinter();
        Stage stage = new Stage(StageStyle.DECORATED);
        PrinterJob job = PrinterJob.createPrinterJob(printer);
        if (job != null) {
            boolean showDialog = job.showPageSetupDialog(stage);
            if (showDialog) {
                myPrint.setScaleX(0.46);
                myPrint.setScaleY(0.46);
                myPrint.setTranslateX(-252);
                myPrint.setTranslateY(-110);
                boolean success = job.printPage(myPrint);
                if (success) {
                    job.endJob();
                }
                myPrint.setTranslateX(0);
                myPrint.setTranslateY(0);
                myPrint.setScaleX(1.0);
                myPrint.setScaleY(1.0);
            }
        }
    }

    private void initFilter() {
        txtsearch = TextFields.createSearchField();
        txtsearch.setPromptText("Search The Records");
        txtsearch.setMinWidth(378);
        txtsearch.textProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable o) {
                if (txtsearch.textProperty().get().isEmpty()) {
                    table.setItems(att);
                    return;
                }
                ObservableList<Reports> tableItems = FXCollections.observableArrayList();
                ObservableList<TableColumn<Reports, ?>> cols = table.getColumns();
                for (int i = 0; i < att.size(); i++) {

                    for (int j = 0; j < cols.size(); j++) {
                        TableColumn<Reports, ?> col = cols.get(j);
                        String cellValue = col.getCellData(att.get(i)).toString();
                        cellValue = cellValue.toLowerCase();
                        if (cellValue.contains(txtsearch.textProperty().get().toLowerCase())) {
                            tableItems.add(att.get(i));
                            break;
                        }
                    }

                }
                table.setItems(tableItems);
            }
        });
    }

    public void btnExportClicked() {
    }
}

