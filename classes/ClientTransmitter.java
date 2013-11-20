package Reti;

import java.io.*;
import java.net.Socket;

/**
 * Gestisce la trasmissione lato client.
 * @author Francesca Madeddu
 */
public class ClientTransmitter {
    private Socket socket;
    private String message;
    
    ClientTransmitter(Socket socket,String message){
        this.socket = socket;
        this.message = message;
    }

    /**
     * Permette di cambiare il valore della socket;
     * @param sock socket sostituisce il valore della socket.
     */
    public void setSocket(Socket sock){
        socket = sock;
    }
    
    /**
     * Permette di settare il valore del messaggio;
     * @param message sostituisce il valore del messaggio.
     */
    public void setMessage(String message){
        this.message = message;
    }    
    
    /**
     * Invia il messaggio sulla oscket di destinazione.
     * @throws IOException nel caso in cui la scrittura generi problemi.
     */
    public void sendMessage() throws IOException{
        OutputStream os = socket.getOutputStream();
        try{
            os.write(message.getBytes(), 0, message.length());
        }catch (IOException ex) {
            System.out.println("Errore in scrittura sulla socket ("+ex+")");
        }
    }

}
