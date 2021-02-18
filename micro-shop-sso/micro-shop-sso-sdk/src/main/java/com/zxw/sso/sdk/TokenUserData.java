package com.zxw.sso.sdk;

import lombok.Getter;
import lombok.Setter;

/**
 * @author wuhongyun
 * @date 2020/5/29 3:32
 */
@Getter
@Setter
public class TokenUserData {
    /**
     * 用户名
     */
    private String username;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 来源
     */
    private String source;
    /**
     * jwt中的jwtId
     */
    private String jwtId;
}
