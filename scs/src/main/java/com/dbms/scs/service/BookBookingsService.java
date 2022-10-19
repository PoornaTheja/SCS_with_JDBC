package com.dbms.scs.service;

import com.dbms.scs.domain.BookBookings;
import com.dbms.scs.domain.Books;
import com.dbms.scs.domain.Student;
import com.dbms.scs.model.BookBookingsDTO;
import com.dbms.scs.repos.BookBookingsRepository;
import com.dbms.scs.repos.BooksRepository;
import com.dbms.scs.repos.StudentRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class BookBookingsService {

    private final BookBookingsRepository bookBookingsRepository;
    private final StudentRepository studentRepository;
    private final BooksRepository booksRepository;

    public BookBookingsService(final BookBookingsRepository bookBookingsRepository,
            final StudentRepository studentRepository, final BooksRepository booksRepository) {
        this.bookBookingsRepository = bookBookingsRepository;
        this.studentRepository = studentRepository;
        this.booksRepository = booksRepository;
    }

    public List<BookBookingsDTO> findAll() {
        return bookBookingsRepository.findAll(Sort.by("bookingid"))
                .stream()
                .map(bookBookings -> mapToDTO(bookBookings, new BookBookingsDTO()))
                .collect(Collectors.toList());
    }

    public BookBookingsDTO get(final Integer bookingid) {
        return bookBookingsRepository.findById(bookingid)
                .map(bookBookings -> mapToDTO(bookBookings, new BookBookingsDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Integer create(final BookBookingsDTO bookBookingsDTO) {
        final BookBookings bookBookings = new BookBookings();
        mapToEntity(bookBookingsDTO, bookBookings);
        return bookBookingsRepository.save(bookBookings).getBookingid();
    }

    public void update(final Integer bookingid, final BookBookingsDTO bookBookingsDTO) {
        final BookBookings bookBookings = bookBookingsRepository.findById(bookingid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(bookBookingsDTO, bookBookings);
        bookBookingsRepository.save(bookBookings);
    }

    public void delete(final Integer bookingid) {
        bookBookingsRepository.deleteById(bookingid);
    }

    private BookBookingsDTO mapToDTO(final BookBookings bookBookings,
            final BookBookingsDTO bookBookingsDTO) {
        bookBookingsDTO.setBookingid(bookBookings.getBookingid());
        bookBookingsDTO.setDateTaken(bookBookings.getDateTaken());
        bookBookingsDTO.setExpectedReturn(bookBookings.getExpectedReturn());
        bookBookingsDTO.setReturnDate(bookBookings.getReturnDate());
        bookBookingsDTO.setPersonalRating(bookBookings.getPersonalRating());
        bookBookingsDTO.setStatus(bookBookings.getStatus());
        bookBookingsDTO.setStudorderbooking(bookBookings.getStudorderbooking() == null ? null : bookBookings.getStudorderbooking().getStudentRollNo());
        bookBookingsDTO.setBookingForBook(bookBookings.getBookingForBook() == null ? null : bookBookings.getBookingForBook().getBookID());
        return bookBookingsDTO;
    }

    private BookBookings mapToEntity(final BookBookingsDTO bookBookingsDTO,
            final BookBookings bookBookings) {
        bookBookings.setDateTaken(bookBookingsDTO.getDateTaken());
        bookBookings.setExpectedReturn(bookBookingsDTO.getExpectedReturn());
        bookBookings.setReturnDate(bookBookingsDTO.getReturnDate());
        bookBookings.setPersonalRating(bookBookingsDTO.getPersonalRating());
        bookBookings.setStatus(bookBookingsDTO.getStatus());
        final Student studorderbooking = bookBookingsDTO.getStudorderbooking() == null ? null : studentRepository.findById(bookBookingsDTO.getStudorderbooking())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "studorderbooking not found"));
        bookBookings.setStudorderbooking(studorderbooking);
        final Books bookingForBook = bookBookingsDTO.getBookingForBook() == null ? null : booksRepository.findById(bookBookingsDTO.getBookingForBook())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "bookingForBook not found"));
        bookBookings.setBookingForBook(bookingForBook);
        return bookBookings;
    }

}
