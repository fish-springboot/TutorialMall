package com.github.fish56.tutorialsmall.util;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GitHubUtil {
    public static boolean inviteCollaborator(String owner,String repoName, String username){
        String url = "https://api.github.com/repos/" + owner +
                "/" + repoName + "/collaborators/" + username + "?permission=pull";
        try {
            HttpResponse response = Unirest.put(url)
                    .header("Authorization", "bearer " + "7b5edba753bf8b416ab98b5fe14493c22420191a")
                    .asString();
            if(response.getStatus() < 299){
                return true;
            } else {
                return false;
            }
        } catch (Exception e){
            log.error(e.getMessage());
            return false;
        }
    }
}
