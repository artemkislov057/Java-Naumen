package urfu.bookingStand.api.dto.team;

import jakarta.validation.constraints.NotBlank;

public class AddTeamDto {
    @NotBlank
    private String Name;
    private String Description;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
