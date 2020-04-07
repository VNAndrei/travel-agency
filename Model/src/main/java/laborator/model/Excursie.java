package laborator.model;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

public class Excursie {
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private Integer id;
    private String destinatie;
    private Long data;
    private String aeroport;
    private Integer nrLocuri;

    public Excursie(Integer id,String destinatie, String data, String aeroport, Integer nrLocuri) {
        this.id=id;
        this.destinatie = destinatie;
        setData(data);
        this.aeroport = aeroport;
        this.nrLocuri = nrLocuri;
    }
    public Excursie(Integer id,String destinatie, Long data, String aeroport, Integer nrLocuri) {
        this.id=id;
        this.destinatie = destinatie;
        this.data=data;
        this.aeroport = aeroport;
        this.nrLocuri = nrLocuri;
    }

    public String getDestinatie() {
        return destinatie;
    }

    public void setDestinatie(String destinatie) {
        this.destinatie = destinatie;
    }

    public String getDataString() {
        Date d=new Date(data);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");
        return sdf.format(d);
    }
    public Long getData() {
        return  data;
    }

    public void setData(String data) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");

        Date date1 = null;
        try {
            date1 = sdf.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        this.data = date1.getTime();
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
        return
                ", destinatie='" + destinatie + '\'' +
                ", data=" + this.getDataString() +
                ", aeroport='" + aeroport + '\'' +
                ", nrLocuri=" + nrLocuri
                ;
    }
}
