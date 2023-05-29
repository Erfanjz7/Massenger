package Controller;

import Model.FileForRequests;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class FriendRequestSurePageController {

    private Stage dialogStage;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    private Stage friendRequestSurePage;

    private FileForRequests fileForRequests;

    private String username;

    public void initFunction(Stage sendRequestStage , String friendUsername , String username){
        this.friendRequestSurePage = sendRequestStage;
        usernameLBL.setText(friendUsername);
        fileForRequests= new FileForRequests();
        this.username= username;
    }

    @FXML
    private Label usernameLBL;

    // cancels the request

    @FXML
    void noBTNHandler(ActionEvent event) throws IOException {
        friendRequestSurePage.close();
    }

      //sends a request.

    @FXML
    void yesBTNHandler(ActionEvent event) {
        if(fileForRequests.requestIsNotExist(this.username,usernameLBL.getText())){
            fileForRequests.sendRequest(this.username,usernameLBL.getText());
            friendRequestSurePage.close();
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Error");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText("You can not send request to " + usernameLBL.getText());
            alert.showAndWait();
        }
    }
}

