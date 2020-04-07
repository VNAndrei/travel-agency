package laborator.services;

import java.io.Serializable;

public class UpdateDTO implements Serializable {
    private Integer eid;
    private Integer numarLocuri;
    public UpdateDTO(Integer eid, Integer numarLocuri) {
        this.eid = eid;
        this.numarLocuri = numarLocuri;
    }

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public Integer getNumarLocuri() {
        return numarLocuri;
    }

    public void setNumarLocuri(Integer numarLocuri) {
        this.numarLocuri = numarLocuri;
    }

    @Override
    public String toString() {
        return "UpdateDTO["+eid+' '+numarLocuri+']';
    }
}
