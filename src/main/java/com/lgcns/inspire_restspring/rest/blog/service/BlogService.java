package com.lgcns.inspire_restspring.rest.blog.service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.lgcns.inspire_restspring.rest.blog.ctrl.BlogCtrl;
import com.lgcns.inspire_restspring.rest.blog.domain.BlogRequestDTO;
import com.lgcns.inspire_restspring.rest.blog.domain.BlogResponseDTO;
import com.lgcns.inspire_restspring.rest.blog.repository.BlogMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor // final이 붙은 멤버변수에 대한 생성자를 자동으로 만들어준다.
public class BlogService {
    
    private final BlogMapper mapper; // 의존성을 주입하는 또 다른 방법 (final + @RequiredArgsConstructor). 근데 사실 걍 @Autowired 쓰는게 나음

    public List<BlogResponseDTO> select() {
        System.out.println("[debug] >>> blog service select ");
        return mapper.selectRow();
    }

    public int insert(BlogRequestDTO request) {
        System.out.println("[debug] >>> blog service insert ");
        return mapper.insertRow(request);
    }

    // public int update(BlogRequestDTO request) {
    //     System.out.println("[debug] >>> blog service update ");
    //     return mapper.updateRow(request);
    // }

    public int update(Integer id, BlogRequestDTO request) {
        System.out.println("[debug] >>> blog service update ");
        // id를 path variable로 받아서 request에 세팅 
        // -> id는 리소스 식별자이므로 request body로 받지 않고 path variable로 받는게 RESTful API 설계 원칙에 더 맞음 (MyBatis의 매퍼가 하나의 객체만 매개변수로 받기 때문도 있음)
        request.setId(id); // request dto에서 id는 body로 프론트에게 받지 않으므로 path variable로 받은 값으로 직접 세팅
        return mapper.updateRow(request); 
    }

    public BlogResponseDTO findById(Integer id) {
        System.out.println("[debug] >>> blog service findById ");
        return mapper.findById(id);
    }

    public int delete(Integer id) {
        System.out.println("[debug] >>> blog service delete ");
        return mapper.deleteRow(BlogRequestDTO.builder().id(id).build());
    }

    public List<BlogResponseDTO> findByKeyword(String keyword) {
        System.out.println("[debug] >>> blog service findByKeyword ");
        return mapper.findByKeyword(keyword);
    }
}
