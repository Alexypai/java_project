package com.myaudiolibary.web.controllerThym;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class accueilControllerThym {

    @GetMapping(value = "/accueil")
    public String Accueil(final ModelMap model) {

        return "Accueil";
    }
}

