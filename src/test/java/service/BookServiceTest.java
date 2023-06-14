package service;

import model.Author;
import model.Book;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookServiceTest {
    static AuthorService authorService = new AuthorService();
    static   BookService bookService = new BookService();

    @BeforeAll
    static void setup() {
        authorService.save(Author.builder().name("John").build());
        authorService.save(Author.builder().name("John Second").build());
        authorService.save(Author.builder().name("John Third").build());

        bookService.save(Book.builder().name("Marts Journey").authorId(List.of(Author.builder().id(1).build(),Author.builder().id(2).build())).build());
        bookService.save(Book.builder().name("Scam").authorId(List.of(Author.builder().id(2).build(),Author.builder().id(3).build())).build());
    }

    @Test
    public void testFindById() {
        // Arrange
        BookService bookService = new BookService();
        Integer id = 1;

        // Act
        Book book = bookService.findById(id);

        // Assert
        assertNotNull(book);
        assertEquals(id, book.getId());
    }

    @Test
    public void testFindAll() {
        // Arrange
        BookService bookService = new BookService();

        // Act
        List<Book> books = bookService.findAll();

        // Assert
        assertNotNull(books);
        assertFalse(books.isEmpty());
    }

    @Test
    public void testUpdate() {
        // Arrange
        BookService bookService = new BookService();
        Book book = new Book();
        book.setId(1);
        book.setName("Updated Title");

        // Act
        bookService.update(book);
        Book updatedBook = bookService.findById(book.getId());

        // Assert
        assertEquals(book.getName(), updatedBook.getName());
    }

    @Test
    public void testSave() {
        // Arrange
        BookService bookService = new BookService();
        Book book = new Book();
        book.setName("New Book");
        book.setAuthorId(List.of(Author.builder().id(2).build()));

        // Act
        bookService.save(book);
        Book savedBook = bookService.findById(book.getId());

        // Assert
        assertEquals(book.getName(), savedBook.getName());
    }
    @Test
    public void testDelete() {
        // Arrange
        BookService bookService = new BookService();
        Book book = new Book();
        book.setName("To Be Deleted");
        book.setAuthorId(List.of(Author.builder().id(2).build()));
        bookService.save(book);

        // Act
        bookService.delete(book);
        Book deletedBook = bookService.findById(book.getId());

        // Assert
        assertNull(deletedBook);
    }
}