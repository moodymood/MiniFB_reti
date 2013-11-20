
package Reti;

import java.io.Serializable;


/**
 * Modella un generico utente.
 * Esso e' identificato dal suo nome e cognome.
 * @author Francesca Madeddu
 */
public class User implements Serializable{
    
    private static final long serialVersionUID = 1L;
    private String name = "";
    private String surname = "";

    /**
     * Crea un istanza di un utente identificato da nome name e cognome surname.
     * @param name nome dell'utente
     * @param surname cognome dell'utente
     */
    public User(String name, String surname) {
        this.name = firstToUpper(name);
        this.surname = firstToUpper(surname);
    }

    /**
     * Ritorna il nome dell'utente su cui il metodo e' invocato.
     * @return il nome dell'utente.
     */
    public String getName() {
        return name;
    }
    /**
     * Ritorna il cognome dell'utente su cui il metodo e' invocato.
     * @return cognome dell'utente.
     */
    public String getSurname() {
        return surname;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if ((this.surname == null) ? (other.surname != null) : !this.surname.equals(other.surname)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 29 * hash + (this.surname != null ? this.surname.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString(){
        return name + " " + surname;
    }
    
    /**
     * Ritorna il nome e cognome dell'utente in formato stringa e con le iniziali
     * maiuscole.
     * @param s Stringa contenente nome e cognome di un utente;
     * @return il nome e cognome dell'utente con le iniziali maiuscole.
     */
    private String firstToUpper(String s){
        char[] stringArray = s.toCharArray();
        stringArray[0] = Character.toUpperCase(stringArray[0]);
        return new String(stringArray);
    }
}
