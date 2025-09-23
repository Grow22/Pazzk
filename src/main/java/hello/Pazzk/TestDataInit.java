package hello.Pazzk;

import hello.Pazzk.domain.Member;
import hello.Pazzk.repository.Item;
import hello.Pazzk.repository.ItemRepository;
import hello.Pazzk.repository.SpringDataJpaMemberRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@RequiredArgsConstructor
@Slf4j
public class TestDataInit {


    private final ItemRepository itemRepository;
    private final SpringDataJpaMemberRepository memberRepository;
    /**
     * 확인용 초기 데이터 추가
     */
    @EventListener(ApplicationReadyEvent.class)
    public void initData() {
        log.info("test data init");


        itemRepository.save(new Item("ㅋㅋ","http://youtube.com/watch?v=U3BJyWSSbro&t=28s","Q7st4TpqDA", 50L));
        itemRepository.save((new Item("레전드","http://youtube.com/watch?v=1Lhfcz9MnmQ/" ,"lOMDkzvzcn", 30L)));
        itemRepository.save(new Item("안녕","https://chzzk.naver.com/clips/9q0NNG2y1u","9q0NNG2y1u", 2L));
        itemRepository.save(new Item("테스트야", "https://chzzk.naver.com/clips/jWqYLuUzFW","jWqYLuUzFW", 25L));
        itemRepository.save(new Item("리소라", "https://chzzk.naver.com/clips/uSjrkATADP", "uSjrkATADP", 60L));
        //itemRepository.save(new Item("두 번째 페이지로","https://chzzk.naver.com/clips/w8NU6CR8YT","w8NU6CR8YT"));
        memberRepository.save(new Member("장성준", "123", "123"));
    }
}