package com.dbms.scs.controller;

import com.dbms.scs.model.BooksDTO;
import com.dbms.scs.service.BooksService;
import com.dbms.scs.util.WebUtils;
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
@RequestMapping("/bookss")
public class BooksController {

    private final BooksService booksService;

    public BooksController(final BooksService booksService) {
        this.booksService = booksService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("bookss", booksService.findAll());
        return "books/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("books") final BooksDTO booksDTO) {
        return "books/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("books") @Valid final BooksDTO booksDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "books/add";
        }
        booksService.create(booksDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("books.create.success"));
        return "redirect:/bookss";
    }

    @GetMapping("/edit/{bookID}")
    public String edit(@PathVariable final Integer bookID, final Model model) {
        model.addAttribute("books", booksService.get(bookID));
        return "books/edit";
    }

    @PostMapping("/edit/{bookID}")
    public String edit(@PathVariable final Integer bookID,
            @ModelAttribute("books") @Valid final BooksDTO booksDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "books/edit";
        }
        booksService.update(bookID, booksDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("books.update.success"));
        return "redirect:/bookss";
    }

    @PostMapping("/delete/{bookID}")
    public String delete(@PathVariable final Integer bookID,
            final RedirectAttributes redirectAttributes) {
        final String referencedWarning = booksService.getReferencedWarning(bookID);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            booksService.delete(bookID);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("books.delete.success"));
        }
        return "redirect:/bookss";
    }

}
