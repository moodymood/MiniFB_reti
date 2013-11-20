package Reti;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Contiene tutte le variabili che caratterizzano lo stato del server, ovvero 
 * tutti quei dati volatili che esistono a prescindere dalle sessioni in esecuzione.
 * Questi dati saranno quindi in comune per ogni thread di gestione delle richieste 
 * (SessionHandler).
 * @author Francesca Madeddu
 */
public class ServerStatus {
    
    private Map<User, Socket> onlineUsers;
    
    ServerStatus(){
        onlineUsers = new HashMap<>();
    }
    
    /**
     * Ritorna la lista degli utenti on-line;
     * @return una lista di utenti on-line.
     */
    public Map<User, Socket> getOnlineUsers(){
        return onlineUsers;
    }
    
    /**
     * Inserisce l'utente user e la socket ad esso associoata alla lista di
     * utenti on-line.
     * @param user utente da inserire nella lista;
     * @param socket socket associata all'utente;
     * @return true se l'inserimento è stato effettuato con successo.
     */
    public boolean insertOnlineUser(User user, Socket socket){
        if(onlineUsers.containsKey(user))
            return false;
        onlineUsers.put(user, socket);
        return true;
    }
    
    /**
     * Rimuove l'utente user e la socket ad esso associoata alla lista di
     * utenti on-line.
     * @param user utente da eliminare nella lista;
     * @return true se l'inserimento è stato effettuato con successo.
     */
    public boolean removeOnlineUser(User user){
        if(!onlineUsers.containsKey(user))
            return false;
        onlineUsers.remove(user);
        return true;
    }

    
}
