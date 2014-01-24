package com.netflix.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.kohsuke.github.GHMyself;
import org.kohsuke.github.GHOrganization;
import org.kohsuke.github.GHPullRequest;
import org.kohsuke.github.GitHub;

public class Github {

	protected GitHub client;

	public Github() throws IOException {
		this.client = GitHub.connect();
	}

	public GHMyself getMyself() throws IOException {
		return this.client.getMyself();
	}

	public SortedSet<Map.Entry<String, Integer>> getTopReposByPullRequests(String orgName, int limit) {
		List<GHPullRequest> pullRequests = this.getOrganizationPullRequests(orgName);
		Map<String, Integer> stats = this.pullRequestStats(pullRequests);

		return this.getTopReposistories(stats, limit);
	}
	
	public List<GHPullRequest> getOrganizationPullRequests(String orgName) {

		List<GHPullRequest> pullRequests = new ArrayList<GHPullRequest>();

		GHOrganization organization;
		try {
			organization = client.getOrganization(orgName);
			//List<GHRepository> repositories = organization.getRepositoriesWithOpenPullRequests();
			pullRequests = organization.getPullRequests();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return pullRequests;
	}
	
	protected Map<String, Integer> pullRequestStats(List<GHPullRequest> pullRequests) {

		Map<String, Integer> stats = new HashMap<String, Integer>();
		
		for (GHPullRequest pull : pullRequests) {
			String repoName = pull.getRepository().getName();
			if (stats.containsKey(repoName)) {
				stats.put(repoName, stats.get(repoName)+1);
			} else {
				stats.put(repoName, 1);
			}
		}

		return stats;
	}
	
	protected SortedSet<Map.Entry<String, Integer>> getTopReposistories(Map<String, Integer> stats, int limit) {

		MapValueComparator comparator = new MapValueComparator();
		SortedSet<Map.Entry<String, Integer>> sortedMap = new TreeSet<Map.Entry<String, Integer>>(comparator);
		
		sortedMap.addAll(stats.entrySet());

		return sortedMap;
	}

}
