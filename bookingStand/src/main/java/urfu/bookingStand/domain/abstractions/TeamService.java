package urfu.bookingStand.domain.abstractions;

import urfu.bookingStand.domain.exceptions.NoAccessException;
import urfu.bookingStand.domain.requests.AddTeamRequest;

import java.util.UUID;

public interface TeamService {
    void AddTeam(AddTeamRequest request, UUID userId);
}
