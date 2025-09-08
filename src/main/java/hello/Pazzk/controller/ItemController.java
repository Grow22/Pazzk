package hello.Pazzk.controller;

import hello.Pazzk.domain.BookmarkItem;
import hello.Pazzk.domain.Member;
import hello.Pazzk.repository.Item;
import hello.Pazzk.repository.JpaLikeItemRepository;
import hello.Pazzk.repository.SpringDataJpaBookmarkRepository;
import hello.Pazzk.service.ItemService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@Slf4j
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    private final JpaLikeItemRepository likeItemRepository;

    private final SpringDataJpaBookmarkRepository bookmarkRepository;

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

    // 검색 페이지 로드 (GET)와 검색 요청 (GET)을 모두 처리하는 단일 메서드
    @GetMapping("/search")
    public String search(@RequestParam(value = "keyword", required = false) String keyword,
                         @RequestParam(value = "page", defaultValue = "0") int page,
                         @RequestParam(value = "sizes", defaultValue = "5") int size,
                         Model model, HttpSession session) {

        System.out.println("메서드 호출");
        Page<Item> lists;

        Member member = (Member) session.getAttribute("loginMember");

        // 로그인하지 않은 상태일 시
        if(member == null) {
            model.addAttribute("member", new Member());
        }
        // 로그인한 상태일 시 session 영역의 member 를 add
        else {
            model.addAttribute("member", member);
        }
        Pageable pageable = PageRequest.of(page, size);

        // keyword 파라미터가 있고, 비어있지 않은 경우
        if (keyword != null && !keyword.isEmpty()) {
            if (member != null) {
                // 로그인 상태 + 키워드 검색
                System.out.println("이름 + 멤버 존재");
                Page<BookmarkItem> bookmarkItems = bookmarkRepository.findByMember_IdAndItem_ItemNameContaining(pageable, member.getId(), keyword);
                lists = bookmarkItems.map(BookmarkItem::getItem);
            } else {
                // 비로그인 상태 + 키워드 검색
                // itemService의 findAll 메서드가 Pageable을 받도록 수정되어야 합니다.
                //System.out.println("삐로그인 상태");
                lists = itemService.getItemWithPagingItemNameContaining(page, size, keyword);
            }
            model.addAttribute("keyword", keyword); // 검색어 유지
        } else {
            // 최초 페이지 진입 또는 키워드 없이 검색 버튼을 누른 경우
            lists = itemService.getItemWithPagingItemNameContaining(page ,size, keyword); // 전체 아이템을 페이징하여 반환
        }

        System.out.println("lists = " + lists);
        model.addAttribute("lists", lists);

        return "search";
    }

    /*
    // Post Mapping
    // Search Method
    @PostMapping("/search")
    public String search(@RequestParam(value = "keyword") String keyword,
                         @RequestParam(value = "page", defaultValue = "0") int page,
                         @RequestParam(value = "sizes", defaultValue = "5") int size, Model model, HttpSession session) {

        List<Item> lists = new ArrayList<>();
        // (1) 로그인한 경우일 경우 해당 member 를 얻은 후
        // member 와 keyword 에 맞는 items 를 추출
        Member member = (Member) session.getAttribute("loginMember");
        if(member != null && keyword != null) {
            List<BookmarkItem> bookmarkItems = bookmarkRepository.findByMember_IdAndItem_ItemNameContaining(member.getId(), keyword);
            for (BookmarkItem bookmarkItem : bookmarkItems) {
                lists.add(bookmarkItem.getItem());
            }
        }
        // (2) member 가 없는 경우 keyword 만 사용하여 결과 반환
        else {
            lists = itemService.findAll(new ItemSearchCond(keyword));
        }
        System.out.println("lists = " + lists);

        model.addAttribute("lists", lists);
        model.addAttribute("member", new Member());
        return "search";
    }
     */

/*
    @PostMapping("/search")
    public String search(@RequestParam(value = "keyword") String keyword,
                         @RequestParam(value = "page", defaultValue = "0") int page,
                         @RequestParam(value = "sizes", defaultValue = "5") int size, Model model, HttpSession session) {

        System.out.println("메서드 호출");
        Page<Item> lists;
        // (1) 로그인한 경우일 경우 해당 member 를 얻은 후
        // member 와 keyword 에 맞는 items 를 추출
        Member member = (Member) session.getAttribute("loginMember");

        Pageable pageable = PageRequest.of(page, size);
        if(member != null && keyword != null) {
            Page<BookmarkItem> bookmarkItems = bookmarkRepository.findByMember_IdAndItem_ItemNameContaining(pageable, member.getId(), keyword);
            lists = bookmarkItems.map(BookmarkItem::getItem);
        }
        // (2) member 가 없는 경우 keyword 만 사용하여 결과 반환
        else {
            lists = itemService.findAll(page,size);
        }

        System.out.println("lists = " + lists);
        model.addAttribute("lists", lists);
        model.addAttribute("member", new Member());
        return "search";
    }


 */
}