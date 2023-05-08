package urfu.bookingStand.domain.abstractions;

import urfu.bookingStand.domain.exceptions.NoAccessException;
import urfu.bookingStand.domain.exceptions.NotSuchTimeException;
import urfu.bookingStand.domain.exceptions.UserNotFoundException;
import urfu.bookingStand.domain.requests.AddStandRequest;
import urfu.bookingStand.domain.requests.BookStandRequest;

import java.util.UUID;

public interface StandService {
    void AddStand(AddStandRequest request, UUID teamId, UUID userId) throws NoAccessException;

    void BookStand(BookStandRequest request, UUID standId, UUID userId) throws NoAccessException, UserNotFoundException, NotSuchTimeException;
}
