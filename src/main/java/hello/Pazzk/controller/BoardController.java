package hello.Pazzk.controller;


import hello.Pazzk.domain.Member;
import hello.Pazzk.repository.Item;
import hello.Pazzk.repository.ItemSearchCond;
import hello.Pazzk.service.ItemService;
import hello.Pazzk.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class BoardController {

    private final ItemService itemService;
    private final MemberService memberService;
    // 영상 확인 메서드
    @GetMapping("/my-videos")
    public void showMyVideos(Model model, HttpSession session) {

        // session 에서 loginMember 를 반환
        Member member = (Member)session.getAttribute("loginMember");

        // Join 시켜서 member 에 맞는 item 들을 반환
        //List<Item> items
    }
}
