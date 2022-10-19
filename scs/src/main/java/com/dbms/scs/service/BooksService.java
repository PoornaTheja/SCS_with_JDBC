package com.dbms.scs.service;

import com.dbms.scs.domain.Books;
import com.dbms.scs.model.BooksDTO;
import com.dbms.scs.repos.BooksRepository;
import com.dbms.scs.util.WebUtils;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class BooksService {

    private final BooksRepository booksRepository;

    public BooksService(final BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<BooksDTO> findAll() {
        return booksRepository.findAll(Sort.by("bookID"))
                .stream()
                .map(books -> mapToDTO(books, new BooksDTO()))
                .collect(Collectors.toList());
    }

    public BooksDTO get(final Integer bookID) {
        return booksRepository.findById(bookID)
                .map(books -> mapToDTO(books, new BooksDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Integer create(final BooksDTO booksDTO) {
        final Books books = new Books();
        mapToEntity(booksDTO, books);
        return booksRepository.save(books).getBookID();
    }

    public void update(final Integer bookID, final BooksDTO booksDTO) {
        final Books books = booksRepository.findById(bookID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(booksDTO, books);
        booksRepository.save(books);
    }

    public void delete(final Integer bookID) {
        booksRepository.deleteById(bookID);
    }

    private BooksDTO mapToDTO(final Books books, final BooksDTO booksDTO) {
        booksDTO.setBookID(books.getBookID());
        booksDTO.setBookName(books.getBookName());
        booksDTO.setAuthor(books.getAuthor());
        booksDTO.setTotalNoOfCopies(books.getTotalNoOfCopies());
        booksDTO.setAvailableCopies(books.getAvailableCopies());
        booksDTO.setStatus(books.getStatus());
        booksDTO.setTotalReads(books.getTotalReads());
        booksDTO.setBookRating(books.getBookRating());
        return booksDTO;
    }

    private Books mapToEntity(final BooksDTO booksDTO, final Books books) {
        books.setBookName(booksDTO.getBookName());
        books.setAuthor(booksDTO.getAuthor());
        books.setTotalNoOfCopies(booksDTO.getTotalNoOfCopies());
        books.setAvailableCopies(booksDTO.getAvailableCopies());
        books.setStatus(booksDTO.getStatus());
        books.setTotalReads(booksDTO.getTotalReads());
        books.setBookRating(booksDTO.getBookRating());
        return books;
    }

    @Transactional
    public String getReferencedWarning(final Integer bookID) {
        final Books books = booksRepository.findById(bookID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!books.getBookingForBookBookBookingss().isEmpty()) {
            return WebUtils.getMessage("books.bookBookings.manyToOne.referenced", books.getBookingForBookBookBookingss().iterator().next().getBookingid());
        }
        return null;
    }

}
