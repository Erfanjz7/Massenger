package Controller;

import Model.FileForGruops;
import Model.Regexes;
import Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddANewGroupPageController {

    private Stage dialogStage;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    private Stage addANewGroupPageStage;

    private Regexes regex;

    private User user;

    private FileForGruops fileForGruops;

    private ChatPageController controller;

    @FXML
    private TextField groupNameTXTFLD;

    @FXML
    private TextField groupLinkTXTFLD;

    public void initFunction(Stage addANewGroupPage , User user , ChatPageController controller){
        this.user = user;
        this.addANewGroupPageStage = addANewGroupPage;
        this.controller = controller;
        regex = new Regexes();
        fileForGruops = new FileForGruops();
    }

    @FXML
    void backBTNHandler(ActionEvent event) {
        addANewGroupPageStage.close();
    }

    //creates a group

    @FXML
    void createBTNHandler(ActionEvent event) {
        if(regex.groupNameRegex(groupNameTXTFLD.getText()) && regex.linkRegex(groupLinkTXTFLD.getText())){
            if(fileForGruops.linkNotExist(groupLinkTXTFLD.getText())){
                fileForGruops.addNewGroup(user,groupNameTXTFLD.getText(),groupLinkTXTFLD.getText());
                controller.addGroupsToTable();
                addANewGroupPageStage.close();
            }

            else
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initOwner(dialogStage);
                alert.setTitle("Error");
                alert.setHeaderText("Please correct invalid fields");
                alert.setContentText("Entered link exist");
                alert.showAndWait();
            }
        }

        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Error");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText("Group link without @");
            alert.showAndWait();
        }
    }
}
