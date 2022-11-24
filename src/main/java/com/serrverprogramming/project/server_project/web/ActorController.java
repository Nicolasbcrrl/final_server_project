package com.serrverprogramming.project.server_project.web;

import com.serrverprogramming.project.server_project.domain.Actor;
import com.serrverprogramming.project.server_project.domain.ActorRepository;
import com.serrverprogramming.project.server_project.domain.Director;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ActorController {
    @Autowired
    private ActorRepository actorRepository;

    // display the list of actors to have look on actors already in the database and possibility to add new actors
    @RequestMapping(value = "/addActor")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String addActor(Model model){
        model.addAttribute("actor", new Actor());
        model.addAttribute("actors", actorRepository.findAll());
        return "addNewActor";
    }

    // Save new actor
    @RequestMapping(value = "/saveActor", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('ADMIN')")
    public String saveActor(Actor actor) {
        actorRepository.save(actor);
        return "redirect:/addActor";
    }

    // just a fonction to go back to the /add page
    @RequestMapping(value = "/goBack")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String saveOtherActor() {
        return "redirect:add";
    }

}
