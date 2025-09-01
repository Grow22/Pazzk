package hello.Pazzk.controller;

import hello.Pazzk.domain.BookmarkItem;
import hello.Pazzk.domain.Member;
import hello.Pazzk.repository.Item;
import hello.Pazzk.repository.ItemSearchCond;
import hello.Pazzk.repository.JpaLikeItemRepository;
import hello.Pazzk.repository.SpringDataJpaBookmarkRepository;
import hello.Pazzk.service.ItemService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;


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

    // Get Mapping
    // Search Method
    @GetMapping("/search")
    public String searchForm(Model model) {
        model.addAttribute("member", new Member());
        return "search";
    }

    // Post Mapping
    // Search Method
    @PostMapping("/search")
    public String search(@RequestParam(value = "keyword") String keyword, Model model, HttpSession session) {


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



}