
import Client.ServicesProxy;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import laborator.services.IServices;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class Application  extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new  FXMLLoader();
        System.out.println(getClass());
        loader.setLocation(getClass().getResource("Views/loginView.fxml"));
        BorderPane sp=  loader.load();
        stage.setScene(new Scene(sp));
        stage.setOnCloseRequest(windowEvent -> {System.exit(0);});
        stage.show();

    }

    public static void main(String[] argv){


        launch(argv);


    }
}