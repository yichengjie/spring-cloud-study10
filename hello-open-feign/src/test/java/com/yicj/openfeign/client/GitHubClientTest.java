package com.yicj.openfeign.client;

import feign.Feign;
import feign.gson.GsonDecoder;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author yicj
 * @date 2023年08月30日 17:27
 */
@Slf4j
public class GitHubClientTest {

    @Test
    public void hello(){
        Type type = GitHubClient.class ;

        GitHubClient github = Feign.builder()
                .decoder(new GsonDecoder())
                .target(GitHubClient.class, "https://api.github.com");
        // Fetch and print a list of the contributors to this library.
        List<GitHubClient.Contributor> contributors = github.contributors("OpenFeign", "feign");
        for (GitHubClient.Contributor contributor : contributors) {
            System.out.println(contributor.login + " (" + contributor.contributions + ")");
        }
    }

}
