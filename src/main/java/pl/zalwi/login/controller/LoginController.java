package pl.zalwi.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.zalwi.login.model.UserCredentials;

@Controller
public class LoginController {

    @GetMapping("/")
    public String loginPage(Model model, UserCredentials userCredentials) {
        model.addAttribute("credentials", userCredentials);
        return "login";
    }

    @PostMapping("/login")
    public String CheckCredentials(Model model, UserCredentials userCredentials){
        System.out.println(userCredentials);
        return "redirect:/start";
    }
}
