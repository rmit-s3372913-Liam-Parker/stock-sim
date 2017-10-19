package view;

import controller.dashboard.FriendsController;
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
    private TextField usernameField;
    private Button addFriendBtn;
    private TextArea messageHistory;
    private TextField messageBox;

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

        FriendsController controller = new FriendsController(this);
        addFriendBtn.setOnAction(controller);
    }

    private HBox buildAddFriendsBar()
    {
        HBox bar = new HBox();

        usernameField = new TextField();
        usernameField.setPromptText("Username");
        HBox.setHgrow(usernameField, Priority.ALWAYS);

        addFriendBtn = new Button("Add Friend");

        bar.getChildren().addAll(usernameField, addFriendBtn);

        return bar;
    }

    private VBox buildFriendListView()
    {
        VBox friendsPanel = new VBox();

        friendListView.setEditable(false);

        friendsPanel.getChildren().addAll(friendListView);
        return friendsPanel;
    }

    private VBox buildActionsView()
    {
        VBox box = new VBox();

        messageHistory = new TextArea();
        messageHistory.setEditable(false);
        messageHistory.setWrapText(true);
        VBox.setVgrow(messageHistory, Priority.ALWAYS);

        messageBox = new TextField();
        messageBox.setPromptText("Type a message...");
        messageBox.setEditable(false);

        box.getChildren().addAll(messageHistory, messageBox);

        return box;
    }

    public ObservableList<String> getFriendListForDisplay()
    {
        return friendsList;
    }

    public ListView<String> getFriendsListView()
    {
        return friendListView;
    }

    public TextField getAddFriendText()
    {
        return usernameField;
    }

    public Button getAddFriendBtn()
    {
        return addFriendBtn;
    }

    public TextArea getMessageHistory()
    {
        return messageHistory;
    }

    public TextField getMessageField()
    {
        return messageBox;
    }

}
