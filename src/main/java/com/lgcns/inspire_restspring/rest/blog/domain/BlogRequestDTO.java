package com.lgcns.inspire_restspring.rest.blog.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@ToString
@Setter // Setter가 있어야 JSON -> Object 변환(오토바인딩이 가능해진다)
@Getter // BlogMapper에서 getTitle(), getContent()를 사용하기 위해 필요
public class BlogRequestDTO {

    @Schema(description = "블로그 식별자", example = "1")
    private Integer id;

    @Schema(description = "블로그 제목", example = "스프링 2일차")
    private String title;

    @Schema(description = "블로그 내용", example = "오토바인딩이 머게")
    private String content;
}
