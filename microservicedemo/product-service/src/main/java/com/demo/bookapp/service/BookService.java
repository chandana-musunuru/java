package com.demo.bookapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.bookapp.entity.Book;
import com.demo.bookapp.exception.BookNotFoundException;
import com.demo.bookapp.repository.BookRepository;

@Service
public class BookService {

	@Autowired
	BookRepository bRepo;
	
	public Book addBook(Book book) {
		book = bRepo.save(book);
		return book;
	}
	
	public Book getBookById(String isbn) throws BookNotFoundException {
		Book book = bRepo.findById(isbn).orElse(null);
		if(book==null)
			throw new BookNotFoundException("Book Id not found");
		return book;
	}

	public Book editBookById(String isbn, boolean status) throws BookNotFoundException  {
		Book book = bRepo.findById(isbn).orElse(null);
		if(book==null)
			throw new BookNotFoundException("Book Id not found");
		book.setStatus(status);
		book = bRepo.save(book);
		return book;
	}

	public List<Book> getAllBooks() throws BookNotFoundException {
		List<Book> books = bRepo.findAll();
		if(books.isEmpty())
			throw new BookNotFoundException("There is no book");
		// TODO Auto-generated method stub
		return books;
	}

}
