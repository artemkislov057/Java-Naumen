package urfu.bookingStand.database.repositories;

import org.springframework.data.repository.CrudRepository;
import urfu.bookingStand.database.entities.Booking;

import java.time.LocalDateTime;
import java.util.Date;

public interface BookingRepository extends CrudRepository<Booking, Long> {
    boolean existsBookingByStartTimeAfter(LocalDateTime date);
    boolean existsBookingByEndTimeBefore(LocalDateTime date);

}
