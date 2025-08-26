package hello.Pazzk.service;

import hello.Pazzk.repository.Item;
import hello.Pazzk.repository.ItemRepository;
import hello.Pazzk.repository.ItemSearchCond;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.internal.constraintvalidators.hv.URLValidator;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    // Url 유효성 검사 메서드
    public URLValidator urlValidator = new URLValidator();


    public Item save(Item item) {
        return itemRepository.save(item);
    }

    public List<Item> findAll(ItemSearchCond cond) {
        return itemRepository.findAll(cond);
    }


    // itemId 에 해당하는 item 을 찾는 메서들
    public Item findById(ItemSearchCond cond) {

        return itemRepository.findById(cond);
    }

}
