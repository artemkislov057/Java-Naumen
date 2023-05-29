package urfu.bookingStand.domain.abstractions;

import urfu.bookingStand.domain.exceptions.NoAccessException;
import urfu.bookingStand.domain.exceptions.ObjectRecreationException;
import urfu.bookingStand.domain.requests.AddTeamRequest;
import urfu.bookingStand.domain.responses.TeamByUserIdResponse;
import urfu.bookingStand.domain.responses.TeamInvitationResponse;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface TeamService {
    void AddTeam(AddTeamRequest request, UUID userId);

    List<TeamByUserIdResponse> getTeamsByUserId(UUID userId);

    void inviteUserToTeam(UUID userId, UUID userToAddId, UUID teamId) throws NoAccessException, ObjectRecreationException;

    List<TeamInvitationResponse> getUserInvitations(UUID userId);

    void acceptInvitationToTeam(UUID userId, UUID teamId) throws NoAccessException;

    void rejectInvitationToTeam(UUID userId, UUID teamId);

    void getReportForTeamByDate(UUID userId, UUID teamId, Date reportDate) throws NoAccessException;
}
