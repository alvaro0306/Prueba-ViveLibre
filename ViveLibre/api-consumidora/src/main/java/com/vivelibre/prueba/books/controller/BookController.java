package com.vivelibre.prueba.books.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vivelibre.prueba.books.beans.Book;
import com.vivelibre.prueba.books.services.BookService;

@RestController
@RequestMapping("/api/books")
public class BookController {

	@Autowired
	private BookService bookService;
	
	@GetMapping("/filter")
    public List<Book> filterBooks() {
        return bookService.filtrarLibros();
    }

    @GetMapping("/jk-rowling")
    public List<Book> getBooksByJKRowling() {
        return bookService.librosDeJKRowling();
    }

    @GetMapping("/authors-count")
    public Map<String, Long> countBooksByAuthor() {
        return bookService.contarLibrosPorAutor();
    }

    @GetMapping("/export")
    public String exportToCsv() throws IOException {
        return bookService.exportarACsv();
    }
}
