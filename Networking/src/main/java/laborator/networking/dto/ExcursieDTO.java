package laborator.networking.dto;

import java.io.Serializable;

public class ExcursieDTO implements Serializable {
    private String aeroport;
    private Integer id;
    private String destinatie;
    private Long data;

    public ExcursieDTO( Integer id, String destinatie, Long data,String aeroport, Integer nrLocuri) {
        this.aeroport = aeroport;
        this.id = id;
        this.destinatie = destinatie;
        this.data = data;
        this.nrLocuri = nrLocuri;
    }

    private Integer nrLocuri;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDestinatie() {
        return destinatie;
    }

    public void setDestinatie(String destinatie) {
        this.destinatie = destinatie;
    }

    public Long getData() {
        return data;
    }

    public void setData(Long data) {
        this.data = data;
    }

    public String getAeroport() {
        return aeroport;
    }

    public void setAeroport(String aeroport) {
        this.aeroport = aeroport;
    }

    public Integer getNrLocuri() {
        return nrLocuri;
    }

    public void setNrLocuri(Integer nrLocuri) {
        this.nrLocuri = nrLocuri;
    }

    @Override
    public String toString() {
        return "ExcursieDTO["+id+' '+destinatie+' '+data+' '+aeroport+' '+nrLocuri+']';
    }
}
