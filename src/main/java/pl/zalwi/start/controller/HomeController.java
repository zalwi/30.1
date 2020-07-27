package pl.zalwi.start.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.zalwi.global.service.OrderService;

@Controller
public class HomeController {

    OrderService orderService;

    @Autowired
    public HomeController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/start")
    public String home(Model model, @RequestParam(required = false) String token) {
        model.addAttribute("token", token);
        model.addAttribute("orders", orderService.getMostPriorityServiceOrders());
        return "home";
    }
}
