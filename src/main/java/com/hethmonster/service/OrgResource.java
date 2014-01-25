package com.hethmonster.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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

import com.hethmonster.service.util.RepoCountPair;

@Path("/org/{orgName}")
@Produces(MediaType.APPLICATION_JSON)
public class OrgResource {

	@GET
	public Map<String, Object> getOrg(@PathParam("orgName") String orgName) throws IOException
	{
		Map<String,Object> attrs = new HashMap<String, Object>();

		try {
			GithubService github = new GithubService();
			// Throws FileNotFoundException when orgName is invalid.
			GHOrganization org = github.getOrganization(orgName);
			attrs.put("name", org.getName());
		} catch (FileNotFoundException fnf) {
			attrs.put("error", "Organization Does Not Exist.");
		} catch (IOException ioe) {
			// TODO Auto-generated catch block
			throw ioe;
			//ioe.printStackTrace();
		}

		return attrs;
	}
	
	@GET
	@Path("/repos")
	public Set<String> getOrgRepositories(@PathParam("orgName") String orgName)
	{
		Set<String> repos = new HashSet<String>();
		try {
			GithubService github = new GithubService();
			Map<String, GHRepository> repoMap = github.getRepositories(orgName);
			repos = repoMap.keySet();

		} catch (IOException e) {
			// Return Empty list
		}
		return repos;
	}

	@GET
	@Path("/top")
	public List<RepoCountPair> getTopReposByPullRequest(@PathParam("orgName") String orgName, @DefaultValue("5") @QueryParam("limit") int limit) {

		try {
			GithubService github = new GithubService();
			SortedSet<Map.Entry<String, Integer>> sortedSet = github.getTopReposByPullRequests(orgName, limit);

			List<RepoCountPair> dataList = new ArrayList<RepoCountPair>();
			
			for (Map.Entry<String, Integer> entry : sortedSet) {
				dataList.add(new RepoCountPair(entry.getKey(), entry.getValue()));
				if (dataList.size() >= limit) {
					break;
				}
			}

			return dataList;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return new ArrayList<RepoCountPair>();
		}
	}
}