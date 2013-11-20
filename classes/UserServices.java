package Reti;

import java.io.*;
import java.net.Socket;
import java.util.*;

/**
 * Implementa le procedure di gestione per tutti i servizi erogabili agli utenti.
 * Necessita come parametro lo stato del server per poter accedere e aggiornare 
 * la lista degli utenti online e di un server data dove vengono memorizzate
 * tutte le informazioni circa gli utenti e le rispettive relazioni.
 * @author Francesca Madeddu
 */
public class UserServices {
    
    private ServerStatus serverStatus;
    private ServerData serverData;
    private User loggedUser;
    
    UserServices(ServerStatus serverStatus) throws IOException, ClassNotFoundException{
        this.serverStatus = serverStatus;
        serverData = new ServerData("fb.txt");
    }
    
    /** Ritorna la lista di utenti registrati.
     * @return la lista degli utenti registrati.
     * @throws IOException
     * @throws ClassNotFoundException  
     */
    public synchronized Set<User> getRegisteredUsers() throws IOException, ClassNotFoundException{
        
        serverData.loadData();
        return serverData.getUsersGraph().getAllUsers();
    }  
    
    /** Ritorna l'utente loggato durante la sessione. 
     * @return l'utente loggato durante la sessione.
     * @throws IOException
     * @throws ClassNotFoundException  
     */
    public synchronized User getLoggedUser() throws IOException, ClassNotFoundException{
        
        serverData.loadData();
        return loggedUser;
    }
    
    /**
     * Registra l'utente inserendone i dati nel grafo.
     * @param user è l'utente da inserire nel grafo.
     * @return una stringa esplicativa sull'esito dell'operazione.
     * @throws FileNotFoundException
     * @throws IOException
     * @throws ClassNotFoundException  
     */
    public synchronized String register(User user) throws FileNotFoundException, IOException, ClassNotFoundException{
        
        serverData.loadData();
        if (serverData.getUsersGraph().addUser(user)){
            serverData.updateFile();
            return "Registrazione avvenuta con successo";
        }
        else 
            return "Registrazione fallita: l'utente potrebbe essere gia' registrato";
    }
    
    /**
     * Effettua il login di un determinato utente inserendone le credenziali sulla lista
     * degli utenti loggati.
     * @param user identifica l'utente che effettua il login.
     * @param clientSocket identifica la socket associata all'utente connesso.
     * @return una stringa esplicativa sull'esito dell'operazione.
     * @throws IOException
     * @throws ClassNotFoundException  
     */
    public synchronized String login(User user, Socket clientSocket) throws IOException, ClassNotFoundException{
        
        serverData.loadData();
        if(!serverData.getUsersGraph().existsUser(user)){
            return "Login fallito: registrarsi al servizio prima di effettuare il login";
        }
        else if(user.equals(loggedUser)||serverStatus.getOnlineUsers().containsKey(user)){
            return "Attenzione: l'utente risulta gia' loggato";
        }
        else if(loggedUser!=null){
            return "Attenzione: e' neccessario fare il logout per potersi loggare con un'altra utenza";
        }
        else{
            if(serverStatus.insertOnlineUser(user, clientSocket)){
               loggedUser = user;
               return "Benvenuto "+loggedUser;
            }
            else {
               return "Login fallito";
            }
        }
    }
    
    /**
     * Scarica il numero di mail di un utente dalla mailbox.
     * @return una stringa esplicativa sull'esito dell'operazione. Null se 
     * non sono presenti mail.
     * @throws IOException 
     * @throws ClassNotFoundException  
     */
    public synchronized String receiveMails() throws IOException, ClassNotFoundException{
        
        serverData.loadData();
        List<Message> userMails = serverData.getServerMail().getMailsFromUser(loggedUser);
        int size = userMails.size();
        if(size==1)
            return "Hai 1 nuovo messaggio";
        else if(userMails.size()>1){
            return "Hai "+userMails.size()+" nuovi messaggi";
        }
        else
            return null;
    }
    
    /**
     * Scarica le mail di un determinato utente dalla mailbox.
     * @return le mail di un utente.
     * @throws FileNotFoundException 
     * @throws IOException
     * @throws ClassNotFoundException  
     */
    public synchronized List<Message> getMailsList() throws FileNotFoundException, IOException, ClassNotFoundException{
        
        serverData.loadData();
        List<Message> userMails = serverData.getServerMail().getMailsFromUser(loggedUser);
        serverData.getServerMail().removeMails(userMails);
        serverData.updateFile();
        return userMails; 
    }
    
