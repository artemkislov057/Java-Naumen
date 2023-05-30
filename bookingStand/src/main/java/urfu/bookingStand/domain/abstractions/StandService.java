package urfu.bookingStand.domain.abstractions;

import urfu.bookingStand.domain.exceptions.*;
import urfu.bookingStand.domain.requests.AddStandRequest;
import urfu.bookingStand.domain.requests.BookStandRequest;
import urfu.bookingStand.domain.responses.StandEmploymentResponse;
import urfu.bookingStand.domain.responses.StandByTeamIdResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface StandService {
    void addStand(AddStandRequest request, UUID teamId, UUID userId) throws DomainExceptionBase;

    void bookStand(BookStandRequest request, UUID standId, UUID userId) throws DomainExceptionBase;

    void deleteBookStand(UUID standId, long bookingId, UUID userId) throws DomainExceptionBase;

    List<StandEmploymentResponse> getStandEmploymentByTimePeriod(UUID standId, LocalDateTime from, LocalDateTime to) throws DomainExceptionBase;

    List<StandByTeamIdResponse> getStandByTeamId(UUID teamId) throws DomainExceptionBase;
}
