package com.example.comicsbe.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ComicCustomDto {
    private Long cid;

    private String title;

    private String creators;

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
