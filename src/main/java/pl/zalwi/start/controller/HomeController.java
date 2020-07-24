package pl.zalwi.start.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @GetMapping("/album")
    public String homePage(Model model, @RequestParam(required = false) String token) {
        model.addAttribute("token", token);
        return "album";
    }

    @GetMapping("/navbar")
    public String homePage2(Model model, @RequestParam(required = false) String token) {
        model.addAttribute("token", token);
        return "navbar";
    }

    @GetMapping("/start")
    public String homeOwn(Model model, @RequestParam(required = false) String token) {
        model.addAttribute("token", token);
        return "own_home";
    }
}
