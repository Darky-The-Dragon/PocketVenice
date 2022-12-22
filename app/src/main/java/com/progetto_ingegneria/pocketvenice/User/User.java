package com.progetto_ingegneria.pocketvenice.User;

public class User {

    protected String fullName, age, email, mobile;

    public User() {
        //MUST BE EMPTY
    }

    public User(String fullName, String age, String email, String mobile) {
        this.fullName = fullName;
        this.age = age;
        this.email = email;
        this.mobile = mobile;
    }

    public String getFullName() {
        return fullName;
    }

    public String getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }
}
