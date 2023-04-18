package urfu.bookingStand.api.dto.stand;

import jakarta.validation.constraints.NotBlank;

public class AddStandDto {
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
