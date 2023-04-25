package urfu.bookingStand.domain.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import urfu.bookingStand.database.entities.Stand;
import urfu.bookingStand.database.repositories.StandRepository;
import urfu.bookingStand.database.repositories.TeamRepository;
import urfu.bookingStand.database.repositories.UserTeamAccessRepository;
import urfu.bookingStand.domain.abstractions.StandService;
import urfu.bookingStand.domain.exceptions.NoAccessException;
import urfu.bookingStand.domain.requests.AddStandRequest;

import java.text.MessageFormat;
import java.util.UUID;

@Component
public class StandServiceImpl implements StandService {
    private final StandRepository standRepository;
    private final UserTeamAccessRepository userTeamAccessRepository;
    private final TeamRepository teamRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public StandServiceImpl(StandRepository standRepository, UserTeamAccessRepository userTeamAccessRepository, TeamRepository teamRepository) {
        this.standRepository = standRepository;
        this.userTeamAccessRepository = userTeamAccessRepository;
        this.teamRepository = teamRepository;
    }

    @Override
    public void AddStand(AddStandRequest request, UUID teamId, UUID userId) throws NoAccessException {
        var team = teamRepository.findById(teamId);
        if (!userTeamAccessRepository.existsByUserIdAndTeamId(userId, teamId) || team.isEmpty()) {
            throw new NoAccessException(MessageFormat.format("User with id {0} has no access to team with id {1}", userId, teamId));
        }

        var stand = modelMapper.map(request, Stand.class);
        stand.setTeam(team.get());
        standRepository.save(stand);
    }
}
