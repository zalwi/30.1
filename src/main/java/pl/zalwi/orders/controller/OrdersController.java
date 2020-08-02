package pl.zalwi.orders.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.zalwi.global.model.ContactPerson;
import pl.zalwi.global.model.ServiceOrder;
import pl.zalwi.global.service.OrderService;
import pl.zalwi.orders.model.OrdersFilters;
import pl.zalwi.people.service.ContactPersonService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class OrdersController {

    OrderService orderService;
    ContactPersonService contactPersonService;

    @Autowired
    public OrdersController(OrderService orderService, ContactPersonService contactPersonService) {
        this.orderService = orderService;
        this.contactPersonService = contactPersonService;
    }
    //    @GetMapping("/list")
//    public String ordersList(Model model, @RequestParam(required = false) String token) {
//        model.addAttribute("token", token);
//        model.addAttribute("orders", orderService.getAllServiceOrders());
//        return "orders";
//    }

    @GetMapping("/list")
    public String listOrdersPageByPage(Model model,
                                         @RequestParam(name = "page", defaultValue = "1") int page,
                                         @RequestParam(name = "sort", required = false) String sort,
                                         OrdersFilters ordersFilters) {
        PageRequest pageable;
        if (sort == null || sort.isEmpty()) {
            pageable = PageRequest.of(page - 1, 10);
            model.addAttribute("sortOption", "");
        } else {
            pageable = PageRequest.of(page - 1, 10, Sort.by(sort).ascending());
            model.addAttribute("sortOption", sort);
        }
        boolean finishedServiceOrders = false;
        Page<ServiceOrder> orderPage = orderService.findAllForFiltersAndSort(ordersFilters, pageable, finishedServiceOrders);
        int totalPages = orderPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("orderList", orderPage.getContent());
        model.addAttribute("filters", ordersFilters);
        model.addAttribute("currentPage", page);
        return "orders";
    }

    @GetMapping("/archive")
    public String listArchivedOrdersPageByPage(Model model,
                                         @RequestParam(name = "page", defaultValue = "1") int page,
                                         @RequestParam(name = "sort", required = false) String sort,
                                         OrdersFilters ordersFilters) {
        PageRequest pageable;
        if (sort == null || sort.isEmpty()) {
            pageable = PageRequest.of(page - 1, 10);
            model.addAttribute("sortOption", "");
        } else {
            pageable = PageRequest.of(page - 1, 10, Sort.by(sort).ascending());
            model.addAttribute("sortOption", sort);
        }
        boolean finishedServiceOrders = true;
        Page<ServiceOrder> orderPage = orderService.findAllForFiltersAndSort(ordersFilters, pageable, finishedServiceOrders);
        int totalPages = orderPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("orderList", orderPage.getContent());
        model.addAttribute("filters", ordersFilters);
        model.addAttribute("currentPage", page);
        return "archive";
    }

    @GetMapping("/newServiceOrder")
    public String addNewServiceOrder(Model model,
                                     @RequestParam(name = "person", required = true) Long personId){
        model.addAttribute("actionDescription", "Dodawanie nowego zlecenia serwisowego");
        model.addAttribute("action", "Dodaj");
        model.addAttribute("actionLink", "addServiceOrder");
        model.addAttribute("isBlocked", false);
        model.addAttribute("isNew", true);
        model.addAttribute("personId", personId);
        return "orderForm";
    }

    @PostMapping("/addServiceOrder")
    public String addServiceOrder(ServiceOrder serviceOrder, @RequestParam(name = "person", required = true) Long personId) {
        serviceOrder.setContactPerson(contactPersonService.getContactPerson(personId));
        serviceOrder.setFinished(false);
        orderService.addNewServiceOrder(serviceOrder);
        return "redirect:/list";
    }
}
