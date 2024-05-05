package reports;

import database.DBConnect;
import dialogs.Confirmation;
import dialogs.ErrorMessage;
import dialogs.SuccessMessage;
import editEntiry.EditLec;
import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.MainWindow;
import org.controlsfx.control.TextFields;

import java.sql.ResultSet;
import java.sql.SQLException;


public class LecturersReports extends Application {

    public static TableView<Reports> tableView;
    public static Label lstudname;
    ObservableList<Reports> att = FXCollections.observableArrayList();
    TableColumn<Reports, String> idCol, fnameCol, lnamecol, admCol, depCol, phoneCol, addressCol, townCol, prefCol;
    TextField txtsearch;
    Button btnedit, btndelete;
    Text textname;
    Scene scene;
    Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @SuppressWarnings("unchecked")
    private void init(Stage primaryStage) {
        Group root = new Group();
        scene = new Scene(root);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(VisitorsReports.class.getResource("att.css").toExternalForm());
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(12));
        tableView = new TableView<Reports>();
        Button button = new Button("Refresh");
        button.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent t) {
                tableView.setItems(getAtt());
            }

        });

        btnedit = new Button("Edit");
        btnedit.setOnAction(e -> {
            editClicked();
        });

        btndelete = new Button("Delete");
        btndelete.setOnAction(e -> {
            deleteClicked();
        });
        lstudname = new Label();

        textname = new Text("ALL LECTURERS REPORTS");
        textname.setFont(Font.font("Andalus", FontPosture.REGULAR, 20));


        idCol = new TableColumn<>();
        idCol.setText("Id");
        idCol.setMinWidth(110);
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        fnameCol = new TableColumn<>();
        fnameCol.setText("First Name");
        fnameCol.setMinWidth(150);
        fnameCol.setCellValueFactory(new PropertyValueFactory<>("fname"));

        lnamecol = new TableColumn<>();
        lnamecol.setText("Last Name");
        lnamecol.setMinWidth(150);
        lnamecol.setCellValueFactory(new PropertyValueFactory<>("lname"));

        admCol = new TableColumn<>();
        admCol.setText("Staff Id");
        admCol.setMinWidth(130);
        admCol.setCellValueFactory(new PropertyValueFactory<>("adm"));

        depCol = new TableColumn<>();
        depCol.setText("Department");
        depCol.setMinWidth(170);
        depCol.setCellValueFactory(new PropertyValueFactory<>("depart"));

        addressCol = new TableColumn<>();
        addressCol.setText("Address");
        addressCol.setMinWidth(150);
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));

        townCol = new TableColumn<>();
        townCol.setText("Town");
        townCol.setMinWidth(130);
        townCol.setCellValueFactory(new PropertyValueFactory<>("town"));

        phoneCol = new TableColumn<>();
        phoneCol.setText("Phone");
        phoneCol.setMinWidth(120);
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));

        prefCol = new TableColumn<>();
        prefCol.setText("Prefix");
        prefCol.setMinWidth(50);
        prefCol.setCellValueFactory(new PropertyValueFactory<>("prefix"));

        tableView.getColumns().addAll(prefCol, fnameCol, lnamecol, admCol, idCol, depCol, phoneCol, addressCol, townCol);
        tableView.setItems(getAtt());

        tableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPersonDetails(newValue));

        txtsearch = new TextField();
        initFilter();
        att = getAtt();
        tableView.setItems(FXCollections.observableArrayList(att));

        HBox layt = new HBox();
        layt.getChildren().add(textname);
        layt.setAlignment(Pos.CENTER);

        HBox layre = new HBox(300);
        layre.getChildren().addAll(txtsearch, lstudname);
        layre.setAlignment(Pos.TOP_LEFT);

        HBox laybtns = new HBox(10);
        laybtns.getChildren().addAll(button, btnedit, btndelete);
        vbox.getChildren().addAll(layt, layre, tableView, laybtns);
        vbox.setId("vb");
        StackPane stack = new StackPane();
        stack.getChildren().addAll(vbox);

        root.getChildren().add(stack);
    }

    /**
     * A service for getting the Reports data. This service exposes an
     * ObservableList for convenience when using the service. This
     * <code>results</code> list is final, though its contents are replaced when
     * a service call successfully concludes.
     */

    public ObservableList<Reports> getAtt() {
        ObservableList<Reports> sales = FXCollections.observableArrayList();
        String sql = "SELECT * FROM  lecturers ORDER BY thumb_id DESC";
        try {
            DBConnect.connect();
            ResultSet rec = DBConnect.stmt.executeQuery(sql);
            while ((rec != null) && (rec.next())) {
                //first_name	last_name	adm	id	phone	address	town	dob	thumb_id	student	picture

                String prefix = (rec.getString("prefix"));
                String id = (rec.getString("n_id"));
                String fname = (rec.getString("first_name"));
                String lname = (rec.getString("last_name"));
                String sid = (rec.getString("s_id"));
                String address = (rec.getString("address"));
                String town = (rec.getString("town"));
                String phone = (rec.getString("phone"));
                String depart = (rec.getString("department"));


                sales.add(new Reports(prefix, fname, lname, sid, id, depart, phone, address, town));
            }
            rec.close();
        } catch (SQLException ea) {
            ErrorMessage.display("Launching Error", ea.getMessage() + "Database Communications Link Failure");
            ea.printStackTrace();
        } catch (Exception e) {
            ErrorMessage.display("Launching Error", e.getMessage());
            e.printStackTrace();
        }

        return sales;
    }

    @Override
    public void start(Stage primaryStage1) throws Exception {
        primaryStage = primaryStage1;
        primaryStage = new Stage();
        init(primaryStage);
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.getIcons().add(new Image("/pic/slogo.png"));
        primaryStage.setX(137);
        primaryStage.setY(27);
        primaryStage.show();

        if (MainWindow.ladmins.getText().equals("User") || MainWindow.ladmins.getText().equals("Office Admin")) {
            btndelete.setDisable(true);
            btnedit.setDisable(true);
        }
    }

    private void initFilter() {
        txtsearch = TextFields.createSearchField();
        txtsearch.setStyle("-fx-background-radius:10;");
        txtsearch.setPromptText("Search The Records");
        txtsearch.setMinWidth(200);
        txtsearch.textProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable o) {
                if (txtsearch.textProperty().get().isEmpty()) {
                    tableView.setItems(att);
                    return;
                }
                ObservableList<Reports> tableItems = FXCollections.observableArrayList();
                ObservableList<TableColumn<Reports, ?>> cols = tableView.getColumns();
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
                tableView.setItems(tableItems);
            }
        });
    }

    private void showPersonDetails(Reports person) {
        if (person != null) {
            // Fill the labels with info from the person object.
            lstudname.setText(person.getId());
        } else {
            // Person is null, remove all the text.
            lstudname.setText("");
        }
    }

    public void editClicked() {
        if (lstudname.getText().equals("")) {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Lecturer Selected");
            alert.setContentText("Please select a Lecturer in the list.");
            alert.showAndWait();
        } else {
            try {
                new EditLec().start(new Stage());
                EditLec.txtid.setText(lstudname.getText());
            } catch (Exception e) {
                ErrorMessage.display("Launch Error", e.getMessage() + " Application Launch Error");
                e.printStackTrace();
            }
        }
    }

    public void deleteClicked() {
        if (lstudname.getText().equals("")) {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Lecturer Selected");
            alert.setContentText("Please select a Lecturer in the list.");
            alert.showAndWait();
        } else {
            boolean result = Confirmation.display("Delete Record", " Are you sure you want to delete this \n Lecturer? ");
            if (result) {
                ObservableList<Reports> selectedProd, allProd;
                allProd = tableView.getItems();
                selectedProd = tableView.getSelectionModel().getSelectedItems();
                //selectedProd.forEach(allProd:: remove);

                //the delete query is now complete  ------------------>
                String query = "DELETE FROM Lecturers where s_id = '" + lstudname.getText() + "'";
                //connect to database
                DBConnect.connect();
                try {
                    DBConnect.stmt.execute(query);
                    selectedProd.forEach(allProd::remove);
                    SuccessMessage.display("Success", "Lecturer has been deleted successfully");
                    DBConnect.closeConnection();
                } catch (Exception ea) {
                    ErrorMessage.display("Launching Error", ea.getMessage() + " Error has occurred. Consult administrator");
                }
            }
        }
    }

    public class Reports {

        String prefix;
        String id;
        String fname;
        String lname;
        String adm;
        String depart;
        String address;
        String town;
        String phone;

        public Reports() {

            this.prefix = "";
            this.id = "";
            this.fname = "";
            this.lname = "";
            this.adm = "";
            this.address = "";
            this.town = "";
            this.phone = "";
        }

        public Reports(String prefix, String fname, String lname, String adm, String id, String depart, String phone, String address, String town) {

            this.prefix = prefix;
            this.id = id;
            this.fname = fname;
            this.lname = lname;
            this.adm = adm;
            this.depart = depart;
            this.address = address;
            this.town = town;
            this.phone = phone;

        }

        public String getPrefix() {
            return prefix;
        }

        public void setPrefix(String prefix) {
            this.prefix = prefix;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFname() {
            return fname;
        }

        public void setFname(String fname) {
            this.fname = fname;
        }

        public String getLname() {
            return lname;
        }

        public void setLname(String lname) {
            this.lname = lname;
        }

        public String getAdm() {
            return adm;
        }

        public void setAdm(String adm) {
            this.adm = adm;
        }

        public String getDepart() {
            return depart;
        }

        public void setDepart(String depart) {
            this.depart = depart;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getTown() {
            return town;
        }

        public void setTown(String town) {
            this.town = town;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

    }
}
