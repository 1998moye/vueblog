package com.myblog.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 
 * </p>
 *
 * @author jobob
 * @since 2021-07-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString
public class MBlog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long userId;

    @NotBlank(message = "标题不能为空")
    private String title;

    private String description;

    private String content;

    private LocalDateTime created;

    private Integer status;

    private String cover;

    private Long scans;

    private Long likes;

}
