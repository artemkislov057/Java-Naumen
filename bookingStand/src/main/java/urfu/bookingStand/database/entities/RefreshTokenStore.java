package urfu.bookingStand.database.entities;

import jakarta.persistence.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "refresh_token")
public class RefreshTokenStore {
    @Id
    @GeneratedValue
    private long Id;

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private String refreshToken;

    @Column(nullable = false)
    private Date endLifeTime;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Date getEndLifeTime() {
        return endLifeTime;
    }

    public void setEndLifeTime(Date endLifeTime) {
        this.endLifeTime = endLifeTime;
    }
}
