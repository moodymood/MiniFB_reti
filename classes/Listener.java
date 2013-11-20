package Reti;

import java.io.IOException;
import java.net.*;

/**
 * Gestisce, lato server,  le richieste di connessione da parte dei client.
 * In particolare, dopo aver inizializzato una socket, attende nuove richieste e 
 * restituisce una nuova socket di comunicazione con un client quando la richiesta 
 * viene accettata.
 * @author  Francesca Madeddu
 */
public class Listener {
    
    private ServerSocket serverSocket;
    
    Listener(String ip, Integer port) throws IOException, IllegalArgumentException{
        try{
            serverSocket = new ServerSocket();
            InetSocketAddress isa;
            isa =  new InetSocketAddress(InetAddress.getByName(ip),port);
            serverSocket.bind(isa);
        }catch(UnknownHostException|IllegalArgumentException ex){
            System.out.println("Errore durante la gestione della connessione ("+ex+")");
        }        
    }
    
    /**
     * Crea e inizializza una socket di comunicazione.
     * @param ip identifica l'ip della macchina che si metterà in attesa di 
     * connessioni.
     * @param port identifica la porta dell'applicazione.
     * @return true l'inizializzazione della socket è andata a buon fine.
     * @throws IOException 
     * @throws IllegalArgumentException  
     */
    public boolean createSocket(String ip, Integer port) throws IOException, IllegalArgumentException{
        try{
            serverSocket = new ServerSocket();
            InetSocketAddress isa;
            isa =  new InetSocketAddress(InetAddress.getByName(ip),port);
            serverSocket.bind(isa);
            return true;
        }catch(UnknownHostException|IllegalArgumentException ex){
            System.out.println("Errore durante la gestione della connessione ("+ex+")");
            return false;
        }
        
    }
    /**
     * Crea una nuova socket di comunicazione per gestire una richiesta di 
     * connessione lato client e il successivo scambio di dati.
     * @return
     */
    public Socket waitAndAccept(){
        Socket data_socket;
        try{ 
            data_socket = serverSocket.accept();
            return data_socket;
        }catch(IOException | IllegalThreadStateException ex){
            System.out.println("Errore durante la gestione della connessione ("+ex+")");
            return null;
        }
    }
}
