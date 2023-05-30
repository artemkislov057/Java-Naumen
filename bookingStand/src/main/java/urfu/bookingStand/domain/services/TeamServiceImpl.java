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
import urfu.bookingStand.domain.abstractions.ReportService;
import urfu.bookingStand.domain.abstractions.TeamService;
import urfu.bookingStand.domain.exceptions.NoAccessException;
import urfu.bookingStand.domain.exceptions.ObjectRecreationException;
import urfu.bookingStand.domain.requests.AddTeamRequest;
import urfu.bookingStand.domain.responses.TeamByUserIdResponse;
import urfu.bookingStand.domain.responses.TeamInvitationResponse;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
public class TeamServiceImpl implements TeamService {
    private final UserTeamAccessRepository userTeamAccessRepository;
    private final TeamRepository teamRepository;
    private final TeamInvitationRepository teamInvitationRepository;
    private final ReportService reportService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public TeamServiceImpl(UserTeamAccessRepository userTeamAccessRepository,
                           TeamRepository teamRepository,
                           TeamInvitationRepository teamInvitationRepository,
                           ReportService reportService) {
        this.userTeamAccessRepository = userTeamAccessRepository;
        this.teamRepository = teamRepository;
        this.teamInvitationRepository = teamInvitationRepository;
        this.reportService = reportService;
    }

    @Override
    public void addTeam(AddTeamRequest request, UUID userId) {
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
    public void inviteUserToTeam(UUID userId, UUID userToAddId, UUID teamId) throws NoAccessException, ObjectRecreationException {
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

    @Override
    public List<TeamInvitationResponse> getUserInvitations(UUID userId) {
        var invitations = teamInvitationRepository.getByUserId(userId);
        var result = new ArrayList<TeamInvitationResponse>();
        for (TeamInvitation teamInvitation : invitations) {
            result.add(modelMapper.map(teamInvitation.getTeam(), TeamInvitationResponse.class));
        }

        return result;
    }

    @Override
    public void acceptInvitationToTeam(UUID userId, UUID teamId) throws NoAccessException {
        var hasUserAccessToTeam = userTeamAccessRepository.existsByUserIdAndTeamId(userId, teamId);
        if (hasUserAccessToTeam) {
            return;
        }

        var invitation = teamInvitationRepository.findByTeamIdAndUserId(teamId, userId);
        if (invitation.isEmpty()) {
            throw new NoAccessException(MessageFormat.format("User with id {0} has no invitation to team {1}", userId, teamId));
        }

        var userTeamAccess = new UserTeamAccess();
        userTeamAccess.setUserId(userId);
        userTeamAccess.setTeam(invitation.get().getTeam());
        userTeamAccessRepository.save(userTeamAccess);

        teamInvitationRepository.delete(invitation.get());
    }

    @Override
    public void rejectInvitationToTeam(UUID userId, UUID teamId) {
        var hasUserAccessToTeam = userTeamAccessRepository.existsByUserIdAndTeamId(userId, teamId);
        var invitation = teamInvitationRepository.findByTeamIdAndUserId(teamId, userId);
        if (hasUserAccessToTeam || invitation.isEmpty()) {
            return;
        }

        teamInvitationRepository.delete(invitation.get());
    }

    @Override
    public void createReportForTeamByDate(UUID userId, UUID teamId, Date reportDate) throws NoAccessException {
        var hasUserAccessToTeam = userTeamAccessRepository.existsByUserIdAndTeamId(userId, teamId);
        if (!hasUserAccessToTeam) {
            throw new NoAccessException(MessageFormat.format("User with id {0} has no invitation to team {1}", userId, teamId));
        }

        reportService.getOrCreateReport(teamId, reportDate);
    }
}
