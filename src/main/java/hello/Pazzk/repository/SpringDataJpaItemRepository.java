package hello.Pazzk.repository;

import hello.Pazzk.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataJpaItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByItemName(String itemName);
}
