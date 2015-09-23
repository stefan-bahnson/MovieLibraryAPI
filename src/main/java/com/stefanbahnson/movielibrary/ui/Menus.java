package com.stefanbahnson.movielibrary.ui;

import java.io.Console;

/**
 * A helper class that prints the many menus offered in the UI to the console.
 * It also handles the selection of options presented in the menus with input
 * error checking to make sure the input is correctly formatted.<p>
 * This class can not be instantiated!
 * 
 * @author Stefan Bahnson
 */
public final class Menus
{    
    private Menus() {}// can not be instantiated!
    
    private static final Console c = System.console();
    
    /**
     * List of options for the main menu.
     */
    public static final String[] MAIN_MENU = {
        ">> Add new movie", // 1
        ">> Edit library",  // 2
        ">> View library",  // 3
        ">> Instructions",  // 4
        "<< Exit"  // 5
    };
    
    /**
     * List of options for the submenu genre.
     */
    public static final String[] SUB_MENU_GENRE = {
        ">> Action",   // 1
        ">> Thriller", // 2
        ">> Horror",   // 3
        ">> Drama",    // 4
        ">> Comedy"    // 5
    };
    
    /**
     * List of options for the submenu edit movie.
     */
    public static final String[] SUB_MENU_EDIT = {
        ">> Edit movie title",     // 1
        ">> Edit genre for title", // 2
        ">> Remove movie",         // 3
        ">> Return to MAIN MENU",  // 4
        };
    
    /**
     * List of options for submenu print.
     */
    public static final String[] SUB_MENU_PRINT = {
        ">> Search for title",         // 1
        ">> Show all sorted by genre", // 2
        ">> Show all sorted by title", // 3
        ">> Show by genre",            // 4
        ">> Return to MAIN MENU",      // 5
    };
    

    
    /**
     * Returns a number that corresponds to a selection in a menu.
     * 
     * @param options a menu list of options
     * @return Returns a number that corresponds to a selection in a menu
     */
    public static int selectMenuOption(String... options) {

        for (int i = 0; i < options.length; ++i) {
            c.writer().printf("%d. %s%n", i + 1, options[i]);
        }
        
        for (;;) {
            final int choice;

            try {
                choice = Integer.parseInt(
                             c.readLine("\nSelect an option and press Enter: \n"
                                      + ">>"));

                if (choice > 0 && choice < options.length + 1) {
                    return choice;
                } else {
                    c.printf("Your choice is not available! choose another.%n");
                }
            } catch (NumberFormatException e) {
                c.printf(
                     "Enter a number that corresponds with a menu choice!%n");
            }
        }
    }
    
}
