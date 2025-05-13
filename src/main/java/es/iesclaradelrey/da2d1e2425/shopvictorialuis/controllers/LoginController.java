package es.iesclaradelrey.da2d1e2425.shopvictorialuis.controllers;

import es.iesclaradelrey.da2d1e2425.shopvictorialuis.entities.Category;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.services.CategoryService;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.services.ShoppingCartItemService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;

@Controller
public class LoginController{


    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

//    todo PostMapping("/register") para registrarse
}
