package urfu.bookingStand.database.entities;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "team")
public class Team {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String name;

    private String description;

    @OneToMany(targetEntity = Stand.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Stand> stands;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

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

    public List<Stand> getStands() {
        return stands;
    }

    public void setStands(List<Stand> stands) {
        this.stands = stands;
    }
}
