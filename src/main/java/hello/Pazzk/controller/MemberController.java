package hello.Pazzk.controller;


import hello.Pazzk.domain.Member;
import hello.Pazzk.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/member")
@Slf4j
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 회원가입 메서드
    // 회원가입 폼을 보여주는 메서드
    @GetMapping("/add")
    public String addForm(Model model) {

        model.addAttribute("member", new Member());
        return "member/add-member";
    }

    @PostMapping("/add")
    @Transactional
    public String addMemberForm(@Validated Member member, BindingResult bindingResult,RedirectAttributes redirectAttributes) {


        // 검증에 실패할 경우
        if(bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "member/add-member";
        }

        // 이름이 중복되는 경우 저장 xx
        if (memberService.findByName(member.getName()).isPresent()) {
            log.info("이미 중복되는 이름이 존재합니다: {}", member.getName());
            // addFlashAttribute로 수정
            redirectAttributes.addFlashAttribute("error", "이미 존재하는 이름입니다.");
            redirectAttributes.addFlashAttribute("member", member); // 입력했던 값 유지
            return "redirect:/member/add";
        }
        redirectAttributes.addAttribute("member", new Member());
        System.out.println("MemberController 호출");
        memberService.saveMember(member);
        // 이름이 같은 데이터가 있는 경우 다시 입력

        redirectAttributes.addAttribute("welcomeName", member.getName());
        // 검색 홈으로 이동
        return "redirect:/";
    }


    // 로그인 메서드

    @PostMapping("/login")
    public void login() {

    }
}