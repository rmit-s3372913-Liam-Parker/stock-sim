package view;


import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import controller.admin.DeletePlayerController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.Player;

public class AdminLoginView extends GridPane{

    private TableView table;
    private ObservableList data;

    public AdminLoginView(List<Player> players) {

        // Books label
        Label label = new Label("Players");
        label.setTextFill(Color.BLACK);
        label.setFont(Font.font("Calibri", FontWeight.BOLD, 36));
        HBox hb = new HBox();
        hb.setAlignment(Pos.TOP_LEFT);
        hb.getChildren().add(label);

        // Table view, data, columns and properties

        table = new TableView();
        data = getInitialTableData(players);
        table.setItems(data);

        TableColumn idCol = new TableColumn("ID");
        idCol.setCellValueFactory(new PropertyValueFactory("id"));
        idCol.setPrefWidth(125);
        TableColumn userNameCol = new TableColumn("User Name");
        userNameCol.setCellValueFactory(new PropertyValueFactory("userName"));
        userNameCol.setPrefWidth(175);
        TableColumn emailCol = new TableColumn("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory("email"));
        emailCol.setPrefWidth(250);
        TableColumn confirmedCol = new TableColumn("Confirmed");
        confirmedCol.setCellValueFactory(new PropertyValueFactory("confirmed"));
        confirmedCol.setPrefWidth(125);
        TableColumn winningCol = new TableColumn("Winning");
        winningCol.setCellValueFactory(new PropertyValueFactory("winning"));
        winningCol.setPrefWidth(125);
        
        table.getColumns().setAll(idCol, userNameCol, emailCol, confirmedCol, winningCol);
        table.setPrefWidth(800);
        table.setPrefHeight(300);
        //table.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
		
        Button deleteButton = new Button("Delete");
        
        deleteButton.setOnAction((event)->{
        	Alert alert = new Alert(AlertType.CONFIRMATION);
        	alert.setTitle("Confirmation Dialog");
        	alert.setHeaderText("Look, a Confirmation Dialog");
        	alert.setContentText("Do you really want to delete Entry ??");

        	Optional<ButtonType> result = alert.showAndWait();
        	if (result.get() == ButtonType.OK){
        	    // ... user chose OK
        		Player player = (Player)table.getSelectionModel().getSelectedItem();
        		String status = new DeletePlayerController(player.getId()).deletePlayer();
        		if(status.equals("DONE")) {
        			Iterator<Player> it = players.iterator();
        			while(it.hasNext()) {
        				if(it.next().equals(player)) {
        					it.remove();
        					break;
        				}
        			}
        			data = getInitialTableData(players);
                    table.setItems(data);
        		}else {
        			Alert error = new Alert(AlertType.ERROR);
                	error.setTitle("Errro Dialog");
                	error.setHeaderText("Look, a Error !!");
                	error.setContentText("Something went Wrong try again later");
                	error.showAndWait();
        		}
                
        	} else {
        	    // ... user chose CANCEL or closed the dialog
        	}
        });

        // Vbox
        VBox vbox = new VBox(20);
        vbox.setPadding(new Insets(25, 25, 25, 25));;
        vbox.getChildren().addAll(hb, table , deleteButton);

        add(vbox,40,40);
        
    } // start()

    private ObservableList getInitialTableData(List<Player> players) {

        ObservableList data = FXCollections.observableList(players);

        return data;
    }
}