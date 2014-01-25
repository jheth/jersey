package com.hethmonster.service;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;

import org.junit.Before;
import org.junit.Test;
import org.kohsuke.github.GHMyself;
import org.kohsuke.github.GHPullRequest;

import com.hethmonster.service.GithubService;
import com.hethmonster.service.util.RepoCountPair;

public class GithubServiceTest {

	private GithubService github;

	@Before
	public void setUp() throws IOException {
		this.github = new GithubService();
	}

	//@Test
	public void testUserInformation() throws IOException {
		GHMyself user = this.github.getMyself();
		assertEquals("Joe Heth", user.getName());
	}
	
	
	//@Test
	public void testGetPullRequestStats() throws IOException
	{
		List<GHPullRequest> pullRequests = this.github.getOrganizationPullRequests("Netflix");
		Map<String, Integer> stats = this.github.pullRequestStats(pullRequests);

		System.out.println(stats);
		for (String key : stats.keySet()) {
			String.format(key + " => " + stats.get(key));
		}
	}
	
	@Test
	// TODO: Fix limits.
	public void testGetTopReposByPullRequests() throws IOException {
		SortedSet<Map.Entry<String, Integer>> sortedSet = this.github.getTopReposByPullRequests("Netflix", 5);

		List<RepoCountPair> dataList = new ArrayList<RepoCountPair>();
		
		for (Map.Entry<String, Integer> entry : sortedSet) {
			dataList.add(new RepoCountPair(entry.getKey(), entry.getValue()));
		}
	}

}
