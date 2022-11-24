package com.serrverprogramming.project.server_project.web;

import com.serrverprogramming.project.server_project.domain.Director;
import com.serrverprogramming.project.server_project.domain.DirectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class DirectorController {
    @Autowired
    private DirectorRepository directorRepository;

    // display the list of directors to have look on directors already in the database and possibility to add new directors
    @RequestMapping(value = "/addDirector")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String addDirector(Model model){
        model.addAttribute("director", new Director());
        model.addAttribute("directors", directorRepository.findAll());
        return "addNewDirector";
    }
    //save a new director
    @RequestMapping(value = "/saveDirector", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('ADMIN')")
    public String saveDirector(Director director) {
        directorRepository.save(director);
        return "redirect:/add";
    }
}
