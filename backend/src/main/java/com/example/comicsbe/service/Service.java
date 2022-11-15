package com.example.comicsbe.service;

import com.example.comicsbe.dto.ComicDto;
import com.example.comicsbe.model.Comics;
import com.example.comicsbe.model.Creators;
import com.example.comicsbe.repository.ComicsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;

import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class Service {

    private final ComicsRepository repository;

    public List<ComicDto> getAll() {
        List<Comics> all = repository.findAll();
        List<ComicDto> returnList = new ArrayList<ComicDto>();
        for (Comics comic : all) {
            if (comic.getCreators().size() == 1) {
                returnList.add(toDto(comic));
            } else {
                for (Creators creator : comic.getCreators()) {
                    Comics newComic = new Comics(comic, creator);
                    returnList.add(toDto(newComic));
                }
            }
        }

        return returnList;
    }

    private ComicDto toDto(Comics comics) {
        ComicDto comicDto = new ComicDto(
                comics.getTitle(),
                comics.getCreators().get(0).getName(),
                comics.getCreators().get(0).getJob(),
                comics.getMain_characters(),
                comics.getSide_characters(),
                comics.getPrice(),
                comics.getRelease_date(),
                comics.getPublisher(),
                comics.getPage_count(),
                comics.getFormat(),
                comics.getIssue_number(),
                comics.getRating());
        return comicDto;

    }
}
