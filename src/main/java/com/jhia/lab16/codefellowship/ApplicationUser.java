package com.jhia.lab16.codefellowship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ApplicationUser {
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    String username;
    String password;

    public ApplicationUser() {}

    public ApplicationUser(String username, String password) {
        this.username = username;
        this.password = this.bCryptPasswordEncoder.encode(password);
    }
}
