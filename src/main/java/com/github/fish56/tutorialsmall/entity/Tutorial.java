package com.github.fish56.tutorialsmall.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Tutorial {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    // 教程对应的厂库的owner，我以后可能不仅仅使用fish56
    private String owner;

    private String repositoryName;

    // 单位：分
    private Integer price;

    // 教程的描述信息
    private String description;
}
