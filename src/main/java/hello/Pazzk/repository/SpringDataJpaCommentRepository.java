package hello.Pazzk.repository;

import hello.Pazzk.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataJpaCommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByMemberId(Long memberId);

    List<Comment> findByItemId(long itemId);
}
