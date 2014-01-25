package com.hethmonster.github;

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
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

import com.hethmonster.service.OrgResource;
import com.hethmonster.service.util.MapValueComparator;
import com.hethmonster.service.util.RepoCountPair;

/**
 * Wrapper class to hide the Kohsuke Github API implementation. Includes helper
 * methods for application specific logic.
 * 
 * @see OrgResource
 * 
 * @author jheth
 */
public class GithubService {

	protected GitHub client;

	/**
	 * Constructor connects to Github Service Raises IOException if unable to
	 * connect.
	 * 
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
	public List<RepoCountPair> getTopReposByPullRequests(String orgName,
			int limit) throws IOException {
		// Get all pull requests.
		List<GHPullRequest> pullRequests = this
				.getOrganizationPullRequests(orgName);
		// Organize and count by repository.
		Map<String, Integer> stats = this.pullRequestStats(pullRequests);

		// Sort Map in descending order (largest to smallest).
		SortedSet<Map.Entry<String, Integer>> sortedSet = this
				.sortMapByValue(stats);

		// Return subset as List<RepoCountPair>
		List<RepoCountPair> dataList = new ArrayList<RepoCountPair>();
		for (Map.Entry<String, Integer> entry : sortedSet) {
			dataList.add(new RepoCountPair(entry.getKey(), entry.getValue()));
			if (dataList.size() >= limit) {
				break;
			}
		}

		return dataList;
	}

	/**
	 * Returns Map of Repository Name => GHRepository entries.
	 * 
	 * @param orgName
	 * @return Map<String, GHRepository>
	 * @throws IOException
	 */
	public Map<String, GHRepository> getRepositories(String orgName)
			throws IOException {
		GHOrganization org = this.getOrganization(orgName);
		return org.getRepositories();
	}

	/**
	 * Returns List of GHPullRequest objects for an Organization.
	 * 
	 * @param orgName
	 *            - GitHub Organizatio Name
	 * @return List<GHPullRequest>
	 * @throws IOException
	 */
	public List<GHPullRequest> getOrganizationPullRequests(String orgName)
			throws IOException {

		GHOrganization organization = client.getOrganization(orgName);
		return organization.getPullRequests();
	}

	/**
	 * Wrapper around GitHub API getOrganization method.
	 * 
	 * @param orgName
	 * @return GHOrganization
	 * @throws IOException
	 */
	public GHOrganization getOrganization(String orgName) throws IOException {
		return client.getOrganization(orgName);
	}

	/**
	 * Returns map of repository names to number of pull requests.
	 * 
	 * @param pullRequests
	 * @return Map<String, Integer>
	 */
	protected Map<String, Integer> pullRequestStats(
			List<GHPullRequest> pullRequests) {

		Map<String, Integer> stats = new HashMap<String, Integer>();

		for (GHPullRequest pull : pullRequests) {
			String repoName = pull.getRepository().getName();
			if (stats.containsKey(repoName)) {
				stats.put(repoName, stats.get(repoName) + 1);
			} else {
				stats.put(repoName, 1);
			}
		}

		return stats;
	}

	/**
	 * Helper method to sort a HashMap based on the value field. Uses custom
	 * Comparator
	 * 
	 * @param map
	 *            - <String,Integer>
	 * @return SortedSet<Map.Entry<String, Integer>>
	 */
	protected SortedSet<Map.Entry<String, Integer>> sortMapByValue(
			Map<String, Integer> map) {
		MapValueComparator comparator = new MapValueComparator();
		SortedSet<Map.Entry<String, Integer>> sortedMap = new TreeSet<Map.Entry<String, Integer>>(
				comparator);

		sortedMap.addAll(map.entrySet());

		return sortedMap;
	}

}
