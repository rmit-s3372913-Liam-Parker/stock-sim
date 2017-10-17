package view;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class FriendsView extends BorderPane
{
    HBox friendsPanel = new HBox();

    public FriendsView()
    {
        ObservableList<String> friendsList = FXCollections.observableArrayList();
        FilteredList<String> filteredFriendsList = new FilteredList<>(friendsList);
        ListView<String> friendListView = new ListView<>(filteredFriendsList);

        friendListView.setEditable(false);
        friendListView.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> observable, String oldVal, String newVal) -> {

        });



        friendsPanel.getChildren().addAll(friendListView);
    }
}
