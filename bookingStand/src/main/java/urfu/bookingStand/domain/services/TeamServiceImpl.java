package urfu.bookingStand.domain.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import urfu.bookingStand.api.dto.team.TeamByUserIdDto;
import urfu.bookingStand.database.entities.Team;
import urfu.bookingStand.database.entities.UserTeamAccess;
import urfu.bookingStand.database.repositories.TeamRepository;
import urfu.bookingStand.database.repositories.UserTeamAccessRepository;
import urfu.bookingStand.domain.abstractions.TeamService;
import urfu.bookingStand.domain.requests.AddTeamRequest;

import java.util.ArrayList;
import java.util.List;
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
        var userTeamAccess = new UserTeamAccess();
        userTeamAccess.setUserId(userId);
        userTeamAccess.setTeamId(team.getId());
        userTeamAccessRepository.save(userTeamAccess);
    }

    @Override
    public List<TeamByUserIdDto> getTeamsByUserId(UUID userId) {
        var teamsByUserIdDto = new ArrayList<TeamByUserIdDto>();

        for (var team : teamRepository.findAll()) {
            if (userTeamAccessRepository.existsByUserIdAndTeamId(userId, team.getId())) {
                var teamDto = modelMapper.map(team, TeamByUserIdDto.class);
                teamsByUserIdDto.add(teamDto);
            }
        }
        return teamsByUserIdDto;
    }
}
