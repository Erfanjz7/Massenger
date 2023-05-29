package Controller;

import Model.FileForGruops;
import Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;

public class LinkOpeningController {
    private Stage openLinkStage;

    private User user;

    private String linkAddress;

    private FileForGruops fileForGruops;

    ChatPageController chatPageController;

    @FXML
    private Hyperlink hyperlink;

    public void initFunction(Stage openLinkStage , String linkAddress , User user){
        this.openLinkStage=openLinkStage;
        hyperlink.setText(linkAddress);
        this.user=user;
        this.linkAddress=linkAddress;
        fileForGruops= new FileForGruops();
    }
    /**
     * bad az zadan roye link.
     */
    @FXML
    void hyperlinkHandler(ActionEvent event) {
        fileForGruops.addMemberToGroup(linkAddress,user);
        openLinkStage.close();
    }

}
