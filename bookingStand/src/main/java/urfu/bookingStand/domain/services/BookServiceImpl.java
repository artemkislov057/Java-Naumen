package urfu.bookingStand.domain.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import urfu.bookingStand.database.entities.Book;
import urfu.bookingStand.database.repositories.BookRepository;
import urfu.bookingStand.domain.abstractions.BookService;
import urfu.bookingStand.domain.models.BookModel;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {

        this.bookRepository = bookRepository;
    }

    @Override
    public void createBook(String name, String author) {
        bookRepository.save(new Book(name, author));
    }

    @Override
    public List<BookModel> getAllBooks() {
        var books = bookRepository.findAll();
        return StreamSupport.stream(books.spliterator(), false)
                .map(b -> modelMapper.map(b, BookModel.class))
                .collect(Collectors.toList());
    }
}
