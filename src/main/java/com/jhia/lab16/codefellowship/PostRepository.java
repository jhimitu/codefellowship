package com.jhia.lab16.codefellowship;

import javafx.geometry.Pos;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface PostRepository extends CrudRepository<Post, Long> {
    Post findById(long id);
    List<Post> findAll();
}
