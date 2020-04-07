package laborator.model;

import java.util.Arrays;
import java.util.List;

public class Rezervare {


    private Integer id;
    private String numeClient;
    private List<String> numeTuristi;
    private String telefon;
    private Integer nrLocuri;
    private Integer eId;
    public Rezervare(Integer id, String numeClient, List<String> numeTuristi, String telefon, Integer nrLocuri, Integer eId) {
        this.id = id;
        this.numeClient = numeClient;
        this.numeTuristi = numeTuristi;
        this.telefon = telefon;
        this.nrLocuri = nrLocuri;
        this.eId = eId;
    }
    public Rezervare(Integer id, String numeClient,String numeTuristi, String telefon, Integer nrLocuri, Integer eId) {
        this.id = id;
        this.numeClient = numeClient;
        this.numeTuristi = Arrays.asList(numeTuristi.split(";"));
        this.telefon = telefon;
        this.nrLocuri = nrLocuri;
        this.eId = eId;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumeClient() {
        return numeClient;
    }

    public void setNumeClient(String numeClient) {
        this.numeClient = numeClient;
    }

    public List<String> getNumeTuristi() {
        return numeTuristi;
    }
    public String getStringTuristi() {
        StringBuilder sb= new StringBuilder();
        for(String turist: numeTuristi){
            sb.append(turist+";");
        }
        return sb.toString();
    }

    public void setNumeTuristi(List<String> numeTuristi) {
        this.numeTuristi = numeTuristi;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public Integer getNrLocuri() {
        return nrLocuri;
    }

    public void setNrLocuri(Integer nrLocuri) {
        this.nrLocuri = nrLocuri;
    }

    public Integer geteId() {
        return eId;
    }

    public void seteId(Integer eId) {
        this.eId = eId;
    }
}
