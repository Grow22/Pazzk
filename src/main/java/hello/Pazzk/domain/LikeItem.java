package hello.Pazzk.domain;


import hello.Pazzk.repository.Item;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

/*
    24시간 이내 좋아요 기능 구현을 위한 Entity
 */
@Entity
@Data
public class LikeItem {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Member member;

    @ManyToOne
    private Item item;
    LocalDate date;
}
