package com.srp.core.services;

import org.apache.sling.api.resource.ResourceResolver;

import javax.jcr.RepositoryException;

import org.apache.sling.api.SlingHttpServletRequest;
import com.srp.core.beans.request.TagsListBean;

public interface GenericPageExporterInterface {
	
	public String getPageAsJson(String pagePath, ResourceResolver resourceResolver, TagsListBean tagslist,
			SlingHttpServletRequest request, String temp);
	
	public String getMenulinksJson(String pagePath, ResourceResolver resourceResolver, TagsListBean tagslist,
			SlingHttpServletRequest request, String temp) throws RepositoryException;
	
	public String getExperienceFragData(String pagePath, ResourceResolver resourceResolver, TagsListBean tagslist,
			SlingHttpServletRequest request, String temp) throws RepositoryException;

}
