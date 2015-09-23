package com.stefanbahnson.movielibrary.ui;

import com.stefanbahnson.movielibrary.model.Genre;
import com.stefanbahnson.movielibrary.model.MovieLibrary;
import com.stefanbahnson.movielibrary.model.Movie;
import java.io.Console;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * This is the user interface where the client can interact with the 
 * Movie Library<p>
 *  
 * There are several options for managing the library and thay are presented in
 * an easy to use menu tree structure.
 *
 * @author Stefan Bahnson
 */
public class UI {

    private static final Console c = System.console();
    
    private final MovieLibrary library;
    private Movie movieSelected;        //The current selected movie
    private int optionSelected;         //The current selected option
    private Genre genreSelected;        //The current selected genre
//    private List<Movie> currentList;    //Current list to print
    
    public UI(MovieLibrary library) {
        this.library = library;
    }
    
    /**
     * Runs the UI containing the main menu.
     * Everything proceeds from the main menu until the application is closed.
     * There are several sub menus but the client will always be able to return 
     * to the main menu.<p>
     * This is where the client can exit the application.<p>
     * Requires a console to run!
     */
    public void run() {
        // check for console else exit application..
        if (c == null) {
            JOptionPane.showMessageDialog(null, 
                    "Please use a Console to run the program");
            System.exit(1);
        }
        
        printInstructions();
        
        for(;;) {
            c.writer().println("\n:: MAIN MENU ::");
            optionSelected = Menus.selectMenuOption(Menus.MAIN_MENU);
            
            switch (optionSelected) {
                case 1: createMovieEntry();  break;
                case 2: submenuEditMovie();  break;
                case 3: submenuPrint();      break; 
                case 4: printInstructions(); break; 
                case 5: closeApplication();  break;
            }
        }    
    }
    
    /**
     * Adds title and genre to a movie which then is added to the Library.
     * These are requested through a query in the console.
     * The title must contain atleast one character to be able to proceed with
     * creating an entry.<p>
     * 
     * The library can only contain one unique title. Other than that, 
     * there are no restrictions in the naming convention.<p>
     * 
     * A genre is added to the the movie by choosing from a submenu of 
     * predetermined genres and must be set to proceed.
     */
    private void createMovieEntry() {
        movieSelected = new Movie();
        String movieTitle = c.readLine(
                "\n\n:: ADD MOVIE ::\nEnter the movie title:\n>>");
        
        // Checks if the library allready contains title
        if (library.findDuplicate(movieTitle)) {
            c.writer().println("\nMovie allready added!");
        }
        // Checks if no title is entered
        else if(movieTitle.isEmpty()) {
            c.writer().println("\nPlease enter a movie title to create a movie entry!");
        }
        else {
            movieSelected.setTitle(movieTitle);
            submenuSelectGenre();
            addGenreToMovie();
            library.addMovie(movieSelected);
            c.writer().printf("%n%n%s added to %s%n%n", movieTitle, movieSelected.getGenre());
        }
    }
    
    /**
     * A submenu to the main menu that has options for editing the movies in the 
     * library. After editing a movie, the submenu will be presented again<p>
     * 
     * The submenu has the choice to return to the main menu.
     */
    private void submenuEditMovie() {
        c.writer().println("\n:: EDIT MOVIE ::");
        optionSelected = Menus.selectMenuOption(Menus.SUB_MENU_EDIT);
        switch (optionSelected) {
            case 1: editMovieTitle();   break;
            case 2: editMovieGenre(); break;
            case 3: removeMovieEntry(); break;
            case 4: c.writer().println("Returning to MAIN MENU\n"); break;
            }
    }
    
    /**
     * Renames a movie title, allready in the the library. The selection is 
     * made through a query in the console. If the query matches a title
     * in the library, a new title name can be given to the movie.<p>
     * 
     * To prevent multiple titles from being matched, the selection is 
     * made by entering the full movie title but is not case sensitive.<p>
     * 
     * When done, returns to the menu: Edit Movie.
     */
    private void editMovieTitle() {
        printToConsole(library.getAllByTitle());
        
        String currentMovieTitle = c.readLine(
            "Please type the full name of the movie you want to edit:\n>> ");
            
        movieSelected = library.getUniqueMovie(currentMovieTitle);
        
        if(movieSelected == null) {
            c.writer().println("\nNo movie by that name...\n\n");
        }
        else {
            String newMovieTitle = c.readLine("Rename to >> ");
            movieSelected.setTitle(newMovieTitle);
        }
        
        submenuEditMovie();
    }
    
    /**
     * Changes the genre of a movie title, allready in the the library. 
     * The selection is made through a query in the console. If the query 
     * matches a title in the library, a new genre can be added to the movie.<p>
     * 
     * To prevent multiple titles from being matched, the selection is 
     * made by entering the full movie title but is not
     * case sensitive.<p>
     * 
     * When done, returns to the menu: Edit Movie.
     */
    private void editMovieGenre() {
        printToConsole(library.getAllByTitle());
        
        String movieTitle = c.readLine(
            "Please type the full name of the movie you want to edit:\n>> ");
            
        movieSelected = library.getUniqueMovie(movieTitle);
        
        if(movieSelected == null) {
            c.writer().println("\nNo movie by that name...\n\n");
        }
        else {
            c.writer().println("\nChoose new genre for title");
            submenuSelectGenre();
            addGenreToMovie();
            c.writer().printf("%n%s is now added to %s%n%n", 
                              movieTitle, movieSelected.getGenre());
        }
        
        submenuEditMovie();
    }
    
