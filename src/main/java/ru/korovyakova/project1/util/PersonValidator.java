package ru.korovyakova.project1.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.korovyakova.project1.dao.PersonDAO;
import ru.korovyakova.project1.model.Person;

@Component
public class PersonValidator implements Validator {

    private final PersonDAO personDAO;
    @Autowired
    public PersonValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        if(personDAO.isPersonByFullNamePresent(person)) {
            errors.rejectValue("fullName", "", "Человек с таким ФИО уже существует");
        }
    }
}
