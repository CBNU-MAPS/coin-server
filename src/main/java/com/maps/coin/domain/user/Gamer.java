package com.maps.coin.domain.user;

import com.maps.coin.domain.BaseEntity;
import com.maps.coin.domain.room.Room;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "gamer")
public class Gamer extends BaseEntity {
    @Id
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "avatar", nullable = false)
    private Integer avatar;

    @Column(name = "bingoCount", nullable = false)
    private Integer bingoCount;

    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private Room room;

    @OneToMany(mappedBy="gamer", cascade = CascadeType.ALL)
    private List<Answer> answers = new ArrayList<>();

    @Builder
    public Gamer(String id, String name, Integer avatar, Room room) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
        this.bingoCount = 0;
        this.room = room;
    }
}
