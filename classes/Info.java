package Reti;

/**
 * Contiene una stringa il cui contenuto può essere richiamato e visualizzato
 * attraverso un metodo statico.
 * @author Francesca Madeddu
 */
public class Info {
    private static String info = "I comandi possibili sono:\n"+
                                " register:<nome>-<cognome>\n "+
                                " login:<nome>-<cognome>\n "+
                                " search:<nome>-<cognome>\n "+
                                " friend:<nome>-<cognome>\n "+
                                " listfriend\n"+
                                " msg:<nome>-<cognome> <testodelmessaggio> \n"+
                                " disconnect\n"+
                                " help\n";
    
    /**
     * Ritorna il comando memorizzato.
     * @return
     */
    public String getInfo(){
        return info;
    }
    
    /**
     * Setta una nuova stringa;
     * @param newInfo sostituisce il valore corrente del messaggio con il valore
     * di str.
     */
    public void setInfo(String newInfo){
        info = newInfo;
    }
    
    /**
     * Stampa a video il contenuto del messaggio;
     */
    public static void printInfo(){
        System.out.println(info);
    }
    
    
}
