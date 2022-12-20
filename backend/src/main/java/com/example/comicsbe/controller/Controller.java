package com.example.comicsbe.controller;

import com.example.comicsbe.dto.ComicCustomDto;
import com.example.comicsbe.dto.ComicDto;
import com.example.comicsbe.dto.ComicForm;
import com.example.comicsbe.service.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class Controller {

    private final Service service;

    @GetMapping(value = "/api/getAll")
    public ResponseEntity<List<ComicDto>> getAll() {
        List<ComicDto> comics = service.getAll();
        return ResponseEntity.ok(comics);
    }

    @GetMapping("/comic/{id}")
    public ResponseEntity<ComicCustomDto> getComicById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getComicById(id));
    }

    @GetMapping("/comicsRatingOver7")
    public ResponseEntity<List<ComicCustomDto>> getComicsRatedOver7() {
        List<ComicCustomDto> comics = service.getComicsRatedOver7();
        return ResponseEntity.ok(comics);
    }


    @GetMapping("/marvelComics")
    public ResponseEntity<List<ComicCustomDto>> getMarvelComics() {
        List<ComicCustomDto> comics = service.getMarvelComics();
        return ResponseEntity.ok(comics);
    }

    @GetMapping("/firstIssueComics")
    public ResponseEntity<List<ComicCustomDto>> getFirstIssueComics() {
        List<ComicCustomDto> comics = service.getFirstIssueComics();
        return ResponseEntity.ok(comics);
    }

    @PostMapping("/comic")
    public ResponseEntity<ComicCustomDto> createComic(@RequestBody ComicForm comicForm){
        return ResponseEntity.ok(service.createComic(comicForm));
    }

    @PutMapping("/comic/{id}")
    public ResponseEntity<ComicCustomDto> updateComicById(@PathVariable Long id, @RequestBody ComicForm comicForm) {
        return ResponseEntity.ok(service.updateComicById(id, comicForm));
    }

    @DeleteMapping("/comic/{id}")
    public ResponseEntity<String> deleteComicById(@PathVariable Long id) {
        service.deleteComicById(id);
        return ResponseEntity.ok("Comic with id: " + id + " was successfully deleted.");
    }

}
