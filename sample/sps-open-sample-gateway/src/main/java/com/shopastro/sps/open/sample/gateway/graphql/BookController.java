package com.shopastro.sps.open.sample.gateway.graphql;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

/**
 * @author ye.ly@shopastro-inc.com
 */
@Controller
public class BookController {

    final BookDao bookDao;

    final AuthorDao authorDao;

    public BookController(BookDao bookDao, AuthorDao authorDao) {
        this.bookDao = bookDao;
        this.authorDao = authorDao;
    }

    @QueryMapping
    public List<Book> recentBooks(@Argument int count, @Argument int offset) {
        return bookDao.getRecentBooks(count, offset);
    }

    @QueryMapping
    public Optional<Author> author(@Argument String id){
        return authorDao.getAuthor(id);
    }

    @SchemaMapping
    public Optional<Author> author(Book book){
        return authorDao.getAuthor(book.getAuthorId());
    }

    @SchemaMapping
    public Optional<Author> firstAuthor(Book book){
        return authorDao.getAuthor(book.getAuthorId());
    }

    @SchemaMapping
    public List<Book> books(Author author){
        return bookDao.getAuthorBooks(author.getId());
    }
}
