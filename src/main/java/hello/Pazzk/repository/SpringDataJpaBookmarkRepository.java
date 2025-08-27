package hello.Pazzk.repository;

import hello.Pazzk.domain.BookmarkItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataJpaBookmarkRepository extends JpaRepository<BookmarkItem, Long> {

    List<BookmarkItem> findByMember_Id(long memberId);
}
