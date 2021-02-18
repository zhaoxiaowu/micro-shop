package com.zxw.sso.server.vo;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author wuhongyun
 * @date 2020/5/26 19:07
 */
@Getter
@Setter
public class UserForm{

    @NotBlank(message = "用户名不能为空", groups = {UserFormSequence.class})
    private String username;

    @NotBlank(message = "密码不能为空")
    @Length(min = 6, message = "密码不小于6位")
    private String password;

    private String redirectUrl;


}
