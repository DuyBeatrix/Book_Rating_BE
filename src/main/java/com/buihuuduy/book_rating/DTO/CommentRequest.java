package com.buihuuduy.book_rating.DTO;

import lombok.Data;

@Data
public class CommentRequest {
    private String content;
    private Integer postId;
}

