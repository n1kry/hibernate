package service;

import dao.AuthorDao;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PreRemove;
import model.Author;
import model.Book;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class AuthorService {
    private static AuthorDao authorDao = new AuthorDao();

    public Author findById(Integer id) {
        authorDao.openSession();

        Author author = authorDao.findById(id);

        authorDao.closeSession();
        return author;
    }

    public List<Author> findAll() {
        authorDao.openSession();

        List<Author> authors = authorDao.findAll();

        authorDao.closeSession();
        return authors;
    }

    public void update(Author author) {
        authorDao.openSession();
        authorDao.update(author);
        authorDao.closeSession();
    }

    public List<String> getAuthorByBookName(String name) {
        authorDao.openSession();
        List<String> author;
        author = authorDao.findAuthorNameByBookName(name);
        authorDao.closeSession();
        return author;
    }

    public Map<Author, List<Book>> getAuthorsBooksMap() {
        authorDao.openSession();
        Map<Author, List<Book>> authorListMap = authorDao.getAuthorsBooksMap();
        authorDao.closeSession();
        return authorListMap;
    }

    public void save(Author author) {
        authorDao.openSession();
        authorDao.save(author);
        authorDao.closeSession();
    }

    @PreRemove
    public void delete(Author author) {
        authorDao.openSession();
        authorDao.delete(author);
        authorDao.closeSession();
    }
}
