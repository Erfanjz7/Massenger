package Controller;

import Model.FileForFriendsMassages;
import Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class DeleteHistoryPageController {

    private Stage deleteHistory;

    private FileForFriendsMassages fileForFriendsMassages;

    private User user;

    private User friend;

    private FriendsChatsController friendsChatsController;

    private ChatPageController chatPageController;

    public void initFunction(Stage clearHistoryStage , User user , User friend , FriendsChatsController friendsChatsController , ChatPageController chatPageController){
        this.deleteHistory = clearHistoryStage;
        fileForFriendsMassages = new FileForFriendsMassages();
        this.friendsChatsController = friendsChatsController;
        this.user = user;
        this.chatPageController = chatPageController;
        this.friend = friend;
    }

    @FXML
    void yesBTNHandler(ActionEvent event) {
        fileForFriendsMassages.clearHistory(user,friend);
        friendsChatsController.initFunction(friendsChatsController.getStage(),user,friend ,chatPageController);
        deleteHistory.close();
    }

    @FXML
    void noBTNHandler(ActionEvent event) {
        deleteHistory.close();
    }
}
