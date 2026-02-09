package com.demo.bookapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.bookapp.entity.Book;
import com.demo.bookapp.exception.BookNotFoundException;
import com.demo.bookapp.service.BookService;

@RestController 
@CrossOrigin//(origins = "http://localhost:8081")
@RequestMapping("/book")
public class BookController {

	@Autowired
	BookService bService;
	
	@PostMapping
	public ResponseEntity<Book> addBook(@RequestBody Book book){
		book = bService.addBook(book);
		return new ResponseEntity<Book>(book, HttpStatus.OK);
	}
	@GetMapping("/{isbn}")
	public ResponseEntity<Book> getBookById(@PathVariable("isbn") String isbn) throws BookNotFoundException{
		Book book = bService.getBookById(isbn);
		return new ResponseEntity<Book>(book, HttpStatus.OK);
	}

	@PutMapping("/{isbn}/{status}")
	public ResponseEntity<Book> editBookById(@PathVariable("isbn") String isbn, @PathVariable("status") boolean status) throws BookNotFoundException{
		Book book = bService.editBookById(isbn,status);
		return new ResponseEntity<Book>(book, HttpStatus.OK);
	}
	@GetMapping()
	public ResponseEntity<List<Book>> getAllBooks() throws BookNotFoundException{
		List<Book> books = bService.getAllBooks();
		return new ResponseEntity<List<Book>>(books, HttpStatus.OK);
	}

}








