package laborator.server;

import laborator.model.Agent;
import laborator.model.Excursie;
import laborator.model.Rezervare;
import laborator.repository.IAgentRepository;
import laborator.repository.IExcursieRepository;
import laborator.repository.IRezervareRepository;
import laborator.services.IObserver;
import laborator.services.IServices;
import laborator.services.ServicesException;
import laborator.services.UpdateDTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Service implements IServices {
    private IAgentRepository repoAgent;
    private IExcursieRepository repoExcursie;
    private IRezervareRepository repoRezervare;
    private Map<String, IObserver> loggedClients;

    public Service(IAgentRepository repoAgent, IExcursieRepository repoExcursie, IRezervareRepository repoRezervare) {
        this.repoAgent = repoAgent;
        this.repoExcursie = repoExcursie;
        this.repoRezervare = repoRezervare;
        loggedClients = new HashMap<>();
    }

    @Override
    public Boolean login(String username, String password, IObserver user) throws ServicesException {
        Agent a=repoAgent.findByUser(username);
        if(a!=null) {
            if (loggedClients.get(username) != null)
                throw new ServicesException("Userul este deja conectat");
            loggedClients.put(username, user);
            return true;
        }else
            throw new ServicesException("Autentificare esuata");
    }

    @Override
    public synchronized void logout(String username, IObserver user) throws ServicesException {
        IObserver localClient=loggedClients.remove(username);
        if (localClient==null)
            throw new ServicesException("User "+user+" is not logged in.");

    }

    public synchronized Iterable<Excursie> findAll(){
        Iterable<Excursie> ex=repoExcursie.findAll();
        return ex;
    }
    public synchronized Iterable<Excursie> findByDate(String destinatie, String begin, String end){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");

        Date date1 = null;
        Date date2 = null;

        try {
            date1 = sdf.parse(begin);
            date2 = sdf.parse(end);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return  repoExcursie.findByDestinationAndDate(destinatie,date1.getTime(),date2.getTime());
    }

    @Override
    public synchronized void addRezervare(Rezervare r) throws ServicesException {
        if(repoRezervare.save(r)){
            repoExcursie.updateNumarLocuri(r.geteId(),r.getNrLocuri());
            UpdateDTO update=new UpdateDTO(r.geteId(),r.getNrLocuri());
            for (IObserver client: loggedClients.values()) {
                client.updateNrLocuri(update);
            }
        }
    }



}
