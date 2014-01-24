package com.netflix.service;

public class DataResponse {

	protected String repository;
	protected Integer pullCount;
	
	public DataResponse(String repoName, Integer pullCount) {
		this.repository = repoName;
		this.pullCount = pullCount;
	}
	
	public String getRepository()
	{
		return this.repository;
	}
	
	public Integer getPullCount()
	{
		return this.pullCount;
	}
}
