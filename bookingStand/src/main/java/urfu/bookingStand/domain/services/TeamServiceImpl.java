package urfu.bookingStand.domain.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import urfu.bookingStand.database.entities.Stand;
import urfu.bookingStand.database.entities.Team;
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
    private final TeamRepository teamRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public TeamServiceImpl(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public void AddTeam(AddTeamRequest request) {
        var team = modelMapper.map(request, Team.class);
        team.setName(request.getName());
        team.setDescription(request.getDescription());
        teamRepository.save(team);
    }
}
