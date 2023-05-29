package Controller;

import Model.Regexes;
import Model.User;
import Model.FileForUsers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;
import java.util.Random;

public class SignUpController {
    private Stage primaryStage = new Stage();

    Regexes regex = new Regexes();

    private Stage dialogStage;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    FileForUsers fileForUsers = new FileForUsers();

    String Code1;

    @FXML
    private TextField TXTName;

    @FXML
    private TextField TXTEmail;

    @FXML
    private Button btnback;

    @FXML
    private TextField TXTUsername;

    @FXML
    private Button BTNregester;

    @FXML
    private TextField TXTLastName;

    @FXML
    private TextField TXTcode;

    @FXML
    private TextField TXTPassword;

    @FXML
    private Button BTNcheck;
    /**
     * regester ActionEvent button
     */

    @FXML
    void signupBTNHandler(ActionEvent event) throws IOException {
        if (isInputValid()) {
            String password = doHashing(TXTPassword.getText());
            User user = new User(TXTName.getText(), TXTLastName.getText(), TXTUsername.getText(),password, TXTEmail.getText());
            if( fileForUsers.userNotExist(user)&&fileForUsers.emailNotExist(TXTEmail.getText())){
                fileForUsers.addUser(user);
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initOwner(dialogStage);
                alert.setTitle("Error");
                alert.setHeaderText("Please correct invalid fields");
                alert.setContentText("Username alreaady exist");
                alert.showAndWait();
            }
            Stage stage = (Stage) BTNregester.getScene().getWindow();
            stage.close();
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../MainPage.fxml"));
            loader.load();
            primaryStage.setScene(new Scene((Parent) loader.getRoot()));
            primaryStage.setTitle("Erfanjz7's Massenger");
            primaryStage.show();
        }
    }
    /**
     * ersal email
     */
    @FXML
    void send4DigitNumberHandler(ActionEvent event) {

        if(TXTEmail.getText() != null && TXTEmail.getText().length() != 0&&regex.EmailRegex(TXTEmail.getText()) == true){
            sendemail(TXTEmail.getText());
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Error");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText("Invalid E-mail");
            alert.showAndWait();

        }
    }

    @FXML
    void backBTNHandler(ActionEvent event) throws IOException {
        Stage stage = (Stage) btnback.getScene().getWindow();
        stage.close();
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../MainPage.fxml"));
        loader.load();
        primaryStage.setScene(new Scene((Parent) loader.getRoot()));
        primaryStage.setTitle("Erfanjz7's Massenger");
        primaryStage.show();
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (TXTName.getText() == null || TXTLastName.getText().length() == 0 || regex.nameRegex(TXTName.getText()) == false) {
            errorMessage += "Invalid first name!\n";
        }

        if (TXTLastName.getText() == null || TXTLastName.getText().length() == 0 || regex.nameRegex(TXTLastName.getText()) == false) {
            errorMessage += "Invalid last name!\n";
        }

        if (TXTUsername.getText() == null || TXTUsername.getText().length() == 0 || regex.UserNameRegex(TXTUsername.getText()) == false) {
            errorMessage += "Invalid USERNAME!\n";
        }

        if (TXTPassword.getText() == null || TXTPassword.getText().length() == 0 || regex.numberRegex(TXTPassword.getText()) == false) {
            errorMessage += "Invalid PASSWORD!\n";

        }
        if (TXTEmail.getText() == null || TXTEmail.getText().length() == 0 || regex.EmailRegex(TXTEmail.getText()) == false||fileForUsers.emailNotExist(TXTEmail.getText())==false) {
            errorMessage += "Invalid Email!\n";

        }
        if (TXTcode.getText() == null || TXTcode.getText().length() == 0||TXTcode.getText().equals(Code1)==false) {
            errorMessage += "Invalid code!\n";

        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Error");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }


    }
    public String doHashing(String password){
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA");
            messageDigest.update(password.getBytes());
            byte[] bytes = messageDigest.digest();
            StringBuilder stringBuilder = new StringBuilder();
            for(byte b : bytes){
                stringBuilder.append(String.format("%02x",b));
            }
            return String.valueOf(stringBuilder);

        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void sendemail(String email){
        String to = email;

        // Sender's email ID needs to be mentioned
        String from = "zahramehdipoor13@gmail.com";

        // Assuming you are sending email from through gmails smtp
        String host = "smtp.gmail.com";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication("erfanbery@gmail.com", "kelase6om");

            }

        });

        // Used to debug SMTP issues
        session.setDebug(true);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("This is the Subject Line!");
            int a[]=new int[4];
            Random rand=new Random();
            String code="";
            for(int i=0;i<4;i++) {
                a[i] = rand.nextInt(10);
                String s = Integer.toString(a[i]);
                code += s;
            }
            Code1=code;
            // Now set the actual message
            message.setText(code);

            System.out.println("sending...");
            // Send message

            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }

    }

}