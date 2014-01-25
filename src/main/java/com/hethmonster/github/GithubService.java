package com.hethmonster.github;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.kohsuke.github.GHMyself;
import org.kohsuke.github.GHOrganization;
import org.kohsuke.github.GHPullRequest;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

import com.hethmonster.service.OrgResource;
import com.hethmonster.service.util.MapValueComparator;

/**
 * Wrapper class to hide the Kohsuke Github API implementation.
 * Includes helper methods for application specific logic.
 * 
 * @see OrgResource
 * 
 * @author jheth
 */
public class GithubService {

	protected GitHub client;

	/**
	 * Constructor connects to Github Servicee
	 * Raises IOException if unable to connect.
	 * @throws IOException
	 */
	public GithubService() throws IOException {
		this.client = GitHub.connect();
	}

	/**
	 * Delegate to GitHub client to get current user.
	 * 
	 * @return GHMyself
	 * @throws IOException
	 */
	public GHMyself getMyself() throws IOException {
		return this.client.getMyself();
	}

	/**
	 * 
	 * @param orgName
	 * @param limit
	 * @return SortedSet<Map.Entry<String, Integer>>
	 * @throws IOException
	 */
	public SortedSet<Map.Entry<String, Integer>> getTopReposByPullRequests(String orgName, int limit) throws IOException {
		List<GHPullRequest> pullRequests = this.getOrganizationPullRequests(orgName);
		Map<String, Integer> stats = this.pullRequestStats(pullRequests);
		
		MapValueComparator comparator = new MapValueComparator();
		SortedSet<Map.Entry<String, Integer>> sortedMap = new TreeSet<Map.Entry<String, Integer>>(comparator);
		
		sortedMap.addAll(stats.entrySet());

		return sortedMap;
	}
	
	public Map<String,GHRepository> getRepositories(String orgName) throws IOException
	{
		GHOrganization org = this.getOrganization(orgName);
		return org.getRepositories();
	}
	
	public List<GHPullRequest> getOrganizationPullRequests(String orgName) throws IOException {

		GHOrganization organization = client.getOrganization(orgName);
		return organization.getPullRequests();
	}
	
	public GHOrganization getOrganization(String orgName) throws IOException
	{
		return client.getOrganization(orgName);
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

}
