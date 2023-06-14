package service;


import model.Author;
import model.Book;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AuthorServiceTest {

    static AuthorService authorService = new AuthorService();
    static BookService bookService = new BookService();

    @BeforeAll
    static void setup() {
        authorService.save(Author.builder().name("John").build());
        authorService.save(Author.builder().name("John Second").build());
        authorService.save(Author.builder().name("John Third").build());

        bookService.save(Book.builder().name("Marts Journey").authorId(List.of(Author.builder().id(1).build(),Author.builder().id(2).build())).build());
        bookService.save(Book.builder().name("Scam").authorId(List.of(Author.builder().id(2).build(),Author.builder().id(3).build())).build());
    }

    @Test
    void findById() {
        // Arrange
        Integer id = 3;

        // Act
        Author author = authorService.findById(id);

        // Assert
        assertNotNull(author);
        assertEquals(Optional.of(id).get(), Optional.of(author.getId()).get().intValue());
    }

    @Test
    void findAll() {
        // Arrange
        AuthorService authorService = new AuthorService();

        // Act
        List<Author> authors = authorService.findAll();

        // Assert
        assertNotNull(authors);
        assertFalse(authors.isEmpty());
    }

    @Test
    void update() {
        // Arrange
        Author author = new Author();
        author.setId(3);
        author.setName("John Doeee");

        // Act
        authorService.update(author);
        Author updatedAuthor = authorService.findById(author.getId());

        // Assert
        assertEquals(author.getName(), updatedAuthor.getName());
    }

    @Test
    void getAuthorByBookName_ExistingBook() {
        // Arrange
        String bookName = "Scam";

        // Act
        List<String> authors = authorService.getAuthorByBookName(bookName);

        // Assert
        assertNotNull(authors);
        assertFalse(authors.isEmpty());
        assertEquals("John Doeee", authors.get(0));
    }

    @Test
    public void getAuthorByBookName_NonExistingBook() {
        // Arrange
        String bookName = "Non-existing Book Title";

        // Act
        List<String> authors = authorService.getAuthorByBookName(bookName);

        // Assert
        assertNotNull(authors);
        assertEquals(Collections.emptyList(), authors);
    }

    @Test
    void getAuthorsBooksMap() {
        // Act
        Map<Author, List<Book>> authorListMap = authorService.getAuthorsBooksMap();

        // Assert
        assertNotNull(authorListMap);
        assertFalse(authorListMap.isEmpty());
    }

    @Test
    void save() {
        AuthorService authorService = new AuthorService();
        Author author = new Author();
        author.setName("Jane Doe");

        // Act
        authorService.save(author);
        Author savedAuthor = authorService.findById(author.getId());

        // Assert
        assertEquals(author.getName(), savedAuthor.getName());
    }

    @Test
    void delete() {
        // Arrange
        AuthorService authorService = new AuthorService();
        Author author = new Author();
        author.setName("John Doe");
        authorService.save(author);

        // Act
        authorService.delete(author);
        Author deletedAuthor = authorService.findById(author.getId());

        // Assert
        assertNull(deletedAuthor);
    }
}