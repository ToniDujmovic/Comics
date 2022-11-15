package com.example.comicsbe.dto;

import com.example.comicsbe.model.Creators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ComicDto {

    private String title;

    private String creatorName;

    private String creatorJob;

    private String main_characters;

    private String[] side_character;

    private String price;

    private String release_date;

    private String publisher;

    private Integer page_count;

    private String format;

    private String issue_number;

    private Double rating;
}
