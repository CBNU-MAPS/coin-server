package com.maps.coin.domain.room;

import com.maps.coin.domain.BaseEntity;
import com.maps.coin.domain.user.Gamer;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "room")
@SQLDelete(sql = "UPDATE room SET deleted = true WHERE id = ?")
public class Room extends BaseEntity {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID roomId;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="personnel", nullable = false)
    private Integer personnel;

    @Column(name="size", nullable = false)
    private Integer size;

    @Column(name="start", nullable = false)
    private Boolean start = Boolean.FALSE;

    private Boolean deleted = Boolean.FALSE;

    @OneToMany(mappedBy="room", cascade = CascadeType.ALL)
    private List<Problem> problems = new ArrayList<>();

    @OneToMany(mappedBy="room", cascade = CascadeType.ALL)
    private List<Gamer> gamers = new ArrayList<>();

    @Builder
    public Room(String name, Integer personnel, Integer size) {
        this.name = name;
        this.personnel = personnel;
        this.size = size;
    }
}
