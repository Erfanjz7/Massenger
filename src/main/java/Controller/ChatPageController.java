package Controller;

import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
public class ChatPageController implements Initializable {

    private Stage primaryStage;

    FileForFriends fileForFriends = new FileForFriends();

    private FileForGroupMassages fileForGroupMassages = new FileForGroupMassages();

    String username;

    private User user;

    private ObservableList<User> userFriends ;

    private FileForUsers fileForUsers;

    private ArrayList<User> userFriendsArr;

    private FileForGruops fileForGruops;

    private FileForBlockedUsers fileForBlockedUsers;

    private  FileForFriendsMassages fileForFriendsMassages;

    public void initFunction(Stage userPageStage , ArrayList<User> userFriends , String username){
        this.primaryStage = userPageStage;
        this.username=username;
        fileForUsers=new FileForUsers();
        fileForBlockedUsers= new FileForBlockedUsers();
        fileForGruops= new FileForGruops();
        fileForFriends= new FileForFriends();
        this.user=fileForUsers.getUser(username);
        this.userFriendsArr=userFriends;
        fileForFriendsMassages=new FileForFriendsMassages();
        this.userFriends = FXCollections.observableArrayList(userFriends);
        addGroupsToTable();
        addFriendsToTable(this.userFriends);
    }

    private Stage dialogStage;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    private Button BTNShowchat;

    @FXML
    private TableView<Groups> GROUPTABLE;

    @FXML
    private TableColumn<User, String> friendculm;

    @FXML
    private TableColumn<Groups, String> groupColumn;

    @FXML
    private TableView<User> FREANDTABLE;

    public Stage getStage(){return this.primaryStage;}

    void addFriendsToTable(ObservableList<User> userFriends){
        FREANDTABLE.setItems(userFriends);
    }

    //block & unblock friends.

