package com.lgcns.inspire_restspring.rest.blog.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lgcns.inspire_restspring.rest.blog.domain.BlogResponseDTO;
import com.lgcns.inspire_restspring.rest.blog.repository.BlogMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor // final이 붙은 멤버변수에 대한 생성자를 자동으로 만들어준다.
public class BlogService {
    
    private final BlogMapper mapper; // 의존성을 주입하는 또 다른 방법 (final + @RequiredArgsConstructor)

    public List<BlogResponseDTO> select() {
        System.out.println("[debug] >>> blog service select ");
        return mapper.selectRow();
    }
}
