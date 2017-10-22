package view;

import controller.dashboard.FriendsController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
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

    private ObservableList<String> friendRqstList = FXCollections.observableArrayList();
    private ListView<String> friendRqstView = new ListView<>(friendRqstList);

    public FriendsView()
    {
        VBox leftBox = new VBox();
        leftBox.getChildren().addAll(buildAddFriendsBar(), buildFriendListView());

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

        friendsPanel.getChildren().addAll(friendListView, new Label("Incoming Requests") ,friendRqstView);
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

    public ObservableList<String> getFriendRqstListForDisplay() 
    {
    	return friendRqstList; 
	}

    public ListView<String> getFriendsListView()
    {
        return friendListView;
    }

    public ListView<String> getFriendRqstListView()
    {
        return friendRqstView;
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
