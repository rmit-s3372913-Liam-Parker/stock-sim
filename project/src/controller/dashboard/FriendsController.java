package controller.dashboard;

import controller.Controller;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import model.Message;
import view.FriendsView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FriendsController extends Controller
{
    private FriendsView view;
    private Map<String, List<Message>> messages = new HashMap<>();
    public String currentChat;

    public FriendsController(FriendsView view)
    {
        this.view = view;

        // We create a thread for monitoring messages and friend related notifications.
        final Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.millis( 10000 ),
                        event -> {
                            processMessages();
                            recalculateLists();
                            setCurrentChat(currentChat);
                        }
                )
        );
        timeline.setCycleCount( Animation.INDEFINITE );
        timeline.play();

        // We call our process functions initially to populate the view.
        processMessages();
        recalculateLists();

        // Respond to list selections
        view.getFriendsListView().getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> observable, String oldVal, String newVal) ->
        {
            setCurrentChat(newVal);
            currentChat = newVal;
        });

        view.getFriendRqstListView().getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> observable, String oldVal, String newVal) ->
        {
            boolean isAccepted = displayConfirmationModal("Friend Request Confirmation","Would you like to accept " + newVal + " as a friend.");
            if(isAccepted)
            {
                view.getFriendRqstListForDisplay().remove(newVal);
                getModel().sendFriendRequest(newVal);
            }
        });
        
        view.getMessageField().setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent key)
            {
                if (key.getCode().equals(KeyCode.ENTER))
                {
                	String error;
            		
            		//store the transaction on cloud database
            		if ((error = getModel().sendMessage(currentChat, view.getMessageField().getText())) == null)
                        processMessages();
            		else
            			displayErrorModal("Message Error", error);

                    setCurrentChat(currentChat);
                }
            }
        });

    }

    @Override
    public void handle(ActionEvent event)
    {
        if(event.getSource().equals(view.getAddFriendBtn()))
        {
            String userToSendFriendRequest = view.getAddFriendText().getText();
            String curUser = getModel().getSessionDetails().getUsername();

            if(curUser.equals(userToSendFriendRequest))
            {
                displayErrorModal("Friend Request Error", "You can't send a friend request to yourself!");
            }

            if(!getModel().getFriends().contains(userToSendFriendRequest))
            {
                getModel().sendFriendRequest(userToSendFriendRequest);
                displayNotificationModal("Friend Request Sent", userToSendFriendRequest + " has been notified of your request." +
                        " If they accept, you'll see them on your friend list.");
            }
            else
            {
                displayErrorModal("Friend Request Error", "The requested username could not be found in the system!");
            }
        }
    }

    private void processMessages()
    {
        String curUser = getModel().getSessionDetails().getUsername();

        // Loop through messages inserting where required.
        for(Message message : getModel().getMessages())
        {
            boolean newMessage = true;
            String sender = message.getSenderUsername();
            String receiver = message.getReceiverUsername();
            String targetFriend = (sender.equals(curUser)) ? receiver : sender;

            // We need to ensure this friends list has already been instantiated.
            messages.putIfAbsent(targetFriend, new ArrayList<>());

            // Check to see if we've already received this message.
            for(Message savedMsg : messages.get(targetFriend))
            {
                if(savedMsg.getId() == message.getId()) newMessage = false;
            }

            // Insert message
            if(newMessage)
                messages.get(targetFriend).add(message);
        }
    }

    private void recalculateLists()
    {
        view.getFriendListForDisplay().clear();
        view.getFriendListForDisplay().addAll(getModel().getFriends());
        view.getFriendRqstListForDisplay().clear();
        view.getFriendRqstListForDisplay().addAll(getModel().getFriendRequest());
        if (currentChat!=null)
        	view.getFriendsListView().getSelectionModel().select(currentChat);
    }

    private void setCurrentChat(String username)
    {
        TextArea chatBox = view.getMessageHistory();
        TextField chatBar = view.getMessageField();

        // Clear previous data
        chatBox.clear();
        chatBar.clear();
        view.getMessageField().setEditable(true);

        // Load history of selected friend
        messages.putIfAbsent(username, new ArrayList<>());
        for(Message message : messages.get(username))
        {
            chatBox.appendText(message.getSenderUsername() + ":\n");
            chatBox.appendText(message.getMessage() + "\n\n");
        }
    }

}
