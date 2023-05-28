package urfu.bookingStand.api.dto.team;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public class InviteUserToTeamDto {
    @NotBlank
    private UUID userId;

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}
