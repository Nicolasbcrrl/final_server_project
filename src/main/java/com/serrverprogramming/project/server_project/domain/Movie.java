package com.serrverprogramming.project.server_project.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String duration;
    @Column(nullable = false)
    private String release;
    @Column(nullable = false)
    private String country;
    @Column(nullable = false)
    private String category;
    @ManyToOne
    private Director director;
    @ManyToMany(cascade = {CascadeType.REFRESH, CascadeType.MERGE}, mappedBy = "movieList")
    private List<Actor> actorList;
    @OneToMany(cascade = {CascadeType.REFRESH, CascadeType.MERGE}, mappedBy = "movie")
    private List<LinksStream> linksStream;


    public Movie() {
        this.actorList = new ArrayList<>();
    }

    public Movie(String title, String duration, String release, String country, String category, Director director) {
        this.title = title;
        this.duration = duration;
        this.release = release;
        this.country = country;
        this.category = category;
        this.director = director;
        this.actorList = new ArrayList<>();
        this.linksStream = new ArrayList<>();
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Director getDirector() {
        return director;
    }

    public void setDirector(Director director) {
        this.director = director;
    }

    public void addActor(Actor actor) {
        this.actorList.add(actor);
    }

    public Actor findActor(long id) {
        //loop through the list of actors and find the actor with the given id
        for (Actor actor : this.actorList) {
            //if the actor id matches the given id, return the actor
            if (actor.getId() == id) {
                return actor;
            }
        }
        //if no actor is found, return null
        return null;
    }

    public List<Actor> getActorList() {
        return actorList;
    }

    public void setActorList(List<Actor> actorList) {
        this.actorList = actorList;
    }

    public void addLinksStream(LinksStream linksStream) {
        this.linksStream.add(linksStream);
    }

    public LinksStream findLinksStream(long id) {
        //loop through the list of linksStream and find the linksStream with the given id
        for (LinksStream linksStream : this.linksStream) {
            //if the linksStream id matches the given id, return the linksStream
            if (linksStream.getId() == id) {
                return linksStream;
            }
        }
        //if no linksStream is found, return null
        return null;
    }

    public List<LinksStream> getLinksStream() {
        return linksStream;
    }

    public void setLinksStream(List<LinksStream> linksStream) {
        this.linksStream = linksStream;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", duration='" + duration + '\'' +
                ", release='" + release + '\'' +
                ", country='" + country + '\'' +
                ", category='" + category + '\'' +
                ", director=" + director +
                '}';
    }
}
