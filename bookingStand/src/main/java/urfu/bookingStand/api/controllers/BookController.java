package urfu.bookingStand.api.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import urfu.bookingStand.api.dto.book.BookDto;
import urfu.bookingStand.domain.abstractions.BookService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class BookController {

    private final BookService bookService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("api/books")
    @ResponseBody
    public List<BookDto> getAllBooks() {
        return bookService.getAllBooks().stream()
                .map(b -> modelMapper.map(b, BookDto.class))
                .collect(Collectors.toList());
    }

    @PostMapping("api/books")
    @ResponseBody
    public BookDto createBook(@RequestBody BookDto requestBody) {
        bookService.createBook(requestBody.getName(), requestBody.getAuthor());
        return requestBody;
    }

}
