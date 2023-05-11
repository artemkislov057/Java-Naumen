package urfu.bookingStand.database.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import urfu.bookingStand.database.entities.Booking;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends CrudRepository<Booking, Long> {
    boolean existsByStartTimeBetween(LocalDateTime dateOne, LocalDateTime dateTwo);
    boolean existsByEndTimeBetween(LocalDateTime dateOne, LocalDateTime dateTwo);

    Iterable<Booking>  findAllByStartTimeBetween(LocalDateTime dateOne, LocalDateTime dateTwo);
    Iterable<Booking> findAllByEndTimeBetween(LocalDateTime dateOne, LocalDateTime dateTwo);

    @Query(value = "SELECT * FROM public.booking WHERE end_time >= :dateOne AND start_time <= :dateTwo", nativeQuery = true)
    List<Booking> findAllBookingsBetween(@Param("dateOne") LocalDateTime dateOne, @Param("dateTwo") LocalDateTime dateTwo);
}

