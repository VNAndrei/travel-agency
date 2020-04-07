package laborator.networking.dto;

import java.io.Serializable;

public class FilterDTO implements Serializable {
    public FilterDTO(String destinatie, String begin, String end) {
        this.destinatie = destinatie;
        this.begin = begin;
        this.end = end;
    }

    private String destinatie;
    private String begin;
    private String end;

    public String getDestinatie() {
        return destinatie;
    }

    public void setDestinatie(String destinatie) {
        this.destinatie = destinatie;
    }

    public String getBegin() {
        return begin;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
