package by.bsuir.wt2.webapp.classes.service;


import by.bsuir.wt2.webapp.classes.entities.Movie;

import java.util.ArrayList;
import java.util.List;


/**
 * The Cart class represents a shopping cart for courses.
 * It includes methods to add, remove, clear, check if a course is contained, retrieve all courses, and check if the cart is empty.
 *
 * @version 1.0
 * @author Aleksej
 * @since 2023-12-07
 */
public class Cart {
    private List<Movie> movies = new ArrayList<>();

    public Cart() {

    }

    public boolean add(Movie movie){
        return movies.add(movie);
    }

    public boolean remove(Movie movie){
        return movies.remove(movie);
    }

    public void clear(){
        movies.clear();
    }

    public boolean contains(Movie movie){
        return movies.contains(movie);
    }

    public List<Movie> getAll() {
        return movies;
    }

    public boolean isEmpty() {
        return movies.isEmpty();
    }
}
