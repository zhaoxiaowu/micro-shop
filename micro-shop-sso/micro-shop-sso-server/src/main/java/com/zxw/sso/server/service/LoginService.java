package com.zxw.sso.server.service;

import com.zxw.sso.server.vo.TokenInfo;
import com.zxw.sso.server.vo.UserForm;
import me.zhyd.oauth.model.AuthCallback;

/**
 * @author wuhongyun
 * @date 2020/5/26 16:58
 */
public interface LoginService {

    /**
     * 登录功能
     *
     * @param userForm
     * @param callback
     *
     * @return 生成的JWT的token
     */
    TokenInfo login(UserForm userForm,String source, AuthCallback callback) throws Exception;
}
