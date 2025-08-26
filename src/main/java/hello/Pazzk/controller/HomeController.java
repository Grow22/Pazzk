    package hello.Pazzk.controller;


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
    import org.springframework.web.servlet.mvc.support.RedirectAttributes;

    import java.util.ArrayList;
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

            model.addAttribute("lists", lists);
            model.addAttribute("member", new Member());
            return "search";
        }




        @PostMapping("/likes/{itemId}")
        public ResponseEntity<Item> plusLike(@PathVariable Long itemId) {

            System.out.println("plusLike 메서드 호출");

            // itemId 에 해당하는 item 을 find
            Item item =itemService.findById(new ItemSearchCond(itemId));


            // 좋아요를 증가
            //item.likePlus();

            //itemService.save(item);
            // 결과 반환
            return new ResponseEntity<>(item, HttpStatus.OK);
        }
    }