package com.maps.coin.domain.room;

import com.maps.coin.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "room")
public class Room extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID roomId;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="personnel", nullable = false)
    private Long personnel;

    @Column(name="size", nullable = false)
    private Long size;

    @Builder
    public Room(String name, Long personnel, Long size) {
        this.name = name;
        this.personnel = personnel;
        this.size = size;
    }
}
