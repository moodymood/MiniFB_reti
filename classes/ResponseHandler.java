package Reti;

import java.io.IOException;
import java.net.Socket;

/**
 * Gestisce la ricezione delle risposte inviate dal server stampandone il
 * contenuto a video.
 * @author Francesca Madeddu
 */
public class ResponseHandler extends Thread{
    
    private Socket serverSocket;
    
    ResponseHandler(Socket socket){
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
        String resMsg;
        ClientReceiver receiver;
        do{
           receiver = new ClientReceiver(serverSocket); 
           try{
               receiver.receive(); 
           }catch(IOException ex){
               System.out.println("Errore in fase di ricezione dalla socket ("+ex+")");
           }
           resMsg=receiver.getMessage();
           
           KeysWriter keyW=new KeysWriter(resMsg);
           keyW.printKeys();
        }while(!resMsg.equals("Goodbye!\n"));
    }
    
}
