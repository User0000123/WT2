package by.bsuir.wt2.webapp.classes.entities;

/**
 * The Movie class represents a movie.
 * It includes properties such as ID, name, price, director, description, and genre of the movie.
 *
 * @version 1.0
 * @author Aleksej
 * @since 2023-12-07
 */
public class Movie {

    private int id;
    private String name;

    private double price;

    private String director;

    private String description;

    private String genre;

    public Movie() {

    }

    public Movie(String movieName, double moviePrice, String movieDirector, String description, String genre) {
        this.name = movieName;
        this.price = moviePrice;
        this.director = movieDirector;
        this.description = description;
        this.genre = genre;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
