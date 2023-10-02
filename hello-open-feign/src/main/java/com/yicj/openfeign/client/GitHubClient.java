package com.yicj.openfeign.client;

import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.List;

/**
 * @author yicj
 * @date 2023年08月30日 17:26
 */
public interface GitHubClient {

    @RequestLine("GET /repos/{owner}/{repo}/contributors")
    List<Contributor> contributors(@Param("owner") String owner, @Param("repo") String repo);

    @RequestLine("POST /repos/{owner}/{repo}/issues")
    void createIssue(Issue issue, @Param("owner") String owner, @Param("repo") String repo);


    public static class Contributor {
        String login;
        int contributions;
    }

    public static class Issue {
        String title;
        String body;
        List<String> assignees;
        int milestone;
        List<String> labels;
    }
}
