package com.example.comicsbe.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document("ComicsNew")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Comics {

    @Id
    private String _id;

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

}
