package com.lgcns.inspire_restspring.rest.blog.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.lgcns.inspire_restspring.rest.blog.domain.BlogResponseDTO;

@Mapper
public interface BlogMapper {
    public List<BlogResponseDTO> selectRow(); // my batis가 블로그 전체조회 목록을 리스트로 반환해줌
}
