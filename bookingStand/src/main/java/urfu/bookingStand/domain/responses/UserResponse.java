package urfu.bookingStand.domain.responses;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public class UserResponse {
    @NotBlank
    private UUID UserId;

    @NotBlank
    private String Name;

    @NotBlank
    private String Shortname;


    public UUID getUserId() {
        return UserId;
    }

    public void setUserId(UUID userId) {
        UserId = userId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getShortname() {
        return Shortname;
    }

    public void setShortname(String shortname) {
        Shortname = shortname;
    }
}
