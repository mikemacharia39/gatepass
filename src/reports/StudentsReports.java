package reports;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.controlsfx.control.TextFields;

import database.DBConnect;
import dialogs.ErrorMessage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;


public class StudentsReports extends Application {

	ObservableList<Reports> att = FXCollections.observableArrayList();
    TableColumn<Reports, String> idCol, fnameCol,	lnamecol,	admCol,	phoneCol, addressCol, townCol;
    TableView<Reports> tableView;
    TextField txtsearch;
    Button btnsearch;
    Text textname;
    Scene scene;
    VBox vbox = new VBox(10);
    @SuppressWarnings("unchecked")
	private void init(Stage primaryStage) {
        scene= new Scene(vbox);
        scene.getStylesheets().add(StudentsReports.class.getResource("att.css").toExternalForm());
        primaryStage.setScene(scene);
       
        vbox.setPadding(new Insets(12));
        tableView = new TableView<Reports>();
        Button button = new Button("Refresh");
        button.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent t) {
                tableView.setItems(getAtt());
            }
        });
       
        textname= new Text("ALL STUDENT REPORTS");
        textname.setFont(Font.font("Andalus", FontPosture.REGULAR, 20));
        
        idCol = new TableColumn<>();
        idCol.setText("Id");
        idCol.setMinWidth(110);
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        
        fnameCol = new TableColumn<>();
        fnameCol.setText("First Name");
        fnameCol.setMinWidth(200);
        fnameCol.setCellValueFactory(new PropertyValueFactory<>("fname"));
        
        lnamecol = new TableColumn<>();
        lnamecol.setText("Last Name");
        lnamecol.setMinWidth(150);
        lnamecol.setCellValueFactory(new PropertyValueFactory<>("lname"));
        
        admCol = new TableColumn<>();
        admCol.setText("School Id");
        admCol.setMinWidth(170);
        admCol.setCellValueFactory(new PropertyValueFactory<>("adm"));
        
        addressCol = new TableColumn<>();
        addressCol.setText("Address");
        addressCol.setMinWidth(170);
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        
        townCol = new TableColumn<>();
        townCol.setText("Town");
        townCol.setMinWidth(150);
        townCol.setCellValueFactory(new PropertyValueFactory<>("town"));
        
        phoneCol = new TableColumn<>();
        phoneCol.setText("Phone");
        phoneCol.setMinWidth(150);
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        
        tableView.getColumns().addAll(idCol, fnameCol,	lnamecol,	admCol,	phoneCol, addressCol, townCol);
        tableView.setItems(getAtt());
        
        txtsearch= new TextField();
		initFilter();
		att=getAtt();
        tableView.setItems(FXCollections.observableArrayList(att));

        HBox layt= new HBox();
        layt.getChildren().add(textname);
        layt.setAlignment(Pos.CENTER);
        
        HBox layre= new HBox();
        layre.getChildren().add(txtsearch);
        layre.setAlignment(Pos.TOP_LEFT);
        
        vbox.getChildren().addAll(layt, layre, tableView, button);
        vbox.setId("vb");
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
            String sql="SELECT * FROM  students";
            			try {
            		    	DBConnect.connect();
            				ResultSet rec = DBConnect.stmt.executeQuery(sql);
            				while((rec!=null) && (rec.next()))
            				{ 
            					//first_name	last_name	adm	id	phone	address	town	dob	thumb_id	student	picture
            					
            					String id = (rec.getString("n_id"));
            					String fname = (rec.getString("first_name"));
            					String lname = (rec.getString("last_name"));
            					String adm = (rec.getString("s_id"));
            					String address = (rec.getString("address"));
            					String town = (rec.getString("town"));
            					String phone = (rec.getString("phone"));
            					
            					
            					sales.add(new Reports(id, fname,lname, adm, address, town, phone));
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

        String id;
        String fname;
        String lname;
        String adm;
        String address;
        String town;
        String phone;

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
        	
        	this.id="";
            this.fname="";
            this.lname="";
            this.adm="";
            this.address="";
            this.town="";
            this.phone="";
        }

        public Reports(String id, String fname, String lname, String adm, String address, String town, String phone) {
        	this.id=id;
            this.fname=fname;
            this.lname=lname;
            this.adm=adm;
            this.address=address;
            this.town=town;
            this.phone=phone;
        	
        }
   
    }

    @Override public void start(Stage primaryStage) throws Exception {
        init(primaryStage);
        primaryStage.getIcons().add(new Image("/pic/slogo.png"));
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.setX(225);
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
    
    public static void main(String[] args) { launch(args); }
}
