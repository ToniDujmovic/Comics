package com.example.comicsbe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ComicNotFoundException extends RuntimeException{

    public ComicNotFoundException(String message) {
        super(message);
    }
}
