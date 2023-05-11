package urfu.bookingStand.domain.abstractions;

import urfu.bookingStand.api.dto.stand.StandByTeamIdDto;
import urfu.bookingStand.domain.exceptions.*;
import urfu.bookingStand.domain.requests.AddStandRequest;
import urfu.bookingStand.domain.requests.BookStandRequest;
import urfu.bookingStand.domain.responses.StandByTeamIdResponse;

import java.util.List;
import java.util.UUID;

public interface StandService {
    void AddStand(AddStandRequest request, UUID teamId, UUID userId) throws NoAccessException;

    void BookStand(BookStandRequest request, UUID standId, UUID userId)
            throws NoAccessException, UserNotFoundException, NotSuchTimeException, StandNotFoundException;

    void DeleteBookStand(UUID standId, long bookingId, UUID userId)
            throws StandNotFoundException,
            NoAccessException,
            UserNotFoundException, BookingNotFoundException;

    List<StandByTeamIdResponse> getStandByTeamId(UUID teamId) throws TeamNotFoundException;
}
