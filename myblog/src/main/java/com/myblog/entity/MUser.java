package com.myblog.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.Email;
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
public class MUser implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    @NotBlank(message = "用户名不能为空")
    private String username;

    private String avatar;
    @Email(message = "邮箱格式不正确")
    private String email;

    @NotBlank(message = "密码不能为空")
    private String password;

    private Integer status;

    private LocalDateTime created;

    private LocalDateTime lastLogin;


}
