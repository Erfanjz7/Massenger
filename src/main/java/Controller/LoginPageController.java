package Controller;

import Model.FileForFriends;
import Model.User;
import Model.FileForUsers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sun.applet.Main;
import java.io.IOException;
import java.util.ArrayList;

public class LoginPageController {
    private Stage primaryStage;

    public void initFunction(Stage primaryStage){
        this.primaryStage = primaryStage;
        fileForUsers =new FileForUsers();
    }

    FileForUsers fileForUsers = new FileForUsers();

    private Stage dialogStage;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    FileForFriends fileForFriends=new FileForFriends();
    @FXML
    private Button btnback;

    @FXML
    private TextField TXTusername;

    @FXML
    private TextField TXTpassword;
    /**
     * login ActionEvent button
     */
    @FXML
    void loginBTNHandler(ActionEvent event) throws IOException {
        if(fileForUsers.userFound(TXTusername.getText(),TXTpassword.getText())){
            ArrayList<User> userFriends = fileForFriends.findFriends(fileForUsers.getUser(TXTusername.getText()));
            FXMLLoader loader= new FXMLLoader(Main.class.getResource("/ChatPage.fxml"));
            loader.load();
            ChatPageController chatPageController= loader.getController();
            primaryStage.setScene(new Scene((Parent) loader.getRoot()));
            primaryStage.setTitle("User Page");
            primaryStage.setResizable(false);
            chatPageController.initFunction(primaryStage,userFriends,TXTusername.getText());
            primaryStage.show();
        }

        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Error");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText("Invalid username or password");

            alert.showAndWait();
        }
    }
    /**
     * back ActionEvent button
     */

    @FXML
    void backBTNHandler(ActionEvent event) throws IOException {
        Stage stage=(Stage)btnback.getScene().getWindow();
        stage.close();
        FXMLLoader loader= new FXMLLoader(this.getClass().getResource("../MainPage.fxml"));
        loader.load();
        primaryStage.setScene(new Scene((Parent) loader.getRoot()));
        primaryStage.setTitle("Erfanjz7's Massenger");
        primaryStage.show();
    }
}
