package Reti;

import java.io.IOException;
import java.net.Socket;

/**
 * Gestisce le richieste effettuate dell'utente tramite linea di comando 
 * inviandole al server.
 * @author Francesca Madeddu
 */
public class RequestHandler extends Thread {
    
    private Socket serverSocket;
    
    RequestHandler(Socket socket){
        serverSocket = socket;
    }
    
    /**
     * Setta la socket di comunicazione col il server.
     * @param socket identificato la socket di comunicazione con il server.
     */
    public void setSocket(Socket socket){
        serverSocket = socket;
    }
    
    @Override
    public void run(){
        String reqMsg;
        KeysReader keyR;
            do{
                keyR=new KeysReader();
                keyR.readKeys();     
                reqMsg=keyR.getKeys();

                if(reqMsg.equals("help"))
                    Info.printInfo();
                else{
                    ClientTransmitter transmitter=new ClientTransmitter(serverSocket,reqMsg); 
                    try{
                        transmitter.sendMessage(); 
                    }catch(IOException ex){
                        System.out.println("Errore durante la trasmissione della richiesta al server ("+ex+")");
                    }
                }
                    
            }while(!reqMsg.equals("disconnect"));
    }
}
