package office;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;

import main.MainWindow;

import org.controlsfx.control.TextFields;

import database.DBConnect;
import dialogs.Confirmation;
import dialogs.ErrorMessage;
import dialogs.SuccessMessage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;


public class OfficeRecords extends Application {

	ObservableList<Reports> att = FXCollections.observableArrayList();
    TableColumn<Reports, String> idCol, fnameCol,	lnamecol,	sidCol,phoneCol, prefCol;
    public static TableView<Reports> tableView;
    TextField txtsearch;
    Button btnedit, btndelete;
    Text textname;
    Scene scene;
    public static Label lstudname;
    
    @SuppressWarnings("unchecked")
	private void init(Stage primaryStage) {
        Group root = new Group();
        scene= new Scene(root);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(OfficeRecords.class.getResource("att.css").toExternalForm());
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
        
        textname= new Text("OFFICE RECORDS");
        textname.setFont(Font.font("Andalus", FontPosture.REGULAR, 20));
        
        
        idCol = new TableColumn<>();
        idCol.setText("Id");
        idCol.setMinWidth(140);
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        
        fnameCol = new TableColumn<>();
        fnameCol.setText("First Name");
        fnameCol.setMinWidth(160);
        fnameCol.setCellValueFactory(new PropertyValueFactory<>("fname"));
        
        lnamecol = new TableColumn<>();
        lnamecol.setText("Last Name");
        lnamecol.setMinWidth(160);
        lnamecol.setCellValueFactory(new PropertyValueFactory<>("lname"));
        
        sidCol = new TableColumn<>();
        sidCol.setText("Staff Id");
        sidCol.setMinWidth(130);
        sidCol.setCellValueFactory(new PropertyValueFactory<>("adm"));
        
        phoneCol = new TableColumn<>();
        phoneCol.setText("Phone");
        phoneCol.setMinWidth(120);
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        
        prefCol = new TableColumn<>();
        prefCol.setText("Prefix");
        prefCol.setMinWidth(70);
        prefCol.setCellValueFactory(new PropertyValueFactory<>("prefix"));
        
        tableView.getColumns().addAll(prefCol, fnameCol,	lnamecol,	sidCol, idCol, phoneCol);
        tableView.setItems(getAtt());
        
        tableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPersonDetails(newValue));
        
        txtsearch= new TextField();
		initFilter();
		att=getAtt();
        tableView.setItems(FXCollections.observableArrayList(att));

        HBox layt= new HBox();
        layt.getChildren().add(textname);
        layt.setAlignment(Pos.CENTER);
        
        HBox layre= new HBox(300);
        layre.getChildren().addAll(txtsearch, lstudname);
        layre.setAlignment(Pos.TOP_LEFT);
        
        HBox laybtns= new HBox(10);
        laybtns.getChildren().addAll(button, btnedit, btndelete);
        vbox.getChildren().addAll(layt, layre, tableView, laybtns);
        vbox.setId("vb");
        StackPane stack = new StackPane();
        stack.getChildren().addAll(vbox);

        root.getChildren().add(stack);
        
