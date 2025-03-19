package com.vivelibre.prueba.books.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vivelibre.prueba.books.beans.Author;
import com.vivelibre.prueba.books.beans.Book;
import com.vivelibre.prueba.books.service.impl.BookServiceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BookServiceImplTest {

	private BookServiceImpl bookService;

    @Mock
    private List<Book> books;

    @Mock
    private Book book1, book2, book3;

    @BeforeEach
    public void setUp() throws StreamReadException, DatabindException, IOException {
    	
    	// Inicializa los mocks de libros.
        book1 = mock(Book.class);
        book2 = mock(Book.class);
        book3 = mock(Book.class);
        
//    	MockitoAnnotations.openMocks(this);
    	
    	when(book1.getPages()).thenReturn(500);
        when(book1.getTitle()).thenReturn("Harry Potter and the Sorcerer's Stone");
        when(book1.getAuthor()).thenReturn(new Author("J.K. Rowling"));
        
        when(book2.getPages()).thenReturn(300);
        when(book2.getTitle()).thenReturn("Some Random Book");
        when(book2.getAuthor()).thenReturn(new Author("Some Other Author"));
        
        when(book3.getPages()).thenReturn(450);
        when(book3.getTitle()).thenReturn("Harry Potter and the Chamber of Secrets");
        when(book3.getAuthor()).thenReturn(new Author("J.K. Rowling"));
        
        // Usamos una lista real en lugar de un mock
        books = new ArrayList<>(Arrays.asList(book1, book2, book3));

        bookService = new BookServiceImpl(books);
    }
    
    @Test
    public void testFiltrarLibros() {
        // Configuramos los mocks
        when(book1.getPages()).thenReturn(500);
        when(book1.getTitle()).thenReturn("Harry Potter and the Sorcerer's Stone");

        when(book2.getPages()).thenReturn(300);
        when(book2.getTitle()).thenReturn("Some Random Book");

        when(book3.getPages()).thenReturn(450);
        when(book3.getTitle()).thenReturn("Harry Potter and the Chamber of Secrets");

        // Ejecutamos la función
        List<Book> result = bookService.filtrarLibros();

        // Verificamos que los resultados son correctos
        assertEquals(2, result.size(), "Debería filtrar dos libros con más de 400 páginas o cuyo título contiene 'Harry'");
        assertTrue(result.contains(book1), "Debe incluir el libro de 'Harry Potter and the Sorcerer's Stone'");
        assertTrue(result.contains(book3), "Debe incluir el libro de 'Harry Potter and the Chamber of Secrets'");
    }

    @Test
    public void testLibrosDeJKRowling() {
        // Configuramos los mocks
        Author author = new Author("J.K. Rowling");

        when(book1.getAuthor()).thenReturn(author);
        when(book1.getTitle()).thenReturn("Harry Potter and the Sorcerer's Stone");

        when(book2.getAuthor()).thenReturn(new Author("Some Other Author"));
        when(book2.getTitle()).thenReturn("Some Random Book");

        when(book3.getAuthor()).thenReturn(author);
        when(book3.getTitle()).thenReturn("Harry Potter and the Chamber of Secrets");

        // Ejecutamos la función
        List<Book> result = bookService.librosDeJKRowling();

        // Verificamos que solo los libros de J.K. Rowling estén presentes
        assertEquals(2, result.size(), "Debe devolver dos libros de J.K. Rowling");
        assertTrue(result.contains(book1), "Debe incluir el libro de J.K. Rowling");
        assertTrue(result.contains(book3), "Debe incluir el libro de J.K. Rowling");
    }

    @Test
    public void testContarLibrosPorAutor() {
        // Configuramos los mocks
        Author author1 = new Author("J.K. Rowling");
        Author author2 = new Author("George R. R. Martin");

        when(book1.getAuthor()).thenReturn(author1);
        when(book2.getAuthor()).thenReturn(author2);
        when(book3.getAuthor()).thenReturn(author1);

        // Ejecutamos la función
        Map<String, Long> result = bookService.contarLibrosPorAutor();

        // Verificamos que los resultados son correctos
        assertEquals(2, result.size(), "Debe contar dos autores diferentes");
        assertEquals(2, result.get("J.K. Rowling"), "J.K. Rowling debe tener 2 libros");
        assertEquals(1, result.get("George R. R. Martin"), "George R. R. Martin debe tener 1 libro");
    }

    @Test
    public void testConvertirFecha() {
        // Configuramos los mocks
        when(book1.getPublicationTimestamp()).thenReturn("1609459200"); // 01/01/2021
        when(book2.getPublicationTimestamp()).thenReturn("1625097600"); // 01/07/2021

        // Ejecutamos la función
        bookService.convertirFecha();
    }

    @Test
    public void testCalcularPromedioPaginas() {
        // Configuramos los mocks
        when(book1.getPages()).thenReturn(500);
        when(book2.getPages()).thenReturn(300);
        when(book3.getPages()).thenReturn(450);

        // Ejecutamos la función
        double promedio = bookService.calcularPromedioPáginas();

        // Verificamos el resultado
        assertEquals(417.0, promedio, "El promedio de páginas debería ser 417.0");
    }

    @Test
    public void testAgruparPorAutor() {
        // Configuramos los mocks
        Author author1 = new Author("J.K. Rowling");
        Author author2 = new Author("George R. R. Martin");

        when(book1.getAuthor()).thenReturn(author1);
        when(book2.getAuthor()).thenReturn(author2);
        when(book3.getAuthor()).thenReturn(author1);

        // Ejecutamos la función
        Map<Author, List<Book>> result = bookService.agruparPorAutor();

        // Verificamos que los libros estén correctamente agrupados por autor
        assertTrue(result.containsKey(author1), "Debe agrupar los libros de J.K. Rowling");
        assertTrue(result.containsKey(author2), "Debe agrupar los libros de George R. R. Martin");
        assertEquals(2, result.get(author1).size(), "J.K. Rowling debe tener 2 libros");
        assertEquals(1, result.get(author2).size(), "George R. R. Martin debe tener 1 libro");
    }

    @Test
    public void testExportarACsv() throws IOException {
    	
    	Book libro1 = new Book(1L, "Harry Potter and the Sorcerer's Stone", new Author("J.K. Rowling"), 500);
        Book libro2 = new Book(2L, "El Señor de los Anillos", new Author("J.R.R. Tolkien"), 600);

        // Agrega los libros a la lista
        books.add(libro1);
        books.add(libro2);

        // Ejecutamos la función
        String csvContent = bookService.exportarACsv();

        // Verificamos que el CSV contiene los valores esperados
        assertTrue(csvContent.contains("1"), "Debe incluir el ID del primer libro");
        assertTrue(csvContent.contains("Harry Potter and the Sorcerer's Stone"), "Debe incluir el título del primer libro");
        assertTrue(csvContent.contains("J.K. Rowling"), "Debe incluir el autor del primer libro");
        assertTrue(csvContent.contains("500"), "Debe incluir el número de páginas del primer libro");

        assertTrue(csvContent.contains("2"), "Debe incluir el ID del segundo libro");
        assertTrue(csvContent.contains("El Señor de los Anillos"), "Debe incluir el título del segundo libro");
        assertTrue(csvContent.contains("J.R.R. Tolkien"), "Debe incluir el autor del segundo libro");
        assertTrue(csvContent.contains("600"), "Debe incluir el número de páginas del segundo libro");

        // Imprimir para depuración
        System.out.println(csvContent);
    }
}
