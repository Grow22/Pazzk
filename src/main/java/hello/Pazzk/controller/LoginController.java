package hello.Pazzk.controller;


import hello.Pazzk.domain.Member;
import hello.Pazzk.repository.Item;
import hello.Pazzk.repository.ItemRepository;
import hello.Pazzk.repository.ItemSearchCond;
import hello.Pazzk.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class LoginController {

    private final ItemRepository itemRepository;

    private final MemberService memberService;
    @PostMapping("/login")
    public String login(RedirectAttributes redirectAttributes, Model model, Member member, HttpServletRequest request) {

        Member loginMember = memberService.login(member.getUserId(), member.getPwd());

        // 로그인 실패 시
        if (loginMember == null) {
            redirectAttributes.addFlashAttribute("error", "아이디 비밀번호를 다시 입력해주세요.");
            return "redirect:/";
        }

        // 로그인 성공 시
        // 1. 세션에 로그인 사용자 정보를 저장
        HttpSession session = request.getSession();
        session.setAttribute("loginMember", loginMember);

        // 2. 메인 페이지로 리다이렉트 (여기서 flash attribute를 통해 환영 메시지를 전달)
        redirectAttributes.addFlashAttribute("welcomeName", loginMember.getName());
        return "redirect:/";
    }


    // 로그아웃 메서드
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {

        // 세션 존재 시 해당 세션을 get( 없으면 null 반환)
        HttpSession session = request.getSession(false);

        // 세션이 존재하는 경우 제거
        if(session != null) {
            session.invalidate();
        }

        // 홈으로 리다이렉트
        return "redirect:/";
    }
}

