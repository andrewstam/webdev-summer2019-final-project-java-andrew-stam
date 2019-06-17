// Created by Andrew Stam
package com.example.myapp.models;

import com.example.myapp.converters.MovieConverter;

import javax.persistence.*;

// Represents a movie object, only with an ID since info is loaded from omdb API
@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Convert(converter = MovieConverter.class)
    private String id;

    public Movie() {
        this.id = "unset";
    }

    public Movie(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
