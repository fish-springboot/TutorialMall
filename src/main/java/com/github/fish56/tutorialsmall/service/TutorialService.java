package com.github.fish56.tutorialsmall.service;

import com.github.fish56.tutorialsmall.entity.Tutorial;

public interface TutorialService {
    /**
     * 获得一个课程信息
     * @param owner
     * @param repositoryName
     * @return
     */
    public Tutorial getTutorial(String owner, String repositoryName);

    /**
     * 创建课程
     * @param tutorial
     * @return
     */
    public Tutorial saveTutorial(Tutorial tutorial);
}
