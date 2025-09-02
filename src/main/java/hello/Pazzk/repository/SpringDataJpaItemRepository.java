package hello.Pazzk.repository;

import hello.Pazzk.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataJpaItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByItemName(String itemName);

    List<Item> findByMember_Id(long memberId);

    List<Item> findByItemNameContaining(String itemName);

    List<Item> findByMemberIdAndItemNameContaining(Long memberId, String itemName);

    Page<Item> findAll(Pageable pageable);
}
