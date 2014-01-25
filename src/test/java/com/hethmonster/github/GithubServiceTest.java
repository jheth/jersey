package com.hethmonster.github;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.kohsuke.github.GHOrganization;
import org.kohsuke.github.GHPullRequest;

import com.hethmonster.service.util.RepoCountPair;

public class GithubServiceTest {

	private GithubService github;
	private String orgName = "Netflix";

	@Before
	public void setUp() throws IOException {
		this.github = new GithubService();
	}

	@Test
	public void testOrganizationName() throws IOException {
		GHOrganization user = this.github.getOrganization(this.orgName);
		assertNotNull(user.getName());
	}
	
	@Test
	public void testGetPullRequestStats() throws IOException
	{
		List<GHPullRequest> pullRequests = this.github.getOrganizationPullRequests(this.orgName);
		Map<String, Integer> stats = this.github.pullRequestStats(pullRequests);

		assertTrue(stats.size() > 0);
	}
	
	@Test
	public void testGetTopReposByPullRequests() throws IOException {
		List<RepoCountPair> results = this.github.getTopReposByPullRequests(this.orgName, 5);
		assertEquals(5, results.size());
		
		int previousPullCount = -1;
		for (RepoCountPair pair : results) {
			if (previousPullCount != -1 && pair.getPullCount() > previousPullCount) {
				fail("Results are not sorted properly by pull count.");
			}
			previousPullCount = pair.getPullCount();
		}
	}

}
