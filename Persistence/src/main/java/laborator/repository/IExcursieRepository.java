package laborator.repository;
import laborator.model.Excursie;

public interface IExcursieRepository{
    public Iterable<Excursie> findByDestinationAndDate(String destinatie, Long begin, Long end);
    public Iterable<Excursie> findAll();
    public void updateNumarLocuri(Integer id, Integer nr);
}
