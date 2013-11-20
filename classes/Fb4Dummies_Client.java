package Reti;

import java.io.IOException;
import java.net.Socket;

/**
 * Richiede una connessione con il server e lancia i thread che gestiscono i
 * servizi offerti da FB4Dummies.
 * In particolare vengono generati due thread, uno per la gestione della lettura
 * da tastiera e quindi l'invio di tali richieste al server;
 * uno per la lettura delle risposte del server e la conseguente stampa a video.
 * Termina quando l'utente digita "disconnect".
 * @author Francesca Madeddu
 */
public class Fb4Dummies_Client {

    public static void main(String[] args) throws IOException, InterruptedException{
       
       String ip = "localhost";
       Integer port = 2943;
       
       try{
           Connector connector = new Connector(ip,port);
           Socket socket=connector.connect();
           Info.printInfo();

           Thread requestHand = new RequestHandler(socket);
           Thread responseHand = new ResponseHandler(socket);

           requestHand.start();
           responseHand.start();
       }catch(IOException ex){
           System.out.println("Errore durante la creazione del client ("+ex+")");
       }
   }

   
}
