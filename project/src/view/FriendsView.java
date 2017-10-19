package view;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class FriendsView extends BorderPane
{
    private Thread messageThread;

    private ObservableList<String> friendsList = FXCollections.observableArrayList();
    private ListView<String> friendListView = new ListView<>(friendsList);

    public FriendsView()
    {
        VBox leftBox = new VBox();
        leftBox.getChildren().addAll(buildAddFriendsBar(), buildFriendListView());
        VBox.setVgrow(friendListView, Priority.ALWAYS);

        this.setLeft(leftBox);
        this.setCenter(buildActionsView());
        this.setPadding(new Insets(2.5));

        // We setup a list listener
        messageThread = new Thread(() ->
        {
            while(messageThread.isAlive())
            {
                try
                {
                    Thread.sleep(10000);
                }
                catch(InterruptedException e)
                {

                }
            }
        });
        messageThread.start();
    }

    private HBox buildAddFriendsBar()
    {
        HBox bar = new HBox();

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        HBox.setHgrow(usernameField, Priority.ALWAYS);

        Button addFriendBtn = new Button("Add Friend");

        bar.getChildren().addAll(usernameField, addFriendBtn);

        return bar;
    }

    private VBox buildFriendListView()
    {
        VBox friendsPanel = new VBox();

        friendListView.setEditable(false);
        friendListView.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> observable, String oldVal, String newVal) ->
        {
            if(messageThread != null)
                messageThread.interrupt();
        });

        friendsPanel.getChildren().addAll(friendListView);
        return friendsPanel;
    }

    private VBox buildActionsView()
    {
        VBox box = new VBox();

        TextArea messageHistory = new TextArea();
        messageHistory.setEditable(false);
        messageHistory.setWrapText(true);
        VBox.setVgrow(messageHistory, Priority.ALWAYS);

        TextField messageBox = new TextField();
        messageBox.setPromptText("Type a message...");
        messageBox.setEditable(false);

        box.getChildren().addAll(messageHistory, messageBox);

        return box;
    }
}
