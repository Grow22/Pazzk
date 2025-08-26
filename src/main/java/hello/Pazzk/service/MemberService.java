package hello.Pazzk.service;

//import hello.Apple.repository.MemberRepository;

import hello.Pazzk.domain.Member;
import hello.Pazzk.repository.SpringDataJpaMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class MemberService {

    private final SpringDataJpaMemberRepository memberRepository;

    // 회원가입 메서드
    public void saveMember(Member member) {
        memberRepository.save(member);
    }

    public Optional<Member> findByName(String name) {
        return memberRepository.findByName(name);
    }

    // 로그인 메서드
    public Member login(String userId, String pwd) {
        Optional<Member> member = memberRepository.findByuserId(userId);
        // 로그인 후 해당 id, pwd 가 일치 할 경우 해당 Member 를 반환
        return member.filter(m -> m.getPwd().equals(pwd))
                .orElse(null);
    }
}