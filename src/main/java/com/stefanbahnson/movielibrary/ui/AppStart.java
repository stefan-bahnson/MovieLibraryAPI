package com.stefanbahnson.movielibrary.ui;

import com.stefanbahnson.movielibrary.model.MovieLibrary;

/**
 * This application keeps a library of movies.<p>
 * 
 * The client is able to add movies aswell as tag them with a genre.
 * There are also options for removing and editing movies in the library as well
 * as powerful search and filtering options to print the movies to the console.
 * 
 * These options are made available through the {@code UI}.<p>
 * 
 * This application requires a console to run.
 *
 * <p>FUTURE FEATURES: Random choice, comments, ratings, search by ratings,
 *                     multiple genres for the same movie, database, 
 *                     web scraping.<p>
 *
 * Last modified: 17-10-2014
 * @author Stefan Bahnson
 */
public class AppStart {

    public static void main(String[] args) {
        MovieLibrary movieLib = new MovieLibrary();
        UI ui = new UI(movieLib);
        ui.run();
    }

}
