package urfu.bookingStand.api.dto.stand;

import jakarta.validation.constraints.NotBlank;

import java.util.Date;

public class BookStandDto {
    @NotBlank
    private Date StartTime;
    @NotBlank
    private Date EndTime;

    public Date getStartTime() {
        return StartTime;
    }

    public void setStartTime(Date startTime) {
        StartTime = startTime;
    }

    public Date getEndTime() {
        return EndTime;
    }

    public void setEndTime(Date endTime) {
        EndTime = endTime;
    }
}