        if(MainWindow.ladmins.getText().equals("User") || MainWindow.ladmins.getText().equals("Gate Admin"))
 		{
 			btndelete.setDisable(true);
 			btnedit.setDisable(true);
 		}
    }

    /**
     * A service for getting the Reports data. This service exposes an
     * ObservableList for convenience when using the service. This
     * <code>results</code> list is final, though its contents are replaced when
     * a service call successfully concludes.
     */

    public ObservableList<Reports> getAtt()
	{
            ObservableList<Reports> sales = FXCollections.observableArrayList();
            String sql="SELECT * FROM  office ORDER BY thumb_id DESC";
            			try {
            		    	DBConnect.connect();
            				ResultSet rec = DBConnect.stmt.executeQuery(sql);
            				while((rec!=null) && (rec.next()))
            				{ 
            					
            					String prefix = (rec.getString("prefix"));
            					String id = (rec.getString("staff_id"));
            					String fname = (rec.getString("first_name"));
            					String lname = (rec.getString("last_name"));
            					String sid = (rec.getString("n_id"));
            					String phone = (rec.getString("phone"));
            					sales.add(new Reports(prefix, fname,lname, sid,id,phone));
            				}
            				rec.close();
            				} catch (SQLException ea) {
            				ErrorMessage.display("Launching Error", ea.getMessage()+ "Database Communications Link Failure");
            				ea.printStackTrace();
            				}
            			catch (Exception e) {
            				ErrorMessage.display("Launching Error", e.getMessage());
            				e.printStackTrace();
            				}
            
            return sales;
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

		public Reports() {
        	
			this.prefix="";
        	this.id="";
            this.fname="";
            this.lname="";
            this.adm="";
            this.phone="";
        }

        public Reports(String prefix, String fname, String lname, String id, String adm,String phone) {
        	
        	this.prefix=prefix;
        	this.id=id;
            this.fname=fname;
            this.lname=lname;
            this.adm=adm;
            this.phone=phone;
        	
        }
   
    }

    @Override public void start(Stage primaryStage) throws Exception {
        init(primaryStage);
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.getIcons().add(new Image("/pic/slogo.png"));
        primaryStage.setX(337);
        primaryStage.setY(27);
        primaryStage.show();
    }
    
    private void initFilter() {
		txtsearch = TextFields.createSearchField();
		txtsearch.setStyle("-fx-background-radius:10;");
		txtsearch.setPromptText("Search The Records");
		txtsearch.setMinWidth(200);
		txtsearch.textProperty().addListener(new InvalidationListener() {
            @Override
		public void invalidated(Observable o) {
            if(txtsearch.textProperty().get().isEmpty()) {
                tableView.setItems(att);
                return;
            }
            ObservableList<Reports> tableItems = FXCollections.observableArrayList();
            ObservableList<TableColumn<Reports, ?>> cols = tableView.getColumns();
            for(int i=0; i<att.size(); i++) {
                
                for(int j=0; j<cols.size(); j++) {
                    TableColumn<Reports, ?> col = cols.get(j);
                    String cellValue = col.getCellData(att.get(i)).toString();
                    cellValue = cellValue.toLowerCase();
                    if(cellValue.contains(txtsearch.textProperty().get().toLowerCase())) {
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
    public void editClicked(){
		if(lstudname.getText().equals(""))
		{
			// Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a Staff in the list.");
            alert.showAndWait();
		}
		else
		{
			try {
				new EditOffice().start(new Stage());
				EditOffice.txtid.setText(lstudname.getText());
			} catch (Exception e) {
				ErrorMessage.display("Launch Error", e.getMessage()+" Application Launch Error");
				e.printStackTrace();
			}
		}
	}
    
    public void deleteClicked()
	{
		if(lstudname.getText().equals(""))
		{
			// Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No One Selected");
            alert.setContentText("Please select a staff in the list.");
            alert.showAndWait();
		}
		else
		{
		boolean result = Confirmation.display("Delete Record", " Are you sure you want to delete this \n Lecturer? ");
			if(result)
			{
				ObservableList<Reports> selectedProd, allProd;
				allProd= tableView.getItems();
				selectedProd = tableView.getSelectionModel().getSelectedItems();
				//selectedProd.forEach(allProd:: remove);
		
				//the delete query is now complete  ------------------>
				String query = "DELETE FROM office where n_id = '" + lstudname.getText() + "'";
				//connect to database
				DBConnect.connect();
				try {
                DBConnect.stmt.execute(query);
                selectedProd.forEach(allProd:: remove);
                SuccessMessage.display("Success", "Lecturer has been deleted successfully");
                DBConnect.closeConnection();
				} catch (Exception ea) {
                ErrorMessage.display("Launching Error", ea.getMessage()+" Error has occurred. Consult administrator");
				} 
				}
			}
	}
    
    public static void main(String[] args) { launch(args); }
}
