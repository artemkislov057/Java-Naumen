package urfu.bookingStand.api.dto.stand;

import urfu.bookingStand.api.dto.user.UserDto;

public class StandEmploymentDto {

    private long standId;

    private String startTime;
    private String endTime;
    private UserDto user;

    public long getId() {
        return standId;
    }

    public void setId(long id) {
        this.standId = id;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }
}
