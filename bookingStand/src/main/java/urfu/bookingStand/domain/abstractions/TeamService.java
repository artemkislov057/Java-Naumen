package urfu.bookingStand.domain.abstractions;

import urfu.bookingStand.api.dto.team.TeamByDescriptionDto;
import urfu.bookingStand.api.dto.team.TeamByNameDto;
import urfu.bookingStand.api.dto.team.TeamByTeamIdDto;
import urfu.bookingStand.domain.requests.AddTeamRequest;

import java.util.List;
import java.util.UUID;

public interface TeamService {
    void AddTeam(AddTeamRequest request);

    List<TeamByTeamIdDto> getTeamsByTeamId(UUID teamId);

    List<TeamByNameDto> getTeamsByName(String name);

    List<TeamByDescriptionDto> getTeamsByDescription(String description);
}
