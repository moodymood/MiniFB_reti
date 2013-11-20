package Reti;

import java.io.*;
import java.net.Socket;
import java.util.*;
import org.json.simple.*;

/**
 * Gestisce la trasmissione dei messaggi lato server incapsulando le
 * risposte in JSONObject prima di inviarli al client.
 * @author Francesca Madeddu
 */
public class ServerTransmitter {
    
    private JSONObject message;
    private Socket clientSocket;
    
    ServerTransmitter(Socket clientSocket){
        message = new JSONObject();
        this.clientSocket = clientSocket;
    }
    
    /**
     * Setta la socket di connessione.
     * @param newClientSocket sostituisce il valore attuale della socket.
     */
    public void setSocket(Socket newClientSocket){
        this.clientSocket = newClientSocket;
    }
    
    /**
     * Aggiunge una coppia di valori all'oggetto JSON che verrà spedito al client.
     * @param <T> 
     * @param s identifica la chiave.
     * @param payload identifica il valore associato alla chiave.
     */
    @SuppressWarnings("unchecked")
    public <T> void appendMessage(String s,T payload){
        switch (s) {
            case "listfriend":
                message.put(s,convertFriendList((Set<String>)payload));
                break;
            case "msginst":
                message.put(s,convertMessage((Message)payload));
                break;
            case "posta":
                message.put(s,convertMailList((List<Message>)payload));
                break;
            default:
                message.put(s,payload);
                break;
        }
    }
    
    /**
     * Elimina il contenuto del JSONObject.
     */
    public void clearMessage(){
        message.clear();
    }
    
    /**
     * Invia il JSONObject sulla socket di connessione dopo averlo opportunamente
     * convertito in String.
     * @return true le l'invio ha avuto successo.
     * @throws IOException
     */
    public boolean sendMessage() throws IOException{
        String json = message.toString();
        try{
            OutputStream os = clientSocket.getOutputStream();
            os.write(json.getBytes(), 0, json.length());
            return true;
        }catch(IOException ex){
            System.out.println("Errore durante l'invio del messaggio ("+ex+")");
            return false;
        }     
    } 
    
    /**
     * Converte una List<Message> in un array di JSONObject definiti come
     * {"sender": "sendername"
     *  "text": "textmessage"}.
     * @return un JSONArray contenente i messaggi sottoforma di JSONObject.
     * @throws IOException
     */    
    @SuppressWarnings("unchecked")
    private JSONArray convertMailList(List<Message> mailsList){
        JSONArray ja = new JSONArray();
        for(Message m:mailsList){
            JSONObject mess = new JSONObject();
            mess.put("sender",m.getMitt().toString());
            mess.put("msg",m.getMsg());
            ja.add(mess);
        }  
        return ja;
    }
    
     /**
     * Converte un Set<String> contenente gli amici di un dato
     * utente in un JSONArray.
     * @return un JSONArray contenente gli elementi presenti in friendList.
     */   
    @SuppressWarnings("unchecked")
    private JSONArray convertFriendList(Set<String> friendsList){
        JSONArray ja = new JSONArray();
        ja.addAll(friendsList);
        return ja;
    }
    
     /**
     * Converte un Message in un JSONObject avente la forma:
     * {"sender": "sendername"
     *  "text": "textmessage"}.
     * @return un JSONobject contenente un messaggio.
     */ 
    @SuppressWarnings("unchecked")
    private JSONObject convertMessage(Message msg){
        JSONObject jo = new JSONObject();
        jo.put("sender", msg.getMitt().toString());
        jo.put("msg", msg.getMsg());
        return jo;
    }    
    
}
