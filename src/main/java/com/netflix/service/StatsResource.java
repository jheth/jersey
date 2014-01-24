package com.netflix.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

@Path("stats")
public class StatsResource {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String sayHello() {
		Gson gson = new Gson();
		String orgName = "Netflix";
		int limit = 5;

		try {
			Github github = new Github();
			SortedSet<Map.Entry<String, Integer>> sortedSet = github.getTopReposByPullRequests(orgName, limit);

			List<DataResponse> dataList = new ArrayList<DataResponse>();
			
			for (Map.Entry<String, Integer> entry : sortedSet) {
				dataList.add(new DataResponse(entry.getKey(), entry.getValue()));
				if (dataList.size() >= limit) {
					break;
				}
			}
			
			String json = gson.toJson(dataList);
			return json;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return gson.toJson(e);
		}
	}
}