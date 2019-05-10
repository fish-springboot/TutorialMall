package com.github.fish56.tutorialsmall.repository;

import com.github.fish56.tutorialsmall.entity.Order;
import com.github.fish56.tutorialsmall.entity.Tutorial;
import com.github.fish56.tutorialsmall.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

// 客户端需要保证唯一性
public interface OrderCrudRepository extends CrudRepository<Order, Integer> {
    /**
     * 查询某个用户是否购买过某个商品
     * @param user 当前用户
     * @param tutorial 目标课程
     * @return
     *   - null： 未发现订单
     */
    public Order findByUserAndTutorial(User user, Tutorial tutorial);

    /**
     * 查询某个用户购买的课程
     * @param user
     * @return
     */
    public List<Order> findByUser(User user);
}
