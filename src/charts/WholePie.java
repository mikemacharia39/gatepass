package charts;

/**
 * Copyright (c) 2008, 2012 Oracle and/or its affiliates.
 * All rights reserved. Use is subject to license terms.
 */

import database.DBConnect;
import dialogs.ErrorMessage;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A circular chart divided into segments. The value of each segment represents
 * a proportion of the total.
 *
 * @see javafx.scene.chart.PieChart
 * @see javafx.scene.chart.Chart
 */
public class WholePie extends Application {

    Stage primaryStage;
    String sp;

    public WholePie(String sp) {
        this.sp = sp;
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void init(Stage primaryStage) {
        HBox root = new HBox();

        Button btnClose = new Button("Close");
        btnClose.setOnAction(e -> {
            primaryStage.close();
        });

        primaryStage.setScene(new Scene(root, 700, 410));

        String qcount = "SELECT date, COUNT(date) FROM  gateentry where type='" + sp + "' GROUP BY date";
        try {
            DBConnect.connect();
            ResultSet rec = DBConnect.stmt.executeQuery(qcount);
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
            while (rec.next()) {
                String date = rec.getString("date");
                int count = rec.getInt("COUNT(date)");
                pieChartData.add(new PieChart.Data(date, count));
            }
            PieChart chart = new PieChart(pieChartData);
            chart.setTitle("Pie Chart Representation of " + sp + " Gate Entries");
            chart.setClockwise(false);
            root.getChildren().addAll(chart, btnClose);
            root.setAlignment(Pos.TOP_RIGHT);
            root.setPadding(new Insets(10, 10, 0, 0));
        } catch (SQLException e) {
            ErrorMessage.display("SQL Error", e.getMessage() + "\n error");
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage1) throws Exception {

        primaryStage = primaryStage1;
        primaryStage = new Stage(StageStyle.UNDECORATED);
        init(primaryStage);
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.setTitle("Chart Representation of Gate Entries");
        primaryStage.getIcons().add(new Image("/pic/slogo.png"));
        primaryStage.setY(80);
        primaryStage.setX(620);
        primaryStage.show();
    }
}
