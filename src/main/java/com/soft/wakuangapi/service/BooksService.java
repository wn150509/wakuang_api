package com.soft.wakuangapi.service;

import com.soft.wakuangapi.entity.Books;
import com.soft.wakuangapi.utils.ResponseUtil;

import java.util.List;

public interface BooksService {
    ResponseUtil findAllBooks();
    Books findBooks(Integer id);
    List<Books> findTypeBooks(Integer id);
}
