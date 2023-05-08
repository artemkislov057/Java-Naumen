package urfu.bookingStand.database.repositories;

import org.springframework.data.repository.CrudRepository;
import urfu.bookingStand.database.entities.Booking;

import java.util.Date;
import java.util.UUID;

public interface BookingRepository extends CrudRepository<Booking, Long> {
    boolean existsBookingByStartTimeAfterOrIdEquals(Date date);
    boolean existsBookingByEndTimeBeforeOrIdEquals(Date date);

}
