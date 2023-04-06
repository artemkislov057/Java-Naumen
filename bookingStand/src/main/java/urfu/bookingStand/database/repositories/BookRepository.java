package urfu.bookingStand.database.repositories;

import org.springframework.data.repository.CrudRepository;
import urfu.bookingStand.database.entities.Book;

public interface BookRepository extends CrudRepository<Book, Long> {
}
