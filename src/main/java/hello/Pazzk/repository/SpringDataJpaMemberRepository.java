package hello.Pazzk.repository;


import hello.Pazzk.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long> {

    // Name 을 통한 find 메서드
    Optional<Member> findByName(String name);

    Optional<Member> findByuserId(String userId);

    // memberId 를 통해 Item 들 반환

}