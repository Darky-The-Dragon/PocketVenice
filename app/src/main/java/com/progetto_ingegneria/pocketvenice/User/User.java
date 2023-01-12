package com.progetto_ingegneria.pocketvenice.User;

public class User {

    protected String fullName, birthdate, email, mobile;


    public User() {
        //MUST BE EMPTY
    }

    /**
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
     * @return Ritorna il nome e cognome.
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * @return Ritrona la data di nascita.
     */
    public String getBirthdate() {
        return birthdate;
    }

    /**
     * @return Ritorna la mail.
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return Ritorna il numero di telefono.
     */
    public String getMobile() {
        return mobile;
    }
}
