package Reti;

import java.io.*;
    
/**
 * Legge da tastiera un messaggio e lo salva.
 * @author Francesca Madeddu
 */
public class KeysReader{

    private String keys;
    
    /**
     * Legge da tastiera e salva la stringa letta.
     */
    public void readKeys(){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            keys = br.readLine();
        } catch (IOException ex) {
            System.out.println("Errore durante la lettura da tastiera ("+ex+")");
        }
    }  
    
    /**
     * Ritorna la stringa letta.
     * @return la stringa letta.
     */
    public String getKeys(){
        return keys;
    }
    
}
