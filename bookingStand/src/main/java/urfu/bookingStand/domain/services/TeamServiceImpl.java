package urfu.bookingStand.domain.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import urfu.bookingStand.database.entities.Team;
import urfu.bookingStand.database.entities.TeamInvitation;
import urfu.bookingStand.database.entities.UserTeamAccess;
import urfu.bookingStand.database.repositories.TeamInvitationRepository;
import urfu.bookingStand.database.repositories.TeamRepository;
import urfu.bookingStand.database.repositories.UserTeamAccessRepository;
import urfu.bookingStand.domain.abstractions.TeamService;
import urfu.bookingStand.domain.exceptions.NoAccessException;
import urfu.bookingStand.domain.exceptions.ObjectRecreationException;
import urfu.bookingStand.domain.requests.AddTeamRequest;
import urfu.bookingStand.domain.responses.TeamByUserIdResponse;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class TeamServiceImpl implements TeamService {
    private final UserTeamAccessRepository userTeamAccessRepository;
    private final TeamRepository teamRepository;
    private final TeamInvitationRepository teamInvitationRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public TeamServiceImpl(UserTeamAccessRepository userTeamAccessRepository,
                           TeamRepository teamRepository,
                           TeamInvitationRepository teamInvitationRepository) {
        this.userTeamAccessRepository = userTeamAccessRepository;
        this.teamRepository = teamRepository;
        this.teamInvitationRepository = teamInvitationRepository;
    }

    @Override
    public void AddTeam(AddTeamRequest request, UUID userId) {
        var team = modelMapper.map(request, Team.class);
        teamRepository.save(team);
        var userTeamAccess = new UserTeamAccess();
        userTeamAccess.setUserId(userId);
        userTeamAccess.setTeam(team);
        userTeamAccessRepository.save(userTeamAccess);
    }

    @Override
    public List<TeamByUserIdResponse> getTeamsByUserId(UUID userId) {
        var accesses = userTeamAccessRepository.getByUserId(userId);
        var result = new ArrayList<TeamByUserIdResponse>();
        for (UserTeamAccess access : accesses) {
            result.add(modelMapper.map(access.getTeam(), TeamByUserIdResponse.class));
        }

        return result;
    }

    @Override
    public void InviteUserToTeam(UUID userId, UUID userToAddId, UUID teamId) throws NoAccessException, ObjectRecreationException {
        var userTeamAccess = userTeamAccessRepository.findByUserIdAndTeamId(userId, teamId);
        if (userTeamAccess.isEmpty()) {
            throw new NoAccessException(MessageFormat.format("User with id {0} has no access to team with id {1}", userId, teamId));
        }

        var isAlreadyAddedToTeam = teamInvitationRepository.existsByTeamIdAndUserId(teamId, userToAddId)
                && userTeamAccessRepository.existsByUserIdAndTeamId(userToAddId, teamId);
        if (isAlreadyAddedToTeam) {
            throw new ObjectRecreationException(MessageFormat.format("User with id {0} already invited to team {1}", userToAddId, teamId));
        }

        var invitation = new TeamInvitation();
        invitation.setUserId(userToAddId);
        invitation.setTeam(userTeamAccess.get().getTeam());
        teamInvitationRepository.save(invitation);
    }
}
