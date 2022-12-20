package com.example.comicsbe.service;

import com.example.comicsbe.dto.ComicCustomDto;
import com.example.comicsbe.dto.ComicDto;
import com.example.comicsbe.dto.ComicForm;
import com.example.comicsbe.dto.CreatorForm;
import com.example.comicsbe.exception.ComicNotFoundException;
import com.example.comicsbe.model.Comics;
import com.example.comicsbe.model.Creators;
import com.example.comicsbe.repository.ComicsRepository;
import lombok.RequiredArgsConstructor;

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

    public ComicCustomDto getComicById(final Long id) {
        Comics comic = repository.findByCid(id).orElseThrow(() -> new ComicNotFoundException("Comic with id: " + id + " not found"));
        if (comic.getCreators() != null && comic.getCreators().size() == 1) {
            StringBuilder creator = new StringBuilder();
            creator.append(comic.getCreators().get(0).getName()).append(" (").append(comic.getCreators().get(0).getJob()).append(")");
            return toDto(comic, creator.toString());
        }

        StringBuilder creators = new StringBuilder();
        String creatorsString = null;
        if (comic.getCreators() != null) {
            for (Creators creator : comic.getCreators()) {
                creators.append(creator.getName()).append(" (").append(creator.getJob()).append("), ");
            }
            creatorsString = creators.substring(0, creators.length() - 2);
        }
        return toDto(comic, creatorsString);
    }

    public ComicCustomDto createComic(ComicForm comicForm) {
        List<Creators> creators = null;
        if (comicForm.getCreators() != null) {
            creators = toCreators(comicForm.getCreators());
        }
        Comics comic = new Comics(comicForm, creators);

        Long lastId = repository.findAll().stream().map(Comics::getCid).mapToLong(i -> i).max().orElse(0);
        comic.setCid(lastId + 1);
        return getComicById(repository.save(comic).getCid());

    }

    public ComicCustomDto updateComicById(Long id, ComicForm comicForm) {
        Comics comic = repository.findByCid(id).orElseThrow(() -> new ComicNotFoundException("Comic with id: " + id + " not found"));
        comic = updateComic(comic, comicForm);
        return getComicById(repository.save(comic).getCid());
    }

    public void deleteComicById(Long id) {
        Comics comic = repository.findByCid(id).orElseThrow(() -> new ComicNotFoundException("Comic with id: " + id + " not found"));
        repository.delete(comic);
    }

    public List<ComicCustomDto> getMarvelComics() {
        List<Comics> comics = repository.getAllByPublisher("Marvel Comics");
        return getComicCustomDtoList(comics);
    }

    public List<ComicCustomDto> getComicsRatedOver7() {
        List<Comics> comics = repository.getAllByRating(7.0);
        return getComicCustomDtoList(comics);
    }

    public List<ComicCustomDto> getFirstIssueComics() {
        List<Comics> comics = repository.getAllByIssueNumber("#1");
        return getComicCustomDtoList(comics);
    }

    private ComicDto toDto(Comics comics) {
        ComicDto comicDto = new ComicDto(
                comics.getCid(),
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

    private ComicCustomDto toDto(Comics comics, String creators) {
        ComicCustomDto comicCustomDto = new ComicCustomDto(
                comics.getCid(),
                comics.getTitle(),
                creators,
                comics.getMain_characters(),
                comics.getSide_characters(),
                comics.getPrice(),
                comics.getRelease_date(),
                comics.getPublisher(),
                comics.getPage_count(),
                comics.getFormat(),
                comics.getIssue_number(),
                comics.getRating());
        return comicCustomDto;
    }

    private List<ComicCustomDto> getComicCustomDtoList(List<Comics> comics) {
        List<ComicCustomDto> returnList = new ArrayList<ComicCustomDto>();
        for (Comics comic : comics) {
            if (comic.getCreators().size() == 1) {
                StringBuilder creator = new StringBuilder();
                creator.append(comic.getCreators().get(0).getName()).append(" (").append(comic.getCreators().get(0).getJob()).append(")");
                returnList.add(toDto(comic, creator.toString()));
            } else {
                StringBuilder creators = new StringBuilder();
                for (Creators creator : comic.getCreators()) {
                    creators.append(creator.getName()).append(" (").append(creator.getJob()).append("), ");
                }
                returnList.add(toDto(comic, creators.substring(0, creators.length() - 2)));
            }
        }
        return returnList;
    }

    private Comics updateComic(Comics comic, ComicForm comicForm) {
        comic.setIssue_number(comicForm.getIssue_number());
        comic.setMain_characters(comicForm.getMain_characters());
        comic.setPage_count(comicForm.getPage_count());
        comic.setPrice(comicForm.getPrice());
        comic.setPublisher(comicForm.getPublisher());
        comic.setRating(comicForm.getRating());
        comic.setRelease_date(comicForm.getRelease_date());
        comic.setFormat(comicForm.getFormat());
        comic.setSide_characters(comicForm.getSide_character());
        comic.setTitle(comicForm.getTitle());

        List<Creators> creators = null;
        if (comicForm.getCreators() != null) {
            creators = toCreators(comicForm.getCreators());
        }
        comic.setCreators(creators);
        return comic;
    }

    private List<Creators> toCreators(List<CreatorForm> creators) {
        List<Creators> ret = new ArrayList<>();
        for (CreatorForm creator : creators) {
            ret.add(new Creators(creator.getJob(), creator.getName()));
        }
        return ret;
    }

}
