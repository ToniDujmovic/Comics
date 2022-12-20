package com.example.comicsbe.repository;

import com.example.comicsbe.model.Comics;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ComicsRepository extends MongoRepository<Comics, String> {

    List<Comics> findAll();

    Optional<Comics> findByCid (Long id);

    @Query("{rating : {$gte : ?0}}")
    List<Comics> getAllByRating(Double i);

    @Query("{publisher : ?0}")
    List<Comics> getAllByPublisher(String publisher);

    @Query("{issue_number : ?0}")
    List<Comics> getAllByIssueNumber(String issueNumber);
}
