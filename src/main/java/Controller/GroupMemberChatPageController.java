package Controller;

import Model.Groups;
import Model.FileForGroupMassages;
import Model.Massages;
import Model.User;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.stage.Stage;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GroupMemberChatPageController implements Initializable {

    private Stage showChatStage;

    private Groups group;

    private FileForGroupMassages fileForGroupMassages;

    private User user;

    private Massages selectedMessage;

    private boolean edited=false;

    private Stage dialogStage;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    public void initFunction(Stage showChatStage , Groups group , User user){
        this.showChatStage=showChatStage;
        this.group=group;
        this.user=user;
        fileForGroupMassages=new FileForGroupMassages();
        groupNameLBL.setText(group.getGroupName());
        addChatToTable();
    }

    @FXML
    private TableView<Massages> chatTable;

    @FXML
    private Button sendBTN;

    @FXML
    private TableColumn<Massages, String> dateAndTimeColumn;

    @FXML
    private Label errorLBL;

    @FXML
    private Label groupNameLBL;

    @FXML
    private TableColumn<Massages, String> messageColumn;

    @FXML
    private TextField messageFLD;

    @FXML
    private TableColumn<Massages, String> senderUsernameColumn;

    @FXML
    void closeHandler(ActionEvent event) {
        showChatStage.close();
    }

    @FXML
    void deleteMessageHandler(ActionEvent event) {
        this.selectedMessage =chatTable.getSelectionModel().getSelectedItem();
        if(selectedMessage != null){
            if(selectedMessage.getSender().getId() == user.getId()){
                fileForGroupMassages.deleteMessage(selectedMessage);
                addChatToTable();
            }

            else
            { Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initOwner(dialogStage);
                alert.setTitle("Invalid Fields");
                alert.setHeaderText("Please correct invalid fields");
                alert.setContentText("You can't delete your friend message ");
                alert.showAndWait();}
        }

        else
        { Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText("Select a user ");
            alert.showAndWait();}
    }

    @FXML
    void editHandler(ActionEvent event) {
        this.selectedMessage = chatTable.getSelectionModel().getSelectedItem();
        if(selectedMessage != null){
            if(selectedMessage.getSenderUsername().equals(user.getUsername())){
                messageFLD.setText(selectedMessage.getMessage());
                edited=true;
            }

            else {

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initOwner(dialogStage);
                alert.setTitle("Invalid Fields");
                alert.setHeaderText("Please correct invalid fields");
                alert.setContentText("You can't edit your friend Message ");
                alert.showAndWait();
            }
        }

        else
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText("Select message ");
            alert.showAndWait();
        }
    }
    /**
     *  mohasebe time baraye ersal payam.
     */
    String getNowDateTime(){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return (dateTimeFormatter.format(now));
    }

    @FXML
    void sendHandler(ActionEvent event) {
        if(edited){
            fileForGroupMassages.editMessage(this.selectedMessage,new Massages(user.getUsername(),group.getId()
                    ,messageFLD.getText(),selectedMessage.getDateTime()));
            edited=false;
            addChatToTable();
        }
        else {
            /**
             * estefade az kelid B baraye pak kardan payam dar 10 sanie.
             */
            new Thread(){
                public void run(){
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            String dateTime= getNowDateTime();
                            String message = messageFLD.getText();
                            fileForGroupMassages.addMessage(message,user.getUsername(),group.getId(),
                                    dateTime);
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
    public void hotKey( String dateTime , String message) {
        Scene scene = sendBTN.getScene();
        scene.getAccelerators().put(
                new KeyCodeCombination(KeyCode.B),
                new Runnable() {
                    @Override
                    public void run() {
                        fileForGroupMassages.deleteMessage(new Massages(user.getUsername(), group.getId()
                                , message, dateTime));
                    }
                }
        );
    }
    public void addChatToTable(){
        ArrayList<Massages> messagesArrayList = new ArrayList<>();
        messagesArrayList.addAll(fileForGroupMassages.getGroupMessages(group.getId()));
        ObservableList<Massages> messages = FXCollections.observableArrayList(messagesArrayList);
        chatTable.setItems(messages);
        messageFLD.setText("");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        senderUsernameColumn.setCellValueFactory(new PropertyValueFactory<>("senderUsername"));
        messageColumn.setCellValueFactory(new PropertyValueFactory<>("message"));
        dateAndTimeColumn.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
    }
}
