package com.dbms.scs.controller;

import com.dbms.scs.domain.Books;
import com.dbms.scs.domain.Student;
import com.dbms.scs.model.BookBookingsDTO;
import com.dbms.scs.repos.BooksRepository;
import com.dbms.scs.repos.StudentRepository;
import com.dbms.scs.service.BookBookingsService;
import com.dbms.scs.util.WebUtils;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/bookBookingss")
public class BookBookingsController {

    private final BookBookingsService bookBookingsService;
    private final StudentRepository studentRepository;
    private final BooksRepository booksRepository;

    public BookBookingsController(final BookBookingsService bookBookingsService,
            final StudentRepository studentRepository, final BooksRepository booksRepository) {
        this.bookBookingsService = bookBookingsService;
        this.studentRepository = studentRepository;
        this.booksRepository = booksRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("studorderbookingValues", studentRepository.findAll().stream().collect(
                Collectors.toMap(Student::getStudentRollNo, Student::getName)));
        model.addAttribute("bookingForBookValues", booksRepository.findAll().stream().collect(
                Collectors.toMap(Books::getBookID, Books::getBookName)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("bookBookingss", bookBookingsService.findAll());
        return "bookBookings/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("bookBookings") final BookBookingsDTO bookBookingsDTO) {
        return "bookBookings/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("bookBookings") @Valid final BookBookingsDTO bookBookingsDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "bookBookings/add";
        }
        bookBookingsService.create(bookBookingsDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("bookBookings.create.success"));
        return "redirect:/bookBookingss";
    }

    @GetMapping("/edit/{bookingid}")
    public String edit(@PathVariable final Integer bookingid, final Model model) {
        model.addAttribute("bookBookings", bookBookingsService.get(bookingid));
        return "bookBookings/edit";
    }

    @PostMapping("/edit/{bookingid}")
    public String edit(@PathVariable final Integer bookingid,
            @ModelAttribute("bookBookings") @Valid final BookBookingsDTO bookBookingsDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "bookBookings/edit";
        }
        bookBookingsService.update(bookingid, bookBookingsDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("bookBookings.update.success"));
        return "redirect:/bookBookingss";
    }

    @PostMapping("/delete/{bookingid}")
    public String delete(@PathVariable final Integer bookingid,
            final RedirectAttributes redirectAttributes) {
        bookBookingsService.delete(bookingid);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("bookBookings.delete.success"));
        return "redirect:/bookBookingss";
    }

}
