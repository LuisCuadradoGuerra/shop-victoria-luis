package es.iesclaradelrey.da2d1e2425.shopvictorialuis.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping({"/", ""})
    public String admin() {
        return "/admin/admin";
    }
}
