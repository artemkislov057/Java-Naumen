package urfu.bookingStand.api.dto.stand;

import jakarta.validation.constraints.NotBlank;

public class AddStandDto {
    @NotBlank
    private String name;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
