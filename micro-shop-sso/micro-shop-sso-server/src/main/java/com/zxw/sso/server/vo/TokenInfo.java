package com.zxw.sso.server.vo;

import lombok.Data;

/**
 * @author wuhongyun
 * @date 2020/5/29 19:21
 */
@Data
public class TokenInfo {
    private String jwt;
    private String redirectUrl;
}