    /**
     * Aggiunge una relazioni di amicizia al grafo.
     * @param user è l'utente al quale si richiede l'amicizia.
     * @return una stringa esplicativa sull'esito dell'operazione.
     * @throws FileNotFoundException
     * @throws IOException
     * @throws ClassNotFoundException  
     */
    public synchronized String newFriend(User user) throws FileNotFoundException, IOException, ClassNotFoundException{
         
         serverData.loadData();
         if(loggedUser == null)
             return "Devi essere loggato per effettuare richieste di amicizia";
         else if(loggedUser.equals(user))
             return "Non puoi stringere amicizia con te stesso :)";
         else{ 
             switch(serverData.getUsersGraph().friendReq(loggedUser, user)){
                 case -1: 
                     return "L'utente non risulta registrato.";
                 case -2: 
                     return "Hai gia' richiesto quest'amicizia.";
                 default: 
                     serverData.updateFile();
                     return "L'amicizia con " + user.toString() + " e' stata aggiunta";
              }
             
         }
    }
    
    /**
     * Cerca se un utente è registrato al servizio e quindi presente sul grafo.
     * @param user è l'utente che si ricerca.
     * @return una stringa esplicativa sull'esito dell'operazione. Null se
     * @throws IOException
     * @throws ClassNotFoundException  
     */
    public synchronized String findUser(User user) throws IOException, ClassNotFoundException{
        
        serverData.loadData();
        if (serverData.getUsersGraph().existsUser(user))
            return "L'utente e' presente";
        else 
            return "L'utente non e' presente";
    }
    
    /**
     * Cerca il numero di amici di un utente.
     * @return una stringa esplicativa sull'esito dell'operazione.
     * @throws IOException
     * @throws ClassNotFoundException  
     */
    public synchronized String friendsList() throws IOException, ClassNotFoundException{
        
        serverData.loadData();
        if(loggedUser==null)
            return "Devi essere loggato per poter richiedere la lista delle tue amicizie";
        else{
            Set<User> listFriend = serverData.getUsersGraph().friendsList(loggedUser);
            int size = listFriend.size();
            switch(size){
                case(0): return "Non hai amici";
                case(1): return "Hai un amico:";
                default: return "Hai "+size+ " amici";
            }
        }
    }
    
    /**
     * Ritorna la lista degli amici di un utente.
     * @return una stringa esplicativa sull'esito dell'operazione.
     * @throws IOException
     * @throws ClassNotFoundException  
     */
    public synchronized Set<String> getFriendsList() throws IOException, ClassNotFoundException{
        
        serverData.loadData();
        if(loggedUser==null)
            return null;
        Set<String> listFriend = serverData.getUsersGraph().friendListAsString(loggedUser);
        if (listFriend.isEmpty()) 
            return null;
        else 
            return listFriend;
     }
    
    /**
     * Spedisce una mail ad un determinato utente. Se il destinatario è online
     * il messaggio viene recapitato in maniera istantanea, altrimenti viene caricato
     * sulla mailbox e scaricato al primo login.
     * @param dest è il destinatario del messaggio;
     * @param msg contiene il testo del messaggio;
     * @return una stringa esplicativa sull'esito dell'operazione.  
     * @throws FileNotFoundException
     * @throws IOException
     * @throws ClassNotFoundException  
     */
    public synchronized String sendMail(User dest, String msg) throws FileNotFoundException, IOException, ClassNotFoundException{

         serverData.loadData();
         if(loggedUser == null)
             return "Devi essere loggato per mandare messaggi";
         
         else if(loggedUser.equals(dest))
             return "Non puoi mandare messaggi a te stesso :)";
         
         else if(serverStatus.getOnlineUsers().containsKey(dest)){
            ServerTransmitter instTrasmitt = new ServerTransmitter(serverStatus.getOnlineUsers().get(dest));
            instTrasmitt.appendMessage("reply", "msginst");
            instTrasmitt.appendMessage("msginst", new Message(loggedUser,dest,msg));
            instTrasmitt.sendMessage();
            instTrasmitt.clearMessage();
            return "Messaggio istantaneo inviato";
         }
         else{ 
             serverData.getServerMail().appendMail(new Message(loggedUser, dest, msg));
             serverData.updateFile();
             return "Messaggio inviato al server mail";
         }
    }
    
    /**
     * Disconnette l'utente rimuovendo l'utente dalla lista degli utenti online.
     * @return una stringa esplicativa sull'esito dell'operazione.  
     */
    public synchronized String disconnect(){
        
        serverStatus.removeOnlineUser(loggedUser);
        return "Goodbye!";
    }
    
    /**
     * Suggerisce un amico al momento del login di un dato utente (scelto tra 
     * i vicini hop2).
     * @return una stringa esplicativa sull'esito dell'operazione.
     * @throws IOException 
     * @throws ClassNotFoundException  
     */
    public synchronized String suggestFriend() throws IOException, ClassNotFoundException{

        serverData.loadData();
        if(loggedUser==null)
            return null;
        else{
            List<User> neighbours = new ArrayList<>(serverData.getUsersGraph().neighbours(loggedUser));
            if (!neighbours.isEmpty()){
                int index = new Random().nextInt(neighbours.size());
                return neighbours.get(index).toString();
            }else
                return null;
        }
    }
}
    
