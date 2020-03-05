package com.yami.studio.banana.merchantapp.entity.user;

public class User {
    private int id;
    private String email;
    private String first_name;
    private String last_name;

    public User(int id, String email, String first_name, String last_name) {
        this.id = id;
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }
}
