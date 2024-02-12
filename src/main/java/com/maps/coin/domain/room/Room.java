package com.maps.coin.domain.room;

import com.maps.coin.domain.BaseEntity;
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

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "room")
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

    @OneToMany(mappedBy="room", cascade = CascadeType.ALL)
    private List<Problem> problems = new ArrayList<>();

    @Builder
    public Room(String name, Integer personnel, Integer size) {
        this.name = name;
        this.personnel = personnel;
        this.size = size;
    }
}
