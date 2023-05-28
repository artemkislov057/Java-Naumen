package urfu.bookingStand.api.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import urfu.bookingStand.api.dto.team.AddTeamDto;
import urfu.bookingStand.api.dto.team.TeamByUserIdDto;
import urfu.bookingStand.domain.abstractions.TeamService;
import urfu.bookingStand.domain.models.BookingUserDetails;
import urfu.bookingStand.domain.requests.AddTeamRequest;
import urfu.bookingStand.domain.responses.TeamByUserIdResponse;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TeamController {
    private final TeamService teamService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @PostMapping("api/teams")
    @ResponseBody
    public void AddTeam(@RequestBody AddTeamDto body, Authentication authentication) {
        var user = (BookingUserDetails) authentication.getPrincipal();
        var request = modelMapper.map(body, AddTeamRequest.class);
        teamService.AddTeam(request, user.getId());
    }

    @GetMapping("api/teams")
    @ResponseBody
    public List<TeamByUserIdDto> GetTeams(Authentication authentication) {
        var user = (BookingUserDetails) authentication.getPrincipal();
        var teams = teamService.getTeamsByUserId(user.getId());
        var result = new ArrayList<TeamByUserIdDto>();
        for (TeamByUserIdResponse team : teams) {
            result.add(modelMapper.map(team, TeamByUserIdDto.class));
        }

        return result;
    }
}
