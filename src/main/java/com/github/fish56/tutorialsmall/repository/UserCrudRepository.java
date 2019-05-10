package com.github.fish56.tutorialsmall.repository;

import com.github.fish56.tutorialsmall.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserCrudRepository extends CrudRepository<User, Integer> {
    /**
     * 通过登录名来查询一个用户
     * @param login 登录名
     * @return 用户信息
     */
    public User findByLogin(String login);

    /**
     * 通过token来查询一个用户
     * @param token 用户的GitHub Token
     * @return 用户信息
     */
    public User findByToken(String token);
}
