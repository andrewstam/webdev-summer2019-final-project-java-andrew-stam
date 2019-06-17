// Created by Andrew Stam
// Based on code found at: https://www.baeldung.com/jpa-attribute-converters
package com.example.myapp.converters;

import com.example.myapp.models.Movie;

import javax.persistence.*;

@Converter
public class MovieConverter implements AttributeConverter<Movie, String> {
    private static final String SEPARATOR = ", ";

    @Override
    public String convertToDatabaseColumn(Movie movie) {
        if (movie == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        System.out.println("\n\n\n\n\n\nCONVERT TO DB:: " + movie.getId() + "\n\n\n");

        return movie.getId();
    }

    @Override
    public Movie convertToEntityAttribute(String dbMovie) {
        // Example value: '{1, tt1856191, 0}'
        if (dbMovie == null || dbMovie.isEmpty()) {
            return null;
        }

        String[] tokens = dbMovie.split(SEPARATOR);

        if (tokens == null || tokens.length == 0) {
            return null;
        }
        System.out.println("\n\n\n\n\n\nCONVERT STRING:: " + dbMovie + "\n\n\n");

        Movie movie = new Movie();
        String id = !tokens[1].isEmpty() ? tokens[1] : null;
        if (dbMovie.contains(SEPARATOR)) {
            movie.setId(id);
        }

        return movie;
    }
}
