package ru.korovyakova.project1.model;

import jakarta.validation.constraints.*;

public class Book {
    private int id;
    @NotEmpty(message = "Название книги не может быть пустым")
    @Size(min = 1, max = 128, message = "Название книги должно быть от 1 до 128 символов")
    private String title;
    @NotEmpty(message = "Книга не может быть без автора")
    @Size(min = 1, max = 128, message = "Имя автора должно быть от 1 до 128 символов")
    private String author;
    @Min(value = 1300, message = "Год издания книги должен быть больше 1500 года")
    private int yearOfRelease;

    // Конструктор по-умолчанию нужен для Spring
    public Book() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYearOfRelease() {
        return yearOfRelease;
    }

    public void setYearOfRelease(int yearOfRelease) {
        this.yearOfRelease = yearOfRelease;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", yearOfRelease=" + yearOfRelease +
                '}';
    }
}
