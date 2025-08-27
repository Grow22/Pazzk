package hello.Pazzk;

import hello.Pazzk.repository.Item;
import hello.Pazzk.repository.ItemRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@RequiredArgsConstructor
@Slf4j
public class TestDataInit {


    private final ItemRepository itemRepository;

    /**
     * 확인용 초기 데이터 추가
     */
    @EventListener(ApplicationReadyEvent.class)
    public void initData() {
        log.info("test data init");


        itemRepository.save(new Item("네이버","http://youtube.com/watch?v=U3BJyWSSbro&t=28s","Q7st4TpqDA"));
        itemRepository.save((new Item("인프런","http://youtube.com/watch?v=1Lhfcz9MnmQ/" ,"lOMDkzvzcn")));

    }
}