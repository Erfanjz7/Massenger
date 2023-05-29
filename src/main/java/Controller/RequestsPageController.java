package Controller;

import Model.FileForFriends;
import Model.FileForRequests;
import Model.User;
import Model.FileForUsers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class RequestsPageController implements Initializable {

    private Stage showRequestsStage;

    FileForRequests fileForRequests=new FileForRequests();

    FileForUsers fileForUsers=new FileForUsers();

    String username;

    User user;

    FileForFriends fileForFriends=new FileForFriends();

    private ChatPageController chatPageController;

    public void initFunction1(Stage showRequestsStage,String username,ChatPageController chatPageController ){
        this.showRequestsStage=showRequestsStage;
        this.chatPageController= chatPageController;
        this.username=username;
        addToTable();
    }

    private Stage dialogStage;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    private TableColumn<User, String> requestsColumn;

    @FXML
    private TableView<User> requestsTable;
    ObservableList<User> userFriends ;

    @FXML
    private Button closeBTN;
    /**
     *   add To Friends.
     */

    @FXML
    void addToFriendsHandler(ActionEvent event) {
        User selectedUser = this.requestsTable.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            fileForFriends.addFriend(fileForUsers.getUser(username), selectedUser);
            fileForRequests.acceptOrRejectRequest(selectedUser.getUsername(), username);
            chatPageController.initFunction(chatPageController.getStage(),
                    fileForFriends.getFriends(fileForUsers.getUserId(username)) ,username);

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(" well done");
            alert.showAndWait();

        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(" Select a user");

            alert.showAndWait();
        }
    }
    public void addToTable(){
        ArrayList<User> applicants = new ArrayList<>();
        ArrayList<String> applicantsUsername = new ArrayList<>();
        applicantsUsername.addAll(fileForRequests.getUserAllRequests(username));
        int size=  applicantsUsername.size();
        for (int i=0 ; i < size; i++ ){
            applicants.add(fileForUsers.getUser(applicantsUsername.get(i)));
        }
        ObservableList<User> applicantUsers = FXCollections.observableArrayList(applicants);
        requestsTable.setItems(applicantUsers);
    }

    @FXML
    void closeHandler(ActionEvent event) throws IOException {
        showRequestsStage.close();
    }
    /**
     *   reject darkhast.
     */

    @FXML
    void rejectHandler(ActionEvent event) {
        User selectedUser = requestsTable.getSelectionModel().getSelectedItem();
        if(selectedUser != null){
            fileForRequests.acceptOrRejectRequest(selectedUser.getUsername(),username);
            showRequestsStage.close();
            chatPageController.initFunction(showRequestsStage,fileForFriends.getFriends(fileForUsers.getUserId(username)) ,username);
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(" Select a user");

            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        requestsColumn.setCellValueFactory(new PropertyValueFactory<User,String>("username"));
    }

}
