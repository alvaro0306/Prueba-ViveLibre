package com.vivelibre.prueba;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.vivelibre.prueba.books.service.impl.BookServiceImpl;

@SpringBootTest(classes = PruebaApplication.class)
class PruebaApplicationTests {

	@Autowired
    private BookServiceImpl bookService;
	
	@Test
	void contextLoads() {
		
		 assertNotNull(bookService, "El servicio debe estar inyectado");
	}

}
