package ru.korovyakova.project1.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.korovyakova.project1.model.Book;
import ru.korovyakova.project1.model.Person;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {
        String SQL = "SELECT * FROM book";
        List<Book> list = jdbcTemplate.query(SQL, new BeanPropertyRowMapper<>(Book.class));
        return list;
    }

    public void save(Book book) {
        String SQL = "INSERT INTO book(title, author, year_of_release) VALUES (?, ?, ?)";
        jdbcTemplate.update(SQL, book.getTitle(), book.getAuthor(), book.getYearOfRelease());
    }
    public Book getById(int id) {
        String SQL = "SELECT * FROM book WHERE id = ?";
        return jdbcTemplate.query(SQL, new Object[] {id},new BeanPropertyRowMapper<>(Book.class))
                .stream().findFirst().orElse(null);
    }

    public void updateById(int id, Book updateBook) {
        String SQL = "UPDATE book SET title = ?, author = ?, year_of_release = ? WHERE id = ?";
        jdbcTemplate.update(SQL, updateBook.getTitle(), updateBook.getAuthor(),
                updateBook.getYearOfRelease(), id);
    }
    public void deleteById(int id) {
        String SQL = "DELETE FROM book WHERE id = ?";
        jdbcTemplate.update(SQL, id);
    }

    public Optional<Person> getBookOwner(int id) {
        String SQL = "SELECT person.* FROM person " +
                "JOIN book ON person.id = book.person_id " +
                "WHERE book.id = ?";
        return jdbcTemplate.query(SQL, new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findFirst();
    }

    public void addBook(int bookId, int personId) {
        String SQL = "UPDATE book SET person_id = ? WHERE id = ?";
        jdbcTemplate.update(SQL, personId, bookId);
    }
    public void freeBook(int bookId) {
        String SQL = "UPDATE book SET person_id = NULL WHERE id = ?";
        jdbcTemplate.update(SQL, bookId);
    }
}
