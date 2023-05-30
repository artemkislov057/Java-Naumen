package urfu.bookingStand.domain.responses;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public class UserResponse {
    @NotBlank
    private UUID userId;

    @NotBlank
    private String name;

    @NotBlank
    private String shortname;


    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }
}
