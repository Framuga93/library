package ru.framuga.api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.framuga.homework.aspect.annotation.Timer;
import ru.framuga.homework.service.BookService;
import ru.framuga.homework.service.IssuerService;
import ru.framuga.homework.service.ReaderService;

@Controller
@RequiredArgsConstructor
public class UIController {

    private final BookService bookService;
    private final ReaderService readerService;
    private final IssuerService issuerService;



    @GetMapping("/ui/books")
    public String listBooks(Model model){
        model.addAttribute("book", bookService.bookList());
        return "books";
    }

    @GetMapping("/ui/readers")
    public String listReaders(Model model){
        model.addAttribute("reader", readerService.readerList());
        return "readers";
    }

    @GetMapping("/ui/issue")
    public String issueTable(Model model){
        model.addAttribute("issue", issuerService.issueList());
        model.addAttribute("issueService", issuerService);
        return "issue";
    }

    @GetMapping("/ui/reader/{id}")
    public String listReaderBooks(@PathVariable("id") long id, Model model){
        model.addAttribute("reader", readerService.findReaderById(id));
        model.addAttribute("readerService", readerService);
        return "UI/readerBooks";
    }
}


