package officeReports;

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
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
import javafx.util.Duration;
import org.controlsfx.control.TextFields;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class MonthlyReports extends Application {

    final Calendar cal = Calendar.getInstance();
    TableView<Reports> table;
    TableColumn<Reports, String> namecol, admcol, datecol, unitcol;
    Stage stage;
    Scene scene;
    Label lname, lclock, ltitle;
    TextField txtsearch;
    Button btnsearch;
    Menu filem;
    MenuItem iprint, iexport, iclose;
    DateFormat format = DateFormat.getInstance();
    ObservableList<Reports> att = FXCollections.observableArrayList();
    Image img = new Image(MonthlyReports.class.getResourceAsStream("/pic/slogo.png"));
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
        stage.getIcons().add(new Image("/pic/slogo.png"));
        stage.setTitle("Office Access Monthly Reports");
        lname = new Label("", imgv);
        lname.setText("Biometric Security Office Access Records");
        lname.setFont(Font.font("Calibri", 20));
        ltitle = new Label("Generated Office Access Report of The "
                + MonthlyGeneratoro.cdate.getValue() + " Month of the Year " + MonthlyGeneratoro.cyear.getValue());
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
        btnsearch = new Button("Search");

        filem = new Menu("File");
        iprint = new MenuItem("Print");
        iexport = new MenuItem("Export to Excel...");
        iclose = new MenuItem("Close");
        iclose.setOnAction(e -> {
            stage.close();
        });
        filem.getItems().addAll(iprint, iexport, new SeparatorMenuItem(), iclose);
        MenuBar bar = new MenuBar();
        bar.getMenus().add(filem);

        namecol = new TableColumn<>("First Name");
        namecol.setMinWidth(230);
        namecol.setCellValueFactory(new PropertyValueFactory<>("name"));

        admcol = new TableColumn<>("University Id");
        admcol.setMinWidth(220);
        admcol.setCellValueFactory(new PropertyValueFactory<>("adm"));

        datecol = new TableColumn<>("Time In");
        datecol.setMinWidth(220);
        datecol.setCellValueFactory(new PropertyValueFactory<>("timein"));

        unitcol = new TableColumn<>("Date");
        unitcol.setMinWidth(200);
        unitcol.setCellValueFactory(new PropertyValueFactory<>("date"));

        table.getColumns().addAll(namecol, admcol, datecol, unitcol);
        table.setItems(getAtt());
        att = getAtt();
        table.setItems(FXCollections.observableArrayList(att));

        HBox lay1 = new HBox(340);
        lay1.getChildren().addAll(lname, lclock);
        lay1.setPadding(new Insets(10, 10, 0, 10));
        HBox lay2 = new HBox(5);
        lay2.getChildren().addAll(txtsearch, btnsearch);
        lay2.setAlignment(Pos.CENTER);

        HBox lay3 = new HBox(10);
        lay3.getChildren().addAll(ltitle);
        lay3.setAlignment(Pos.CENTER);

        VBox layall = new VBox(10);
        layall.getChildren().addAll(lay1, lay3, lay2, table);
        layall.setPadding(new Insets(0, 0, 10, 0));

        BorderPane laymall = new BorderPane();
        laymall.setTop(bar);
        laymall.setCenter(layall);

        scene = new Scene(laymall, 1000, 525);
        stage.setScene(scene);
        stage.setX(270);
        stage.setY(28);
        stage.showAndWait();

    }

    public ObservableList<Reports> getAtt() {
        ObservableList<Reports> list = FXCollections.observableArrayList();
        String sql = "SELECT first_name, staff_id, time_in, date FROM  officeentry"
                + " WHERE date BETWEEN '" + MonthlyGeneratoro.cyear.getValue() + "-''" + MonthlyGeneratoro.cdate.getValue() + "-1'"
                + " AND '" + MonthlyGeneratoro.cyear.getValue() + "-''" + MonthlyGeneratoro.cdate.getValue() + "-31'";
        try {
            DBConnect.connect();
            ResultSet rec = DBConnect.stmt.executeQuery(sql);
            while ((rec != null) && (rec.next())) {
                String name = (rec.getString("first_name"));
                String adm = (rec.getString("staff_id"));
                String time = (rec.getString("time_in"));
                String date = (rec.getString("date"));

                list.add(new Reports(name, adm, time, date));
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

