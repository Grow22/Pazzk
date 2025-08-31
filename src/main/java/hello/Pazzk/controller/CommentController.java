package hello.Pazzk.controller;


import hello.Pazzk.domain.Comment;
import hello.Pazzk.domain.CommentDto;
import hello.Pazzk.domain.Member;
import hello.Pazzk.repository.SpringDataJpaCommentRepository;
import hello.Pazzk.repository.SpringDataJpaItemRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;
import java.util.List;

/*
    댓글 관리 컨트롤러
 */
@Controller
@Slf4j
@RequiredArgsConstructor
public class CommentController {

    private final SpringDataJpaCommentRepository commentRepository;

    private final SpringDataJpaItemRepository itemRepository;

    // 댓글 추가 컨트롤러
    @PostMapping("comments/add")
    public ResponseEntity<Comment> addComment(@RequestBody CommentDto commentDto, HttpSession session) {

        Member member = (Member) session.getAttribute("loginMember");
        Long itemId = commentDto.getItemId();
        System.out.println("commentDto = " + commentDto);

        // (1) comment 의 연관관계 설정 후 repo 에 해당 comment 저장
        Comment comment = new Comment();
        comment.setMember(member);
        comment.setText(commentDto.getText());
        comment.setItem(itemRepository.findById(itemId).get());
        comment.setTimeStamp(new Date());
        Comment svaedComment = commentRepository.save(comment);

        // (2) 저장된 댓글 반환
        return new ResponseEntity<>(svaedComment, HttpStatus.OK);
    }

    // 댓글 호출 컨트롤러
    @GetMapping("/comments/{itemId}")
    public ResponseEntity<List<Comment>> showComments(HttpSession session, @PathVariable("itemId") long itemId) {

        Member member = (Member) session.getAttribute("loginMember");

        // memberId 에 맞는 댓글 반환
        List<Comment> comments = commentRepository.findByItemId(itemId);

        for (Comment comment : comments) {
            log.info("comment={}", comment);
        }
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }
}
