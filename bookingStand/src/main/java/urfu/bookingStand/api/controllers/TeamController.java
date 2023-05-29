package urfu.bookingStand.api.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import urfu.bookingStand.api.dto.team.AddTeamDto;
import urfu.bookingStand.api.dto.team.InviteUserToTeamDto;
import urfu.bookingStand.api.dto.team.TeamByUserIdDto;
import urfu.bookingStand.api.dto.team.TeamInvitationDto;
import urfu.bookingStand.domain.abstractions.TeamService;
import urfu.bookingStand.domain.exceptions.NoAccessException;
import urfu.bookingStand.domain.exceptions.ObjectRecreationException;
import urfu.bookingStand.domain.models.BookingUserDetails;
import urfu.bookingStand.domain.requests.AddTeamRequest;
import urfu.bookingStand.domain.responses.TeamByUserIdResponse;
import urfu.bookingStand.domain.responses.TeamInvitationResponse;

import java.util.ArrayList;
import java.util.Date;
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
        teamService.inviteUserToTeam(user.getId(), body.getUserId(), teamId);
    }

    @GetMapping("api/teams/invitations")
    @ResponseBody
    public List<TeamInvitationDto> getMyInvitations(Authentication authentication) {
        var user = (BookingUserDetails) authentication.getPrincipal();
        var invitations = teamService.getUserInvitations(user.getId());
        var result = new ArrayList<TeamInvitationDto>();
        for (TeamInvitationResponse invitation : invitations) {
            result.add(modelMapper.map(invitation, TeamInvitationDto.class));
        }

        return result;
    }

    @PostMapping("api/teams/{teamId}/accept-invitation")
    @ResponseBody
    public void AcceptInvitation(@PathVariable UUID teamId, Authentication authentication) throws NoAccessException {
        var user = (BookingUserDetails) authentication.getPrincipal();
        teamService.acceptInvitationToTeam(user.getId(), teamId);
    }

    @PostMapping("api/teams/{teamId}/reject-invitation")
    @ResponseBody
    public void RejectInvitation(@PathVariable UUID teamId, Authentication authentication) {
        var user = (BookingUserDetails) authentication.getPrincipal();
        teamService.rejectInvitationToTeam(user.getId(), teamId);
    }

    @GetMapping("api/teams/{teamId}/report")
    @ResponseBody
    public void getReportForTeamByDate(@PathVariable UUID teamId,
                                       @RequestParam("report-date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date reportDate,
                                       Authentication authentication) throws NoAccessException {
        var user = (BookingUserDetails) authentication.getPrincipal();
        teamService.getReportForTeamByDate(user.getId(), teamId, reportDate);
    }
}
