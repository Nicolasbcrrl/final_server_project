package com.serrverprogramming.project.server_project.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ActorRepository extends CrudRepository<Actor, Long> {
    Actor findByFirstNameAndLastName(String firstName, String lastName);
    List<Actor> findByFirstName(String firstName);
    List<Actor> findByLastName(String lastName);
    List<Actor> findActorByMovieListTitle(String title);
}
