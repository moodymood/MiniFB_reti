package Reti;

import java.io.*;
import java.net.Socket;
import java.util.*;
import java.util.regex.*;

/**
 * Gestisce la ricezione di messaggi lato server.
 * In particolare riceve una stringa da un determinato flusso di dati
 * associato ad una socket. La stringa viene memorizzata ed eventualmente 
 * convertita in una Map<String,String> i cui elementi hanno una precisa semantica.
 * In particolare il valore associato alla chiave "cmd" sarà "unknown" se la sintassi
 * della stringa ricevuta non è corretta.
 * @author Francesca Madeddu
 */
public class ServerReceiver {
    private Socket clientSocket;
    private String message;
    
    ServerReceiver(Socket socket){
        clientSocket = socket;
    }
    
    /**
     * Setta la socket al valore socket.
     * @param socket
     */
    public void setSocket(Socket socket){
        clientSocket = socket;
    }
    
    /**
     * Restituisce la stringa ricevuta sotto forma di mappa.
     * @return una mappa contenente la stringa opportunamente suddivisa.
     */
    public Map<String,String> getMappedMessage(){
        return MapMessage();
    }
    
    /**
     * Ritorna il messaggio ricevuto come stringa.
     * @return il messaggio ricevuto come stringa.
     */
    public String getMessage(){
        return message;
    }
    
    /**
     * Legge una stringa dalla socket.
     * @return
     * @throws IOException
     */
    public boolean readMessage() throws IOException{
        int r,dim_buff = 1024;
        byte[] buff = new byte[dim_buff];
        InputStream is;
        try{
            is = clientSocket.getInputStream();
            r = is.read(buff, 0, dim_buff);
            if (r > 0) {
                String message = new String(buff, 0, r);
                this.message = message.toLowerCase().replace("\r\n", "").trim();
                return true;
            }
            else
                return false;
        }catch(IOException ex){
        System.out.println("Errore durante la lattura dalla socket ("+ex+")");
            return false;
        }
     }
    
    /**
     * Effetta controlli di sintassi sul messsaggio ricevuto.
     * @return true se la sintassi è rispettata.
     */    
    @SuppressWarnings("ConvertToStringSwitch")
    private boolean checkSyntax(){
        
        Pattern patt;
        int index = message.indexOf(":");
        // CMD+PAR
        if(index>0){
            // Msg
            String cmd = message.substring(0,index);
            if(cmd.equals("msg"))
                patt = Pattern.compile("[a-z0-9]{3}+:+[a-z0-9]{1,25}+-+[a-z0-9]{1,25}+ +[.[^-:]]{1,200}", Pattern.CASE_INSENSITIVE);
            // Altri casi
            else if(cmd.equals("login")||cmd.equals("register")||cmd.equals("friend")||cmd.equals("search"))
                patt = Pattern.compile("[a-z0-9]{5,8}+:+[a-z0-9]{1,25}+-+[a-z0-9]{1,25}", Pattern.CASE_INSENSITIVE);
            else
                return false;
        }
        // solo CMD
        else{
            String cmd = message.substring(0,message.length());
            if(cmd.equals("listfriend")||cmd.equals("disconnect"))
                patt = Pattern.compile("[a-z0-9]{6,11}", Pattern.CASE_INSENSITIVE);
            else
                return false;
        }
        Matcher match = patt.matcher(message);
        return match.matches();    
    }
    
    /**
     * Converte la stringa in una map.
     * La stringa deve contenere un parola che identifica il comando (quindi la
     * richiesta del client) ed eventuali parametri associali da tale comando.
     * @return una mappa contenente comando e parametri.
     */  
    @SuppressWarnings("ConvertToStringSwitch")
    private Map<String,String> MapMessage(){
             Map<String,String> map = new HashMap<>();
             if(!checkSyntax())
                 map.put("cmd", "unknown");
             else{
                 int index = message.indexOf(":");
                     // Interpretazione stringa ricevuta
                     // CMD+PAR
                     if(index>0){
                         String cmd = message.substring(0,index);
                         map.put("cmd",cmd);
                         String par = message.substring(index+1,message.length());
                         // MSG
                         if(cmd.equals("msg")){
                             String[] arr_utente = (par.substring(0,par.indexOf(" "))).split("-");
                             map.put("nome",arr_utente[0]);
                             map.put("cognome", arr_utente[1]);
                             String msg = par.substring(par.indexOf(" ")+1,par.length());
                             map.put("msg",msg);
                         }
                         // ALTRO
                         else if(cmd.equals("login")||cmd.equals("register")||cmd.equals("friend")||cmd.equals("search")){
                             String[] arr_utente = par.split("-");
                             map.put("nome",arr_utente[0]);
                             map.put("cognome", arr_utente[1]);
                         }
                         else map.put("cmd","unknown");
                     }
                     // solo CMD
                     else{
                         String cmd = message.substring(0,message.length());
                         if(cmd.equals("listfriend")||cmd.equals("disconnect"))
                             map.put("cmd",cmd);
                         else map.put("cmd", "unknown");
                     }
                 }
             return map;
        }
    }
