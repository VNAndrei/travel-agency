package Controller;

import Client.ServicesProxy;
import laborator.services.IObserver;
import laborator.services.IServices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import laborator.model.Excursie;
import laborator.services.ServicesException;
import laborator.services.UpdateDTO;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class AgentController implements IObserver {
    ObservableList<Excursie> model= FXCollections.observableArrayList();
    private IServices service;

    public void setUser(String user) {
        this.user = user;
    }

    public void setLoginStage(Scene loginStage) {
        this.loginScene = loginStage;
    }

    private Scene loginScene;
    private String user;
    @FXML
    private TableView<Excursie> tabelExcursii;

    @FXML
    private TextField destinatieTxt;

    @FXML
    private DatePicker startDate;

    @FXML
    private DatePicker finalDate;

    @FXML
    private Label destinationLabel;

    @FXML
    private Label startLabel;

    @FXML
    private Label endLabel;

    @FXML
    private Hyperlink filterLink;

    @FXML
    private Button filterBtn;

    @FXML
    private Hyperlink deleteFilter;

    @FXML
    void filtreaza(ActionEvent event) throws ParseException, ServicesException {
        System.out.println(startDate.getValue());
        if(destinatieTxt.getText().isEmpty()||startDate.getValue()==null||finalDate.getValue()==null){
           Alert alert = new Alert(Alert.AlertType.WARNING);
           alert.setContentText("Introduceti toate datele pentru filtrare!!!!!!!!!!!!!!!");
           alert.show();
        }
        else {

            Iterable<Excursie> excursii=service.findByDate(destinatieTxt.getText(),startDate.getValue()+" 00:00",finalDate.getValue()+" 23:59");
            loadTable(excursii);
        }
    }

    @FXML
    void logout(ActionEvent event) throws ServicesException {
        service.logout(user,this);
        Stage stage = (Stage) startDate.getScene().getWindow();
        stage.setScene(loginScene);
        stage.show();
       // System.exit(0);
    }


    @FXML
    void loadTable(ActionEvent event) throws ServicesException {
        Iterable<Excursie> excursii=service.findAll();
        loadTable(excursii);
    }

    @FXML
    void openFilter(ActionEvent event) {
        destinatieTxt.opacityProperty().setValue(1);
        startLabel.opacityProperty().setValue(1);
        endLabel.opacityProperty().setValue(1);
        filterBtn.opacityProperty().setValue(1);
        destinationLabel.opacityProperty().setValue(1);
        deleteFilter.opacityProperty().setValue(1);
        startDate.opacityProperty().setValue(1);
        finalDate.opacityProperty().setValue(1);
        filterLink.opacityProperty().setValue(0);

    }

    @FXML
    void openRezervare(ActionEvent event) throws IOException, ServicesException {
        if(tabelExcursii.getSelectionModel().getSelectedItem()==null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Selectati o excursie");
            alert.show();
        }
        else {
        Stage stage = new Stage();
        FXMLLoader loader = new  FXMLLoader();
        loader.setLocation(ClassLoader.getSystemClassLoader().getResource("Views/rezervareView.fxml"));
        AnchorPane sp=  loader.load();
        RezervareController controller= loader.getController();
        //controller.setService(service);
        controller.setExcursie(tabelExcursii.getSelectionModel().getSelectedItem().getId());
        stage.setScene(new Scene(sp));
        stage.showAndWait();

        }
    }

    public void initialize() throws ServicesException {
        ApplicationContext a = new ClassPathXmlApplicationContext("App.xml");
        this.service= a.getBean(ServicesProxy.class);
        tabelExcursii.setItems(model);

        Iterable<Excursie> excursii=service.findAll();
        loadTable(excursii);
    }

    public void loadTable(Iterable<Excursie> excursies){

        List<Excursie> exList = StreamSupport.stream(excursies.spliterator(),false).collect(Collectors.toList());
        model.setAll(exList);
    }

    @Override
    public void updateNrLocuri(UpdateDTO updateDTO) throws ServicesException {
        Iterable<Excursie> excursii=service.findAll();
        loadTable(excursii);
    }
}