package urfu.bookingStand.database.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import urfu.bookingStand.database.entities.Booking;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface BookingRepository extends CrudRepository<Booking, Long> {
    boolean existsByStandIdEqualsAndStartTimeBetween(UUID standId, LocalDateTime dateOne, LocalDateTime dateTwo);

    boolean existsByStandIdEqualsAndEndTimeBetween(UUID standId, LocalDateTime dateOne, LocalDateTime dateTwo);

    @Query(value = "SELECT * FROM public.booking WHERE stand_id = :standId AND end_time >= :dateOne AND start_time <= :dateTwo", nativeQuery = true)
    List<Booking> findAllBookingsBetween(@Param("standId") UUID standId, @Param("dateOne") LocalDateTime dateOne, @Param("dateTwo") LocalDateTime dateTwo);
}

