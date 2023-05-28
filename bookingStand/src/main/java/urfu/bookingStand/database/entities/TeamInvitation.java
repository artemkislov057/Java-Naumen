package urfu.bookingStand.database.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "team_invitation")
public class TeamInvitation {
    @Id
    @GeneratedValue
    private long id;
    private UUID userId;

    @ManyToOne(targetEntity = Team.class, fetch = FetchType.EAGER)
    private Team team;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
