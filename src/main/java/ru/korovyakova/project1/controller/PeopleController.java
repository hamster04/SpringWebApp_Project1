package ru.korovyakova.project1.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.korovyakova.project1.dao.PersonDAO;
import ru.korovyakova.project1.model.Person;
import ru.korovyakova.project1.util.PersonValidator;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PersonDAO personDAO;
    private final PersonValidator personValidator;
    @Autowired
    public PeopleController(PersonDAO personDAO, PersonValidator personValidator) {
        this.personDAO = personDAO;
        this.personValidator = personValidator;
    }

    // +
    @GetMapping
    public String index(Model model) {
        model.addAttribute("people", personDAO.index());
        model.addAttribute("personDAO", personDAO);// для вывода доп информации о взятых книгах
        return "people/index";
    }
    // +
    @GetMapping("/{id}")// с Get запросов @ModelAttribute не получить, только если внедрить
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personDAO.getById(id));// получаем пользователя
        model.addAttribute("books", personDAO.getBookByPersonId(id));// и его взятые книги
        return "people/show";
    }
    // +
    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/new";
    }
    // +
    @PostMapping
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {

        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()) return "people/new";

        personDAO.save(person);
        return "redirect:/people";
    }
    // +
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personDAO.getById(id));
        return "people/edit";
    }
    // +
    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id, @ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "people/edit";
        personDAO.updateById(id, person);
        return "redirect:/people/{id}";
    }
    // +
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        personDAO.deleteById(id);
        return "redirect:/people";
    }
}
