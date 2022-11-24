package com.serrverprogramming.project.server_project.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Director {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="dir_id")
    private long id;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @OneToMany(cascade = {CascadeType.REFRESH, CascadeType.MERGE}, mappedBy = "director")
    private List<Movie> movies;

    public Director() {
        this.movies = new ArrayList<>();
    }

    public Director(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.movies = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void addMovie(Movie movie) {
        this.movies.add(movie);
    }

    public Movie getMovie(long id) {
        //loop through the list of movies and find the movie with the given id
        for (Movie movie : this.movies) {
            //if the movie id matches the given id, return the movie
            if (movie.getId() == id) {
                return movie;
            }
        }
        //if no movie is found, return null
        return null;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
