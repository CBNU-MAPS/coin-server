package com.maps.coin.domain.user;

import com.maps.coin.domain.BaseEntity;
import com.maps.coin.domain.room.Room;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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

    @OneToMany
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private Room room;

    @Builder
    public Gamer(String name, Integer avatar) {
        this.name = name;
        this.avatar = avatar;
    }
}
