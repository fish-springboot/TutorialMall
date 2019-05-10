package com.github.fish56.tutorialsmall.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * 订单实体
 *   名称不能和数据库的保留字冲突
 */
@Entity(name = "order_info")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    // 对应的用户
    @OneToOne
    private User user;

    // 对应的教程
    @OneToOne
    @Basic(fetch = FetchType.LAZY)
    private Tutorial tutorial;

    // 购买时实际字符的金额
    private Integer money;

    // 支付平台下发的订单号
    private String paymentId;

    // 二维码地址
    private String qrcode;

    // 支付状态
    private Boolean isPaid = false;
}
