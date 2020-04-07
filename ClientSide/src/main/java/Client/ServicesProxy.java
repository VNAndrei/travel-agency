package Client;

import javafx.application.Platform;
import laborator.model.Agent;
import laborator.model.Excursie;
import laborator.model.Rezervare;
import laborator.networking.dto.*;
import laborator.services.IServices;
import laborator.services.IObserver;
import laborator.networking.protocol.*;
import laborator.services.ServicesException;
import laborator.services.UpdateDTO;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ServicesProxy implements IServices{
    private String host;
    private int port;

    private IObserver client;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Socket connection;

    private BlockingQueue<Response> qresponses;
    private volatile boolean finished;
    public ServicesProxy(String host, int port) {
        this.host = host;
        this.port = port;
        qresponses=new LinkedBlockingQueue<Response>();
    }

    @Override
    public void logout(String user,IObserver client) throws ServicesException {
        AgentDTO agentDTO= DTOUtils.getDTO(new Agent(user,""));
        Request req=new Request.Builder().type(RequestType.LOGOUT).data(agentDTO).build();
        sendRequest(req);
        Response response=readResponse();
        closeConnection();
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            throw new ServicesException(err);
        }




    }
    private void sendRequest(Request request)throws ServicesException {
        try {
            initializeConnection();
            output.writeObject(request);
            output.flush();
        } catch (IOException e) {
            throw new ServicesException("Error sending object "+e);
        }

    }

    @Override
    public Boolean login(String username, String password, IObserver user) throws ServicesException {



        AgentDTO agentDTO = new AgentDTO(username,password);
        Request request= new Request.Builder().type(RequestType.LOGIN).data(agentDTO).build();
        sendRequest(request);
        Response response= readResponse();
        if(response.type()==ResponseType.OK){
            this.client=user;
            return true;
        }
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            closeConnection();
            throw new ServicesException(err);
        }
        return false;
    }


    @Override
    public Iterable<Excursie> findAll() throws ServicesException {
        Request request= new Request.Builder().type(RequestType.GET_ALL_EXCURSII).data(null).build();
        sendRequest(request);
        Response response= readResponse();
        if(response.type()==ResponseType.ERROR){
            String err = response.data().toString();
            throw new ServicesException(err);
        }
        ExcursieDTO[] excursiiDTO = (ExcursieDTO[]) response.data();
        Excursie[] exxcursii = DTOUtils.getFromDTO(excursiiDTO);
        return Arrays.asList(exxcursii);
    }

    @Override
    public Iterable<Excursie> findByDate(String destinatie, String begin, String end) throws ServicesException {
        FilterDTO filter= new FilterDTO(destinatie,begin,end);
        Request request= new Request.Builder().type(RequestType.GET_BY_DATE).data(filter).build();
        sendRequest(request);
        Response response= readResponse();
        if(response.type()==ResponseType.ERROR){
            String err = response.data().toString();
            throw new ServicesException(err);
        }
        ExcursieDTO[] excursiiDTO = (ExcursieDTO[]) response.data();
        Excursie[] excursii = DTOUtils.getFromDTO(excursiiDTO);
        return Arrays.asList(excursii);
    }

    @Override
    public void addRezervare(Rezervare r) throws ServicesException {
        RezervareDTO rezervareDTO= DTOUtils.getDTO(r);
        Request request = new Request.Builder().type(RequestType.ADD_REZERVARE).data(rezervareDTO).build();
        sendRequest(request);
        Response response= readResponse();
    }

    private void initializeConnection() throws ServicesException {
        try {
            connection=new Socket(host,port);
            output=new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input=new ObjectInputStream(connection.getInputStream());
            finished=false;
            startReader();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void startReader(){
        Thread tw=new Thread(new ReaderThread());
        tw.start();
    }
    private void closeConnection() {
        finished=true;
        try {
            input.close();
            output.close();
            connection.close();
            client=null;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private Response readResponse() throws ServicesException {
        Response response=null;
        try{

            response=qresponses.take();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }
    private void handleUpdate(Response response){
        UpdateDTO update= (UpdateDTO) response.data();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    client.updateNrLocuri(update);
                } catch (ServicesException e) {
                    e.printStackTrace();
                }
            }
        });

    }
    private class ReaderThread implements Runnable{
        public void run() {
            while(!finished){
                try {
                    Object response=input.readObject();
                    System.out.println("response received "+response);
                    if (((Response)response).type()==ResponseType.UPDATE){
                        handleUpdate((Response)response);
                    }else{

                        try {
                            qresponses.put((Response)response);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Reading error "+e);
                } catch (ClassNotFoundException e) {
                    System.out.println("Reading error "+e);
                }
            }
        }
    }
}
