package com.serrverprogramming.project.server_project;

import com.serrverprogramming.project.server_project.domain.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ServerProjectApplication {
    private static final Logger log = LoggerFactory.getLogger(ServerProjectApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ServerProjectApplication.class, args);
    }

    @Bean
    public CommandLineRunner streamingTransaction(MovieRepository movieRepository, DirectorRepository directorRepository, ActorRepository actorRepository, LinksStreamRepository linksStreamRepository, UserRepository userRepository) {
        return (args) -> {

            //create a standar user and an admin user
            BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
            userRepository.save(new User("admin", bc.encode("admin"), "ADMIN"));
            userRepository.save(new User("user", bc.encode("user"), "USER"));


            log.info("---------------------------------------------------------strat saving Tarantino movies---------------------------------------------------------");
            log.info("-------------------------strat saving director-------------------------");
            directorRepository.save(new Director("Quentin","Tarantino"));
            log.info("-------------------------strat saving actors-------------------------");
            List<Actor> actorsPulpFiction = new ArrayList<>();
            actorsPulpFiction.add(new Actor("John","Travolta"));
            actorsPulpFiction.add(new Actor("Samuel", "L. Jackson"));
            actorsPulpFiction.add(new Actor("John", "Travolta"));
            actorsPulpFiction.add(new Actor("Bruce", "Willis"));
            actorsPulpFiction.add(new Actor("Uma", "Thurman"));
            actorsPulpFiction.add(new Actor("Harvey", "Keitel"));
            actorsPulpFiction.add(new Actor("Ving", "Rhames"));
            actorsPulpFiction.add(new Actor("Tim", "Roth"));
            actorsPulpFiction.add(new Actor("Amanda", "Plummer"));

            //loop through the list of actors and save them to the database
            for(Actor actor : actorsPulpFiction){
                actorRepository.save(actor);
            }

            log.info("-------------------------strat saving pulp Fiction-------------------------");
            Movie pulpFiction = new Movie("Pulp Fiction", "2 h 34 min", "26 October 1994", "USA", "Gangster, Drama",directorRepository.findByFirstNameAndLastName("Quentin","Tarantino"));
            //pulpFiction.setActorList(actorsPulpFiction);
            movieRepository.save(pulpFiction);
            for(Actor actor : actorsPulpFiction){
                actor.addMovie(pulpFiction);
                actorRepository.save(actor);
            }
            log.info("-------------------------strat saving link-------------------------");
            List<LinksStream> linksPulpFiction = new ArrayList<>();
            linksPulpFiction.add(new LinksStream("https://tv.apple.com/fi/movie/pulp-fiction/umc.cmc.1hfvw2p79f9qdeydow8nmrn7t?action=play",Provider.AppleTv,pulpFiction, 3.99));
            //loop through the list of links and save them to the database
            for (LinksStream link : linksPulpFiction){
                linksStreamRepository.save(link);
            }


            log.info("---------------------------------------------------------strat saving Nolan movies--------------------------------------------------------------");
            log.info("-------------------------strat saving director-------------------------");
            directorRepository.save(new Director("Christopher","Nolan"));
            log.info("-------------------------strat saving actors-------------------------");
            List<Actor> actorsInterstellar = new ArrayList<>();
            actorsInterstellar.add(new Actor("Matthew","McConaughey"));
            actorsInterstellar.add(new Actor("Anne","Hathaway"));
            actorsInterstellar.add(new Actor("Jessica","Chastain"));
            actorsInterstellar.add(new Actor("Mackenzie","Foy"));
            actorsInterstellar.add(new Actor("Matt","Damon"));
            actorsInterstellar.add(new Actor("John","Lithgow"));
            actorsInterstellar.add(new Actor("Michael","Cain"));
            actorsInterstellar.add(new Actor("Wes","Bentley"));
            actorsInterstellar.add(new Actor("Ellen","Burstyn"));
            actorsInterstellar.add(new Actor("Bill","Irwin"));

            for (Actor actor : actorsInterstellar){
                actorRepository.save(actor);
            }
            log.info("-------------------------strat saving Interstellar-------------------------");
            Movie interstellar = new Movie("Interstellar", "2 h 49 min", "5 November 2014", "USA", "Aventure, Science-fiction, Drama",directorRepository.findByFirstNameAndLastName("Christopher","Nolan"));
            //interstellar.setActorList(actorsInterstellar);
            movieRepository.save(interstellar);
            //loop through the list of actors and add the movie to their list of movies
            for(Actor actor : actorsInterstellar){
                actor.addMovie(interstellar);
                actorRepository.save(actor);
            }

            log.info("-------------------------strat saving link-------------------------");
            List<LinksStream> linksInterstellar = new ArrayList<>();
            linksInterstellar.add(new LinksStream("https://play.hbomax.com/page/urn:hbo:page:GYGP7pwQv_ojDXAEAAAFc:type:feature?source=googleHBOMAX&action=open",Provider.HBOMax,interstellar, 8.99));
            linksInterstellar.add(new LinksStream("https://tv.apple.com/fi/movie/interstellar-2014/umc.cmc.1vrwat5k1ucm5k42q97ioqyq3?action=play",Provider.AppleTv,interstellar, 3.99));
            linksInterstellar.add(new LinksStream("https://play.google.com/store/movies/details?id=F6TU69adAzE&pli=1",Provider.GooglePlay,interstellar, 3.49));
            //loop through the list of links and save them to the database
            for (LinksStream link : linksInterstellar){
                linksStreamRepository.save(link);
            }

            log.info("---------------------------------------------------------strat saving Spielberg movies---------------------------------------------------------");

            directorRepository.save(new Director("Steven","Spielberg"));
            for (Movie movie : movieRepository.findAll()) {
                log.info(movie.toString());
            }
            for(Actor actor : actorRepository.findActorByMovieListTitle("Pulp Fiction")){
                log.info(actor.toString());
            }

        };
    }

}
