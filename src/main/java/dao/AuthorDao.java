package dao;

import jakarta.persistence.NoResultException;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import lombok.Getter;
import lombok.Setter;
import model.Author;
import model.Book;
import org.hibernate.Session;
import util.HibernateUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuthorDao implements IAuthorDao<Author, Integer> {

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
    public List<Author> findAll() {
        return session.createQuery("from Author", Author.class).list();
    }

    @Override
    public Author findById(Integer id) {
        return session.get(Author.class, id);
    }

    @Override
    public void update(Author updatedAuthor) {
        Author currentAuthor = session.get(Author.class, updatedAuthor.getId());
        if (updatedAuthor.getName() != null) {
            currentAuthor.setName(updatedAuthor.getName());
        }
        session.beginTransaction();
        session.getTransaction().commit();
    }

    @Override
    public Map<Author, List<Book>> getAuthorsBooksMap() {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Tuple> criteriaQuery = criteriaBuilder.createTupleQuery();
        Root<Book> bookRoot = criteriaQuery.from(Book.class);
        Join<Book, Author> authorJoin = bookRoot.join("authorId");
        criteriaQuery.multiselect(authorJoin, bookRoot);

        TypedQuery<Tuple> query = session.createQuery(criteriaQuery);
        List<Tuple> results = query.getResultList();

        Map<Author, List<Book>> authorBookMap = new HashMap<>();
        for (Tuple tuple : results) {
            Author author = tuple.get(authorJoin);
            Book book = tuple.get(bookRoot);
            authorBookMap.computeIfAbsent(author, k -> new ArrayList<>()).add(book);
        }

        return authorBookMap;
    }

    @Override
    public List<String> findAuthorNameByBookName(String bookName) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<String> criteriaQuery = criteriaBuilder.createQuery(String.class);

        Root<Book> bookRoot = criteriaQuery.from(Book.class);
        Join<Book, Author> authorJoin = bookRoot.join("authorId");

        criteriaQuery.select(authorJoin.get("name"))
                .where(criteriaBuilder.equal(bookRoot.get("name"), bookName))
                .distinct(true);

        try {
            TypedQuery<String> query = session.createQuery(criteriaQuery);
            return query.getResultList();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public void save(Author author) {
        session.beginTransaction();
        session.persist(author);
        session.getTransaction().commit();
    }

    @Override
    public void delete(Author author) {
        session.beginTransaction();
        author = session.find(Author.class, author.getId());
        List<Book> books = author.getBooks();
        System.out.println(books);
        if (books != null && !books.isEmpty()) {
            for (Book book : books) {
                book.getAuthorId().remove(author);
            }
            books.clear();
        }
        try {
            session.remove(author);
        } catch (IllegalArgumentException e) {
            System.out.println("This author doesn't exist");
        }
        session.flush();
        session.clear();
        session.getTransaction().commit();
    }
}
