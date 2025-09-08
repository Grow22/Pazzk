package hello.Pazzk.repository;

import hello.Pazzk.domain.BookmarkItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SpringDataJpaBookmarkRepository extends JpaRepository<BookmarkItem, Long> {

    Page<BookmarkItem> findByMember_Id(Pageable pageable, long memberId);

    Optional<BookmarkItem> findByMemberIdAndItemId(long memberId, long itemId);

    Optional<BookmarkItem> findByItemId(Long itemId);

    void deleteByItemId(Long itemId);

    Page<BookmarkItem> findByMember_IdAndItem_ItemNameContaining(Pageable pageable, Long memberId, String itemName);
}
