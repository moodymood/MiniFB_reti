package Reti;

import java.io.IOException;
import java.net.Socket;

/**
 * Accetta connessioni da diversi client ai quali offre i servizi di FB4Dummies.
 * Rimane sempre attivo generando un thread per ogni utente che si collega.
 * @author Francesca Madeddu
 */
public class Fb4Dummies_Server {

    public static void main(String[] args) throws IOException{
        
       String ip = "localhost";
       Integer port = 2943;
       
       try{
           ServerStatus serverStatus = new ServerStatus();
           Listener ascoltatore = new Listener(ip,port);
           System.out.println("In attesa sull'indirizzo "+ip+":"+port);
           Thread thread;
           while(true){    
                Socket clientSocket = ascoltatore.waitAndAccept();
                thread = new SessionHandler(serverStatus,clientSocket);
                thread.start();
       }
       }catch(IOException ex){
           System.out.println("Errore durante la creazione del server ("+ex+")");
       }
    }
    
}
