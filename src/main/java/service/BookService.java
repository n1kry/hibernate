package service;

import dao.BookDao;
import model.Book;

import java.util.List;

public class BookService {
    private static BookDao bookDao = new BookDao();

    public Book findById(Integer id) {
        bookDao.openSession();
        Book book = bookDao.findById(id);
        bookDao.closeSession();
        return book;
    }

    public List<Book> findAll() {
        bookDao.openSession();
        List<Book> books = bookDao.findAll();
        bookDao.closeSession();
        return books;
    }

    public void update(Book updatedBook) {
        bookDao.openSession();
        bookDao.update(updatedBook);
        bookDao.closeSession();
    }

    public void save(Book book) {
        bookDao.openSession();
        bookDao.save(book);
        bookDao.closeSession();
    }

    public void delete(Book book) {
        bookDao.openSession();
        bookDao.delete(book);
        bookDao.closeSession();
    }
}
