package urfu.bookingStand.database.repositories;

import org.springframework.data.repository.CrudRepository;
import urfu.bookingStand.database.entities.Booking;

import java.time.LocalDateTime;
import java.util.Optional;

public interface BookingRepository extends CrudRepository<Booking, Long> {
    boolean existsByStartTimeBetween(LocalDateTime dateOne, LocalDateTime dateTwo);
    boolean existsByEndTimeBetween(LocalDateTime dateOne, LocalDateTime dateTwo);

    Iterable<Booking>  findAllByStartTimeBetween(LocalDateTime dateOne, LocalDateTime dateTwo);
    Iterable<Booking> findAllByEndTimeBetween(LocalDateTime dateOne, LocalDateTime dateTwo);

}

