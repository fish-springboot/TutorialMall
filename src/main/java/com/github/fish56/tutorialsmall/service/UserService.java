package com.github.fish56.tutorialsmall.service;

import com.github.fish56.tutorialsmall.entity.User;

public interface UserService {
    /**
     * 创建用户
     * @param user
     * @return
     */
    public User postUser(User user);

    /**
     * 查询用户是否在数据库中存在
     * @param user
     * @return
     */
    public boolean checkoutUser(User user);

    public User findByToken(String token);
}
