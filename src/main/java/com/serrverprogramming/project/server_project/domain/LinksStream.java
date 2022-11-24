package com.serrverprogramming.project.server_project.domain;

import javax.persistence.*;
import java.util.List;

@Entity
public class LinksStream {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="link_id")
    private long id;
    @Column(nullable = false)
    private String link;
    @Column(nullable = false)
    private Provider provider;
    @ManyToOne
    private Movie movie;
    private double price;

    public LinksStream() {
    }

    public LinksStream(String link, Provider provider, Movie movie, double price) {
        this.link = link;
        this.provider = provider;
        this.movie = movie;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "LinksStream{" +
                "id=" + id +
                ", link='" + link + '\'' +
                ", provider=" + provider +
                ", movie=" + movie +
                '}';
    }
}
