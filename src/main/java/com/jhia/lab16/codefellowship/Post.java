package com.jhia.lab16.codefellowship;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    String body;
    LocalDate createdAt;

    public Post() { }

    public Post(String body, LocalDate createdAt) {
        this.body = body;
        this.createdAt = createdAt;
    }

    public String getBody() {
        return body;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }
}
