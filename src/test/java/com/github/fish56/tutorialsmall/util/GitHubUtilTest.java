package com.github.fish56.tutorialsmall.util;

import com.github.fish56.tutorialsmall.TutorialsMallApplicationTests;
import org.junit.Test;

import static org.junit.Assert.*;

public class GitHubUtilTest  {

    @Test
    public void inviteCollaborator() {
        boolean res = GitHubUtil.inviteCollaborator(
                "bitfishxyz", "dd", "saltfish666");
        System.out.println(res);
    }
}