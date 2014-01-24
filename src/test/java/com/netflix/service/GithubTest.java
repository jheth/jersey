package com.netflix.service;

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

import com.google.gson.Gson;

public class GithubTest {

	private Github github;

	@Before
	public void setUp() throws IOException {
		this.github = new Github();
	}

	//@Test
	public void testUserInformation() throws IOException {
		GHMyself user = this.github.getMyself();
		assertEquals("Joe Heth", user.getName());
	}
	
	
	//@Test
	public void testGetPullRequestStats()
	{
		List<GHPullRequest> pullRequests = this.github.getOrganizationPullRequests("Netflix");
		Map<String, Integer> stats = this.github.pullRequestStats(pullRequests);

		System.out.println(stats);
		for (String key : stats.keySet()) {
			String.format(key + " => " + stats.get(key));
		}
	}
	
	@Test
	public void testGetTopReposByPullRequests() {
		SortedSet<Map.Entry<String, Integer>> sortedSet = this.github.getTopReposByPullRequests("Netflix", 5);

		List<DataResponse> dataList = new ArrayList<DataResponse>();
		
		for (Map.Entry<String, Integer> entry : sortedSet) {
			dataList.add(new DataResponse(entry.getKey(), entry.getValue()));
		}
		
		Gson gson = new Gson();
		String json = gson.toJson(dataList);
		System.out.println(json);
		//System.out.println(sortedSet);
	}

}
