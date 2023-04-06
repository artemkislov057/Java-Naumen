package urfu.bookingStand.domain.abstractions;

import urfu.bookingStand.domain.models.BookModel;

import java.util.List;

public interface BookService {

    void createBook(String name, String author);

    List<BookModel> getAllBooks();

}
