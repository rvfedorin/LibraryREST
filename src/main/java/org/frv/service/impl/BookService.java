package org.frv.service.impl;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.frv.dto.AuthorDto;
import org.frv.dto.BookDto;
import org.frv.exceptions.ItemNotFound;
import org.frv.model.*;
import org.frv.repository.IBookRepository;
import org.frv.service.CommonService;
import org.frv.service.IAuthorService;
import org.frv.service.IBookService;
import org.frv.service.IGenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.*;

/**
 * @author Roman V.F.
 * Date: 13.08.2021
 */
@Slf4j
@Service
@NoArgsConstructor
public class BookService extends CommonService<Book> implements IBookService {

    @Autowired
    private IBookRepository bookRepository;

    @Autowired
    private IAuthorService authorService;

    @Autowired
    private IGenreService genreService;

    @PostConstruct
    private void init() {
        this.setRepository(bookRepository);
    }

    @Transactional
    @Override
    public void addNewBook(BookDto bookDtoToSave) {
        Book book = getBookFromBookDto(bookDtoToSave);
        saveGenresIfNeedForBook(book, bookDtoToSave);
        saveAuthorsIfNeedForBook(book, bookDtoToSave);
        bookRepository.save(book);
    }

    @Override
    public Book findDetailedOne(Long bookId) {
        Book book;
        try {
            book = bookRepository.findDetailedOne(bookId);
        } catch (EmptyResultDataAccessException exception) {
            String errorMessage = String.format("Book with id %s not found.", bookId);
            log.info(errorMessage);
            throw new ItemNotFound(errorMessage);
        }
        return book;
    }

    @Override
    public void changeBook(BookDto bookDtoToChange) {
        Book bookToUpdate = bookRepository.findOne(bookDtoToChange.getId());
        if (bookToUpdate == null) {
            String errorMessage = "A Book to update not found.";
            log.warn(errorMessage);
            throw new ItemNotFound(errorMessage);
        }
        bookToUpdate.updateBook(bookDtoToChange);

        if (bookDtoToChange.getAuthors() != null) {
            Set<Author> authors = new HashSet<>();
            for (AuthorDto author : bookDtoToChange.getAuthors()) {
                Author authorFromDto = new Author(author.getFirstName(), author.getLastName());
                Author authorFromDb = authorService.saveAuthorIfNotExistAndGet(authorFromDto);
                authors.add(authorFromDb);
            }
            bookToUpdate.setAuthors(authors);
        }
        if (bookDtoToChange.getGenre() != null) {
            Genre genreFromDto = new Genre(bookDtoToChange.getGenre().getName());
            Genre genreFromDb = genreService.addNewGenreIfNeedAndGetIt(genreFromDto);
            bookToUpdate.setGenre(genreFromDb);
        }

        bookRepository.update(bookToUpdate);
    }

    @Override
    public Map<String, List<BookDto>> getCatalog(CatalogType catalogType) {
        switch (catalogType) {
            case AUTHOR:
                return getCatalogByAuthor();
            case GENRE:
                return getCatalogByGenre();
            default:
                return getCatalogByABC();
        }
    }

    private Map<String, List<BookDto>> getCatalogByABC() {
        List<Book> bookList = bookRepository.findAll();
        bookList.sort(Comparator.comparing(Book::getTitle));
        Map<String, List<BookDto>> catalog = new HashMap<>();
        for (Book book : bookList) {
            BookDto bookDto = new BookDto(book);
            String key = bookDto.getTitle().substring(0, 1);
            if (!catalog.containsKey(key)) {
                catalog.put(key, new ArrayList<>());
            }
            catalog.get(key).add(bookDto);
        }
        return catalog;
    }

    private Map<String, List<BookDto>> getCatalogByAuthor() {
        List<Book> bookList = bookRepository.findAll();
        bookList.sort(Comparator.comparing(Book::getTitle));
        Map<String, List<BookDto>> catalog = new HashMap<>();
        for (Book book : bookList) {
            BookDto bookDto = new BookDto(book);
            String key = "not specified";
            if (bookDto.getAuthors() != null) {
                for (AuthorDto author : bookDto.getAuthors()) {
                    key = author.toString();
                    break;
                }
            }

            if (!catalog.containsKey(key)) {
                catalog.put(key, new ArrayList<>());
            }
            catalog.get(key).add(bookDto);
        }
        return catalog;
    }

    private Map<String, List<BookDto>> getCatalogByGenre() {
        List<Book> bookList = bookRepository.findAll();
        bookList.sort(Comparator.comparing(Book::getTitle));
        Map<String, List<BookDto>> catalog = new HashMap<>();
        for (Book book : bookList) {
            BookDto bookDto = new BookDto(book);
            String key = "not specified";
            if (bookDto.getGenre() != null) {
                key = bookDto.getGenre().getName();
            }

            if (!catalog.containsKey(key)) {
                catalog.put(key, new ArrayList<>());
            }
            catalog.get(key).add(bookDto);
        }
        return catalog;
    }

    private Book getBookFromBookDto(BookDto bookDto) {
        Book book = new Book();
        book.setTitle(bookDto.getTitle());
        book.setDescription(bookDto.getDescription());
        book.setReleaseDate(bookDto.getReleaseDate());
        book.setCost(bookDto.getCost());
        book.setStatus(AvailableStatus.AVAILABLE);

        return book;
    }

    private void saveGenresIfNeedForBook(Book bookFor, BookDto bookDtoFrom) {
        if (bookDtoFrom.getGenre() != null) {
            Genre genre = new Genre(bookDtoFrom.getGenre().getName());
            bookFor.setGenre(genreService.addNewGenreIfNeedAndGetIt(genre));
        }
    }

    private void saveAuthorsIfNeedForBook(Book bookFor, BookDto bookDtoFrom) {
        if (bookDtoFrom.getAuthors() != null) {
            Set<Author> authors = new HashSet<>();
            for (AuthorDto authorDto : bookDtoFrom.getAuthors()) {
                Author author = new Author(authorDto.getFirstName(), authorDto.getLastName());
                authors.add(authorService.saveAuthorIfNotExistAndGet(author));
            }
            bookFor.setAuthors(authors);
        }
    }

}
