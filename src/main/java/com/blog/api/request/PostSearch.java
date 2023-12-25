package com.blog.api.request;

import lombok.Builder;
import lombok.Data;

@Data
public class PostSearch {

    private int page;
    private int size;

    public PostSearch() {
    }

    @Builder
    public PostSearch(Integer page, Integer size) {
        this.page = page == null ? 1 : page;
        this.size = size == null ? 5 : size;
    }
}
