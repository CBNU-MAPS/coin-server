package com.maps.coin.domain.user;

import com.maps.coin.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "gamer")
public class Gamer extends BaseEntity {
    @Id
    private String id;

    @Column(name = "roomId", nullable = false)
    private Long roomId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "avatar", nullable = false)
    private Integer avatar;
}
