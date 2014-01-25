package com.hethmonster.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.kohsuke.github.GHOrganization;
import org.kohsuke.github.GHRepository;

import com.hethmonster.github.GithubService;
import com.hethmonster.service.util.RepoCountPair;

/**
 * Jersey Resource Class for exposing /rest/org/{orgName} URLs.
 * 
 * Exceptions are handled through the exception mapper classes:
 * 
 * @see FileNotFoundMapper, IOExceptionmapper
 * 
 * @author jheth
 */
@Path("/org/{orgName}")
@Produces(MediaType.APPLICATION_JSON)
public class OrgResource {

	/**
	 * 
	 * @param orgName
	 * @return Map<String, Object>
	 * @throws IOException
	 */
	@GET
	public Map<String, Object> getOrg(@PathParam("orgName") String orgName)
			throws IOException {
		Map<String, Object> attrs = new HashMap<String, Object>();

		GithubService github = new GithubService();
		// Throws FileNotFoundException when orgName is invalid.
		GHOrganization org = github.getOrganization(orgName);

		String[] properties = { "Name", "Location", "AvatarUrl", "Blog",
				"Email", "CreatedAt" };

		for (String prop : properties) {
			java.lang.reflect.Method method;
			try {
				method = org.getClass().getMethod("get" + prop);
				Object value = method.invoke(org);
				attrs.put(prop.toLowerCase(), value);
			} catch (Exception e) {
				System.out.println("Failed to get value for " + prop + " "
						+ e.getMessage());
			}
		}

		return attrs;
	}

	@GET
	@Path("/repos")
	public Set<String> getOrgRepositories(@PathParam("orgName") String orgName)
			throws IOException {
		GithubService github = new GithubService();
		Map<String, GHRepository> repoMap = github.getRepositories(orgName);
		return repoMap.keySet();
	}

	@GET
	@Path("/top")
	public List<RepoCountPair> getTopReposByPullRequest(
			@PathParam("orgName") String orgName,
			@DefaultValue("5") @QueryParam("limit") int limit)
			throws IOException {

		GithubService github = new GithubService();
		SortedSet<Map.Entry<String, Integer>> sortedSet = github
				.getTopReposByPullRequests(orgName, limit);

		List<RepoCountPair> dataList = new ArrayList<RepoCountPair>();

		for (Map.Entry<String, Integer> entry : sortedSet) {
			dataList.add(new RepoCountPair(entry.getKey(), entry.getValue()));
			if (dataList.size() >= limit) {
				break;
			}
		}

		return dataList;
	}
}