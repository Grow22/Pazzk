package hello.Pazzk.repository;

import java.util.List;
import java.util.Optional;

public interface ItemRepository {

    // Item 저장 메서드
    Item save(Item item);

    // findAll 메서드
    List<Item> findAll(ItemSearchCond cond);

    // id 에 해당하는 Item 을 찾는 메서드
    Optional<Item> findById(ItemSearchCond cond);

    // vote 메서드 ==> 좋아요 메서드
    public void vote(Item item);

    List<Item> findByMember_Id(long memberId);
}