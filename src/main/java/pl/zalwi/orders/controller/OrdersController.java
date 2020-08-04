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
import pl.zalwi.global.model.OrderTask;
import pl.zalwi.global.model.ServiceOrder;
import pl.zalwi.global.model.TaskStatus;
import pl.zalwi.global.repository.SystemUserRepository;
import pl.zalwi.global.service.ServiceOrderService;
import pl.zalwi.global.service.SystemUserService;
import pl.zalwi.login.model.SystemUser;
import pl.zalwi.orders.model.OrdersFilters;
import pl.zalwi.global.service.ContactPersonService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class OrdersController {

    ServiceOrderService serviceOrderService;
    ContactPersonService contactPersonService;
    SystemUserService systemUserService;

    @Autowired
    public OrdersController(ServiceOrderService serviceOrderService, ContactPersonService contactPersonService, SystemUserService systemUserService) {
        this.serviceOrderService = serviceOrderService;
        this.contactPersonService = contactPersonService;
        this.systemUserService = systemUserService;
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
        Page<ServiceOrder> orderPage = serviceOrderService.findAllForFiltersAndSort(ordersFilters, pageable, finishedServiceOrders);
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
        Page<ServiceOrder> orderPage = serviceOrderService.findAllForFiltersAndSort(ordersFilters, pageable, finishedServiceOrders);
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

    @GetMapping("/updateServiceOrder")
    public String updateServiceOrder(@RequestParam(name = "id") Long id, Model model) {
        Optional<ServiceOrder> optionalServiceOrderById = serviceOrderService.getServiceOrderById(id);
        if (optionalServiceOrderById.isPresent()) {
            model.addAttribute("serviceOrder", optionalServiceOrderById.get());
        } else {
            return "err";
        }
        model.addAttribute("actionDescription", "Modyfikowanie zlecenia");
        model.addAttribute("action", "Modyfikuj");
        model.addAttribute("actionLink", "modifyServiceOrder");
        model.addAttribute("isBlocked", false);
        model.addAttribute("isBlockedEndDate", false);
        return "orderForm";
    }

    @GetMapping("/removeServiceOrder")
    public String removeServiceOrder(@RequestParam(name = "id") Long id, Model model) {
        Optional<ServiceOrder> optionalServiceOrderById = serviceOrderService.getServiceOrderById(id);
        if (optionalServiceOrderById.isPresent()) {
            model.addAttribute("serviceOrder", optionalServiceOrderById.get());
        } else {
            return "err";
        }
        model.addAttribute("actionDescription", "Usuwanie zlecenia");
        model.addAttribute("action", "Usuń");
        model.addAttribute("actionLink", "deleteServiceOrder");
        model.addAttribute("isBlocked", true);
        model.addAttribute("isBlockedEndDate", true);
        return "orderForm";
    }

    @PostMapping("/addServiceOrder")
    public String addServiceOrder(ServiceOrder serviceOrder, @RequestParam(name = "person", required = true) Long personId) {
        serviceOrder.setContactPerson(contactPersonService.getContactPerson(personId));
        serviceOrder.setFinished(false);
        serviceOrderService.addNewServiceOrder(serviceOrder);
        return "redirect:/list";
    }

    @PostMapping("/modifyServiceOrder")
    public String modifyServiceOrder(ServiceOrder serviceOrder) {
        serviceOrderService.modifyServiceOrder(serviceOrder);
        return "redirect:/list";
    }

    @PostMapping("/deleteServiceOrder")
    public String deleteTask(@RequestParam Long id) {
        serviceOrderService.deleteServiceOrder(id);
        return "redirect:/list";
    }



    @GetMapping("/testtask")
    public String testAddTask(){
        Optional<SystemUser> systemUserById = systemUserService.getSystemUserById(4L);
        Optional<ServiceOrder> serviceOrderById = serviceOrderService.getServiceOrderById(2L);
        OrderTask orderTaskTest = new OrderTask(null, TaskStatus.INPROGRESS, LocalDateTime.now(), null, "Sprawdzić test", new BigDecimal("123"), systemUserById.get(), null);
        serviceOrderService.addNewTaskToServiceOrder(1L, orderTaskTest);
        return "redirect:/test";
    }
}
