package controller.login;

import com.sendgrid.*;
import controller.Controller;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import ultilities.EmailHelper;
import view.LoginView;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.Random;

public class ForgotPasswordController extends Controller
{
    private LoginView view;

    public ForgotPasswordController(LoginView view)
    {
        this.view = view;
    }

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
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Password Recovery");
        dialog.setHeaderText("Please Enter Username & Email");

        // Set the button types.
        ButtonType loginButtonType = new ButtonType("Submit", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField username = new TextField();
        username.setPromptText("Username");
        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        grid.add(new Label("Username:"), 0, 0);
        grid.add(username, 1, 0);
        grid.add(new Label("Email:"), 0, 1);
        grid.add(emailField, 1, 1);

        // Enable/Disable login button depending on whether a username was entered.
        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

        // Do some validation (using the Java 8 lambda syntax).
        username.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

        // Request focus on the username field by default.
        Platform.runLater(() -> username.requestFocus());

        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(username.getText(), emailField.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();
        String userName;
        String email;
        if(result.isPresent())
        {
            userName = result.get().getKey();
            email = result.get().getValue();
            System.out.println(userName + " : " + email);
            String userEmail = getModel().getUserEmailByUsername(userName);
            if(email.equals(userEmail))
            {

                StringBuilder pin = new StringBuilder();
                Random random = new Random();
                for (int i=0; i<5; i++){
                    pin.append(random.nextInt(10));
                }

                EmailHelper.sendAuthenticationEmail(email, pin.toString());

                String resp = getModel().updateUserPinByUsername(userName, pin.toString());
                if(resp.equals("DONE")) {
                    TextInputDialog dialogPin = new TextInputDialog("");
                    dialogPin.setTitle("Pin");
                    dialogPin.setHeaderText("Enter Pin Sent on Email");
                    dialogPin.setContentText("Pin: ");

                    // Traditional way to get the response value.
                    Optional<String> resultPin = dialogPin.showAndWait();
                    String userPin;
                    if (resultPin.isPresent())
                    {
                        userPin = resultPin.get();
                        String savedPin = getModel().getUserPinByUsername(userName);
                        if(userPin.equals(savedPin))
                        {

                            boolean matched = false;
                            while(!matched)
                            {
                                Dialog<Pair<String, String>> dialogPassword = new Dialog<>();
                                dialogPassword.setTitle("Password Recovery");
                                dialogPassword.setHeaderText("Please Enter New Password & Confirm");

                                // Set the button types.
                                ButtonType loginButtonType2 = new ButtonType("Submit", ButtonBar.ButtonData.OK_DONE);
                                dialogPassword.getDialogPane().getButtonTypes().addAll(loginButtonType2, ButtonType.CANCEL);

                                // Create the username and password labels and fields.
                                GridPane grid2 = new GridPane();
                                grid2.setHgap(10);
                                grid2.setVgap(10);
                                grid2.setPadding(new Insets(20, 150, 10, 10));

                                PasswordField password = new PasswordField();
                                PasswordField confirmPassword = new PasswordField();

                                grid2.add(new Label("New Password:"), 0, 0);
                                grid2.add(password, 1, 0);
                                grid2.add(new Label("Confirm Password:"), 0, 1);
                                grid2.add(confirmPassword, 1, 1);

                                // Enable/Disable login button depending on whether a username was entered.
                                Node loginButton2 = dialogPassword.getDialogPane().lookupButton(loginButtonType2);
                                loginButton2.setDisable(true);

                                // Do some validation (using the Java 8 lambda syntax).
                                password.textProperty().addListener((observable, oldValue, newValue) -> {
                                    loginButton2.setDisable(newValue.trim().isEmpty());
                                });

                                dialogPassword.getDialogPane().setContent(grid2);

                                // Request focus on the username field by default.
                                Platform.runLater(() -> password.requestFocus());

                                // Convert the result to a username-password-pair when the login button is clicked.
                                dialogPassword.setResultConverter(dialogButton -> {
                                    if (dialogButton == loginButtonType2) {
                                        return new Pair<>(password.getText(), confirmPassword.getText());
                                    }
                                    return null;
                                });

                                Optional<Pair<String, String>> result2 = dialogPassword.showAndWait();

                                if(result2.isPresent()) {
                                    if(result2.get().getKey().equals(result2.get().getValue())) {
                                        String status = getModel().updateUserPasswordByUsername(userName,result2.get().getValue());
                                        if(status.equals("DONE")) {
                                            matched=true;
                                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                            alert.setTitle("Information Dialog");
                                            alert.setHeaderText("Look, Password Updated");
                                            alert.setContentText("Your password is updated. You may Login!");

                                            alert.showAndWait();
                                        }
                                    }else {
                                        Alert alert = new Alert(Alert.AlertType.ERROR);
                                        alert.setTitle("Error Dialog");
                                        alert.setHeaderText("Look, Password didn't Match");
                                        alert.setContentText("Ooops, Password didn't Match");

                                        alert.showAndWait();
                                    }
                                }
                            }
                        }else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error Dialog");
                            alert.setHeaderText("Look, Incorrect Pin");
                            alert.setContentText("Pin You Entered Doesn't match pin sent by Email");

                            alert.showAndWait();
                        }
                    }

                    // The Java 8 way to get the response value (with lambda expression).
                    result.ifPresent(name -> System.out.println("Your name: " + name));
                }
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Look, Incorrect Email !!");
                alert.setContentText("Email You Entered doesn't match email saved");

                alert.showAndWait();
            }
        }
    }
}
