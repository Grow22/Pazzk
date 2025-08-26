package hello.Pazzk.controller;

import hello.Pazzk.domain.Member;
import hello.Pazzk.repository.Item;
import hello.Pazzk.service.ItemService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@Slf4j
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

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
}