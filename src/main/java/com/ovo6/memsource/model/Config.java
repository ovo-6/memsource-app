package com.ovo6.memsource.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Config {

    @Id private String username;
    private String password;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
