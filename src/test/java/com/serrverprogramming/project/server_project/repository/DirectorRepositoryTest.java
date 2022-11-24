package com.serrverprogramming.project.server_project.repository;

import com.serrverprogramming.project.server_project.domain.Director;
import com.serrverprogramming.project.server_project.domain.DirectorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class DirectorRepositoryTest {

    @Autowired
    private DirectorRepository repository;

    @Test
    public void findByFirstNameTest(){
        List<Director> lstDirector = repository.findByFirstName("Quentin");
        Director director = lstDirector.get(0);
        assertThat(lstDirector).hasSize(1);
        assertThat(director.getLastName()).isEqualTo("Tarantino");
    }

    @Test
    public void findByLastNameTest(){
        List<Director> lstDirector = repository.findByLastName("Tarantino");
        Director director = lstDirector.get(0);
        assertThat(lstDirector).hasSize(1);
        assertThat(director.getFirstName()).isEqualTo("Quentin");
    }

    @Test
    public void findByFirstNameAndLastNameTest(){
        Director director = repository.findByFirstNameAndLastName("Quentin", "Tarantino");
        assertThat(director.getFirstName()).isEqualTo("Quentin");
        assertThat(director.getLastName()).isEqualTo("Tarantino");
    }

    @Test
    public void createNewDirector(){
        Director director = new Director("James", "Cameron");
        repository.save(director);
        assertThat(director.getId()).isEqualTo(repository.findByFirstNameAndLastName(director.getFirstName(), director.getLastName()).getId());
    }

    @Test
    public void deleteTest(){
        Director director = new Director("James", "Cameron");
        repository.save(director);
        repository.deleteById(director.getId());
        List<Director> lstDirector = repository.findByLastName("Cameron");
        assertThat(lstDirector).hasSize(0);
    }

}