    @FXML
    void blockandUnblockBTNHandler(ActionEvent event) {
        User selectedUser = FREANDTABLE.getSelectionModel().getSelectedItem();
        if(selectedUser != null){
            if(!fileForBlockedUsers.isBlocked(fileForUsers.getUser(username) , selectedUser)){
                fileForBlockedUsers.blockFriend(fileForUsers.getUser(username),selectedUser);
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initOwner(dialogStage);
                alert.setTitle("Error");
                alert.setHeaderText("Please correct invalid fields");
                alert.setContentText("this  user is blocked");
                alert.showAndWait();
            }

            else {
                fileForBlockedUsers.unblockuser(fileForUsers.getUser(username),selectedUser);
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initOwner(dialogStage);
                alert.setTitle("Error");
                alert.setHeaderText("Please correct invalid fields");
                alert.setContentText("this  user is ublocked");
                alert.showAndWait();
            }
        }

        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Error");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText("Select a user");
            alert.showAndWait();
        }
    }

    public void addGroupsToTable(){
        ArrayList<Groups> groupsArr = new ArrayList<>();
        groupsArr.addAll(fileForGruops.getUserAllGroups(user));
        ObservableList<Groups> groups = FXCollections.observableArrayList(groupsArr);
        GROUPTABLE.setItems(groups);
    }

      //friends's pv chats

    @FXML
    void conversationsBTNHandler(ActionEvent event) throws IOException {
        User selectedUser = FREANDTABLE.getSelectionModel().getSelectedItem();
        if( selectedUser != null){
            FXMLLoader loader= new FXMLLoader(getClass().getResource("/FriendsChatPage.fxml"));
            loader.load();
            FriendsChatsController friendsChatsController = loader.getController();
            Stage stage = new Stage();
            stage.setScene(new Scene((Parent) loader.getRoot()));
            stage.setTitle("Conversation");
            stage.setResizable(false);
            friendsChatsController.initFunction(stage,fileForUsers.getUser(this.username) ,selectedUser,this);
            stage.show();
        }

        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Error");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText("Select a user");

            alert.showAndWait();
        }
    }

    //back to previous page

    @FXML
    void backBTNHandler(ActionEvent event) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/loginPage.fxml"));
        loader.load();
        LoginPageController loginPageController = loader.getController();
        primaryStage.setScene(new Scene((Parent) loader.getRoot()));
        primaryStage.setTitle("Login Page");
        primaryStage.setResizable(false);
        loginPageController.initFunction(primaryStage);
        primaryStage.show();
    }

      //opens the group's chats
    @FXML
    void showGroupBTNHandler(ActionEvent event) {
        Groups selectedGroup = GROUPTABLE.getSelectionModel().getSelectedItem();
        if(selectedGroup != null){
            if( fileForGruops.isAdmin(selectedGroup,user)){
                try {
                    FXMLLoader loader= new FXMLLoader(getClass().getResource("/GroupAdminChat.fxml"));
                    loader.load();
                    GropAdminChatPageController groupAdminChatPageController = loader.getController();
                    Stage stage= new Stage();
                    stage.setScene(new Scene((Parent) loader.getRoot()));
                    stage.setTitle("Conversation");
                    stage.setResizable(false);
                    groupAdminChatPageController.initFunction(stage,selectedGroup,user);
                    stage.show();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }

            else {
                try {
                    FXMLLoader loader= new FXMLLoader(getClass().getResource("/GroupMemberChatPage.fxml"));
                    loader.load();
                    GroupMemberChatPageController groupMemberChatPageController = loader.getController();
                    Stage stage= new Stage();
                    stage.setScene(new Scene((Parent) loader.getRoot()));
                    stage.setTitle("Conversation");
                    stage.setResizable(false);
                    groupMemberChatPageController.initFunction(stage,selectedGroup,user);
                    stage.show();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Error");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText("Select a group");

            alert.showAndWait();
        }
    }

    //delets the selected group

    @FXML
    void deleteBTNHandler(ActionEvent event) {
        Groups selectedGroup = GROUPTABLE.getSelectionModel().getSelectedItem();
        if(selectedGroup != null){
            if( fileForGruops.isAdmin(selectedGroup, user)) {
                fileForGroupMassages.clearHistory(selectedGroup);
                fileForGruops.removeGroup(selectedGroup);
                addGroupsToTable();
            }

            else
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initOwner(dialogStage);
                alert.setTitle("Error");
                alert.setHeaderText("Please correct invalid fields");
                alert.setContentText("You are not  this group's admin");
                alert.showAndWait();
            }
        }

        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Error");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText("Select a group");
            alert.showAndWait();
        }
    }

    //create a new group

    @FXML
    void newGroupHandler(ActionEvent event) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/AddANewGroupPage.fxml"));
        loader.load();
        AddANewGroupPageController addANewGroupPageController = loader.getController();
        Stage stage= new Stage();
        stage.setScene(new Scene((Parent) loader.getRoot()));
        stage.setTitle("New Group");
        stage.setResizable(false);
        addANewGroupPageController.initFunction(stage,user,this);
        stage.show();
    }

    //sends a freind request

    public void addFriendBTNHandler(ActionEvent event) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/FriendRequestSearchPage.fxml"));
        loader.load();
        Stage stage = new Stage();
        FriendRequestSearchPage friendRequestSearchPage = loader.getController();
        stage.setScene(new Scene((Parent) loader.getRoot()));
        stage.setTitle("Send Request");
        stage.setResizable(false);
        friendRequestSearchPage.initFunction(stage , username);
        stage.show();
    }

    //shows recieved friend requests

    public void showRequestsBTNHandler(ActionEvent event) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/RequestsPage.fxml"));
        loader.load();
        RequestsPageController requestsPageController = loader.getController();
        Stage stage = new Stage();
        stage.setScene(new Scene((Parent) loader.getRoot()));
        stage.setTitle("Requests");
        stage.setResizable(false);
        requestsPageController.initFunction1(stage ,username,this);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {friendculm.setCellValueFactory(new PropertyValueFactory<User,String>("username"));groupColumn.setCellValueFactory(new PropertyValueFactory<Groups,String>("groupName"));
    }
}
