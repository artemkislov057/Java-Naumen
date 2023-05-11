package urfu.bookingStand.domain.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import urfu.bookingStand.api.dto.team.TeamByDescriptionDto;
import urfu.bookingStand.api.dto.team.TeamByNameDto;
import urfu.bookingStand.api.dto.team.TeamByTeamIdDto;
import urfu.bookingStand.database.entities.Team;
import urfu.bookingStand.database.repositories.TeamRepository;
import urfu.bookingStand.domain.abstractions.TeamService;
import urfu.bookingStand.domain.requests.AddTeamRequest;

import java.util.ArrayList;
import java.util.List;
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

    @Override
    public List<TeamByTeamIdDto> getTeamsByTeamId(UUID teamId) {
        var teamsByTeamIdDto = new ArrayList<TeamByTeamIdDto>();

        for (var team : teamRepository.findAll()) {
            if (team.getId().equals(teamId)) {
                var teamDto = modelMapper.map(team, TeamByTeamIdDto.class);
                teamsByTeamIdDto.add(teamDto);
            }
        }
        return teamsByTeamIdDto;
    }

    @Override
    public List<TeamByNameDto> getTeamsByName(String name) {
        var teamsByNameDto = new ArrayList<TeamByNameDto>();

        for (var team : teamRepository.findAll()) {
            if (team.getName().equals(name)) {
                var teamDto = modelMapper.map(team, TeamByNameDto.class);
                teamsByNameDto.add(teamDto);
            }
        }
        return teamsByNameDto;
    }

    @Override
    public List<TeamByDescriptionDto> getTeamsByDescription(String description) {
        var teamsByDescriptionDto = new ArrayList<TeamByDescriptionDto>();

        for (var team : teamRepository.findAll()) {
            if (team.getDescription().equals(description)) {
                var teamDto = modelMapper.map(team, TeamByDescriptionDto.class);
                teamsByDescriptionDto.add(teamDto);
            }
        }
        return teamsByDescriptionDto;
    }
}
