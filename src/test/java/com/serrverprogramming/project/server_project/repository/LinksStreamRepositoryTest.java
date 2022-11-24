package com.serrverprogramming.project.server_project.repository;

import com.serrverprogramming.project.server_project.domain.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class LinksStreamRepositoryTest {

    @Autowired
    private LinksStreamRepository repository;

    @Autowired
    private DirectorRepository directorRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Test
    public void findByLinkTest(){
        List<LinksStream> lstStream = repository.findByLink("https://tv.apple.com/fi/movie/pulp-fiction/umc.cmc.1hfvw2p79f9qdeydow8nmrn7t?action=play");
        LinksStream stream = lstStream.get(0);
        assertThat(lstStream).hasSize(1);
    }

    @Test
    public void findLinksStreamByMovie_IdTest(){
        Movie movie = movieRepository.findByTitle("Pulp Fiction");
        List<LinksStream> lstStream = repository.findLinksStreamByMovie_Id(movie.getId());
        LinksStream stream = lstStream.get(0);
        assertThat(lstStream).hasSize(1);
    }

    @Test
    public void createNewLinksStream(){
        Movie movie = movieRepository.findByTitle("Pulp Fiction");
        LinksStream stream = new LinksStream("https://www.youtube.com/watch?v=HcJWKlScENQ",Provider.Youtube,movie, 3.99);
        repository.save(stream);
        assertThat(stream.getId()).isEqualTo(repository.findByLink(stream.getLink()).get(0).getId());
    }

    @Test
    public void deleteTest(){
        Movie movie = movieRepository.findByTitle("Pulp Fiction");
        LinksStream stream = new LinksStream("https://www.youtube.com/watch?v=HcJWKlScENQ",Provider.Youtube,movie, 3.99);
        repository.save(stream);
        repository.deleteById(stream.getId());
        List<LinksStream> lstStream = repository.findByLink(stream.getLink());
        assertThat(lstStream).hasSize(0);
    }

}
