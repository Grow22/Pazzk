    package hello.Pazzk.controller;


    import hello.Pazzk.domain.LikeItem;
    import hello.Pazzk.domain.Member;
    import hello.Pazzk.repository.Item;
    import hello.Pazzk.repository.ItemSearchCond;
    import hello.Pazzk.service.ItemService;
    import lombok.RequiredArgsConstructor;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.*;

    import java.util.Date;
    import java.util.List;

    @Controller
    @RequiredArgsConstructor
    public class HomeController {


        private final ItemService itemService;

        // home 메서드
        @GetMapping("/")
        public String home(@ModelAttribute("welcomeName") String welcomeName, Model model)
        {
            if(welcomeName != null) {
                model.addAttribute("welcomeName", welcomeName);
            }
            List<Item> lists = itemService.findAll(new ItemSearchCond());
            model.addAttribute("lists", lists);
            model.addAttribute("member", new Member());
            System.out.println("lists = " + lists);
            return "search";
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
        public String search(@RequestParam(value = "keyword") String keyword, Model model) {

            // 검색 이름(keyword) 에 맞는 결과들 반환
            List<Item> lists = itemService.findAll(new ItemSearchCond(keyword));
            System.out.println("lists = " + lists);
            model.addAttribute("lists", lists);
            model.addAttribute("member", new Member());
            return "search";
        }





    }