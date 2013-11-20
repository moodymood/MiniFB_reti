package Reti;

import java.io.Serializable;

/**
 * Rappresenta un generico messaggio e-mail.
 * Esso e' composto da tre campi: utente mittente, utente destinatario e una 
 * stringa contentente il messaggio.
 * @author Francesca Madeddu
 */
public class Message implements Serializable{

    private User sender;
    private User recipient;
    private String message;

    /**
     * Crea un istanza di un messaggio.
     * @param mitt e' il mittente del messaggio;
     * @param dest e' il destinatario del messaggio;
     * @param msg e' il testo del messaggio;
     */
    public Message(User mitt, User dest, String msg){
        this.sender=mitt;
        this.recipient=dest;
        this.message=msg;
    }
    
    /**
     * Ritorna il destianatrio del messaggio su cui il metodo e' invocato.
     * @return il destinatario User di un determinato messaggio.
     */
    public User getDest() {
        return recipient;
    }

    /**
     * Ritorna il mittente del messaggio su cui il metodo e' invocato.
     * @return e' il mittente di un determinato messaggio.
     */
    public User getMitt() {
        return sender;
    }

    /**
     * Ritorna il testo del messaggio su cui il metodo e' invocato.
     * @return il testo del messaggio di una determinata mail.
     */
    public String getMsg() {
        return message;
    }

    @Override
    public String toString(){
        return "\nDa:"+sender+" a "+recipient+"\nMsg:"+message;
    }


}
