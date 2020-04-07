package laborator.services;


import laborator.model.Excursie;
import laborator.model.Rezervare;

public interface IServices {
    public Boolean login(String username,String password,IObserver user)throws ServicesException;
    public void logout(String username,IObserver user)throws ServicesException;
    public Iterable<Excursie> findAll()throws ServicesException;
    public Iterable<Excursie> findByDate(String destinatie, String begin, String end)throws ServicesException;
    public void addRezervare(Rezervare r)throws ServicesException;
}
