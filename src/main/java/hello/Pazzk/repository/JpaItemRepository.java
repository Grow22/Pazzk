package hello.Pazzk.repository;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaItemRepository implements ItemRepository{

    private final SpringDataJpaItemRepository itemRepository;

    @Override
    public Item save(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public List<Item> findAll(ItemSearchCond cond) {

        String itemName=  cond.getItemName();

        //System.out.println("findAll 메서드 호출");
        // 문자가 존재할 경우
        if(StringUtils.hasText(itemName)) {
            return itemRepository.findByItemNameContaining(itemName);
        }
        // 문자가 없을 경우 모든 문자열 반환
        else {
            return itemRepository.findAll();
        }
    }



    @Override
    public Optional<Item> findById(ItemSearchCond cond) {
       return itemRepository.findById(cond.getItemId());
    }



    @Override
    public void vote(Item item) {

    }

    @Override
    public List<Item> findByMember_Id(long memberId) {
        return itemRepository.findByMember_Id(memberId);
    }

    // 검색 조건: memberId, ItemName
    public List<Item> findByMemberIdAndItemNameContaining(long memberId, String itemName) {

        return itemRepository.findByMemberIdAndItemNameContaining(memberId, itemName);
    }
}
