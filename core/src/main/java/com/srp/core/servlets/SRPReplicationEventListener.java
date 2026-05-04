package com.srp.core.servlets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.jcr.resource.api.JcrResourceConstants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

import com.day.cq.replication.ReplicationAction;
import com.day.cq.replication.ReplicationActionType;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.Page;
import com.srp.core.beans.request.PublishContentRequestBean;
import com.srp.core.constants.CommonConstants;
import com.srp.core.models.common.MenuLinksReferenceDataModel;
import com.srp.core.services.ApiServiceCall;
import com.srp.core.services.ResourceResolverService;
import com.srp.core.utils.SRPCommonUtils;

@Component(service = EventHandler.class, immediate = true, property = {
		"event.topics=" + ReplicationAction.EVENT_TOPIC })
public class SRPReplicationEventListener implements EventHandler {

	Logger log = LoggerFactory.getLogger(SRPReplicationEventListener.class);

	@Reference
	private ApiServiceCall apiServiceCall;

	@Reference
	ResourceResolverService resourceResolverService;

	PublishContentRequestBean publishContentRequestBean;

	@Override
	public void handleEvent(Event event) {
		log.error("SRP- -- Event Handler started -- ");
		ReplicationAction action = ReplicationAction.fromEvent(event);
		String path = action.getPath();
		log.error("SRP-Page Path{}", path);
		
		try {
			if (action.getType() == ReplicationActionType.ACTIVATE) {
				ResourceResolver resourceResolver = resourceResolverService.getResourceResolver();
				List<String> pagesArr = getMenuPagesFromMenulinkPage(path, resourceResolver);
				pagesArr.add(path);
				String resp = StringUtils.EMPTY;
				
				PublishContentRequestBean publishContentRequestBean = SRPCommonUtils.setRequestBeanforUpdateContent(pagesArr,
						CommonConstants.LABEL_UPDATE, resourceResolver);
				resp = apiServiceCall.updateSRPContent(publishContentRequestBean);
				log.error("SRP-AWS API Response :" + resp);
				log.error("SRP- -- Content Published -- ");
			} else if (action.getType() == ReplicationActionType.DEACTIVATE) {
				ResourceResolver resourceResolver = resourceResolverService.getResourceResolver();
				List<String> pagesArr = getMenuPagesFromMenulinkPage(path, resourceResolver);
				pagesArr.add(path);
				String resp = StringUtils.EMPTY;
				
				PublishContentRequestBean publishContentRequestBean = SRPCommonUtils.setRequestBeanforUpdateContent(pagesArr,
						CommonConstants.LABEL_UPDATE, resourceResolver);
				resp = apiServiceCall.updateSRPContent(publishContentRequestBean);
				log.error("SRP-AWS API Response :" + resp);
				log.error("SRP--- Content Unpublished--");
			}
		} catch (Exception e) {
			log.error("SRP-Error Occured-" + e);
		}

	}

	private List<String> getMenuPagesFromMenulinkPage(String pagepath, ResourceResolver resourceResolver) {

		log.error("SRP-Entered getMenuPagesFromMenulinkPage method");

		List<String> headerPath = new ArrayList<>();
		QueryBuilder builder = resourceResolver.adaptTo(QueryBuilder.class);
		Map<String, String> predicate = new HashMap<>();
		predicate.put("path", CommonConstants.SLASH.concat(CommonConstants.PAGEPATH_ROOT));
		predicate.put("1_property", JcrResourceConstants.SLING_RESOURCE_TYPE_PROPERTY);
		predicate.put("1_property.value", CommonConstants.MENULINK_REFERENCE);
		predicate.put("p.limit", "-1");/// No limit for the result size

		Session session = resourceResolver.adaptTo(Session.class);
		Query query = builder.createQuery(PredicateGroup.create(predicate), session);
		SearchResult results = query.getResult();

		for (Hit hit : results.getHits()) {
			Resource resource;
			try {
				resource = hit.getResource();
				MenuLinksReferenceDataModel model = resource.adaptTo(MenuLinksReferenceDataModel.class);
				String parentPath = getPageContainingResource(resource);
				String path = model.getMenulinkPath();
				if (pagepath.contains(path)) {
					headerPath.add(parentPath);
				}
			} catch (RepositoryException e) {
				log.error("SRP-Exception Occured:", e);
			}
		}
		log.error("SRP-Exiting getHeaderFromMenulinkPage method");
		return headerPath;
	}
	
	private String getPageContainingResource(Resource resource) {

		if (null != resource) {
			Resource parentRes = resource.getParent();
			Page parent = parentRes.adaptTo(Page.class);
			if (null != parent)
				return parent.getPath();
			else
				return getPageContainingResource(parentRes);
		}
		return null;
	}
}