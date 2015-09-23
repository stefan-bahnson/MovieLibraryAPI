package com.stefanbahnson.movielibrary.model;

import java.util.Comparator;
import java.util.Objects;

/**
 * Represents a movie with a given title and genre. A {@code Movie} can 
 * only have one name and genre.<p>
 * 
 * A movie is naturally ordered alphabetically by title name but can
 * also be ordered by genre then title, both in ascending order.
 *
 * @author Stefan Bahnson
 */
public class Movie implements Comparable<Movie> {
    /**
     * Orders {@code movie}s by genre then title.
     */
    public static final Comparator<Movie> BY_GENRE = new Comparator<Movie>() {
        // sort by movie genre
        @Override
        public int compare(Movie m1, Movie m2) {
            int c = m1.getGenre().name().compareTo(m2.getGenre().name());
            if (c == 0) {
                c = m1.getTitle().toLowerCase().compareTo(m2.getTitle().toLowerCase());
            }
            return c;
        }
    };
    
    private String title;
    private Genre genre;
    
    public String getTitle() {
        return title;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }
    
    /**
     * Returns a string that "textually represents" a {@code Movie} for 
     * increased readability.
     * 
     * @return A String representation of a Movie
     */
    @Override
    public String toString() {
        return new StringBuilder()
                .append(getGenre())
                .append(" - ")
                .append(getTitle())
                .toString();
    }
    
    /**
     * Compares Strings of this movie's title with the specified movie's 
     * title for order.
     * 
     * @param movie the specified Movie object
     * @return      a negative integer, zero, or a positive integer as this object 
     *              is less than, equal to, or greater than the specified movie.
     */
    @Override
    public int compareTo(Movie movie) {
        Objects.requireNonNull(movie);
        return this.title.compareTo(movie.title);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        
        if (obj instanceof Movie) {
            Movie that = (Movie) obj;
            
            return this.genre == that.genre &&
                   Objects.equals(this.title, that.title);
        }
        
        return false;
    }
}
