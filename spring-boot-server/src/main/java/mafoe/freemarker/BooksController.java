package mafoe.freemarker;

import mafoe.repository.BookRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.transaction.Transactional;

/**
 * Spring WebMVC controller for Freemarker.
 */
@Controller
public class BooksController {

    private final BookRepository bookRepository;

    public BooksController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Transactional
    @RequestMapping(value = "/books", method = RequestMethod.GET)
    public String books(Model model) {

        model.addAttribute("books", bookRepository.findBooksForFreemarker());
        return "books";
    }
}