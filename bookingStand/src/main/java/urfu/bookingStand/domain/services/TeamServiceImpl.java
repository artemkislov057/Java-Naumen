package urfu.bookingStand.domain.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import urfu.bookingStand.database.entities.Team;
import urfu.bookingStand.database.entities.UserTeamAccess;
import urfu.bookingStand.database.repositories.TeamRepository;
import urfu.bookingStand.database.repositories.UserTeamAccessRepository;
import urfu.bookingStand.domain.abstractions.TeamService;
import urfu.bookingStand.domain.requests.AddTeamRequest;
import urfu.bookingStand.domain.responses.TeamByUserIdResponse;

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
}
