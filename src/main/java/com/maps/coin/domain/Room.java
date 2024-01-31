package com.maps.coin.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID roomId;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="personnel", nullable = false)
    private Long personnel;

    @Column(name="size", nullable = false)
    private Long size;

    @CreatedDate private LocalDateTime createdAt;
    @LastModifiedBy private LocalDateTime updatedAt;

    @Builder
    public Room(String name, Long personnel, Long size) {
        this.name = name;
        this.personnel = personnel;
        this.size = size;
    }
}
