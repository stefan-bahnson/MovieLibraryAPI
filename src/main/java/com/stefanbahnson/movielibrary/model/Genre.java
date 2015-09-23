package com.stefanbahnson.movielibrary.model;

/**
 * Represents the different genres a {@code Movie} can have.
 * 
 * @author Stefan Bahnson
 */
public enum Genre {
    //Added whitespace to be better presented in a console..
    ACTION("Action  "),
    THRILLER("Thriller"),
    HORROR("Horror  "),
    DRAMA("Drama   "),
    COMEDY("Comedy  ");

    private final String genreAsString;

    private Genre(String genreAsString) {
        this.genreAsString = genreAsString;
    }
    
    /**
     * Returns a string that "textually represents" a {@code Genre} for 
     * increased readability.
     * 
     * @return A String representation of a Genre
     */
    @Override
    public String toString() {
        return this.genreAsString;
    }

}
