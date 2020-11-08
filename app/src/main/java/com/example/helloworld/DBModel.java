package com.example.helloworld;

public class DBModel {
    String email;
    String pass;

    // contrustor(empty)
    public DBModel() {
    }

    // constructor
    public DBModel(String email, String pass) {
        this.email = email;
        this.pass = pass;
    }

    /*Setter and Getter*/

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

}
