package com.progetto_ingegneria.pocketvenice.User;

public class User {

    protected String fullName, birthdate, email, mobile;

    /**
     * Costruttore pubblico senza parametri usato per creare un utente vuoto.
     */
    public User() {
        //MUST BE EMPTY
    }

    /**
     * Costruttore pubblico con parametri usato per creare un utente con le informazioni fornite.
     * @param fullName indica il nome e cognome.
     * @param birthdate indica la data di nascita.
     * @param email indica la mail.
     * @param mobile indica il numero di telefono.
     */
    public User(String fullName, String birthdate, String email, String mobile) {
        this.fullName = fullName;
        this.birthdate = birthdate;
        this.email = email;
        this.mobile = mobile;
    }

    /**
     * Getter
     * @return Ritorna il nome e cognome.
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Getter
     * @return Ritrona la data di nascita.
     */
    public String getBirthdate() {
        return birthdate;
    }

    /**
     * Getter
     * @return Ritorna la mail.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Getter
     * @return Ritorna il numero di telefono.
     */
    public String getMobile() {
        return mobile;
    }
}
