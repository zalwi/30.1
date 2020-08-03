package pl.zalwi.start.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.zalwi.global.service.ServiceOrderService;

@Controller
public class HomeController {

    ServiceOrderService serviceOrderService;

    @Autowired
    public HomeController(ServiceOrderService serviceOrderService) {
        this.serviceOrderService = serviceOrderService;
    }

    @GetMapping("/start")
    public String home(Model model, @RequestParam(required = false) String token) {
        model.addAttribute("token", token);
        model.addAttribute("orders", serviceOrderService.getMostPriorityServiceOrders());
        return "home";
    }
}
