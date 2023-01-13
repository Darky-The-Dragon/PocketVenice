package com.progetto_ingegneria.pocketvenice.User;

/**
 * Permette di creare oggetti di tipo User
 */
public class User {

    protected String fullName, birthdate, email, mobile;

    public User() {
        //MUST BE EMPTY
    }

    public User(String fullName, String birthdate, String email, String mobile) {
        this.fullName = fullName;
        this.birthdate = birthdate;
        this.email = email;
        this.mobile = mobile;
    }

    /**
     * @return Ritorna nome e cognome dell'utente.
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * @return Ritorna la data di nascita dell'utente.
     */
    public String getBirthdate() {
        return birthdate;
    }

    /**
     * @return Ritorna la mail dell'utente.
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return Ritorna il numero di telefono dell'utente.
     */
    public String getMobile() {
        return mobile;
    }
}
