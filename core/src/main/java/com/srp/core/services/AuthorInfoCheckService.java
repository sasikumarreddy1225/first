package com.srp.core.services;

import java.util.List;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;

import com.srp.core.beans.request.TagsBeanClass;

public interface AuthorInfoCheckService {
	
	public TagsBeanClass getTagValuesOfAuthor(ResourceResolver resourceResolver, List<String> userGroup);

	public TagsBeanClass getResourceTags(Resource resource);

	public List<String> getAdminGroups(ResourceResolver resourceResolver);

}
