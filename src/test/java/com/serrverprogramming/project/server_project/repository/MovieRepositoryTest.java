package com.serrverprogramming.project.server_project.repository;

import com.serrverprogramming.project.server_project.domain.Director;
import com.serrverprogramming.project.server_project.domain.DirectorRepository;
import com.serrverprogramming.project.server_project.domain.Movie;
import com.serrverprogramming.project.server_project.domain.MovieRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class MovieRepositoryTest {
    @Autowired
    private MovieRepository repository;
    @Autowired
    private DirectorRepository direpository;

    @Test
    public void findByTitleTest(){
        Movie movie = repository.findByTitle("Pulp Fiction");
        assertThat(movie.getId()).isEqualTo(repository.findByTitle("Pulp Fiction").getId());
    }

    @Test
    public void saveMovieTest(){
        Director director = new Director("Lana", "Wachowski");
        direpository.save(director);
        Movie movie  = new Movie("The Matrix", "2 h 16 min", "31 March 1999", "USA", "Action, Sci-Fi", director);
        repository.save(movie);
        assertThat(movie.getId()).isEqualTo(repository.findByTitle("The Matrix").getId());
    }

    @Test
    public void deleteMovieTest(){
        Director director = new Director("Lana", "Wachowski");
        direpository.save(director);
        Movie movie  = new Movie("The Matrix", "2 h 16 min", "31 March 1999", "USA", "Action, Sci-Fi", director);
        repository.save(movie);
        repository.deleteById(movie.getId());
        assertThat(repository.findByTitle("The Matrix")).isNull();
    }
}
