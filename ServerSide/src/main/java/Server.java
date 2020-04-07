import laborator.networking.utils.AbstractServer;
import laborator.networking.utils.ConcurrentServer;
import laborator.networking.utils.ServerException;
import laborator.server.Service;
import laborator.services.IServices;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Server {
    private static int defaultPort=55545;


    public static void main(String[] argv){
        ApplicationContext a = new ClassPathXmlApplicationContext("App.xml");
        IServices service= a.getBean(Service.class);

        System.out.println("Starting server on port: "+defaultPort);
        AbstractServer server = new ConcurrentServer(defaultPort, service);
        try {
            server.start();
        } catch (ServerException e) {
            System.err.println("Error starting the server" + e.getMessage());
        }finally {
            try {
                server.stop();
            } catch (ServerException e) {
                System.err.println("Error stopping server " + e.getMessage());
            }

        }
    }
}