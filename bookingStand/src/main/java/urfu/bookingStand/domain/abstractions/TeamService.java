package urfu.bookingStand.domain.abstractions;

import urfu.bookingStand.api.dto.team.TeamByUserIdDto;
import urfu.bookingStand.domain.requests.AddTeamRequest;

import java.util.List;
import java.util.UUID;

public interface TeamService {
    void AddTeam(AddTeamRequest request, UUID userId);

    List<TeamByUserIdDto> getTeamsByUserId(UUID userId);
}
