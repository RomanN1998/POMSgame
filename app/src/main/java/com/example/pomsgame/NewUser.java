package com.example.pomsgame;

public class NewUser {
    public String password;
    public String id, name, sec_name, email;
    public int xpUser;

    public NewUser() {}

    public NewUser(String id, String name, String password,String sec_name, String email, int xpUser) {
        this.id = id;
        this.name = name;
        this.sec_name = sec_name;
        this.email = email;
        this.xpUser = xpUser;
        this.password = password;
    }
}
