package com.example.comicsbe.controller;

import com.example.comicsbe.dto.ComicDto;
import com.example.comicsbe.model.Comics;
import com.example.comicsbe.service.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class Controller {

    private final Service service;

    @GetMapping(value = "/getAll")
    public ResponseEntity<List<ComicDto>> getAll(){
        List<ComicDto> comics = service.getAll();
        return ResponseEntity.ok(comics);
    }
}
