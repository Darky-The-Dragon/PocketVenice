package com.progetto_ingegneria.pocketvenice.User;


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

    public String getFullName() {
        return fullName;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }
}
