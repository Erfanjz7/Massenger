package Controller;

import Model.FileForGroupMassages;
import Model.FileForGruops;
import Model.Groups;
import Model.FileForGruops;
import Model.FileForGroupMassages;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class KickingMemberPage implements Initializable {

    private Stage kickStage;

    private Groups group;

    private FileForGruops fileForGruops;

    private GropAdminChatPageController controller;

    private Stage dialogStage;

    private FileForGroupMassages fileForGroupMassages=new FileForGroupMassages();

    public void initFunction(Stage kickStage , Groups group , GropAdminChatPageController controller){
        groupNameLBL.setText(group.getGroupName());
        this.kickStage= kickStage;
        this.group=group;
        this.fileForGruops= new FileForGruops();
        this.controller=controller;
        addToTable();
    }

    @FXML
    private Label errorLBL;

    @FXML
    private TableView<User> groupMembersTable;

    @FXML
    private TableColumn<User, String> membersColumn;

    @FXML
    private Label groupNameLBL;

    @FXML
    void closeHandler(ActionEvent event) {
        kickStage.close();
    }

    public void addToTable(){
        ArrayList<User> usersArrayList = new ArrayList<>();
        usersArrayList.addAll(fileForGruops.getGroupMembers(group.getId()));
        ObservableList<User> users = FXCollections.observableArrayList(usersArrayList);
        groupMembersTable.setItems(users);
    }

    @FXML
    void kickMemberHandler(ActionEvent event) {
        User selectedMember = groupMembersTable.getSelectionModel().getSelectedItem();
        if(selectedMember != null){

            Groups groupAfterKickMember = fileForGruops.kickMember(selectedMember, group);
            addToTable();
            controller.initFunction(controller.getStage() , groupAfterKickMember ,group.getAdmin());
            kickStage.close();

        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(" Select a member");
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        membersColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
    }
}
