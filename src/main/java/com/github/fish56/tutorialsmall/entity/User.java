package com.github.fish56.tutorialsmall.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    // 这里对应github的对每个用户的标识符，比如 "login": "bitfishxyz",
    //   命名有点奇葩，但是跟着github走吧
    @Column(unique = true, nullable = false)
    private String login;

    // 对接github的token
    private String token;
}
