package ru.korovyakova.project1.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.korovyakova.project1.dao.BookDAO;
import ru.korovyakova.project1.dao.PersonDAO;
import ru.korovyakova.project1.model.Book;
import ru.korovyakova.project1.model.Person;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookDAO bookDAO;
    private final PersonDAO personDAO;
    @Autowired
    public BookController(BookDAO bookDAO, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("books", bookDAO.index());// подразумевается что в библиотеке уже есть хоть одна книга
        model.addAttribute("bookDAO", bookDAO);// для уточнения свободна книга или уже занята
        return "books/index";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("book", bookDAO.getById(id));
        if ((bookDAO.getBookOwner(id)).isPresent()) model.addAttribute("bookOwner", bookDAO.getBookOwner(id).get());
        else model.addAttribute("listOfPeople", personDAO.index());
        return "books/show";
    }
    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "books/new";
        bookDAO.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookDAO.getById(id));
        return "books/edit";
    }
    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id, @ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "books/edit";

        bookDAO.updateById(id, book);
        return "redirect:/books/{id}";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookDAO.deleteById(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/add")
    public String addBook(@PathVariable("id") int id, @ModelAttribute("person") Person person) {
        bookDAO.addBook(id, person.getId());
        return "redirect:/books/{id}";
    }
    @PatchMapping("/{id}/free")
    public String freeBook(@PathVariable("id") int id) {
        bookDAO.freeBook(id);
        return "redirect:/books/{id}";
    }
}
