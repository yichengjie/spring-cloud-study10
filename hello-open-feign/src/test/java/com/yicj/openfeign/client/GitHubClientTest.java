package com.yicj.openfeign.client;

import feign.Feign;
import feign.gson.GsonDecoder;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import java.util.List;

/**
 * @author yicj
 * @date 2023年08月30日 17:27
 */
@Slf4j
public class GitHubClientTest {

    @Test
    public void hello(){
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
