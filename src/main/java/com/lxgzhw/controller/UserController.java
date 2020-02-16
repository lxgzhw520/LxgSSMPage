package com.lxgzhw.controller;

import com.lxgzhw.bean.User;
import com.lxgzhw.lxgSSMUtils.bean.Page;
import com.lxgzhw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@CrossOrigin(origins = "*", allowCredentials = "true", maxAge = 3600)
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping("/all")
    public List<User> all(Model model) {
        List<User> users = userService.all();
        return users;
    }

    @ResponseBody
    @RequestMapping("/page")
    public Page<User> page() {
        //查询第1页,每页3条数据
        Page<User> page = userService.page(1, 3);
        return page;
    }
}
