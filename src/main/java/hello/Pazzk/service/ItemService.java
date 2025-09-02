package hello.Pazzk.service;

import hello.Pazzk.repository.*;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.internal.constraintvalidators.hv.URLValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    private final SpringDataJpaItemRepository jpaItemRepository;
    // Url 유효성 검사 메서드


    public Item save(Item item) {
        return itemRepository.save(item);
    }

    public List<Item> findAll(ItemSearchCond cond) {
        return itemRepository.findAll(cond);
    }

    public Page<Item> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return jpaItemRepository.findAll(pageable);
    }


    // itemId 에 해당하는 item 을 찾는 메서들
    public Optional<Item> findById(ItemSearchCond cond) {

        return itemRepository.findById(cond);
    }


    public List<Item > findByMemberId(Long memberId) {
     return itemRepository.findByMember_Id(memberId);
    }

    public List<Item> findByMemberIdAndItemNameContaining(long memberId, String itemName) {
        return itemRepository.findByMemberIdAndItemNameContaining(memberId, itemName);
    }

    // 페이징 메서드
    public Page<Item> getItemWithPaging(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return jpaItemRepository.findAll(pageable);
    }
}
