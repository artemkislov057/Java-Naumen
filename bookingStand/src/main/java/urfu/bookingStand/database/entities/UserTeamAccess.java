package urfu.bookingStand.database.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "user_team_access")
public class UserTeamAccess {
    @Id
    @GeneratedValue
    private long Id;

    @Column(nullable = false)
    private UUID userId;

    @Column(name = "team_id", insertable = false, updatable = false, nullable = false)
    private UUID teamId;

    @ManyToOne(targetEntity = Team.class, fetch = FetchType.EAGER)
    private Team team;

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getTeamId() {
        return teamId;
    }

    public void setTeamId(UUID teamId) {
        this.teamId = teamId;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
