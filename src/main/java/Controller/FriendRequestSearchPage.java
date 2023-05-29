package Controller;

import Model.FileForFriends;
import Model.Regexes;
import Model.FileForUsers;
import Model.UserNotFiundExeption;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class FriendRequestSearchPage {

    private Stage friendRequestSearchPage;
    Regexes regex = new Regexes();
    String username;
    FileForUsers fileForUsers = new FileForUsers();
    FileForFriends fileForFriends = new FileForFriends();
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    private Stage dialogStage;

    public void initFunction(Stage sendRequestStage,String username){
        this.friendRequestSearchPage = sendRequestStage;
        this.username=username;
    }

    @FXML
    private TextField usernameTXTFLD;

    @FXML
    private Button backBTN;


    @FXML
    void findBTNHandler(ActionEvent event){
        try {
            if(regex.UserNameRegex(usernameTXTFLD.getText()) && !usernameTXTFLD.getText().equals(this.username) &&
                    fileForUsers.searchUser(usernameTXTFLD.getText()) ){
                if( !fileForFriends.isFriend(username, usernameTXTFLD.getText())){

                    loadingFriendRequestCheckPage(usernameTXTFLD.getText() , username);

                }

                else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.initOwner(dialogStage);
                    alert.setTitle("Error");
                    alert.setHeaderText("Please correct invalid fields");
                    alert.setContentText("Entered username already is a friend");
                    alert.showAndWait();
                }

            }

            else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initOwner(dialogStage);
                alert.setTitle("Error");
                alert.setHeaderText("Please correct invalid fields");
                alert.setContentText("User not found");
                alert.showAndWait();
                throw new UserNotFiundExeption();
            }

        }catch (UserNotFiundExeption | IOException e){
            System.out.println(e.getMessage());
        }
    }

    public void loadingFriendRequestCheckPage(String friendUsername , String username) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/FriendRequestSurePage.fxml"));
        loader.load();
        FriendRequestSurePageController friendRequestSurePageController= loader.getController();
        friendRequestSearchPage.setScene(new Scene((Parent) loader.getRoot()));
        friendRequestSearchPage.setTitle("Are you sure?");
        friendRequestSearchPage.setResizable(false);
        friendRequestSurePageController.initFunction(friendRequestSearchPage , friendUsername,username);
        friendRequestSearchPage.show();
    }

    @FXML
    void backBTNHandler(ActionEvent event) throws IOException {friendRequestSearchPage.close();}
}
