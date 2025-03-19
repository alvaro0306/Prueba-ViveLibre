package com.vivelibre.prueba.books.services;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.vivelibre.prueba.books.beans.Author;
import com.vivelibre.prueba.books.beans.Book;

public interface BookService {

	public List<Book> filtrarLibros();
	
	public List<Book> librosDeJKRowling();
	
	public Map<String, Long> contarLibrosPorAutor();
	
	public void convertirFecha();
	
	public double calcularPromedioPáginas();
	
	public Map<Author, List<Book>> agruparPorAutor();
	
	public String exportarACsv() throws IOException;
	
}
