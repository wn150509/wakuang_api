package com.soft.wakuangapi.service.serviceimpl;

import com.soft.wakuangapi.dao.BooksRepository;
import com.soft.wakuangapi.entity.Books;
import com.soft.wakuangapi.service.BooksService;
import com.soft.wakuangapi.utils.ResponseUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BooksServiceImpl implements BooksService {
    @Resource
    private BooksRepository booksRepository;

    @Override
    public ResponseUtil findAllBooks() {
        List<Books>booksList=booksRepository.findAll();
        return new ResponseUtil(0,"get all books",booksList);
    }

    @Override
    public Books findBooks(Integer id) {
        return booksRepository.findBooksByBooksId(id);
    }

    @Override
    public List<Books> findTypeBooks(Integer id) {
        return booksRepository.findBooksByTypeId(id);
    }
}
