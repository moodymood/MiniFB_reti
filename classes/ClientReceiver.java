
package Reti;

import java.io.*;
import java.net.Socket;
import org.json.simple.*;


/**
 * Gestisce la ricezione lato client: riceve da una socket dei messaggi e li salva,
 * permettendo all'occorrenza opportune conversioni.
 * In particolare la classe riceve stringhe, ma nel caso in cui la stringa
 * contenga oggetti JSON, tramite il metodo di conversione, e' possibile
 * estrapolarne il contenuto convertendolo in formato String.
 * @author Francesca Madeddu
 */

public class ClientReceiver{
    
    private Socket socket;
    private String message;
    
    ClientReceiver(Socket socket){
        this.socket = socket;
    }

    /**
     * Permette di cambiare il valore della socket;
     * @param sock socket sostituisce il valore della socket.
     */
    public void setSocket(Socket sock){
        socket = sock;
    }
    
    /**
     * Riceve un flusso di byte dalla socket convertendo i dati in una 
     * stringa;
     * @throws IOException nel caso in cui la lettura generi problemi.
     */
    public void receive() throws IOException{
        InputStream is = socket.getInputStream();
        int dim_buff = 1024, r;
        byte[] buff = new byte[dim_buff];
        try {
            r = is.read(buff, 0, dim_buff);
            message = new String(buff, 0, r);
        } catch (IOException ex) {
            System.out.println("Errore in lettura dalla socket ("+ex+")");
        }
    }
    
    /**
     * Ritorna il messaggio memorizzato opportunamento convertito;
     * @return il messaggio memorizzato opportunamento convertito.
     */
    public String getMessage(){
        return convertMessage();
    }
    
    /**
     * Converte il messaggio memorizzato.
     * In particolare converte un JSONObject o un JSONArray in formato String.
     * @return la stringa risultate dalla conversione del messaggio.
     */
    private String convertMessage(){

        JSONObject object = (JSONObject) JSONValue.parse(message);  
        JSONArray array = new JSONArray();
        String reply = (String) object.get("reply");
        String res = reply;
        
        if(object.containsKey("suggerimento")){
            String amico = (String) object.get("suggerimento");    
            res +="\nTi suggriesco "+amico+" come amico";
        }
        
        if(object.containsKey("posta")){
            res+= "\n"+(String) object.get("infoposta");
            array = (JSONArray) object.get("posta");
            for(int i=0;i<array.size();i++){
               res +="\n("+i+")";
               res +=" Inviato da: "+((JSONObject)array.get(i)).get("sender")+"\n";
               res +="    Testo: "+((JSONObject)array.get(i)).get("msg");
            }
        }
       
        
        else if(object.containsKey("listfriend")){
            res.replaceAll(reply, "");
            array = (JSONArray) object.get("listfriend");
            for(int i=0;i<array.size();i++){
               res +="\n("+i+") "+array.get(i);
            }
        }

        else if(object.containsKey("msginst")){
            object = (JSONObject) object.get("msginst");    
            res ="Il tuo amico "+(String)object.get("sender")+" ti ha scritto: "+(String)object.get("msg");
        }
        
        else if(object.containsKey("disconnect")){
            object = (JSONObject) object.get("disconnect");    
            res +="disconnect";
        } 
        
        return res+"\n";
    }
    
}
