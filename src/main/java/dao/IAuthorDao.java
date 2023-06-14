package dao;

import model.Author;
import model.Book;

import java.util.List;
import java.util.Map;

public interface IAuthorDao<T extends Author, Id extends Integer> {
    List<T> findAll();

    T findById(Id id);

    void update(T updated);

    List<String> findAuthorNameByBookName(String bookName);

    Map<T, List<Book>> getAuthorsBooksMap();

    void save(T author);

    void delete(T author);
}
