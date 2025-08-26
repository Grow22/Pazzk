package hello.Pazzk.repository;

import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class MemoryItemRepository implements ItemRepository{

    private static final Map<Long, Item> store = new HashMap<>(); // Item 객체 저장소
    private static long sequence = 0L; // 저장할 ID 값

    // item 저장 메서드
    @Override
    public Item save(Item item) {
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }


    @Override
    public List<Item> findAll(ItemSearchCond cond) {

        // itemName 을 조건으로 데이터 검색 후 데이터 반환
        String itemName = cond.getItemName();
        return store.values().stream()
                .filter(item -> {
                    if(ObjectUtils.isEmpty(itemName)) {
                        return true;
                    }
                    return item.getItemName().contains(itemName);
                }).collect(Collectors.toList());
    }

    @Override
    public void vote(Item item) {
        item.likePlus(); // 좋아요 증가
    }

    @Override
    public Item findById(ItemSearchCond cond) {

        // itemId 에 해당하는 item 을 반환
        Long itemId = cond.getItemId();

        return store.get(itemId);
    }
}