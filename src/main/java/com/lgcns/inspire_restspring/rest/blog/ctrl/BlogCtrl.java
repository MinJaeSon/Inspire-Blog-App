package com.lgcns.inspire_restspring.rest.blog.ctrl;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lgcns.inspire_restspring.rest.blog.domain.BlogRequestDTO;
import com.lgcns.inspire_restspring.rest.blog.domain.BlogResponseDTO;
import com.lgcns.inspire_restspring.rest.blog.service.BlogService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;


/*
HTTP 통신 client가 웹서버(WAS-tomcat)에게 요청의 목적을 알리는 수단
- Get(읽기), Post(새로운 자원 생성), Put(전체 수정), Patch(일부 수정), Delete(삭제)

요청 - 응답 템플릿
- RequestEntity, ResponseEntity
 */

@RestController
@RequestMapping("/api/v1/blog")
@Tag(name = "Blog API", description = "블로그 관련 API 명세서")
public class BlogCtrl {
    @Autowired
    private BlogService service;

    @Operation(
        summary = "블로그 전체 조회", 
        description = "블로그 전체 조회 API"
    )
    @ApiResponses(
        {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "조회 실패")
        }
    )
    @GetMapping("/blogs")
    public ResponseEntity<List<BlogResponseDTO>> blogs() {
        System.out.println("[debug] >>> blog ctrl path: /blogs ");
        // String response = "응답문자열";
        // Map<String, String> map = new HashMap<>();
        // map.put("id", "jslim");
        // map.put("email", "jslim9413@naver.com");

        List<BlogResponseDTO> list = service.select();

        return new ResponseEntity<List<BlogResponseDTO>>(list, HttpStatus.OK);
    }
    
    // @Operation(
    //     summary = "블로그 등록", 
    //     description = "제목과 내용을 입력 받아 블로그를 등록하는 API"
    // )
    // @PostMapping("/register")
    // public ResponseEntity<Void> register(
    //     @Parameter(description = "블로그 제목, 내용 입력")    
    //     @RequestParam("title")      String title,
    //     @RequestParam("content")    String content) {
    //     System.out.println("[debug] >>> blog ctrl path POST : /register ");
    //     System.out.println("[debug] >>> title   = " + title);
    //     System.out.println("[debug] >>> content = " + content);
        
    //     return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 : 처리 성공, 응답 본문 없음 (POST라서 반환할 내용 없으므로)
    // }
    
    @PostMapping("/register")
    public ResponseEntity<Void> register(
        @RequestBody(description = "생성할 블로그 정보를 전달 받음")
        @org.springframework.web.bind.annotation.RequestBody BlogRequestDTO request) { // parameter를 DTO 객체로 받는 방법 -> json으로 들어오는걸 스프링이 자동으로 매핑해줌(오토바인딩). 일반적으로 이 방법을 많이 씀

        System.out.println("[debug] >>> blog ctrl path POST : /register ");
        System.out.println("[debug] >>> param dto   = " + request);

        int flag = service.insert(request);
        if (flag == 1) {
            // return ResponseEntity.status(HttpStatus.CREATED).body(null); // body 없이 이렇게 반환도 가능
            return new ResponseEntity<>(HttpStatus.CREATED); // 201 : 생성 성공
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<BlogResponseDTO> read(@PathVariable("id") Integer id) {
        System.out.println("[debug] >>> blog ctrl path GET : /read/ ");
        System.out.println("[debug] param is = " + id);
        // BlogResponseDTO response = BlogResponseDTO.builder()
        //                                         .id(id)
        //                                         .title("블로그 제목 ")
        //                                         .content("블로그 내용 ")
        //                                         .build();

        BlogResponseDTO response = service.findById(id);
        if (response != null) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    // path value와 request body를 동시에 받는 방법
    @PutMapping("/update/{id}")
    public ResponseEntity<Void> update(
        @PathVariable("id") Integer id,
        @org.springframework.web.bind.annotation.RequestBody BlogRequestDTO request) {
        
        System.out.println("[debug] >>> blog ctrl path PUT : /update ");
        System.out.println("[debug] >>> param is    = " + id);
        System.out.println("[debug] >>> param dto   = " + request);

        int flag = service.update(id, request);
        if (flag == 1) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        System.out.println("[debug] >>> blog ctrl path DELETE : /delete/ ");
        System.out.println("[debug] param is = " + id);

        int flag = service.delete(id);
        if (flag == 1) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<BlogResponseDTO>> search(@RequestParam("keyword") String keyword) { // path variable 방식도 가능은 한데, 검색어니까 파라미터로 합시더
        System.out.println("[debug] >>> blog ctrl path GET : /search ");
        System.out.println("[debug] param is = " + keyword);

        List<BlogResponseDTO> list = service.findByKeyword(keyword);
        if (list != null && list.size() > 0) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
