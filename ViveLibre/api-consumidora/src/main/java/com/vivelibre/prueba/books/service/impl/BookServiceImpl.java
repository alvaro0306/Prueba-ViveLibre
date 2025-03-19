package com.vivelibre.prueba.books.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URL;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vivelibre.prueba.books.beans.Author;
import com.vivelibre.prueba.books.beans.Book;
import com.vivelibre.prueba.books.services.BookService;

import jakarta.annotation.PostConstruct;

@Service("bookService")
public class BookServiceImpl implements BookService {
	
	private static final Logger logger = LoggerFactory.getLogger(BookService.class);

	private List<Book> books;

	public BookServiceImpl() {
        // Constructor vacío
    }
	
	@Autowired
    public BookServiceImpl(List<Book> books) {
    	this.books = (books != null) ? books : new ArrayList<>();
    }
    
    @PostConstruct
    public void init() {
    	loadBooksFromJson();
    }

    // Filtra los libros con más de 400 páginas o cuyo título contenga "Harry"
    public List<Book> filtrarLibros() {
    	logger.info("[BookService] Iniciando el filtrado de libros con más de 400 páginas o cuyo título contenga 'Harry'.");
    	List<Book> result = books.stream()
                .filter(book -> book.getPages() > 400 || book.getTitle().contains("Harry"))
                .collect(Collectors.toList());
    	logger.info("[BookService] Se encontraron {} libros que cumplen con los criterios.", result.size());
        return result;
    }

    // Filtra los libros escritos por "J.K. Rowling"
    public List<Book> librosDeJKRowling() {
    	logger.info("Filtrando los libros escritos por 'J.K. Rowling'.");
    	List<Book> result = books.stream()
                .filter(book -> "J.K. Rowling".equals(book.getAuthor().getName()))
                .collect(Collectors.toList());
    	logger.info("Se encontraron {} libros de 'J.K. Rowling'.", result.size());
    	return result;
    }

    // Lista los títulos ordenados alfabéticamente y cuenta los libros por autor
    public Map<String, Long> contarLibrosPorAutor() {
    	logger.info("Contando los libros por autor.");
    	Map<String, Long> result = books.stream()
                .collect(Collectors.groupingBy(book -> book.getAuthor().getName(), Collectors.counting())); 
    	logger.info("Se encontraron {} autores con sus respectivos libros.", result.size());
    	return result;
    }

    // Convierte publicationTimestamp a formato AAAA-MM-DD
    public void convertirFecha() {
    	logger.info("Iniciando la conversión de publicationTimestamp a formato 'yyyy-MM-dd'.");
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        // Itera sobre los libros
        books.forEach(book -> {
            // Verifica si el timestamp no es nulo
            if (book.getPublicationTimestamp() != null) {
                try {
                    // Convierte el timestamp (String) a Long
                    long timestamp = Long.parseLong(book.getPublicationTimestamp());
                    
                    // Convierte el timestamp a Instant
                    Instant instant = Instant.ofEpochSecond(timestamp);
                    
                    // Convierte el Instant a LocalDate con el formato deseado
                    String formattedDate = instant.atZone(ZoneId.systemDefault()) // Zona horaria predeterminada
                            .toLocalDate()
                            .format(formatter);
                    
                    // Imprime la fecha formateada
                    logger.info("Fecha de publicación de {}: {}", book.getTitle(), formattedDate);
                } catch (NumberFormatException e) {
                    // Si el timestamp no puede ser convertido a Long, manejar la excepción
                    logger.error("Error al convertir el timestamp para el libro '{}'.", book.getTitle(), e);                }
            }
        });
    }

    // Calcula el promedio de páginas y encuentra el libro con más y menos páginas
    public double calcularPromedioPáginas() {
    	logger.info("Calculando el promedio de páginas de los libros.");
    	double promedio = Math.round(books.stream().mapToInt(Book::getPages).average().orElse(0.0));
    	logger.info("El promedio de páginas es: {}", promedio);
    	return promedio;
    }

    // Añadir un campo wordCount y agrupar los libros por autor
    public Map<Author, List<Book>> agruparPorAutor() {
    	logger.info("Añadiendo el campo wordCount a los libros y agrupándolos por autor.");
    	books.forEach(book -> {
            int wordCount = book.getPages() * 250;
            book.setWordCount(wordCount); // Asignamos el wordCount al libro (si tu clase `Book` tiene un setter)
            logger.debug("Libro '{}' tiene un wordCount de: {}", book.getTitle(), wordCount);
        });

    	Map<Author, List<Book>> groupedByAuthor = books.stream()
                .collect(Collectors.groupingBy(Book::getAuthor));
    	
    	logger.info("Se han agrupado los libros en {} autores.", groupedByAuthor.size());
        // Agrupamos los libros por autor
    	return groupedByAuthor;
    }

    // Exportar a CSV
    public String exportarACsv() throws IOException {
    	logger.info("Iniciando la exportación de libros a CSV.");
    	if (books == null || books.isEmpty()) {
    		logger.warn("No hay libros para exportar.");
            throw new IllegalStateException("No hay libros para exportar");
        }
    	
        StringWriter stringWriter = new StringWriter();
        CSVPrinter csvPrinter = new CSVPrinter(stringWriter, CSVFormat.DEFAULT.withHeader("id", "title", "author_name", "pages").withRecordSeparator("\n"));
        
        for (Book book : books) {
            csvPrinter.printRecord(book.getId(), book.getTitle(), book.getAuthor().getName(), book.getPages());
            logger.debug("Escribiendo libro: {} en el CSV.", book.getTitle());
        }

        csvPrinter.flush();
        
        logger.info("Exportación de libros a CSV completada.");
        
        return stringWriter.toString();
    }
    
    private void loadBooksFromJson() {
    	logger.info("Iniciando la carga de libros desde el archivo JSON.");
        try {
            // Cargar el archivo JSON desde el classpath
        	
        	URL resource = getClass().getClassLoader().getResource("books.json");

        	if (resource == null) {
        	    System.err.println("books.json NO está en el classpath.");
        	} else {
        	    System.out.println("books.json encontrado en: " + resource.getPath());
        	}
        	
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("books.json");
            if (inputStream != null) {
                // Crear un ObjectMapper de Jackson para deserializar el JSON
                ObjectMapper objectMapper = new ObjectMapper();

                // Deserializar el JSON en una lista de libros
                List<Book> bookList = objectMapper.readValue(inputStream, new TypeReference<List<Book>>() {});

                // Asignar la lista de libros al atributo `books`
                this.books = bookList;

                logger.info("Libros cargados correctamente desde JSON.");
            } else {
            	logger.error("No se pudo encontrar el archivo books.json.");
            }
        } catch (IOException e) {
        	 logger.error("Error al cargar los libros desde el archivo JSON.", e);
        }
    }
}
