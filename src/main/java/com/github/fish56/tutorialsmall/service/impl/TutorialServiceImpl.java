package com.github.fish56.tutorialsmall.service.impl;

import com.github.fish56.tutorialsmall.entity.Tutorial;
import com.github.fish56.tutorialsmall.repository.TutorialCrudRepository;
import com.github.fish56.tutorialsmall.service.TutorialService;
import org.springframework.beans.factory.annotation.Autowired;

public class TutorialServiceImpl implements TutorialService {
    @Autowired
    private TutorialCrudRepository repository;

    @Override
    public Tutorial getTutorial(String owner, String repositoryName) {
        return repository.findByOwnerAndRepositoryName(owner, repositoryName);
    }

    @Override
    public Tutorial saveTutorial(Tutorial tutorial) {
        Tutorial tutorial1 = repository.findByOwnerAndRepositoryName(tutorial.getOwner(), tutorial.getRepositoryName());

        // 防止重复插入，owner + repoName 应该是唯一的
        if (tutorial1 != null) {
            return tutorial1;
        }
        return repository.save(tutorial);
    }
}
