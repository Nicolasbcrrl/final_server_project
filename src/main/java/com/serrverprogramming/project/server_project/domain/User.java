package com.serrverprogramming.project.server_project.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="person")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    // Username with unique constraint
    @Column(name = "name", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String passwordHash;

    @Column(name = "role", nullable = false)
    private String role;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Movie> myMovies;

    public User() {
    }

    public User(String username, String passwordHash, String role) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
        this.myMovies = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void addMovie(Movie movie){
        this.myMovies.add(movie);
    }

    public void removeMovie(Movie movie){
        this.myMovies.remove(movie);
    }

    public List<Movie> getMyMovies() {
        return myMovies;
    }

    public void setMyMovies(List<Movie> myMovies) {
        this.myMovies = myMovies;
    }

    public void setMovie(Movie movie){
        for(int i = this.myMovies.size()-1; i >= 0; i-- ){
            if(this.myMovies.get(i).getId() == movie.getId()){
                this.myMovies.remove(i);
            }
        }
    }

}
