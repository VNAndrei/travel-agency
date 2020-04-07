package laborator.networking.dto;


import laborator.model.Agent;
import laborator.model.Excursie;
import laborator.model.Rezervare;

import java.util.Arrays;
import java.util.List;

public class DTOUtils {
    public static Agent getFromDTO(AgentDTO agentDTO){
        String user=agentDTO.getUsername();
        String pass=agentDTO.getPassword();
        return new Agent(user, pass);

    }
    public static AgentDTO getDTO(Agent user){
        String id=user.getUsername();
        String pass=user.getPassword();
        return new AgentDTO(id, pass);
    }

    public static Rezervare getFromDTO(RezervareDTO rezervareDTO){

        Integer id = rezervareDTO.getId();
        String numeClient = rezervareDTO.getNumeClient();
        String numeTuristi =rezervareDTO.getNumeTuristi();
        String telefon = rezervareDTO.getTelefon();
        Integer nrLocuri = rezervareDTO.getNrLocuri();
        Integer eId = rezervareDTO.geteId();
        return new Rezervare(id,numeClient,numeTuristi,telefon,nrLocuri,eId);

    }

    public static RezervareDTO getDTO(Rezervare rezervare){
        Integer id = rezervare.getId();
        String numeClient = rezervare.getNumeClient();
        String numeTuristi =rezervare.getStringTuristi();
        String telefon = rezervare.getTelefon();
        Integer nrLocuri = rezervare.getNrLocuri();
        Integer eId = rezervare.geteId();
        return new RezervareDTO(id, numeClient, numeTuristi,telefon,nrLocuri,eId);
    }

    public static ExcursieDTO[] getDTO(Excursie[] excursii){
        ExcursieDTO[] frDTO=new ExcursieDTO[excursii.length];
        for(int i=0;i<excursii.length;i++)
            frDTO[i]=getDTO(excursii[i]);
        return frDTO;
    }

    private static ExcursieDTO getDTO(Excursie excursie) {
        Integer id=excursie.getId();
        String destinatie = excursie.getDestinatie();
        Long data=excursie.getData();
        String aeroport = excursie.getAeroport();
        Integer nrLocuri = excursie.getNrLocuri();
        return new ExcursieDTO(id,destinatie,data,aeroport,nrLocuri);

    }

    public static Excursie[] getFromDTO(ExcursieDTO[] excursiiDTO){
        Excursie[] friends=new Excursie[excursiiDTO.length];
        for(int i=0;i<excursiiDTO.length;i++){
            friends[i]=getFromDTO(excursiiDTO[i]);
        }
        return friends;
    }

    private static Excursie getFromDTO(ExcursieDTO excursieDTO) {
        Integer id=excursieDTO.getId();
        String destinatie = excursieDTO.getDestinatie();
        Long data=excursieDTO.getData();
        String aeroport = excursieDTO.getAeroport();
        Integer nrLocuri = excursieDTO.getNrLocuri();
        return new Excursie(id,destinatie,data,aeroport,nrLocuri);
    }
}

