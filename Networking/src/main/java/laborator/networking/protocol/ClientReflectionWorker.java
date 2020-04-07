package laborator.networking.protocol;


import laborator.model.Agent;
import laborator.model.Excursie;
import laborator.model.Rezervare;
import laborator.networking.dto.*;
import laborator.services.ServicesException;
import laborator.services.IServices;
import laborator.services.IObserver;
import laborator.services.UpdateDTO;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


public class ClientReflectionWorker implements Runnable, IObserver {
    private IServices server;
    private Socket connection;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean connected;


    private static Response okResponse=new Response.Builder().type(ResponseType.OK).build();


    public ClientReflectionWorker(IServices server, Socket connection) {
        this.server = server;
        this.connection = connection;
        try{
            output=new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input=new ObjectInputStream(connection.getInputStream());
            connected=true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while(connected){
            try {
                Object request=input.readObject();
                Response response=handleRequest((Request)request);
                if (response!=null){
                    sendResponse(response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            input.close();
            output.close();
            connection.close();
        } catch (IOException e) {
            System.out.println("Error "+e);
        }
    }

    private Response handleRequest(Request request){
        Response response=null;
        String handlerName="handle"+(request).type();
        System.out.println("HandlerName "+handlerName);
        try {
            Method method=this.getClass().getDeclaredMethod(handlerName, Request.class);
            response=(Response)method.invoke(this,request);
            System.out.println("Method "+handlerName+ " invoked");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return response;
    }

    private Response handleLOGIN(Request request){
        System.out.println("Login request ..."+request.type());
        AgentDTO agentDTO=(AgentDTO) request.data();
        Agent user= DTOUtils.getFromDTO(agentDTO);
        try {
            server.login(user.getUsername(),user.getPassword(), this);
            return okResponse;
        } catch (ServicesException e) {
            connected=false;
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleLOGOUT(Request request){
        System.out.println("Logout request...");
        AgentDTO agentDTO=(AgentDTO) request.data();
        Agent user=DTOUtils.getFromDTO(agentDTO);
        try {
            server.logout(user.getUsername(), this);
            connected=false;
            return okResponse;

        } catch (ServicesException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }
    private Response handleADD_REZERVARE(Request request){
        System.out.println("SendMessageRequest ...");
        RezervareDTO rezervareDTO=(RezervareDTO) request.data();
        Rezervare rezervare=DTOUtils.getFromDTO(rezervareDTO);
        try {
            server.addRezervare(rezervare);
            return okResponse;
        } catch (ServicesException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }


    }

    private Response handleGET_ALL_EXCURSII(Request request){
        System.out.println("GetALLEXCURSII Request ...");
        try {

            Iterable<Excursie> excursiiIt=server.findAll();
            ArrayList<Excursie> list = (ArrayList<Excursie>) StreamSupport.stream(excursiiIt.spliterator(),false).collect(Collectors.toList());
            Excursie[] excursii= new Excursie[list.size()];
            int i = 0;
            for(Excursie e:excursiiIt){
                excursii[i]=e;
                i++;
            }
            ExcursieDTO[] excursiiDTO=DTOUtils.getDTO(excursii);
            return new Response.Builder().type(ResponseType.GET_ALL_EXCURSII).data(excursiiDTO).build();
        } catch (ServicesException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }
    private Response handleGET_BY_DATE(Request request){
        System.out.println("Get BY DATE Request ...");
        FilterDTO filter= (FilterDTO) request.data();
        try {

            Iterable<Excursie> excursiiIt=server.findByDate(filter.getDestinatie(),filter.getBegin(),filter.getEnd());
            ArrayList<Excursie> list = (ArrayList<Excursie>) StreamSupport.stream(excursiiIt.spliterator(),false).collect(Collectors.toList());
            Excursie[] excursii= new Excursie[list.size()];
            int i = 0;
            for(Excursie e:excursiiIt){
                excursii[i]=e;
                i++;
            }
            ExcursieDTO[] excursiiDTO=DTOUtils.getDTO(excursii);
            return new Response.Builder().type(ResponseType.GET_ALL_EXCURSII).data(excursiiDTO).build();
        } catch (ServicesException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }
    private void sendResponse(Response response) throws IOException{
        System.out.println("sending response "+response);
        output.writeObject(response);
        output.flush();
    }

    @Override
    public void updateNrLocuri(UpdateDTO updateDTO) {
        Response resp=new Response.Builder().type(ResponseType.UPDATE).data(updateDTO).build();
        System.out.println("Update "+updateDTO);
        try {
            sendResponse(resp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
