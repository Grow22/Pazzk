    package hello.Pazzk.controller;


    import hello.Pazzk.domain.Member;
    import hello.Pazzk.repository.Item;
    import hello.Pazzk.service.ItemService;
    import lombok.RequiredArgsConstructor;
    import org.springframework.data.domain.Page;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.ModelAttribute;
    import org.springframework.web.bind.annotation.RequestParam;

    @Controller
    @RequiredArgsConstructor
    public class HomeController {


        private final ItemService itemService;


        /*
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
         */
        @GetMapping("/")
        public String home(@ModelAttribute("welcomeName") String welcomeName, Model model, @RequestParam(value = "page", defaultValue = "0") int page,
                           @RequestParam(value = "size", defaultValue = "5") int size)
        {
            if(welcomeName != null) {
                model.addAttribute("welcomeName", welcomeName);
            }
            Page<Item> lists = itemService.findAll(page, size);
            model.addAttribute("lists", lists);
            model.addAttribute("member", new Member());
            System.out.println("lists = " + lists);

            return "search";
        }

    }