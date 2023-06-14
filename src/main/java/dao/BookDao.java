package dao;

import lombok.Getter;
import lombok.Setter;
import model.Author;
import model.Book;
import org.hibernate.Session;
import util.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

public class BookDao implements IBookDao<Book, Integer> {

    @Getter
    @Setter
    private Session session;

    public void openSession() {
        session = HibernateUtil.getSessionFactory().openSession();
    }

    public void closeSession() {
        session.close();
    }

    @Override
    public List<Book> findAll() {
        return session.createQuery("from Book", Book.class).list();
    }

    @Override
    public Book findById(Integer id) {
        return session.get(Book.class, id);
    }

    @Override
    public void update(Book updatedBook) {
        Book currentBook = session.get(Book.class, updatedBook.getId());
        if (updatedBook.getName() != null) {
            currentBook.setName(updatedBook.getName());
        }
        if (updatedBook.getAuthorId() != null) {
            currentBook.setAuthorId(updatedBook.getAuthorId());
        }
        session.beginTransaction();
        session.getTransaction().commit();
    }

    @Override
    public void save(Book book) {
        session.beginTransaction();
        List<Author> author = new ArrayList<>();
        try {
            for (Author auth:
                    book.getAuthorId()) {
                author.add(session.find(Author.class,auth.getId()));
            }
            session.persist(book);
            session.getTransaction().commit();
        } catch (NullPointerException e) {
            System.out.println("A book should have at leas 1 author");
            session.getTransaction().rollback();
        }
    }

    @Override
    public void delete(Book book) {
        session.beginTransaction();
        try {
            session.remove(book);
            session.flush();
        } catch (Exception e) {
            System.out.println("This book doesn't exist");
        }
        session.clear();
        session.getTransaction().commit();
    }
}
