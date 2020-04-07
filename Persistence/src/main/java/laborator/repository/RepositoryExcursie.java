package laborator.repository;

import laborator.model.Excursie;
import laborator.repository.Utils.JdbcUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class RepositoryExcursie implements IExcursieRepository {

    private JdbcUtils dbUtils;
    private String selectByDestinationString="select * from Excursii where destinatie=? and data > ? and data < ?";
    private String selectAllString="select * from Excursii";
    private String updateNrLocuriString="update Excursii set nrLocuri= nrLocuri-? where id =?";
    private  static  final Logger logger= LogManager.getLogger();


    public RepositoryExcursie(Properties properties){
        logger.info("Initializing SortingTaskRepository with properties: {} ",properties);
        dbUtils=new JdbcUtils(properties);
    }



    public Iterable<Excursie> findByDestinationAndDate(String destinatie, Long begin, Long end) {

        Connection con=dbUtils.getConnection();
        List<Excursie> excursii=new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement(selectByDestinationString)) {


            preStmt.setString(1,destinatie);
            preStmt.setLong(2,begin);
            preStmt.setLong(3,end);
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {
                    Integer id = result.getInt("id");
                    String dest = result.getString("destinatie");
                    Long data = result.getLong("data");
                    String aeroport = result.getString("aeroport");
                    Integer nrLocuri = result.getInt("nrLocuri");
                    Excursie e = new Excursie(id,destinatie,data,aeroport,nrLocuri);
                    excursii.add(e);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB "+e);
        }
        logger.traceExit(excursii);
        return excursii;
    }
    public Iterable<Excursie> findAll() {

        Connection con=dbUtils.getConnection();
        List<Excursie> excursii=new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement(selectAllString)) {

            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {
                    Integer id = result.getInt("id");
                    String destinatie = result.getString("destinatie");
                    Long data =result.getLong("data");
                    String aeroport = result.getString("aeroport");
                    Integer nrLocuri = result.getInt("nrLocuri");
                    Excursie e = new Excursie(id,destinatie,data,aeroport,nrLocuri);
                    excursii.add(e);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB "+e);
        }
        logger.traceExit(excursii);
        return excursii;
    }
    public void updateNumarLocuri(Integer id,Integer nr) {

        Connection con=dbUtils.getConnection();

        try(PreparedStatement preStmt=con.prepareStatement(updateNrLocuriString)) {
            preStmt.setInt(1,nr);
            preStmt.setInt(2,id);
            preStmt.executeUpdate();

        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB "+e);
        }
    }

}
