package ru.korovyakova.project1.model;

import jakarta.validation.constraints.*;

public class Person {

    private int id;

    @Pattern(regexp = "[А-Я][а-я]+ [А-Я][а-я]+ [А-Я][а-я]+",
            message = "Ваши данные должны быть по формату: Фамилия Имя Отчество")
    @Size(min = 8, max = 128, message = "ФИО должно быть от 5 до 128 символов")
    private String fullName;

    @Min(value = 1900, message = "Год рождения не может быть меньше 1900 года")
    private int yearOfBorn;

    // Конструктор по-умолчанию нужен для Spring
    public Person() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getYearOfBorn() {
        return yearOfBorn;
    }

    public void setYearOfBorn(int yearOfBorn) {
        this.yearOfBorn = yearOfBorn;
    }

}