    /**
     * Removes a movie from the library. The selection is made through a query 
     * in the console. Only if the query matches a title in the library, 
     * can the movie be removed.<p>
     * 
     * To prevent multiple titles from being matched, the selection is 
     * made by entering the full movie title but is not
     * case sensitive.<p>
     * 
     * When done, returns to the menu: Edit Movie.
     */
    private void removeMovieEntry() {
        printToConsole(library.getAllByTitle());
        
        String movieTitle = c.readLine(
            "Please type the full name of the movie you want to remove:\n>> ");
            
        movieSelected = library.getUniqueMovie(movieTitle);
        
        if(movieSelected == null) {
            c.writer().println("\nNo movie by that name...\n\n");
        }
        else {
            library.removeMovie(movieSelected);
            c.writer().printf("%n%s was successfully removed%n%n", 
                              movieSelected.getTitle());
        }
        
        submenuEditMovie();
    }
    
    /**
     * A submenu to the main menu that shows the movies in the library through
     * different filter options.<p>
     * 
     * The submenu has the choice to return to the main menu.
     */
    private void submenuPrint() {
        c.writer().println("\n:: VIEW LIBRARY ::");
        optionSelected = Menus.selectMenuOption(Menus.SUB_MENU_PRINT);
        switch (optionSelected) {
            case 1: printAllSearchMatches(); break;
            case 2: printAllSortedByGenre(); break;
            case 3: printAllSortedByTitle(); break;
            case 4: printSelectedGenre();    break;
            case 5: c.writer().println("Returning to MAIN MENU"); break;
            }
    }
    
    /**
     * Searches the Library for titles matching the search query and prints 
     * the result to a console.
     * If no search query is entered, the whole library will be printed.
     * Depending on how specific the query is, the more relevant the result 
     * will be.<p>
     * 
     * The search is not case sensitive and matches the character sequence 
     * of the query to parts of the title.<p>
     * 
     * i.e. Searching for (man) will match both:<br>
     * Bat[man] and [Man]eater<p>
     * 
     * When done, returns to the menu: View Library.
     */
    private void printAllSearchMatches() {
        String searchQuery = c.readLine("Search for movie:\n>>");
        List<Movie> currentList = library.getMovieMatches(searchQuery);
        printToConsole(currentList);
        submenuPrint();
    }
    
    /**
     * Prints all movies in the library to the console, sorted by genre 
     * then title.<p>
     * 
     * When done, returns to the menu: View Library.
     */
    private void printAllSortedByGenre() {
        List<Movie> currentList = library.getAllByGenre();
        printToConsole(currentList);
        submenuPrint();
    }
    
    /**
     * Prints all movies in the library to the console, sorted by title<p>
     * 
     * When done, the user will be returned to the menu: View Library.
     */
    private void printAllSortedByTitle() {
        List<Movie> currentList = library.getAllByTitle();
        printToConsole(currentList);
        submenuPrint();
    }
    
    /**
     * Prints all movies in the selected genre to the console, sorted by 
     * genre then title.<p>
     * 
     * When done, the user will be returned to the menu: View Library.
     */
    private void printSelectedGenre() {
        submenuSelectGenre();
        List<Movie> currentList = library.getByGenre(genreSelected);
        printToConsole(currentList);    
        submenuPrint();
    }
    
    /**
     * Iterates through a list of movies and prints them to a console.
     * This is true as long as the {@code printList} is not empty 
     * or returns {@code null}. If not, an error message will be displayed.
     * 
     * @param currentList A sorted and/or filtered list of movies
     */
    private void printToConsole(List<Movie> currentList) {
        c.writer().println();
        if (currentList != null && !currentList.isEmpty()) {
            for (Movie movie : currentList) {
                c.writer().println(movie);
            }
        }
        else { c.writer().println("No match found!"); }
        c.writer().println();
    }

    /**
     * A submenu to the main menu that shows the available genres that can
     * be selected.<p>
     * 
     * The selection can be added to a new movie or be used to change the 
     * genre of a movie in the library.
     * This submenu does NOT by itself add to or change the genre of a movie 
     * but can be used to do so.
     */
    private void submenuSelectGenre() {
        c.writer().println("\n:: SELECT GENRE ::");
        
        optionSelected = Menus.selectMenuOption(Menus.SUB_MENU_GENRE);
        switch (optionSelected) {
            case 1: genreSelected = Genre.ACTION;   break;
            case 2: genreSelected = Genre.THRILLER; break;
            case 3: genreSelected = Genre.HORROR;   break;
            case 4: genreSelected = Genre.DRAMA;    break;
            case 5: genreSelected = Genre.COMEDY;   break;
        }
    }
    
    /**
     * Adds a genre to the currently selected movie if a genre has previously
     * been selected. This is done in the submenu Genre.
     */
    private void addGenreToMovie() {
        movieSelected.setGenre(genreSelected);
    }
    
    /**
     * Prints instructions to the console on how to use the application.
     */
    private void printInstructions() {
        c.writer().println(
            "\n:: INSTRUCTIONS ::\n\n"
          + "Enter the number next to the option you want to choose\n"
          + "and continue by pressing ENTER.\n\n"
          + "When selecting a movie for edit, the full title must be entered.\n"
          + "Selecting a movie is not case sensitive\n\n"
          + "When searching for a movie, the query will be matched to part of\n"
          + "a movie title i.e. man will return Batman.\n"
          + "The search is not case sensitive.\n\n");
        
        c.readLine("Press Enter to continue to the MAIN MENU ");
    }
    
    /**
     * Displays a message and closes the application.
     */
    private void closeApplication() {
        c.writer().println("\nClosing application...");
        System.exit(1);
    }
}
