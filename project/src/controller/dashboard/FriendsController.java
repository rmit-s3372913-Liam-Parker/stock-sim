package controller.dashboard;

import controller.Controller;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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

    public FriendsController(FriendsView view)
    {
        this.view = view;

        processMessages();

        view.getFriendListForDisplay().addAll(getModel().getFriends());
        view.getFriendsListView().getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> observable, String oldVal, String newVal) ->
        {
            setCurrentChat(newVal);
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
            //TODO: Might be able to simplify this to a .contains() call depending on how Messages are compared.
            for(Message savedMsg : messages.get(targetFriend))
            {
                if(savedMsg.getId() == message.getId()) newMessage = false;
            }

            // Insert message
            if(newMessage)
                messages.get(targetFriend).add(message);
        }
    }

    private void setCurrentChat(String username)
    {
        TextArea chatBox = view.getMessageHistory();
        TextField chatBar = view.getMessageField();

        // Clear previous data
        chatBar.clear();
        chatBox.clear();

        // Load history of selected friend
        messages.putIfAbsent(username, new ArrayList<>());
        for(Message message : messages.get(username))
        {
            chatBox.appendText(message.getSenderUsername() + ":\n");
            chatBox.appendText(message.getMessage() + "\n\n");
        }
    }

}
