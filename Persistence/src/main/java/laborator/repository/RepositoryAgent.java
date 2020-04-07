package laborator.repository;

import laborator.model.Agent;
import laborator.repository.Utils.JdbcUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class RepositoryAgent implements IAgentRepository {
    private JdbcUtils dbUtils;

    private String selectByUser="select * from Agenti where user=?;";
    private  static  final Logger logger= LogManager.getLogger();

    public RepositoryAgent(Properties properties){
        logger.info("Initializing SortingTaskRepository with properties: {} ",properties);
        dbUtils=new JdbcUtils(properties);
    }




    public Agent findByUser(String user) {

        Connection con=dbUtils.getConnection();
        Agent a=null;
        try(PreparedStatement preStmt=con.prepareStatement(selectByUser)) {
            preStmt.setString(1,user);
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {

                    String username = result.getString("user");
                    String pass = result.getString("password");
                    a= new Agent(username,pass);

                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB "+e);
        }
        logger.traceExit(a);
        return a;
    }

}
