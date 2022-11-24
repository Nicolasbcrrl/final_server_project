package com.serrverprogramming.project.server_project.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MovieRepository extends CrudRepository<Movie, Long> {
    Movie findByTitle(String title);
}
