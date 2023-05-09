package urfu.bookingStand.domain.abstractions;

import urfu.bookingStand.domain.exceptions.*;
import urfu.bookingStand.domain.requests.AddStandRequest;
import urfu.bookingStand.domain.requests.BookStandRequest;

import java.util.UUID;

public interface StandService {
    void AddStand(AddStandRequest request, UUID teamId, UUID userId) throws NoAccessException;

    void BookStand(BookStandRequest request, UUID standId, UUID userId)
            throws NoAccessException, UserNotFoundException, NotSuchTimeException, StandNotFoundException;

    void DeleteBookStand(UUID standId, long bookingId, UUID userId)
            throws StandNotFoundException,
            NoAccessException,
            UserNotFoundException, BookingNotFoundException;
}
