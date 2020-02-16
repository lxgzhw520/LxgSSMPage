package com.lxgzhw.service;

import com.lxgzhw.bean.User;
import com.lxgzhw.lxgSSMUtils.bean.Page;

import java.util.List;

public interface UserService {
    List<User> all();
    Page<User> page(int currentPage,int rows);
}
