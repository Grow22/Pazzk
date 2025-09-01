package hello.Pazzk.repository;

import hello.Pazzk.domain.LikeItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaLikeItemRepository extends JpaRepository<LikeItem, Long> {

    Optional<LikeItem>  findByMemberIdAndItemId(Long memberId, Long itemId);
}
