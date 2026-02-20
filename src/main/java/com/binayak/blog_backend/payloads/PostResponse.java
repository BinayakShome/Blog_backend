package com.binayak.blog_backend.payloads;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@JsonPropertyOrder({
        "content",
        "pageNumber",
        "pageSize",
        "totalElements",
        "totalPages",
        "islastPage"
})
@NoArgsConstructor
@Getter
@Setter
public class PostResponse {
    private List<PostDto> content;
    private Integer pageNumber;
    private Integer pageSize;
    private long totalElements;
    private long totalPages;
    private boolean isLastPage;
    private String sortBy;
    private String sortDir;
}
