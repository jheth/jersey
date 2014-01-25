package com.netflix.service.util;

public class RepoCountPair {

	private String repository;
	private Integer pullCount;
	
	public RepoCountPair(String repoName, Integer pullCount) {
		this.repository = repoName;
		this.pullCount = pullCount;
	}
	
	public void setRepository(String repository) {
		this.repository = repository;
	}

	public void setPullCount(Integer pullCount) {
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
