package urfu.bookingStand.api.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import urfu.bookingStand.api.dto.team.AddTeamDto;
import urfu.bookingStand.api.dto.team.InviteUserToTeamDto;
import urfu.bookingStand.api.dto.team.TeamByUserIdDto;
import urfu.bookingStand.domain.abstractions.TeamService;
import urfu.bookingStand.domain.exceptions.NoAccessException;
import urfu.bookingStand.domain.exceptions.ObjectRecreationException;
import urfu.bookingStand.domain.models.BookingUserDetails;
import urfu.bookingStand.domain.requests.AddTeamRequest;
import urfu.bookingStand.domain.responses.TeamByUserIdResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    @PostMapping("api/teams/{teamId}/invite")
    @ResponseBody
    public void InviteUserToTeam(@PathVariable UUID teamId,
                                 @RequestBody InviteUserToTeamDto body,
                                 Authentication authentication) throws NoAccessException, ObjectRecreationException {
        var user = (BookingUserDetails) authentication.getPrincipal();
        teamService.InviteUserToTeam(user.getId(), body.getUserId(), teamId);
    }
}
