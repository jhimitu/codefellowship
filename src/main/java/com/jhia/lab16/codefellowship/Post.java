package com.jhia.lab16.codefellowship;


import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
public class Post {
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
