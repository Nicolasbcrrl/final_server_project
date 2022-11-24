package com.serrverprogramming.project.server_project.repository;
import com.serrverprogramming.project.server_project.domain.Movie;
import com.serrverprogramming.project.server_project.domain.MovieRepository;
import com.serrverprogramming.project.server_project.domain.User;
import com.serrverprogramming.project.server_project.domain.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    @Autowired
    private MovieRepository movieRepository;

    BCryptPasswordEncoder bc = new BCryptPasswordEncoder();

    @Test
    public void findByUsernameTest(){
        assertThat(repository.findByUsername("user")).isNotNull();
    }

    @Test
    public void creatUserTest(){
        User nicolas = new User("Nicolas", bc.encode("password"), "USER");
        Movie movie = movieRepository.findByTitle("Pulp Fiction");
        nicolas.addMovie(movie);
        repository.save(nicolas);
        assertThat(repository.findByUsername("Nicolas").getId()).isEqualTo(nicolas.getId());
    }

    @Test
    public void deleteUserTest(){
        User nicolas = new User("Nicolas", bc.encode("password"), "USER");
        Movie movie = movieRepository.findByTitle("Pulp Fiction");
        nicolas.addMovie(movie);
        repository.save(nicolas);
        User nicolas2 = repository.findByUsername("Nicolas");
        nicolas2.setMovie(movie);
        nicolas = nicolas2;
        repository.save(nicolas);
        repository.deleteById(nicolas.getId());
        assertThat(repository.findById(nicolas.getId())).isEqualTo(Optional.empty());
    }

}
