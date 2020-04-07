package Controller;

import Client.ServicesProxy;
import laborator.model.Rezervare;
import laborator.services.IServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import laborator.services.ServicesException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RezervareController {
    private Integer eid;
    private IServices service;
    @FXML
    private TextField numeCTxt;

    @FXML
    private TextField telefonTxt;

    @FXML
    private Spinner<Integer> nrLocuriSpinner;

    @FXML
    private TextArea numeTTxt;

    @FXML
    private Button inregistrareBtn;

    @FXML
    void addRezervare(ActionEvent event) throws ServicesException {
        if(telefonTxt.getText().isEmpty()||numeCTxt.getText().isEmpty()||numeTTxt.getText().isEmpty()|| nrLocuriSpinner.getValue()<1){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("introduceti datele corespunzatoare");
            alert.show();
        }
       else{
           service.addRezervare(new Rezervare(1,numeCTxt.getText(),numeTTxt.getText(),telefonTxt.getText(),nrLocuriSpinner.getValue(),eid));
        Stage stage = (Stage) inregistrareBtn.getScene().getWindow();

        stage.close();}
    }


    public void  setExcursie(Integer id){
        this.eid= id;
    }
    public void  initialize(){
        ApplicationContext a = new ClassPathXmlApplicationContext("App.xml");
        this.service= a.getBean(ServicesProxy.class);
        SpinnerValueFactory<Integer> valueFactory1=new SpinnerValueFactory.IntegerSpinnerValueFactory(1,100,1);
        nrLocuriSpinner.setValueFactory(valueFactory1);
    }
}