package Reti;


import java.io.Serializable;
import java.util.*;


/**
 * Realizza il grafo per la memorizzazione dei dati di base di FB4dummies.
 * In particolare memorizza le informazioni circa nome e cognome degli utente e 
 * le relazioni di amicizia tre essi.
 * @author Francesca Madeddu
 */
public class Graph implements Serializable{
    
    private static final long serialVersionUID = 1L;
    private Map<User,Set<User>> myGraph = new HashMap<>();
    
    /**
     * Ritorna tutti gli utenti memorizzati nel grafo.
     * @return tutti gli utenti memorizzati nel grafo.
     */
    public Set<User> getAllUsers(){
        return myGraph.keySet();
    }

    /**
     * Effettua un controllo di esistenza di un particolare utente.
     * @param u identifica l'utente da cercare;
     * @return ritorna true se l'utente u è contenuto nel grafo.
     */
    public boolean existsUser(User u){
        if(myGraph.containsKey(u))
            return true;
        else
            return false;
    }

    /**
     * Aggiunge un utente al grafo.
     * @param u identifica l'utente da aggiungere nel grafo
     * @return true se l'utente è stato aggiunto nel grafo; false e l'utente è già presente nel grafo.
     */
    public boolean addUser(User u){
        if(this.existsUser(u)){
            return false;
        }
        myGraph.put(u, new HashSet<User>());
        return true;
    }
    
    /**
     * Aggiunge una relazione di amicizia tra due utenti presenti nel grafo.
     * Non è possibile aggiungere una relazione di amicizia con se stessi ed entrambi gli utenti
     * devono ovviamente essere presenti nel grafo.
     * @param source è il richiedente dell'amicizia;
     * @param dest è l'utente a cui viene fatta la richiesta di amicizia;
     * @return 1 se l'amicizia è stata aggiunta; -1 se uno degli utenti non esiste;
     * -2 se l'amicizia è già presente;
     *          
     */
    public int friendReq(User source, User dest ){
        if(!this.existsUser(dest)){
            return -1;
        }
        else{
            Set<User> newSet = myGraph.get(source);
            if(newSet.add(dest)){
               myGraph.put(source, newSet);
               newSet = myGraph.get(dest);
               newSet.add(source);
               myGraph.put(dest, newSet);
            }
            else
                return -2;
        }
        return 0;
   }    
    
    /**
     * Restituisce una lista contenente gli amici di un dato utente.
     * @param user 
     * @return la List<User> degli amici  dell'utente identificato dal nome name
     * e cognome surname.
     */
    public Set<User> friendsList(User user){
        if(!this.existsUser(user)){
            return null;
        }
        return myGraph.get(user);
    }
    
    /**
     * Restituisce una lista contenente gli amici di un dato utente in formato
     * Set<String>.
     * @param user identifica l'utente di cui si vogliono conoscere gli amici;
     * @return la List<String> degli amici dell'utente identificato name e surname
     * in formato String.
     */
    public Set<String> friendListAsString(User user){
        Set<User> amici = friendsList(user);
        Set<String> amicis = new HashSet<>();
        for(User u:amici)
           amicis.add(u.toString());
        return amicis;
    }
    
    /**
     * Restituisce la lista dei vicini hop2 di un dato utente.
     * @param user identifica l'utente di cui si cercano i vicini.
     * @return l'insieme dei vicini di hop2 dell'utente specificato da name
     * e surname.
     */
    public Set<User> neighbours(User user){
        // nb. usando set non ho bisogno di eliminare gli amici duplicati
        Set<User> amici1Hop = friendsList(user),
                  amici2Hop = new HashSet<>();
        // Prendo gli amici degli amici
        if(!amici1Hop.isEmpty())
            for(User u: amici1Hop){
                amici2Hop.addAll(friendsList(u));
            }
        amici2Hop.removeAll(amici1Hop);
        amici2Hop.removeAll(Collections.singleton(user));
        return amici2Hop;
    }
    
    /**
     * Restituisce una stringa rappresentate l'oggetto Graph.
     * @return una stringa rappresentate l'oggetto Graph.
     */
    @Override
    public String toString(){
        String s = "";
        for(User u: myGraph.keySet())
            s+=u+", ";
        return s;
    }
}
