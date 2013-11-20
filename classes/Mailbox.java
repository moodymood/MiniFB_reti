package Reti;

import java.io.Serializable;
import java.util.*;

/**
 * Realizza un server di posta da cui e' possibile scaricare o caricare messaggi.
 * @author Francesca Madeddu
 */
public class Mailbox implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private List<Message> mailBox = new ArrayList<>();

    /**
     * Restituisce tutti i messaggi contenuti nella mailbox.
     * @return tutti i messaggi contenuti nella mailbox.
     */
    public List<Message> getMailbox() {
        return mailBox;
    }

    /**
     * Restituisce la lista dei messaggi di un dato utente.
     * @param user e' l'utente destinatario della lista di messaggi;
     * @return List<Messaggio> con i messaggi dell'utente con nome name e cognome surname.
     */
    public List<Message> getMailsFromUser(User user){
        List<Message> emailUser = new ArrayList<>();
        Iterator<Message> it = mailBox.iterator();
        for(Message m:mailBox){
            if(m.getDest().equals(user))
                emailUser.add(m);
        }
        return emailUser;
    }

    /**
     * Restituisce true se un dato utente ha posta sulla mailbox.
     * @param name nome del'utente;
     * @param surname cognome dell'utente;
     * @return true se l'utente ha delle mail non lette.
     */
    public boolean hasMail(String name, String surname){
        boolean res = false;
        for(Message msg:mailBox)
            if(msg.getDest().equals(new User(name,surname)))
                res = true;
        return res;
    }

    /**
     * Aggiunge un particolare messaggio sul server di posta.
     * @param msg e' il messaggio da aggiungere sul server
     * @return true se il messaggio e' stato aggiunto
     */
    public boolean appendMail(Message msg){
        return mailBox.add(msg);
    }

    /**
     * Rimuove dal server di posta una particolare lista di messaggi.
     * @param msgList e una List<Messaggio> da rimuovere dal server di posta;
     * @return true se la rimozione e' avvenuta con successo.
     */
    public boolean removeMails(List<Message> msgList){
        return mailBox.removeAll(msgList);
    }

    @Override
    public String toString(){
        String res = "Lista Messaggi";
        for(Message m:mailBox){
            res+= m.toString();
        }
        return res;
    }

}

