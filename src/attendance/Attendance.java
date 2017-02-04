package attendance;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.DBConnect;
import dialogs.ErrorMessage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


public class Attendance extends Application {

    final GetDailySalesService service = new GetDailySalesService();

    @SuppressWarnings({ "unchecked", "rawtypes" })
	private void init(Stage primaryStage) {
        Group root = new Group();
        primaryStage.setScene(new Scene(root));

        VBox vbox = new VBox(5);
        vbox.setPadding(new Insets(12));
        TableView tableView = new TableView();
        Button button = new Button("Refresh");
        button.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent t) {
                service.restart();
            }
        });
        vbox.getChildren().addAll(tableView, button);

        Region veil = new Region();
        veil.setStyle("-fx-background-color: rgba(0, 800, 0, 0.3)");
        ProgressIndicator p = new ProgressIndicator();
        p.setMaxSize(150, 150);

        //Define table columns
        TableColumn idCol = new TableColumn();
        idCol.setText("ID");
        idCol.setMinWidth(150);
        idCol.setCellValueFactory(new PropertyValueFactory<>("dailySalesId"));
        tableView.getColumns().add(idCol);
        TableColumn qtyCol = new TableColumn();
        qtyCol.setText("Qty");
        qtyCol.setMinWidth(150);
        qtyCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        tableView.getColumns().add(qtyCol);
        TableColumn dateCol = new TableColumn();
        dateCol.setText("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        dateCol.setMinWidth(150);
        tableView.getColumns().add(dateCol);


        p.progressProperty().bind(service.progressProperty());
        veil.visibleProperty().bind(service.runningProperty());
        p.visibleProperty().bind(service.runningProperty());
        tableView.itemsProperty().bind(service.valueProperty());

        StackPane stack = new StackPane();
        stack.getChildren().addAll(vbox, veil, p);

        root.getChildren().add(stack);
        service.start();
    }

    /**
     * A service for getting the DailySales data. This service exposes an
     * ObservableList for convenience when using the service. This
     * <code>results</code> list is final, though its contents are replaced when
     * a service call successfully concludes.
     */
    public class GetDailySalesService extends Service<ObservableList<DailySales>> {

        /**
         * Create and return the task for fetching the data. Note that this
         * method is called on the background thread (all other code in this
         * application is on the JavaFX Application Thread!).
         *
         * @return A task
         */
        @SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
        protected Task createTask() {
            return new GetDailySalesTask();
        }
    }

    public class GetDailySalesTask extends Task<ObservableList<DailySales>> {       
        @Override protected ObservableList<DailySales> call() throws Exception {
            for (int i = 0; i < 500; i++) {
                updateProgress(i, 500);
                Thread.sleep(20);
            }
            ObservableList<DailySales> sales = FXCollections.observableArrayList();
            String sql="SELECT first_name, school_id, type FROM  gateentry";
            			try {
            		    	DBConnect.connect();
            				ResultSet rec = DBConnect.stmt.executeQuery(sql);
            				while((rec!=null) && (rec.next()))
            				{ 
            					String name = (rec.getString("first_name"));
            					String adm = (rec.getString("school_id"));
            					String timein = (rec.getString("type"));
            					
            					
            					sales.add(new DailySales(name,adm,timein));
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
            
            
            
            
            //sales.add(new DailySales(1, 5000, new Date()));
            //sales.add(new DailySales(2, 2473, new Date(0)));
            return sales;
        }
    }

    public class DailySales {

        private String dailySalesId;
        private String quantity;
        private String date;

        public DailySales() {
        }

        public DailySales(String id, String qty, String date) {
            this.dailySalesId = id;
            this.quantity = qty;
            this.date = date;
        }

		public String getDailySalesId() {
			return dailySalesId;
		}

		public void setDailySalesId(String dailySalesId) {
			this.dailySalesId = dailySalesId;
		}

		public String getQuantity() {
			return quantity;
		}

		public void setQuantity(String quantity) {
			this.quantity = quantity;
		}

		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
		}

        
    }

    @Override public void start(Stage primaryStage) throws Exception {
        init(primaryStage);
        primaryStage.show();
    }
    public static void main(String[] args) { launch(args); }
}
