package hello.Pazzk.controller;


import hello.Pazzk.domain.BookmarkItem;
import hello.Pazzk.domain.Member;
import hello.Pazzk.repository.Item;
import hello.Pazzk.repository.ItemSearchCond;
import hello.Pazzk.repository.SpringDataJpaBookmarkRepository;
import hello.Pazzk.service.ItemService;
import hello.Pazzk.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.*;

@Controller
@Slf4j
@RequiredArgsConstructor
public class BoardController {

    private final ItemService itemService;
    private final MemberService memberService;

    private final SpringDataJpaBookmarkRepository bookmarkRepository;
    // 영상 확인 메서드
    @GetMapping("/my-videos")
    public String showMyVideos(Model model, HttpSession session) {

        // session 에서 loginMember 를 반환
        Member member = (Member)session.getAttribute("loginMember");

        // Join 시켜서 member 에 맞는 item 들을 반환
        List<Item> items = itemService.findByMemberId(member.getId());

        model.addAttribute("items", items);

        // 내 영상들 확인
        return "myitem/my-items";
    }

    // 즐겨찾기 클릭 시 해당 메서드 호출
    @PostMapping("/bookmark/{itemId}")
    public ResponseEntity<Map<String, Object>> bookmark(@PathVariable("itemId") Long itemId, HttpSession session) {

        // (1) Session 으로부터 Member 를 얻은 후 memberId 휙득
        Member member=  (Member) session.getAttribute("loginMember");

        System.out.println("itemId = " + itemId);
        // (2) itemId 로 부터 해당 item 을 get
        Optional<Item> OptionalItem = itemService.findById(new ItemSearchCond(itemId));

        Item item = OptionalItem.get();
        System.out.println("item = " + item);
        // (3) bookmarkItem 연관관계 설정 후 해당 Item 을 저장
        BookmarkItem bookmarkItem = new BookmarkItem();
        bookmarkItem.setMember(member);
        bookmarkItem.setItem(item);

        bookmarkRepository.save(bookmarkItem);

        // 성공 응답으로 간단한 JSON 객체 생성
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "즐겨찾기가 성공적으로 추가되었습니다.");


        return ResponseEntity.ok(response);
    }

    // 사용자 북마크 호출 메서드
    @GetMapping("/my-bookmark")
    public String callBookmark(Model model, HttpSession session) {

        // (1) Session 으로부터 member 휙득 후 id get
        Member member=  (Member) session.getAttribute("loginMember");
        Long memberId = member.getId();

        // (2) memberId 에 해당하는 bookmark 를 호출
        List<BookmarkItem> bookmarkItems = bookmarkRepository.findByMember_Id(memberId);

        System.out.println("bookmarkItems = " + bookmarkItems);
        List<Item> items = new ArrayList<>();
        // bookmarkItem 의 itemId 에 해당하는 객체를 items 에 저장
        for (BookmarkItem bookmarkItem : bookmarkItems) {
            Item item = itemService.findById(new ItemSearchCond(bookmarkItem.getItem().getId())).get();
            items.add(item);
        }

        model.addAttribute("items", items);

        return "myitem/my-bookmark";
    }
}