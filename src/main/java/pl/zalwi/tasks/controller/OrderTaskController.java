package pl.zalwi.tasks.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.zalwi.global.model.OrderTask;
import pl.zalwi.global.model.TaskStatus;
import pl.zalwi.global.service.OrderTaskService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class OrderTaskController {

    OrderTaskService orderTaskService;

    @Autowired
    public OrderTaskController(OrderTaskService orderTaskService) {
        this.orderTaskService = orderTaskService;
    }

    @GetMapping("/task")
    public String listOrdersPageByPage(Model model,
                                       @RequestParam(name = "page", defaultValue = "1") int page,
                                       @RequestParam(name = "sort", required = false) String sort,
                                       TaskStatus status) {
        PageRequest pageable;
        if (sort == null || sort.isEmpty()) {
            pageable = PageRequest.of(page - 1, 10);
            model.addAttribute("sortOption", "");
        } else {
            pageable = PageRequest.of(page - 1, 10, Sort.by(sort).ascending());
            model.addAttribute("sortOption", sort);
        }
        Page<OrderTask> tasksPage = orderTaskService.findAllForStatusAndSort(status, pageable);
        int totalPages = tasksPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("taskList", tasksPage.getContent());
        model.addAttribute("filter", status);
        model.addAttribute("currentPage", page);
        return "tasks";
    }
}
