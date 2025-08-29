package hello.Pazzk;

import hello.Pazzk.repository.ItemRepository;
import hello.Pazzk.repository.SpringDataJpaMemberRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PazzkApplication {

	public static void main(String[] args) {
		SpringApplication.run(PazzkApplication.class, args);
	}

	// TestDataInit 스프링 빈 등록
	@Bean
	public TestDataInit testDataInit(ItemRepository itemRepository, SpringDataJpaMemberRepository memberRepository) {
		System.out.println("테스트 호출");
		return new TestDataInit(itemRepository, memberRepository);
	}
}
