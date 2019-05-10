package com.github.fish56.tutorialsmall.repository;

import com.github.fish56.tutorialsmall.entity.Tutorial;
import org.springframework.data.repository.CrudRepository;

public interface TutorialCrudRepository extends CrudRepository<Tutorial, Integer> {
    /**
     * 禁止Controller层直接调用save方法，
     *   一个用户
     */

    /**
     * 通过owner 以及 repository 查询一个课程信息
     * @param owner
     * @param repositoryName
     * @return
     */
    public Tutorial findByOwnerAndRepositoryName(String owner, String repositoryName);
}
