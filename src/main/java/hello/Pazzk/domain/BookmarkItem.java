package hello.Pazzk.domain;


import hello.Pazzk.repository.Item;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

// 즐겨찾기 Entity
@Data
@Entity
public class BookmarkItem {

    @Id
    @GeneratedValue
    private Long bookmarkId;

    @ManyToOne
    private Member member;

    @ManyToOne
    private Item item;
}
