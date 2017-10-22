package controller.dashboard;

import controller.Controller;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import view.FriendsView;

public class FriendsController extends Controller
{
    private FriendsView view;

    public FriendsController(FriendsView view)
    {
        this.view = view;

        view.getFriendListForDisplay().addAll(getModel().getFriends());
        view.getFriendsListView().getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> observable, String oldVal, String newVal) ->
        {
            //TODO: Not yet implemented!
        });
    }

    @Override
    public void handle(ActionEvent event)
    {
        if(event.getSource().equals(view.getAddFriendBtn()))
        {
            String userToSendFriendRequest = view.getAddFriendText().toString();
            if(getModel().sendFriendRequest(userToSendFriendRequest) != null)
            {
                displayErrorModal("Friend Request Error", "The requested username could not be found in the system!");
            }
            else
            {
                displayNotificationModal("Friend Request Sent", userToSendFriendRequest + " has been notified of your request." +
                    " If they accept, you'll see them on your friend list.");
            }
        }
    }

}
