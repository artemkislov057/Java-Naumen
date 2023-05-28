package urfu.bookingStand.domain.responses;

public class StandEmploymentResponse {

    private long standId;

    private String startTime;
    private String endTime;
    private UserResponse user;

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

    public UserResponse getUser() {
        return user;
    }

    public void setUser(UserResponse user) {
        this.user = user;
    }
}
