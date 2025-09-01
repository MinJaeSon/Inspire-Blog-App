package com.lgcns.inspire_restspring.rest.comment.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.lgcns.inspire_restspring.rest.comment.domain.CommentRequestDTO;
import com.lgcns.inspire_restspring.rest.comment.domain.CommentResponseDTO;

@Mapper
public interface CommentMapper {
    public int                          insertRow(CommentRequestDTO request);
    public List<CommentResponseDTO>     selectRow(Integer blog_id);
}
