package Reti;

import java.io.*;

/**
 * Estrapola e restituisce i dati archiviati dal server.
   Richiede come parametro il percorso dell'archivio.
 * @author Francesca Madeddu
 */
public class ServerData {
    
    private Graph usersGraph;
    private Mailbox serverMail;
    private String fbFilePath;
    
    @SuppressWarnings("OverridableMethodCallInConstructor")
    ServerData(String fbFilePath) throws IOException, ClassNotFoundException{
        this.fbFilePath = fbFilePath;
        if(!loadData()){
            serverMail = new Mailbox();
            usersGraph = new Graph();
            updateFile();
        }
    }
    
    /**
     * Setta il filepath al valore specificato dalla stringa fbFilePath
     * @param fbFilePath identifica il filepath del file dove evvettuare load
     * e update dei dati.
     */
    public void setFilePath(String fbFilePath){
        this.fbFilePath = fbFilePath;
    }
    
    /**
     * Ritorna gli utenti memorizzati nel grafo e le rispettive
     * relazioni di amicizia
     * @return gli utenti memorizzati nel grafo e le rispettive
     * relazioni di amicizia
     */
    public Graph getUsersGraph(){
        return usersGraph;
    }
    
    /**
     * Ritorna i messaggi memorizzati nel server mail.
     * @return i messaggi memorizzati nel server mail.
     */
    public Mailbox getServerMail(){
        return serverMail;
    }
    
    /**
     * Legge il percorso dell'archivio e memorizza i dati in esso presenti;
     * @return true se l'operazione viene effettuata con successo.
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws NullPointerException  
     */
    public synchronized boolean loadData() throws IOException, ClassNotFoundException, NullPointerException {
            ObjectInputStream ois;
            if((new File(fbFilePath)).exists()){
               try {
                   ois = new ObjectInputStream(new FileInputStream(new File(fbFilePath)));
                   usersGraph = (Graph) ois.readObject();
                   serverMail = (Mailbox) ois.readObject();
                   ois.close();
                   return true;
               } catch (IOException|NullPointerException ex) {
                   System.out.println("Errore durante la fase di caricamento dei dati ("+ex+")");
                   return false;
               }
            }else {
                return false;
            }
    }
    
    /**
     * Aggiorna l'archivio e salvare le modifiche ai dati;
     * @return true se l'operazione viene effettuata con successo.
     * @throws FileNotFoundException 
     * @throws NullPointerException
     * @throws IOException  
     */   
    public synchronized boolean updateFile() throws FileNotFoundException, IOException, NullPointerException  {
        ObjectOutputStream oos;
        System.out.println("ServerData:updateFile");
        try{
            oos = new ObjectOutputStream(new FileOutputStream(fbFilePath));
            oos.writeObject(usersGraph);
            oos.writeObject(serverMail);
            oos.close();
            return true;
        }catch(IOException |NullPointerException ex){
            System.out.println("Errore durante la fase di aggiornamento ("+ex+")");
            return false;
        }
    }
    
}
