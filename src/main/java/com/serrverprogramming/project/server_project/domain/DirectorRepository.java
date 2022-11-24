package com.serrverprogramming.project.server_project.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DirectorRepository extends CrudRepository<Director, Long> {
    List<Director> findByFirstName(String firstName);
    List<Director> findByLastName(String lastName);
    Director findByFirstNameAndLastName(String firstName, String lastName);
}
