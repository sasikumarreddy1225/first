package com.srp.core.services.impl;

import java.io.Serializable;
import java.util.Map;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import org.apache.commons.collections4.map.HashedMap;
import com.srp.core.services.ResourceResolverService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Component(service = ResourceResolverService.class, immediate = true)
public class ResourceResolverServiceImpl implements ResourceResolverService, Serializable{

	private static final long serialVersionUID = 1L;

	private static final Logger log = LoggerFactory.getLogger(ResourceResolverServiceImpl.class);

	@Reference
	protected transient ResourceResolverFactory resolverFactory;

	@Override
	public ResourceResolver getResourceResolver() throws LoginException {
		log.error("SRP-Entered getResourceResolver method");
		Map<String, Object> map = new HashedMap<>();
		map.put(ResourceResolverFactory.SUBSERVICE, "readService");
		log.error("SRP-Exiting getResourceResolver method");
		return resolverFactory.getServiceResourceResolver(map);
	}

	@Override
	public ResourceResolver getWriteResourceResolver() throws LoginException {
		log.error("SRP-Entered getWriteResourceResolver method");
		Map<String, Object> map = new HashedMap<>();
		map.put(ResourceResolverFactory.SUBSERVICE, "writeService");
		log.error("SRP-Exiting getWriteResourceResolver method");
		return resolverFactory.getServiceResourceResolver(map);
	}

	@Override
	public void closeResourceResolver(ResourceResolver resourceResolver) {
		if (null != resourceResolver && resourceResolver.isLive()) {
			resourceResolver.close();
		}

	}

}
