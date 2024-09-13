package com.najackdo.server.core.controller;

import com.najackdo.server.core.properties.BookRepository;
import com.najackdo.server.core.util.AladdinOpenAPI;
import com.najackdo.server.domain.book.entity.Book;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class BookController {

    @Autowired
    AladdinOpenAPI aladdinOpenAPI;
    @Autowired
    BookRepository bookRepository;

    @GetMapping("/input")
    public void insertBook() throws Exception {
        List<Book> list = aladdinOpenAPI.addBooks(10,50);
        log.info("리스트 도착");
        bookRepository.saveAll(list);
    }

}
