package com.serrverprogramming.project.server_project.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="act_id")
    private long id;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @ManyToMany(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    private List<Movie> movieList;

    public Actor() {
        this.movieList = new ArrayList<>();
    }

    public Actor(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.movieList = new ArrayList<>();
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

    public void addMovie(Movie movie){
        this.movieList.add(movie);
    }

    public Movie findMovie(long index) {
        //loop through the list of movies and find the movie with the given id
        for (Movie movie : this.movieList) {
            //if the movie id matches the given id, return the movie
            if (movie.getId() == index) {
                return movie;
            }
        }
        //if no movie is found, return null
        return null;
    }

    public List<Movie> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }
}
