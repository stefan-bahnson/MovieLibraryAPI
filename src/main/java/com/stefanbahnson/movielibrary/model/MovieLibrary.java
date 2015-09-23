package com.stefanbahnson.movielibrary.model;

import static com.stefanbahnson.movielibrary.model.Movie.BY_GENRE;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a library of {@code Movie}s. There are options for adding, 
 * removing, editing, sorting and filtering the library.<p>
 * 
 * To add to the library, a movie with set descriptions is required. 
 * Pre existing movies can have their descriptions edited but the library 
 * can not create a new movie.<p>
 * 
 * By default the movies are sorted by their title in ascending 
 * alphabetical order. 
 * More options for sorting and filtering is offered.
 * The filtering option includes viewing part of the library if a query 
 * is provided.<p>
 * 
 * There ia also an option for checking for duplicate movies with the 
 * same unique title.
 * 
 * @author Stefan Bahnson
 */
public class MovieLibrary {
    /**
     * Holds the library of movies.<p>
     * 
     * This class guarantee that this list will always be sorted.
     */
    private final List<Movie> list = new ArrayList();
    
    /**
     * Matches a provided movie title to a movie title in the library to
     * see if duplicate is found.
     * 
     * @param movieTitle title description of a movie
     * @return {@code true} if a duplicate is found and {@code false} if none.
     */
    public boolean findDuplicate(String movieTitle) {
        for (Movie mov : list) {
            if (mov.getTitle().equalsIgnoreCase(movieTitle)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Adds a movie with set descriptions to the library.
     * 
     * @param movie represents a movie with a given title and genre
     */
    public void addMovie(Movie movie) {
        list.add(movie);
        Collections.sort(list);
    }
    
    /**
     * Removes a movie from the library if the provided movie title matches 
     * a movie title in the library. The titles must match exactly but is not
     * case-sensitive.<p>
     * 
     * @param movieTitle title description of a movie
     */
    public void removeMovie(Movie movie) {
        for (Iterator<Movie> itr = list.iterator(); itr.hasNext();) {
            Movie mov = itr.next();
            if (mov.equals(movie)) {
                itr.remove();
            }
        }
        Collections.sort(list);
    }
    
    /**
     * Returns all movies that has the same genre as the one provided.
     * These movies can only be viewed and not modified.
     *  
     * @param genre genre description of a movie
     * @return an unmodifiable partial view of the library
     */
    public List<Movie> getByGenre(Genre genre) {
        List<Movie> currentGenreList = new ArrayList();
                
        for (Movie movie : list) {
            if (movie.getGenre().name().equalsIgnoreCase(genre.name())) {
                currentGenreList.add(movie);
            }
        }
        return Collections.unmodifiableList(currentGenreList);
    }
    
    /**
     * Returns all movies that has a title which matches the query.<p>
     * 
     * The exact characther sequence of the query is matched to parts 
     * of a movie title in that specific order. The query can contain a 
     * single character. If none, the whole library will be matched.
     * The match is not case-sensitive!<p>
     * 
     * i.e. <b>[rd of the ri]</b> will match Lo<b>[rd of the Ri]</b>ngs
     * 
     * @param query a characther sequence of any sort
     * @return a new List of all the matches movies
     */
    public List<Movie> getMovieMatches(String query) {
        List<Movie> movieMatches = new ArrayList();
                
        for (Movie mov : list) {
            if (mov.getTitle().matches("(?i).*" + query + ".*")) {
                movieMatches.add(mov);
            }
        }
        return movieMatches;
    }
    
    /**
     * Returns a movie that has the same movie title as the title provided.
     * The titles must match exactly but is not case-sensitive.
     * 
     * @param movieTitle title description of a movie
     * @return a movie that has the same movie title as the title provided.
     */
    public Movie getUniqueMovie(String movieTitle) {
        for (Movie mov : list) {
            if (mov.getTitle().equalsIgnoreCase(movieTitle)) {
                return mov;
            }
        }
        return null;
    }
    
    /**
     * Returns all movies in the library sorted by title.
     * These movies can only be viewed and not modified.
     * 
     * @return all movies in the library sorted by title
     */
    public List<Movie> getAllByTitle() {
        return Collections.unmodifiableList(list);
    }
    
    /**
     * Returns all movies in the library sorted by genre.
     * These movies can only be viewed and not modified.
     * 
     * @return All movies in the library sorted by genre
     */
    public List<Movie> getAllByGenre() {
        List<Movie> copy = new ArrayList<>(list);
        Collections.sort(copy, BY_GENRE);
        return copy;
    }
}