package pl.zalwi.people.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.zalwi.global.service.SystemUserService;

@Controller
public class SystemUserController {

    SystemUserService systemUserService;

    @Autowired
    public SystemUserController(SystemUserService systemUserService) {
        this.systemUserService = systemUserService;
    }

    @GetMapping("/employee")
    public String addNewServiceOrder(Model model){
        model.addAttribute("employees", systemUserService.getAllEmployees());
        return "systemUsers";
    }
}
