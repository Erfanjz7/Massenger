package Controller;

import Model.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.stage.Stage;
import sun.applet.Main;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class FriendsChatsController implements Initializable {

    private Stage friendChatsPage;

    private User user;

    private User friend;

    private FileForUsers fileForUsers;

    private FileForFriendsMassages messageFile;

    private boolean edited = false;

    private boolean removed = false;

    private Massages selectedMessage;

    public FileForFriendsMassages fileForFriendsMassages=new FileForFriendsMassages();

    private Stage dialogStage;

    private ChatPageController chatPageController;

    FileForGruops fileForGruops=new FileForGruops();

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    private FileForBlockedUsers fileForBlockedUsers = new FileForBlockedUsers();

    public void initFunction(Stage firendsChatPage , User user , User friend , ChatPageController chatPageController){
        this.friendChatsPage = firendsChatPage;
        this.user = user;
        this.fileForUsers = new FileForUsers();
        this.friend = friend;
        this.chatPageController = chatPageController;
        usernameTitleLBL.setText(friend.getUsername());
        messageFile = new FileForFriendsMassages();
        addChatToTable();
    }

    @FXML
    private TableView<Massages> chatTable;

    @FXML
    private TableColumn<Massages, String> messageColumn;

    @FXML
    private TableColumn<Massages, String> dateAndTimeColumn;

    @FXML
    private TextField messageFLD;

    @FXML
    private Button sendBTN;

    @FXML
    private Label errorLBL;

    @FXML
    private TableColumn<Massages, String> senderUsernameColumn;

    @FXML
    private Label usernameTitleLBL;

    @FXML
    void closeHandler(ActionEvent event) {
        chatPageController.addGroupsToTable();
        friendChatsPage.close();
    }

    String getSystemDateTime(){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return (dateTimeFormatter.format(now));
    }

    public Stage getStage(){
        return friendChatsPage;
    }

    @FXML
    void sendHandler(ActionEvent event) {
        if(! fileForBlockedUsers.isBlocked(user,friend)){
            if(edited) {
                edited=false;
                messageFile.editMessage(this.selectedMessage,new Massages(selectedMessage.getSenderUsername(), selectedMessage.getReceiverUsername(),messageFLD.getText(), selectedMessage.getDateTime()));
                addChatToTable();
            }

            else {
                new Thread(){
                    public void run(){
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                String dateTime= getSystemDateTime();
                                String message = messageFLD.getText();
                                messageFile.addMessage(message,user.getUsername(),friend.getUsername(), dateTime);
                                addChatToTable();
                                hotKey(dateTime, message);
                            }
                        });
                        try {
                            Thread.sleep(10000);
                        } catch (InterruptedException e) {
                            System.out.println(e.getMessage());
                        }
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                addChatToTable();
                            }
                        });
                    }
                }.start();
            }
        }

        else
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(dialogStage);
            alert.setTitle("Error");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText("You can't send message to this user");

            alert.showAndWait();
        }
    }

    public void hotKey( String dateTime , String message){
        Scene scene= sendBTN.getScene();
        scene.getAccelerators().put(
                new KeyCodeCombination(KeyCode.B),
                new Runnable() {
                    @Override
                    public void run() {
                        fileForFriendsMassages.deleteMessage(new Massages(user.getUsername(),friend.getUsername() , message , dateTime));
                    }
                }
        );
    }

    public void addChatToTableAfterEditing(){
        ArrayList<Massages> messagesArrayList = new ArrayList<>();
        messagesArrayList.addAll(messageFile.getUserMessages(user.getId(),friend.getId()));
        ObservableList<Massages> messages= FXCollections.observableArrayList(messagesArrayList);
        chatTable.setItems(messages);
        messageFLD.setText("");
    }

    public void addChatToTable(){
        ArrayList<Massages> messagesArrayList = new ArrayList<>();
        messagesArrayList.addAll(messageFile.getUserMessages(user.getId(),friend.getId()));
        ObservableList<Massages> messages = FXCollections.observableArrayList(messagesArrayList);
        chatTable.setItems(messages);
        messageFLD.setText("");
    }

    @FXML
    void editHandler(ActionEvent event){
        this.selectedMessage = chatTable.getSelectionModel().getSelectedItem();
        if(this.selectedMessage != null){
            if(selectedMessage.getSenderUsername().equals(user.getUsername())){
                messageFLD.setText(selectedMessage.getMessage());
                edited=true;
            }

            else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initOwner(dialogStage);
                alert.setTitle("Error");
                alert.setHeaderText("Please correct invalid fields");
                alert.setContentText("You can not edit your friend Message ");
                alert.showAndWait();
            }
        }

        else
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(dialogStage);
            alert.setTitle("Error");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText("Select message ");
            alert.showAndWait();
        }
    }

    @FXML
    void clearHistoryHandler(ActionEvent event) throws IOException {
        FXMLLoader loader= new FXMLLoader(Main.class.getResource("/DeleteHistoryPage.fxml"));
        loader.load();
        DeleteHistoryPageController deleteHistoryPageController= loader.getController();
        Stage stage = new Stage();
        stage.setScene(new Scene((Parent) loader.getRoot()));
        stage.setTitle("Login Page");
        stage.setResizable(false);
        deleteHistoryPageController.initFunction(stage,user,friend,this , chatPageController);
        stage.show();
    }

    @FXML
    void deleteMessageHandler(ActionEvent event) {
        errorLBL.setText("");
        this.selectedMessage= chatTable.getSelectionModel().getSelectedItem();
        if(selectedMessage != null){
            if( selectedMessage.getSenderUsername().equals(user.getUsername())){
                messageFile.deleteMessage(this.selectedMessage);
                ArrayList<Massages> messagesAfterDeleting = messageFile.getUserMessages(fileForUsers.getUserId(selectedMessage.getSenderUsername()), fileForUsers.getUserId(selectedMessage.getReceiverUsername()));
                ObservableList<Massages> messages = FXCollections.observableArrayList(messagesAfterDeleting);
                chatTable.setItems(messages);
            }

            else
            {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initOwner(dialogStage);
                alert.setTitle("Error");
                alert.setHeaderText("Please correct invalid fields");
                alert.setContentText("you cant delete massege ");

                alert.showAndWait();
            }

        }

        else
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(dialogStage);
            alert.setTitle("Error");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText("Select message ");
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        senderUsernameColumn.setCellValueFactory(new PropertyValueFactory<Massages,String>("senderUsername"));
        messageColumn.setCellValueFactory(new PropertyValueFactory<Massages,String>("message"));
        dateAndTimeColumn.setCellValueFactory(new PropertyValueFactory<Massages,String>("dateTime"));
    }

    @FXML
    void openLinkHandler(ActionEvent event) throws IOException {
        Massages selectedMessage = chatTable.getSelectionModel().getSelectedItem();
        if(selectedMessage != null){
            Groups selectedGroup = fileForGruops.getGroup(selectedMessage.getMessage());
            if( ( selectedMessage.getMessage().charAt(0) == '@' ) &&
                    (!fileForGruops.linkNotExist(selectedMessage.getMessage()) && fileForGruops.isAdmin(selectedGroup,selectedMessage.getSender()) && isNotMember(user,selectedGroup) && !(selectedMessage.getSenderUsername().equals(user.getUsername())))){

                FXMLLoader loader= new FXMLLoader(Main.class.getResource("/LinkOpeningPage.fxml"));
                loader.load();
                LinkOpeningController linkOpeningController= loader.getController();
                Stage stage = new Stage();
                stage.setScene(new Scene((Parent) loader.getRoot()));
                stage.setTitle("Open Link");
                stage.setResizable(false);
                linkOpeningController.initFunction(stage,selectedMessage.getMessage() , user);
                stage.show();

            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initOwner(dialogStage);
                alert.setTitle("Error");
                alert.setHeaderText("Please correct invalid fields");
                alert.setContentText("Select link ");
                alert.showAndWait();
            }
        }

        else
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(dialogStage);
            alert.setTitle("Error");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText("Select message ");
            alert.showAndWait();
        }
    }

    public boolean isNotMember(User user , Groups group){
        if(group.getMembers() != null){

            for(int i=0 ; i < group.getMembers().size() ; i++){
                if(user.getId() == group.getMembers().get(i).getId()){
                    return false;
                }
            }
        }
        return true;
    }
}
