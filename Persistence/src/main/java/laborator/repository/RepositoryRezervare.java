package laborator.repository;

import laborator.model.Rezervare;
import laborator.repository.Utils.JdbcUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class RepositoryRezervare implements IRezervareRepository {
    private JdbcUtils dbUtils;
    private String insertString="insert into Rezervari(numeClient, numeTuristi, numarTelefon, nrLocuri, eid) values (?,?,?,?,?);";
    private  static  final Logger logger= LogManager.getLogger();


    public RepositoryRezervare(Properties properties){
        logger.info("Initializing SortingTaskRepository with properties: {} ",properties);
        dbUtils=new JdbcUtils(properties);
    }

    public Boolean save(Rezervare rezervare){
        logger.traceEntry("saving task {} ",rezervare);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement(insertString)){

            preStmt.setString(1,rezervare.getNumeClient());
            preStmt.setString(2,rezervare.getStringTuristi());
            preStmt.setString(3,rezervare.getTelefon());
            preStmt.setInt(4,rezervare.getNrLocuri());
            preStmt.setInt(5,rezervare.geteId());
            int result=preStmt.executeUpdate();

        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
            return false;

        }
        logger.traceExit();
        return true;


    }


}
