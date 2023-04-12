package urfu.bookingStand.database.repositories;

import org.springframework.data.repository.CrudRepository;
import urfu.bookingStand.database.entities.Booking;

public interface BookingRepository extends CrudRepository<Booking, Long> {
}
