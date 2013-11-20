package Reti;

/**
 * Stampa a video un dato messaggio.
 * @author Francesca Madeddu
 */
public class KeysWriter {
    private String keys;
    
    KeysWriter(String keys){
        this.keys = keys;
    }
    
    /**
     * Setta il valore della stringa da stampare a video a keys.
     * @param keys sostituisce il valore della stringa da stampare a video.
     */
    public void setKeys(String keys){
        this.keys = keys;
    }
    
    /**
     * Stampa a video il messaggio.
     */
    public void printKeys(){
        System.out.println(keys);
    }
    
}
