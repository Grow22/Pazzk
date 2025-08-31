package hello.Pazzk.domain;


import hello.Pazzk.repository.Item;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Comment {

    @Id
    @GeneratedValue
    private Long id;


    private String text;

    @ManyToOne
    private Member member;

    @ManyToOne
    private Item item;

    // 댓글 시간 표시
    private Date timeStamp;

    @Override
    public String toString() {
        return "Comment{" +
                " id=" + id +
                " memberId=" + member.getId() +
                " itemId=" + item.getId() +
                ", text='" + text + '\'' +
                ", timeStamp=" + timeStamp +
                '}';
    }
}
