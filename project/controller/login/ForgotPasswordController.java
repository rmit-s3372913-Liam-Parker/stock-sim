package controller.login;

import controller.Controller;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import ultilities.EmailHelper;
import ultilities.PinGenerator;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

public class ForgotPasswordController extends Controller
{
    @Override
    public void handle(ActionEvent event)
    {
        try
        {
            verifyRecoveryEmail();
        }
        catch(Exception e)
        {
            displayExceptionModal(e, "An error occurred while trying to recover the users email!" +
                    " Send this stack trace to a developer.");
        }
    }

    private void verifyRecoveryEmail() throws UnsupportedEncodingException, NoSuchAlgorithmException
    {
        Optional<Pair<String, String>> result = getUserDetails();

        if(!result.isPresent()) return;

        String userName = result.get().getKey();
        String email = result.get().getValue();
        String systemEmail = getModel().getUserEmailByUsername(userName);

        if(email.equals(systemEmail))
        {
            String pin = PinGenerator.generatePin();
            EmailHelper.sendAuthenticationEmail(email, pin);
            String resp = getModel().updateUserPinByUsername(userName, pin);

            if(resp.equals("DONE"))
            {
                Optional<String> inputPin = displayTextInputModal("Email Authentication","Enter the pin sent to " + email);
                if (inputPin.isPresent())
                {
                    String userPin = inputPin.get();
                    String databasePin = getModel().getUserPinByUsername(userName);
                    if(userPin.equals(databasePin))
                        updatePassword(userName);
                    else
                        displayErrorModal("Authentication Failed", "An incorrect pin was entered, please try again.");
                }
            }
        }
        else
        {
            displayErrorModal("Invalid Email","The email you provided doesn't match any email in our system.");
        }
    }

    private Optional<Pair<String, String>> getUserDetails()
    {
        // Construct our dialog object
        // Due to the more targeting requirements for this
        // we don't use the controller notification functions
        // and instead create a custom dialog here.
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Password Recovery");
        dialog.setHeaderText("Please Enter Username & Email");

        // Set the button types.
        ButtonType loginButton = new ButtonType("Submit", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButton, ButtonType.CANCEL);

        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(15, 125, 8, 10));
        grid.setHgap(8);
        grid.setVgap(8);

        // Create our text fields.
        TextField usernameText = new TextField();
        usernameText.setPromptText("Username");
        TextField emailText = new TextField();
        emailText.setPromptText("Email");

        // Specify format for return value of showAndWait
        dialog.setResultConverter(dialogButton ->
        {
            if (dialogButton == loginButton) { return new Pair<>(usernameText.getText(), emailText.getText()); }
            return null;
        });

        // Set fields into grid pane.
        grid.add(new Label("Username:"), 0, 0);
        grid.add(usernameText, 1, 0);
        grid.add(new Label("Email:"), 0, 1);
        grid.add(emailText, 1, 1);

        // Disable submission without relevant input.
        // We read the input as it happens and enable/disable submission buttons
        // as required.
        Node loginNode = dialog.getDialogPane().lookupButton(loginButton);
        Node cancelNode = dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
        loginNode.setDisable(true);
        cancelNode.setDisable(true);

        usernameText.textProperty().addListener((observable, oldValue, newValue) ->
        {
            if(!emailText.textProperty().get().isEmpty())
                    loginNode.setDisable(newValue.trim().isEmpty());
        });

        emailText.textProperty().addListener((observable, oldValue, newValue) ->
        {
            if(!usernameText.textProperty().get().isEmpty())
                loginNode.setDisable(newValue.trim().isEmpty());
        });

        // Set form into dialog and show to user.
        dialog.getDialogPane().setContent(grid);
        return dialog.showAndWait();
    }

    private void updatePassword(String userName)
    {
        // Build our dialog to hold form
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Password Recovery");
        dialog.setHeaderText("Please Enter New Password & Confirm");

        // Build the buttons for  our form
        ButtonType submitBtnType = new ButtonType("Submit", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(submitBtnType, ButtonType.CANCEL);

        // Create the form itself
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        PasswordField passwordField = new PasswordField();
        PasswordField confirmPasswordField = new PasswordField();

        // Validate input and enable/disable submission
        Node submitNode = dialog.getDialogPane().lookupButton(submitBtnType);
        submitNode.setDisable(true);

        passwordField.textProperty().addListener((observable, oldValue, newValue) ->
        {
            if(!confirmPasswordField.textProperty().get().isEmpty())
                submitNode.setDisable(newValue.trim().isEmpty());
        });

        confirmPasswordField.textProperty().addListener((observable, oldValue, newValue) ->
        {
            if(!passwordField.textProperty().get().isEmpty())
                submitNode.setDisable(newValue.trim().isEmpty());
        });

        grid.add(new Label("New Password:"), 0, 0);
        grid.add(passwordField, 1, 0);
        grid.add(new Label("Confirm Password:"), 0, 1);
        grid.add(confirmPasswordField, 1, 1);

        dialog.getDialogPane().setContent(grid);

        // Specify the expected return value
        dialog.setResultConverter(dialogButton ->
        {
            if (dialogButton == submitBtnType)
            {
                return new Pair<>(passwordField.getText(), confirmPasswordField.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();
        if(result.isPresent())
        {
            String pw = result.get().getKey();
            String confirmPw = result.get().getValue();
            if(pw.equals(confirmPw))
            {
                try
                {
                    String status = getModel().updateUserPasswordByUsername(userName, pw);
                    if(status.equals("DONE"))
                    {
                        displayNotificationModal("Password Recovered","Your password is now recovered," +
                                " you'll be taken back to the login screen now.");
                    }
                }
                catch(Exception e)
                {
                    displayExceptionModal(e, "An error occurred while trying to update your password in the system");
                }

            }
            else
            {
                displayErrorModal("Input Error","The passwords provided don't match, try again.");
            }
        }
    }
}
