package Reti;

import java.io.IOException;
import java.net.Socket;
import java.util.*;

/**
 * Gestisce la connessione con il client elaborando la richiesta
 * effettuata dal client e fornendo come output una stringa esplicativa sull'
 * esito della richiesta.
 * @author Francesca Madeddu
 */
public class SessionHandler extends Thread{
    
    ServerStatus serverStatus;
    Socket clientSocket;
    
    SessionHandler(ServerStatus serverStatus, Socket clientSocket){
        this.serverStatus = serverStatus;
        this.clientSocket = clientSocket;
    }
    
    /**
     * Setta lo status del server.
     * @param serverStatus è il nuovo valore dello status del server.
     */
    public void setServerStatus(ServerStatus serverStatus){
        this.serverStatus = serverStatus;
    }
    
    /**
     * Setta la socket del client con quale il server comunica.
     * @param clientSocket è la socket del client che richiede il servizio.
     */
    public void setClientSocket(Socket clientSocket){
        this.clientSocket = clientSocket;
    }
    
    @Override
    public void run(){
        ServerReceiver ricevitore = new ServerReceiver(clientSocket); 
        ServerTransmitter trasmettitore = new ServerTransmitter(clientSocket);
        UserServices services;
        try {
            services = new UserServices(serverStatus);
            Map<String,String> map;
            do{
                System.out.println("Utenti online:"+ serverStatus.getOnlineUsers().keySet());
                System.out.println("Utenti registrati:"+services.getRegisteredUsers());
                System.out.println("Utente loggato:"+services.getLoggedUser()+"\n");
                ricevitore.readMessage();

                map = ricevitore.getMappedMessage();
                System.out.println("Richiesta del client:"+map);

                if(!map.get("cmd").equals("unknown")){
                    String outcome;
                    User user;

                    switch(map.get("cmd")){                           

                     case "register":
                            user = new User(map.get("nome"),map.get("cognome"));
                            outcome=services.register(user);
                            trasmettitore.appendMessage("reply",outcome);

                     break;    

                     case "disconnect":
                            outcome=services.disconnect();
                            trasmettitore.appendMessage("reply",outcome);

                     break;    

                     case "login":
                            user = new User(map.get("nome"),map.get("cognome"));
                            outcome=services.login(user,clientSocket);
                            trasmettitore.appendMessage("reply",outcome);

                            outcome=services.suggestFriend();
                            
                            if(outcome!=null)
                            trasmettitore.appendMessage("suggerimento",outcome);
                            
                            outcome=services.receiveMails();
                            if(outcome!=null)
                            {
                               trasmettitore.appendMessage("infoposta",outcome);
                               List<Message> mailList = services.getMailsList();
                               trasmettitore.appendMessage("posta",mailList); 
                            }
                            
                     break;    

                     case "search":
                           user = new User(map.get("nome"),map.get("cognome"));
                           outcome=services.findUser(user);
                           trasmettitore.appendMessage("reply",outcome);

                     break;

                     case "friend":
                           user = new User(map.get("nome"),map.get("cognome"));
                           outcome=services.newFriend(user);
                           trasmettitore.appendMessage("reply",outcome);
                     break;

                     case "listfriend":
                           outcome=services.friendsList();
                           trasmettitore.appendMessage("reply",outcome);

                           Set<String> friends = services.getFriendsList();
                           if(friends!=null)
                             trasmettitore.appendMessage("listfriend",friends);

                     break;

                      case "msg":
                            user = new User(map.get("nome"),map.get("cognome"));
                            outcome=services.sendMail(user,map.get("msg"));
                            trasmettitore.appendMessage("reply",outcome);

                     break;

                     default:
                            trasmettitore.appendMessage("reply", "Comando non riconosciuto");

                         break;
                   }// switch

                }else
                    trasmettitore.appendMessage("reply", "Sintassi non valida");

                trasmettitore.sendMessage();
                trasmettitore.clearMessage();
           }while(!map.get("cmd").equals("disconnect"));
           trasmettitore.clearMessage();
       }catch (IOException | ClassNotFoundException ex){
           System.out.println("Errore durante l'erogazione del servizio ("+ex+")");
       }
   } 
}