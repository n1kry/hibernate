import model.Author;
import model.Book;
import service.AuthorService;
import service.BookService;

import java.util.Collection;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        AuthorService service = new AuthorService();
//        service.save(author);
//        service.update(1,author);
//        System.out.println(service.getAuthorByBookName("Cars Hist"));
//        System.out.println(service.getAuthorsBooksMap());

        service.save(Author.builder().name("John").build());
        service.save(Author.builder().name("John Second").build());
        service.save(Author.builder().name("John Third").build());

        BookService bookService = new BookService();

//        service.delete(Author.builder().id(6).build());

        bookService.save(Book.builder().name("Marts Journey").authorId(List.of(Author.builder().id(1).build(),Author.builder().id(2).build())).build());
        bookService.save(Book.builder().name("Scam").authorId(List.of(Author.builder().id(2).build(),Author.builder().id(3).build())).build());

//        bookService.update(Book.builder().id(1).name("Harry").build());
//        bookService.delete(Book.builder().id(1).build());
//        service.update(Author.builder().id(1).name("Camila").build());
//        service.delete(Author.builder().id(2).build());
        System.out.println(service.getAuthorByBookName("Scam"));
        System.out.println(service.getAuthorsBooksMap());
//        bookService.save(Book.builder().name("sdsd").build());
//        System.out.println(service.findAll());
//        System.out.println(bookService.findAll());

//        service.update(Author.builder().id(5).name("Madv").build());
        bookService.update(Book.builder().id(1).name("Cars History").authorId(List.of(Author.builder().id(2).build())).build());
    }
}
