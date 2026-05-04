package com.srp.core.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.jcr.resource.api.JcrResourceConstants;
import org.osgi.service.component.annotations.Component;

import com.srp.core.beans.request.TagsBeanClass;
import com.srp.core.constants.CommonConstants;
import com.srp.core.models.TagsDataModel;
import com.srp.core.models.configuration.AdminConfigDataModel;
import com.srp.core.models.configuration.AdminGroupsListDataModel;
import com.srp.core.models.configuration.AuthorConfigurationsDataModel;
import com.srp.core.services.AuthorInfoCheckService;

@Component(service = AuthorInfoCheckService.class, immediate = true)
public class AuthorInfoCheckServiceImpl implements AuthorInfoCheckService {

	private static final String SITE_CONFIG_PATH = "content/experience-fragments/srp/general/site_configuration/configurations/master";
	private static final String AUTHOR_CONFIG_COMP = "srp/components/configuration/authorConf";
	private static final String ADMIN_CONFIG_COMP = "srp/components/configuration/adminConf";
	
	Logger log = LoggerFactory.getLogger(AuthorInfoCheckServiceImpl.class);

	@Override
	public TagsBeanClass getTagValuesOfAuthor(ResourceResolver resourceResolver, List<String> userGroup) {
		log.error("SRP-Entered into getTagValuesOfAuthor method");
		TagsBeanClass tagsBeanClass = new TagsBeanClass();
		List<String> geoTags = new ArrayList<>();
		List<String> positionTags = new ArrayList<>();
		List<String> brandTags = new ArrayList<>();
		Session session = resourceResolver.adaptTo(Session.class);
		QueryBuilder querybuilder = resourceResolver.adaptTo(QueryBuilder.class);
		Map<String, String> predicate = new HashMap<>();
		predicate.put("path", CommonConstants.SLASH.concat(SITE_CONFIG_PATH));
		predicate.put("1_property", JcrResourceConstants.SLING_RESOURCE_TYPE_PROPERTY);
		predicate.put("1_property.value", AUTHOR_CONFIG_COMP);
		Query query = null;
		query = querybuilder.createQuery(PredicateGroup.create(predicate), session);
		SearchResult searchresults = query.getResult();
		for (Hit hit : searchresults.getHits()) {
			Resource resource;
			try {
				resource = hit.getResource();
				AuthorConfigurationsDataModel configurations = resource.adaptTo(AuthorConfigurationsDataModel.class);
				String groupName = configurations.getGroupId();
				log.error("SRP-Author group:{}", groupName);
				if (userGroup.contains(groupName)) {
					tagsBeanClass = getResourceTags(resource);
					if (null != tagsBeanClass.getGeoTags()) {
						geoTags.addAll(tagsBeanClass.getGeoTags());
					}
					if (null != tagsBeanClass.getRoleTags()) {
						positionTags.addAll(tagsBeanClass.getRoleTags());
					}
					if (null != tagsBeanClass.getBrandTags()) {
						brandTags.addAll(tagsBeanClass.getBrandTags());
					}
				}
			} catch (RepositoryException e) {
				log.error("SRP-Exception Occured:", e);
			}
		}
		tagsBeanClass.setGeoTags(geoTags);
		tagsBeanClass.setRoleTags(positionTags);
		tagsBeanClass.setBrandTags(brandTags);
		log.error("SRP-Tags of Author :{}", tagsBeanClass);
		log.error("SRP-Exiting getTagValuesOfAuthor method");
		return tagsBeanClass;
	}

	@Override
	public TagsBeanClass getResourceTags(Resource resource) {
		TagsBeanClass tagsBeanClass = new TagsBeanClass();
		TagsDataModel children = resource.adaptTo(TagsDataModel.class);
		List<String> geoTags = children.getGeoTags();
		List<String> positionTags = children.getRoleTags();
		List<String> brandTags = children.getBrandTags();
		tagsBeanClass.setBrandTags(brandTags);
		tagsBeanClass.setGeoTags(geoTags);
		tagsBeanClass.setRoleTags(positionTags);
		return tagsBeanClass;
	}

	@Override
	public List<String> getAdminGroups(ResourceResolver resourceResolver) {
		log.error("SRP-Entered into getAdminGroups method");
		List<String> adminGroups = new ArrayList<>();
		Session session = resourceResolver.adaptTo(Session.class);
		QueryBuilder querybuilder = resourceResolver.adaptTo(QueryBuilder.class);
		Map<String, String> predicate = new HashMap<>();
		predicate.put("path", CommonConstants.SLASH.concat(SITE_CONFIG_PATH));
		predicate.put("1_property", JcrResourceConstants.SLING_RESOURCE_TYPE_PROPERTY);
		predicate.put("1_property.value", ADMIN_CONFIG_COMP);
		Query query = null;
		query = querybuilder.createQuery(PredicateGroup.create(predicate), session);
		SearchResult searchresults = query.getResult();
		for (Hit hit : searchresults.getHits()) {
			Resource resource;
			try {
				resource = hit.getResource();
				AdminConfigDataModel configurations = resource.adaptTo(AdminConfigDataModel.class);
				List<AdminGroupsListDataModel> groups = configurations.getAdminGroups();
				groups.forEach(i -> {
					String id = i.getGroupId();
					adminGroups.add(id);
				});
			} catch (RepositoryException e) {
				log.error("SRP-Exception Occured:", e);
			}
		}
		log.error("SRP-Admin Groups:{}", adminGroups);
		log.error("SRP-Exiting getAdminGroups method");
		return adminGroups;
	}

}
