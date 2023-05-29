import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        FXMLLoader loader= new FXMLLoader(this.getClass().getResource("MainPage.fxml"));
        try {
            loader.load();
        }catch (IOException e){
            e.printStackTrace();
        }
        primaryStage.setScene(new Scene((Parent) loader.getRoot()));
        primaryStage.setTitle("Erfanjz7's Massenger");
        primaryStage.setResizable(false);
        primaryStage.show();

    }
}
