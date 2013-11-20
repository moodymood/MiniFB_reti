package Reti;

import java.io.IOException;
import java.net.*;

/**
 * Gestisce la connessione lato client. In particolare permette la connesione
 * su un determinato indirizzo identificato da IP e PORT.
 * @author Francesca Madeddu
 */
public class Connector {
    
    private Socket socket;
    private String ip;
    private Integer port;
    
    /**
     * Crea un nuovo Connector.
     * @param ip identifica l'indirizzo ip del server a cui ci si vuole connettere;
     * @param port identifica la porta dell'applicazione con la quale si vuole 
     * comunicare;
     */
    public Connector(String ip, Integer port){
        socket = new Socket();
        this.ip = ip;
        this.port = port;
    }
    
    /**
     * Permette di settare la socket con un nuovo indirizzo.
     * @param ip identifica l'indirizzo ip del server a cui ci si vuole connettere;
     * @param port identifica la porta dell'applicazione con la quale si vuole 
     * comunicare;
     */
    public void setSocket(String ip, Integer port){
        this.ip = ip;
        this.port = port;        
    }
    
    /**
     * Effettua la connessione.
     * @return la socket di comunicazione tra client e server.
     * @throws IOException nel caso in cui la connect fallisca.
     */
    public Socket connect() throws IOException{
        socket.connect(new  InetSocketAddress (InetAddress.getByName(ip),port));
        return socket;
    }
    
    /**
     * Chiude la connessione con il server.
     * @throws IOException nel caso in cui la chiusura del canale di comunicazione
     * fallisca.
     */
    public void close() throws IOException{
        socket.close();
    }
    
}
