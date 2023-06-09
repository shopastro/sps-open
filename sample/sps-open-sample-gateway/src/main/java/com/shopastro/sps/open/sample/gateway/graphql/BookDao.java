package com.shopastro.sps.open.sample.gateway.graphql;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ye.ly@shopastro-inc.com
 */
@Service
public class BookDao {
    private final List<Book> books;

    public BookDao(List<Book> books) {
        this.books = books;
    }

    @PostConstruct
    public void init() {
        for (int id = 0; id < 10; ++id) {
            for (int authorId = 0; authorId < 10; ++authorId) {
                Book book = new Book();
                book.setId("Book" + authorId + id);
                book.setTitle("Book " + authorId + ":" + id);
                book.setCategory("Book category");
                book.setText("Book " + id + " + by author " + authorId);
                book.setAuthorId("Author" + authorId);
                books.add(book);
            }
        }
    }

    public List<Book> getRecentBooks(int count, int offset) {
        return books.stream()
                .skip(offset)
                .limit(count)
                .collect(Collectors.toList());
    }

    public List<Book> getAuthorBooks(String author) {
        return books.stream()
                .filter(book -> author.equals(book.getAuthorId()))
                .collect(Collectors.toList());
    }

    public void saveBook(Book book) {
        books.add(book);
    }
}