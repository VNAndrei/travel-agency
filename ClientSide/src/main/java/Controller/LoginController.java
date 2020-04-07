package Controller;

import Client.ServicesProxy;

import javafx.scene.control.*;
import laborator.services.IServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import laborator.services.ServicesException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class LoginController {
    private IServices service;
    @FXML
    private Label warningLabel;

    @FXML
    private PasswordField passwordTxt;

    @FXML
    private TextField userTxt;

    @FXML
    private Button loginBtn;

    @FXML
    void login(ActionEvent event) throws IOException, ServicesException {
        if(passwordTxt.getText().isEmpty()|| userTxt.getText().isEmpty()){
            warningLabel.setTextFill(Color.RED);
        }
        else {
            String user=userTxt.getText();
            Stage stage = (Stage) passwordTxt.getScene().getWindow();
            FXMLLoader loader = new  FXMLLoader();
            loader.setLocation(ClassLoader.getSystemClassLoader().getResource("Views/agentView.fxml"));

            AgentController controller= loader.getController();

            try {
                    service.login(userTxt.getText(),passwordTxt.getText(),controller);
                    AnchorPane sp=  loader.load();
                    controller= loader.getController();
                    controller.setUser(user);
                    controller.setLoginStage(userTxt.getScene());
                    stage.setScene(new Scene(sp));

            }
            catch (ServicesException e){
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setHeaderText(e.getMessage());
                a.setContentText("User sau parola incorecte");
                a.showAndWait();
            }

        }
    }

    public void initialize(){
        ApplicationContext a = new ClassPathXmlApplicationContext("App.xml");
        this.service= a.getBean(ServicesProxy.class);



    }

}
