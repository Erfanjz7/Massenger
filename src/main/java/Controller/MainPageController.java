package Controller;

import Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;

public class MainPageController {

    private Stage primarystage = new Stage();

    private ArrayList<User> users = new ArrayList<>();

    private User user;

    @FXML
    private Button LognBTN;

    @FXML
    private Button SignupBTN;

    public void initFunction(ArrayList<User> users , User user){
        this.users = users;
        this.user = user;
    }

    //login ActionEvent button

    @FXML
    void loginBTNHandler(ActionEvent event) throws IOException {
        Stage stage=(Stage)LognBTN.getScene().getWindow();
        stage.close();
        FXMLLoader loader= new FXMLLoader(this.getClass().getResource("../LoginPage.fxml"));
        loader.load();
        LoginPageController loginPageController=loader.getController();
        loginPageController.initFunction(primarystage);
        primarystage.setScene(new Scene(loader.<Parent>getRoot()));
        primarystage.show();

    }

    //pressregester ActionEvent button

    @FXML
    void signupBTNHandler(ActionEvent event) throws IOException {
        Stage stage=(Stage)SignupBTN.getScene().getWindow();
        stage.close();
        FXMLLoader loader= new FXMLLoader(this.getClass().getResource("../SignUpPage.fxml"));
        loader.load();
        primarystage.setScene(new Scene(loader.<Parent>getRoot()));
        primarystage.show();
    }
}

