package pl.zalwi.people.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.zalwi.global.model.ContactPerson;
import pl.zalwi.global.service.ContactPersonService;

@Controller
public class ContactPersonController {

    ContactPersonService contactPersonService;

    @Autowired
    public ContactPersonController(ContactPersonService contactPersonService) {
        this.contactPersonService = contactPersonService;
    }

    @GetMapping("/new")
    public String chooseContactPerson(Model model) {
        model.addAttribute("persons", contactPersonService.findAllContactPersons());
        return "personList";
    }

    @GetMapping("/add")
    public String newContactPerson(Model model) {
        model.addAttribute("actionDescription", "Dodawanie nowego zleceniodawcy");
        model.addAttribute("action", "Dodaj");
        model.addAttribute("actionLink", "addPerson");
        model.addAttribute("isBlocked", false);
        model.addAttribute("isNew", true);
        return "personForm";
    }

    @PostMapping("/addPerson")
    public String newTask(Model model, ContactPerson contactPerson) {
        Long newId = contactPersonService.addNewPersonAndReturnId(contactPerson);
        return "redirect:/newServiceOrder?person=" + newId;
    }
}
