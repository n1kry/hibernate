package dao;

import model.Book;

import java.util.List;

public interface IBookDao<T extends Book, Id extends Integer> {
    List<T> findAll();

    T findById(Id id);

    void update(T updated);

    void save(T book);

    void delete(T book);
}
