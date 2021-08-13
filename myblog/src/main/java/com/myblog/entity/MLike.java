package com.myblog.entity;

import lombok.Data;

@Data
public class MLike {
    private Long userId;

    private Long blogId;

    private Integer liked;
}
