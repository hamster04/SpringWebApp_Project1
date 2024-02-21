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
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        String SQL = "SELECT * FROM person";
        List<Person> list = jdbcTemplate.query(SQL, new BeanPropertyRowMapper<>(Person.class));
        return list;
    }

    public void save(Person person) {
        String SQL = "INSERT INTO person(full_name, year_of_born) VALUES (?, ?)";
        jdbcTemplate.update(SQL, person.getFullName(), person.getYearOfBorn());
    }
    public Person getById(int id) {
        String SQL = "SELECT * FROM person WHERE id = ?";
        return jdbcTemplate.query(SQL, new Object[] {id},new BeanPropertyRowMapper<>(Person.class))
                .stream().findFirst().orElse(null);
    }

    public void updateById(int id, Person updatePerson) {
        String SQL = "UPDATE person SET full_name = ?, year_of_born = ? WHERE id = ?";
        jdbcTemplate.update(SQL, updatePerson.getFullName(), updatePerson.getYearOfBorn(), id);
    }
    public void deleteById(int id) {
        String SQL = "DELETE FROM person WHERE id = ?";
        jdbcTemplate.update(SQL, id);
    }
    public List<Book> getBookByPersonId(int id) {
        String SQL = "SELECT * FROM book WHERE person_id = ?";
        return jdbcTemplate.query(SQL, new Object[]{id}, new BeanPropertyRowMapper<>(Book.class));
    }

    public boolean isPersonByFullNamePresent(Person person) {
        String SQL = "SELECT * FROM person WHERE full_name = ?";
        return jdbcTemplate.query(SQL, new Object[]{person.getFullName()}, new BeanPropertyRowMapper<>(Book.class))
                .stream()
                .findFirst()
                .isPresent();
    }

}
