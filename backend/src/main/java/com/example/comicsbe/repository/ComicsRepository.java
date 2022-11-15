package com.example.comicsbe.repository;

import com.example.comicsbe.model.Comics;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ComicsRepository extends MongoRepository<Comics, String> {

    List<Comics> findAll();
}
