package urfu.bookingStand.domain.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import urfu.bookingStand.database.entities.Stand;
import urfu.bookingStand.database.entities.Team;
import urfu.bookingStand.database.entities.UserTeamAccess;
import urfu.bookingStand.database.repositories.StandRepository;
import urfu.bookingStand.database.repositories.TeamRepository;
import urfu.bookingStand.database.repositories.UserRepository;
import urfu.bookingStand.database.repositories.UserTeamAccessRepository;
import urfu.bookingStand.domain.abstractions.TeamService;
import urfu.bookingStand.domain.exceptions.NoAccessException;
import urfu.bookingStand.domain.requests.AddTeamRequest;

import java.text.MessageFormat;
import java.util.UUID;

@Component
public class TeamServiceImpl implements TeamService {
    private final UserTeamAccessRepository userTeamAccessRepository;
    private final TeamRepository teamRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public TeamServiceImpl(UserTeamAccessRepository userTeamAccessRepository, TeamRepository teamRepository) {
        this.userTeamAccessRepository = userTeamAccessRepository;
        this.teamRepository = teamRepository;
    }

    @Override
    public void AddTeam(AddTeamRequest request, UUID userId) {
        var team = modelMapper.map(request, Team.class);
        teamRepository.save(team);
        var userTeamAccess = modelMapper.map(userId, UserTeamAccess.class);
        userTeamAccess.setTeam(team);
        userTeamAccessRepository.save(userTeamAccess);
    }
}
