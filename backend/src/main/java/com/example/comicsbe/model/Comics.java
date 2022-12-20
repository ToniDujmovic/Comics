package com.example.comicsbe.model;

import com.example.comicsbe.dto.ComicForm;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document("Comics")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Comics {

    @Id
    private String _id;

    private Long cid;

    private String issue_number;

    private String main_characters;

    private Integer page_count;

    private List<Creators> creators;

    private String price;

    private String publisher;

    private Double rating;

    private String format;

    private String release_date;

    private String[] side_characters;

    private String title;

    public Comics(Comics comics, Creators creators){
        this._id = comics._id;
        this.cid = comics.cid;
        this.issue_number = comics.issue_number;
        this.main_characters = comics.main_characters;
        this.page_count = comics.page_count;
        this.price = comics.price;
        this.publisher = comics.publisher;
        this.rating = comics.rating;
        this.format = comics.format;
        this.release_date = comics.release_date;
        this.side_characters = comics.side_characters;
        this.title = comics.title;

        this.creators = new ArrayList<Creators>();
        this.creators.add(creators);
    }

    public Comics(ComicForm comics, List<Creators> creators){
        this.issue_number = comics.getIssue_number();
        this.main_characters = comics.getMain_characters();
        this.page_count = comics.getPage_count();
        this.price = comics.getPrice();
        this.publisher = comics.getPublisher();
        this.rating = comics.getRating();
        this.format = comics.getFormat();
        this.release_date = comics.getRelease_date();
        this.side_characters = comics.getSide_character();
        this.title = comics.getTitle();
        this.creators = creators;
    }

}
