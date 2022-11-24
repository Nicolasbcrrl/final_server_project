package com.serrverprogramming.project.server_project.web;

import com.serrverprogramming.project.server_project.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static java.lang.Long.parseLong;

@Controller
public class MovieController {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ActorRepository actorRepository;
    @Autowired
    private DirectorRepository directorRepository;
    @Autowired
    private LinksStreamRepository linksStreamRepository;
    @Autowired
    private UserRepository userRepository;

    //access to the page login
    @RequestMapping(value="/login")
    public String login() {
        return "login";
    }

    //for the creation of a logout button
    @RequestMapping(value = "/logout")
    public String logout() {
        return "redirect:/login?logout";
    }

    //method to search a movie by title
    private List<Movie> searchByTitle(String title,List<Movie> movies){
        List<Movie> results = new ArrayList<>();
        title = title.toUpperCase();
        for(Movie movie : movies){
            String movieTitleUpper = movie.getTitle().toUpperCase();
            if(movieTitleUpper.contains(title)){
                results.add(movie);
            }
        }
        return results;
    }

    //method to search a movie by director
    private List<Movie> searchByDirector(String lastname, List<Movie> movies){
        List<Movie> results = new ArrayList<>();
        lastname = lastname.toUpperCase();
        for(Movie movie : movies){
            String directorLastnameUpper = movie.getDirector().getLastName().toUpperCase();
            if(directorLastnameUpper.contains(lastname)){
                results.add(movie);
            }
        }
        return results;
    }

    // Show all movies
    @RequestMapping(value = {"/", "/movieList"})
    public String movieList(@RequestParam(value = "title", required = false) String title,
                            @RequestParam(value = "director", required = false) String director,Model model){
        List<Movie> movies = (List<Movie>) movieRepository.findAll();
        if(director != null && !director.equals("")){
            movies = searchByDirector(director, movies);
        }
        if(title != null && !title.equals("")){
            movies = searchByTitle(title, movies);
        }
        model.addAttribute("movies", movies);
        return "movieList";
    }

    //create a new movie
    @RequestMapping(value = "/addMovie")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String addMovie(Model model) {
        model.addAttribute("movie", new Movie());
        model.addAttribute("directors", directorRepository.findAll());
        return "addNewMovie";
    }


    //method to put in place the page add
    @RequestMapping(value="/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String add(){
        return "add";
    }

