package com.lgcns.inspire_restspring.rest.comment.ctrl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lgcns.inspire_restspring.rest.comment.domain.CommentRequestDTO;
import com.lgcns.inspire_restspring.rest.comment.service.CommentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;

@RestController
@RequestMapping("/api/v1/blog/comment")
@Tag(name = "Comment API", description = "블로그 댓글 관련 API 명세서")
public class CommentCtrl {

    @Resource(name = "commentService") // Bean(Service 구현체)이 여러 개인 경우 @Autowired는 어떤 걸 써야 할지 모르기 때문에 @Resource로 특정 이름을 지정
    private CommentService service;

    @Operation(
        summary = "블로그 댓글 입력", 
        description = "블로그 댓글을 등록"
    )
    @PostMapping("/register")
    public ResponseEntity<Void> register(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "생성할 댓글 정보를 전달 받음")
        @org.springframework.web.bind.annotation.RequestBody CommentRequestDTO request) {
        
        System.out.println("[debug] >>> blog comment ctrl path POST : /register ");
        System.out.println("[debug] param dto = " + request);

        int result = service.insertComment(request);
        if (result == 1) {
            return ResponseEntity.status(HttpStatus.CREATED).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
