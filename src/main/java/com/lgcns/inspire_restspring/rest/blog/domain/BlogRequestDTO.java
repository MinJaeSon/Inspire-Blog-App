package com.lgcns.inspire_restspring.rest.blog.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter // Setter가 있어야 JSON -> Object 변환(오토바인딩이 가능해진다)
public class BlogRequestDTO {
    @Schema(description = "블로그 제목", example = "스프링 2일차")
    private String title;
    @Schema(description = "블로그 내용", example = "오토바인딩이 머게")
    private String content;
}
