package com.zxw.sso.server.controller;

import com.zxw.common.web.web.JSONResponse;
import com.zxw.sso.sdk.SsoConstant;
import com.zxw.sso.server.service.LoginService;
import com.zxw.sso.server.vo.UserForm;
import com.zxw.sso.server.vo.UserFormSequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.groups.Default;

/**
 * @author wuhongyun
 * @date 2020/5/26 16:51
 */
@Controller
@CrossOrigin
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping(value = "/login")
    @ResponseBody
    public JSONResponse login(@Validated({UserFormSequence.class, Default.class}) @RequestBody UserForm userForm) throws Exception {
        return JSONResponse.succeed(loginService.login(userForm, SsoConstant.SOURCE_WEB,null));
    }

    @RequestMapping("/test")
    @ResponseBody
    public String test() {
        return "test";
    }

}
