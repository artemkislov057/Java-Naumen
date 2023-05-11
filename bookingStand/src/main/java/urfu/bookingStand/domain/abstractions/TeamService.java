package urfu.bookingStand.domain.abstractions;

import urfu.bookingStand.domain.requests.AddTeamRequest;
import urfu.bookingStand.domain.responses.TeamByUserIdResponse;

import java.util.List;
import java.util.UUID;

public interface TeamService {
    void AddTeam(AddTeamRequest request, UUID userId);
  
    List<TeamByUserIdResponse> getTeamsByUserId(UUID userId);
}
