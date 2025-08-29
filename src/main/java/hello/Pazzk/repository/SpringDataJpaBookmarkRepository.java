package hello.Pazzk.repository;

import hello.Pazzk.domain.BookmarkItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SpringDataJpaBookmarkRepository extends JpaRepository<BookmarkItem, Long> {

    List<BookmarkItem> findByMember_Id(long memberId);

    Optional<BookmarkItem> findByMemberIdAndItemId(long memberId, long itemId);

    Optional<BookmarkItem> findByItemId(Long itemId);

    void deleteByItemId(Long itemId);
}
