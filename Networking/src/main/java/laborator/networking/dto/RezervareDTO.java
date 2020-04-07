package laborator.networking.dto;

import java.io.Serializable;

public class RezervareDTO implements Serializable {
    private Integer id;
    private String numeClient;

    public RezervareDTO(Integer id, String numeClient, String numeTuristi, String telefon,  Integer nrLocuri,Integer eId) {
        this.id = id;
        this.numeClient = numeClient;
        this.numeTuristi = numeTuristi;
        this.telefon = telefon;
        this.eId = eId;
        this.nrLocuri = nrLocuri;
    }

    private String numeTuristi;
    private String telefon;
    private Integer eId;
    private Integer nrLocuri;



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
    public String getNumeTuristi() {
        return numeTuristi;
    }

    public void setNumeTuristi(String numeTuristi) {
        this.numeTuristi = numeTuristi;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }


    public Integer geteId() {
        return eId;
    }

    public void seteId(Integer eId) {
        this.eId = eId;
    }

    public Integer getNrLocuri() {
        return nrLocuri;
    }

    public void setNrLocuri(Integer nrLocuri) {
        this.nrLocuri = nrLocuri;
    }

    @Override
    public String toString() {
        return "RezervareDTO["+id+' '+numeClient+' '+numeTuristi+' '+telefon+' '+nrLocuri+' '+eId+']';
    }
}