    //method to save a new movie into the database
    @RequestMapping(value = "/saveMovie", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('ADMIN')")
    public String saveMovie(Movie movie) {
    Movie save = movieRepository.save(movie);
    System.out.println("-----------------------------------------"+ save.getId());
    return "redirect:/addCasting/"+save.getId();
    }

    //the method to select the actors and return a update list of actors without the actors already selected
    public List<Actor> actorSelection(Long id){
        Movie movie = movieRepository.findById(id).get();
        List<Actor> actorSelection = new ArrayList<>();
        List<Actor> lstAll = new ArrayList<>();
        actorSelection = actorRepository.findActorByMovieListTitle(movie.getTitle());
        lstAll = (List<Actor>) actorRepository.findAll();
        for(int i = actorSelection.size()-1; i >= 0; i--){
            lstAll.remove(actorSelection.get(i));
        }
        return lstAll;
    }

    //method to add a casting to a movie
    @RequestMapping(value="/addCasting/{id}", method= RequestMethod.GET)
    @PreAuthorize("hasAuthority('ADMIN')")
    public String addCasting(@PathVariable("id") String id, Model model ){
        System.out.println("id: "+id);
        long longId = parseLong(id);
        Movie movie = movieRepository.findById(longId).get();
        model.addAttribute("movie", movie);
        model.addAttribute("actors", actorSelection(longId));
        model.addAttribute("actor", new Actor());
        return "addActor";
    }

    //method to save a new casting into the database
    @RequestMapping(value="/saveCasting/{id}", method= RequestMethod.POST)
    @PreAuthorize("hasAuthority('ADMIN')")
    public String saveCasting(@PathVariable("id") String id, Actor actor){
        long longId = parseLong(id);
        Movie movie = movieRepository.findById(longId).get();
        Actor actorFind = actorRepository.findById(actor.getId()).get();
        actorFind.addMovie(movie);
        actorRepository.save(actorFind);
        return "redirect:/addCasting/"+id;
    }

    //method to redirect to the main page /movieList
    @RequestMapping(value="/finish")
    public String finish() {
       return "redirect:/movieList";
    }

    //method to display all the information about a movie and to access to the streaming links
    @RequestMapping(value="/watch", method=RequestMethod.GET)
    public String watchMovie(@RequestParam(name="id") String id, Model model){
        Long movieId = parseLong(id);
        Movie movie = movieRepository.findById(movieId).get();
        List<Actor> actorList = actorRepository.findActorByMovieListTitle(movie.getTitle());
        model.addAttribute("movie", movieRepository.findById(movieId).get());
        model.addAttribute("director", movie.getDirector());
        model.addAttribute("links", linksStreamRepository.findLinksStreamByMovie_Id(movieId));
        model.addAttribute("actors", actorList);
        return "moviePage";
    }

    //method to add edit a streaming link
    @RequestMapping(value="/editLink", method=RequestMethod.GET)
    @PreAuthorize("hasAuthority('ADMIN')")
    public String editLink(@RequestParam(name="id") String id, Model model){
        System.out.println("on rentre dans editLink");
        Long movieId = parseLong(id);
        model.addAttribute("movie", movieRepository.findById(movieId).get());
        model.addAttribute("links", linksStreamRepository.findLinksStreamByMovie_Id(movieId));
        model.addAttribute("newLink", new LinksStream());
        System.out.println("on sort de editLink");
        return "edit";
    }

    //method to save the modification about a streaming link
    @RequestMapping(value="/saveModification", method=RequestMethod.POST)
    @PreAuthorize("hasAuthority('ADMIN')")
    public String saveModification(@RequestParam(name="id") String id,LinksStream linksStream){
        Movie movie = movieRepository.findById(Long.parseLong(id)).get();
        linksStream.setMovie(movie);
        linksStreamRepository.save(linksStream);
        return "redirect:/movieList";
    }

    private String getUserDetails(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String usernameRequest = "";
        if (principal instanceof UserDetails){
            usernameRequest = ((UserDetails)principal).getUsername();
        }
        else {
            usernameRequest = principal.toString();
        }
        return usernameRequest;
    }

    //method to show the list of favourite movies of a user
    @RequestMapping(value = "/myList")
    public String myList(HttpServletRequest request,Model model){
        String usernameRequest = getUserDetails();
        List<Movie> movies = userRepository.findByUsername(usernameRequest).getMyMovies();
        model.addAttribute("movies", movies);
        return "myList";
    }

    //method to add a movie to the list of favourite movies of a user
    @RequestMapping(value = "/addMyList", method = RequestMethod.GET)
    public String addMyList(@RequestParam(name="id") String id){
        String usernameRequest = getUserDetails();
        User user = userRepository.findByUsername(usernameRequest);
        Movie movie = movieRepository.findById(Long.parseLong(id)).get();
        user.addMovie(movie);
        userRepository.save(user);
        List<Movie> lst = user.getMyMovies();
        for (Movie m : lst){
            System.out.println("-----------------------------"+"username : " + user.getUsername() + " " + m.getTitle());
        }
        return "redirect:/movieList";
    }

    //method to delete a movie from the list of favourite movies of a user
    @RequestMapping(value = "/removeMyList", method = RequestMethod.GET)
    public String removeMyList(@RequestParam(name="id") String id){
        String usernameRequest = getUserDetails();
        User user = userRepository.findByUsername(usernameRequest);
        Movie movie = movieRepository.findById(Long.parseLong(id)).get();
        user.removeMovie(movie);
        userRepository.save(user);
        List<Movie> lst = user.getMyMovies();
        for (Movie m : lst){
            System.out.println("-----------------------------"+"username : " + user.getUsername() + " " + m.getTitle());
        }
        return "redirect:/myList";
    }


}