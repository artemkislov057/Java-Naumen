package urfu.bookingStand.domain.models.report;

import java.time.LocalDateTime;
import java.util.Date;

public class TeamStandReportModel {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String username;

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
