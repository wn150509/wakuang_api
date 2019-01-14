package com.soft.wakuangapi.dao;

import com.soft.wakuangapi.entity.Books;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BooksRepository extends JpaRepository<Books,Integer> {

    List<Books>findAll();
    Books findBooksByBooksId(Integer id);
    List<Books> findBooksByTypeId(Integer id);
}
