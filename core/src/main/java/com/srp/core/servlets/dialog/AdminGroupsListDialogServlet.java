package com.srp.core.servlets.dialog;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Servlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import com.srp.core.services.ResourceResolverService;
import com.srp.core.utils.SRPCommonUtils;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.adobe.granite.ui.components.ds.DataSource;

@Component(service = Servlet.class, property = { "sling.servlet.methods=" + HttpConstants.METHOD_GET,
		"sling.servlet.resourceTypes=" + AdminGroupsListDialogServlet.SELECTOR })
public class AdminGroupsListDialogServlet extends SlingSafeMethodsServlet{
	
	private static final long serialVersionUID = 1L;
	private transient Logger log = LoggerFactory.getLogger(AdminGroupsListDialogServlet.class);
	
	protected static final String SELECTOR = "getSrpGroupAdminsList";
	
	@Reference
	private transient ResourceResolverService resourceResolverService;
	
	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {

		log.error("SRP-Entered doGet method of AdminGroupsListDialogServlet");
		LinkedHashMap<String, String> admingroups = new LinkedHashMap<>();
		ResourceResolver resourceResolver = null;
		try {
			resourceResolver = resourceResolverService.getResourceResolver();
			log.error("SRP-Resourceresolver created");
			QueryBuilder queryBuilder = resourceResolver.adaptTo(QueryBuilder.class);

			// Define query to fetch all nodes of type 'rep:Group'
			Map<String, String> queryMap = new HashMap<>();
			queryMap.put("path", "/home/groups"); // Location of user groups
			queryMap.put("type", "rep:Group"); // Only get groups
			queryMap.put("p.limit", "-1"); // Get all results

			Query query = queryBuilder.createQuery(PredicateGroup.create(queryMap),
					resourceResolver.adaptTo(Session.class));
			SearchResult result = query.getResult();

			for (Hit hit : result.getHits()) {
				Node node = hit.getNode();
				if (node != null && node.hasProperty("rep:principalName")) {
					String groupName = node.getProperty("rep:principalName").getString();
					String groupId = node.getProperty("rep:authorizableId").getString();
					log.error("SRP-Fetched Group:{}", groupId);
					admingroups.put(groupId, groupName);
				}
			}

			DataSource datasource = SRPCommonUtils.convertListToDatasource(request, admingroups);
			request.setAttribute(DataSource.class.getName(), datasource);

			log.error("SRP-Exiting doGet method of AdminGroupsListDialogServlet");
		} catch (LoginException | RepositoryException e) {
			log.error("SRP-Exception occured in AdminGroupsListDialogServlet", e);
		}
	}

}
