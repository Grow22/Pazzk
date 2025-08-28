package hello.Pazzk.controller;

import hello.Pazzk.domain.LikeItem;
import hello.Pazzk.domain.Member;
import hello.Pazzk.repository.Item;
import hello.Pazzk.repository.ItemSearchCond;
import hello.Pazzk.repository.JpaLikeItemRepository;
import hello.Pazzk.service.ItemService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.Optional;


@Controller
@Slf4j
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    private final JpaLikeItemRepository likeItemRepository;

    // 글 추가 HTML 이동 메서드
    // GET Mapping
    @GetMapping("/add")
    public String moveAddForm(Model model) {
        model.addAttribute("item", new Item()); // 빈 model 객체 사용
        return "add-form";
    }

    // 글 추가 메서드
    // Post Mapping
    @PostMapping("/add")
    public String addNewItem(@ModelAttribute Item item, RedirectAttributes redirectAttributes, Model model, HttpSession session) {

        // (1) Session 에서 member 정보 가져오기
        Member member = (Member)session.getAttribute("loginMember");
        System.out.println("member = " + member);

        // (2) 사용자가 입력한 url -> fullUrl 로 변환 후 저장
        String fullUrl = "https://chzzk.naver.com/clip/" + item.getVideoId();
        item.setUrl(fullUrl);

        // (3) item 의 연관관계 설정 후 저장
        item.setMember(member);
        Item savedItem = itemService.save(item);

        return "redirect:/";
    }

    // 좋아요 메서드
    @PostMapping("/likes/{itemId}")
    public ResponseEntity<Item> plusLike(@PathVariable("itemId") Long itemId, HttpSession session) {

        Member member = (Member) session.getAttribute("loginMember");

        System.out.println("plusLike 메서드 호출");

        // itemId 에 해당하는 item 을 find
        Item item =itemService.findById(new ItemSearchCond(itemId)).get();

        Optional<LikeItem> optionalLikeItem = likeItemRepository.findByMemberIdAndItemId(member.getId(), itemId);
        System.out.println("optionalLikeItem = " + optionalLikeItem);
        // 만약 좋아요를 누른 Item 의 좋아요 수가 0 일 경우 ==> LikeItem 객체가 존재하지 않을 경우
        if(optionalLikeItem.isEmpty()) {
            System.out.println("첫 번째 if 문 호출");
            // 좋아요를 증가 후 Item 저장
            item.likePlus();
            itemService.save(item);

            // 리팩토링 필요 -> 연관관계 설정 부분
            LikeItem likeItem = new LikeItem();
            likeItem.setDate(new Date());
            likeItem.setItem(itemService.findById(new ItemSearchCond(itemId)).get());
            likeItem.setMember(member);
            System.out.println("likeItem = " + likeItem);
            likeItemRepository.save(likeItem);
            return new ResponseEntity<>(item, HttpStatus.OK);
        }
        // 만약 좋아요를 누른 Item 의 날짜가 오늘과 같을 시 증가 xx
        else if(optionalLikeItem.get().getDate().equals(new Date())) {
            System.out.println("두 번째 if 문 호출");
            System.out.println("오늘 날짜와 같습니다.");
            return new ResponseEntity<>(item, HttpStatus.OK);
        }

        System.out.println("if 문 밖 호출");
        // 객체가 존재하고 오늘 날짜와 같지 않은 경우 -> 좋아요 증가
        item.likePlus();
        itemService.save(item);

        // 해당 item 의 날짜를 오늘로 변경
        LikeItem likeItem = likeItemRepository.findByMemberIdAndItemId(member.getId(), itemId).get();
        likeItem.setDate(new Date());

        // 결과 반환
        return new ResponseEntity<>(item, HttpStatus.OK);
    }
}