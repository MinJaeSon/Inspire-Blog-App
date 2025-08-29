package com.lgcns.inspire_restspring;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.lgcns.inspire_restspring.rest.blog.domain.BlogRequestDTO;
import com.lgcns.inspire_restspring.rest.blog.domain.BlogResponseDTO;
import com.lgcns.inspire_restspring.rest.blog.repository.BlogMapper;

@SpringBootTest
public class BlogTddTests {
    
    @Autowired
    private BlogMapper mapper;

    // @BeforeEach
    // public void init() {
    //     // given 테스트용 데이터 사입
    //     mapper.insertRow(BlogRequestDTO.builder()
    //                         .title("스프링!!")
    //                         .content("고양ㅇ이울ㅇㅇ")
    //                         .build());
    // }

    // TDD - Red(Faling Test) : 블로그 등록 기능 단위테스트
    @Test
    public void blogInsertTest() {
        // given
        BlogRequestDTO request = BlogRequestDTO.builder()
                                        .title("야호")
                                        .content("TDD로 블로그 등록하기")
                                        .build();

        // when
        int flag = mapper.insertRow(request);
        System.out.println("[debug] >>> flag : " + flag);
        System.out.println("[debug] >>> id : " + request.getId());

        // then
        assertEquals(1, flag);
        assertNotNull(request.getId());
    }

    @Test
    public void blogUpdateTest() {
        // given
        BlogRequestDTO blog = BlogRequestDTO.builder()
                                        .id(1)
                                        .title("수정된 제목")
                                        .content("수정된 내용")
                                        .build();

        // when
        int flag = mapper.updateRow(blog);

        // then
        assertEquals(1, flag);
    }

    @Test
    public void blogListTest() {
        // given

        // when
        List<BlogResponseDTO> list = mapper.selectRow();
        int cnt = list.size();

        // then
        System.out.println(">>>> 블로그 전체 목록(총 " + cnt + "개) : ");
        list.stream()
            .forEach(System.out::println);
    }

    @Test
    public void blogDeleteTest() {
        // given
        BlogRequestDTO blog = BlogRequestDTO.builder()
                                        .id(2)
                                        .build();       
        // when
        int beforeCnt = mapper.selectRow().size();
        int flag = mapper.deleteRow(blog);
        int afterCnt = mapper.selectRow().size();

        // then
        // 삭제된 row 확인, 삭제 후 다시 조회 시 null, 전체 count 감소
        assertEquals(1, flag);
        assertNull(mapper.findById(5));
        assertEquals(beforeCnt - 1, afterCnt);
    }

    @Test
    public void blogFindByKeywordTest() {
        // given
        String keyword = "스프링";

        // when
        List<BlogResponseDTO> list = mapper.findByKeyword(keyword);

        // then
        System.out.println(">>>> 검색 결과 : ");
        list.stream()
            .forEach(System.out::println);
    }
}
