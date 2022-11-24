package com.serrverprogramming.project.server_project.web;

import com.serrverprogramming.project.server_project.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LinkController {
    @Autowired
    private LinksStreamRepository linksStreamRepository;
    @Autowired
    private MovieRepository movieRepository;

    private Provider provider;

    //create a new link
    @RequestMapping(value="/addLink")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String addLink(Model model) {
        model.addAttribute("linkStream", new LinksStream());
        model.addAttribute("movies", movieRepository.findAll());
        return "addNewLink";
    }
    //save a new link in the database
    @RequestMapping(value="/saveLink")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String saveLink(LinksStream linksStream) {
        linksStreamRepository.save(linksStream);
        return "redirect:/addLink";
    }

    //delete a link taking the id of the link as parameter
    @RequestMapping(value="/deleteLink/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteLink(@PathVariable("id") String id, Model model) {
        Movie movie = linksStreamRepository.findById(Long.parseLong(id)).get().getMovie();
        linksStreamRepository.deleteById(Long.parseLong(id));
        return "redirect:../editLink?id="+movie.getId();
    }
}
