package com.maps.coin.domain.user;

import com.maps.coin.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "answer")
public class Answer extends BaseEntity {

}
