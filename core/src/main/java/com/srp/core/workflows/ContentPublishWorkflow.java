package com.srp.core.workflows;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.RepositoryException;
import javax.jcr.Session;


import org.apache.http.ParseException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.jcr.resource.api.JcrResourceConstants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;
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
import com.srp.core.utils.SRPCommonUtils;

@Component(service = WorkflowProcess.class, property = { "process.label=SRP Content Publish Workflow" })
public class ContentPublishWorkflow implements WorkflowProcess {

	Logger log = LoggerFactory.getLogger(ContentPublishWorkflow.class);

	@Reference
	ApiServiceCall apiServiceCall;

	@Override
	public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap metaDataMap)
			throws WorkflowException {

		log.error("SRP-SRP Content Publish Custom Workflow Started");
		ResourceResolver resourceResolver = workflowSession.adaptTo(ResourceResolver.class);
		WorkflowData data = workItem.getWorkflowData();
		String pagePath = data.getPayload().toString();
		log.error("SRP-Workflow Started for page {}", pagePath);
		List<String> pagesList = getHeaderFromMenulinkPage(pagePath, resourceResolver);
		pagesList.add(pagePath);

		PublishContentRequestBean publishContentRequestBean = SRPCommonUtils.setRequestBeanforUpdateContent(pagesList,
				CommonConstants.LABEL_UPDATE, resourceResolver);

		// initating a call to aws on contentpublish
		if (!publishContentRequestBean.isExternal()) {
			try {
				String resp = apiServiceCall.updateSRPContent(publishContentRequestBean);
				log.error("SRP-Response of AWS Publish API: {}", resp);
			} catch (ParseException | IOException e) {
				log.error("SRP-Exception Occured in :SRP Content Publish Custom Workflow API Content Publish Call to AWS",
						e);
			}
		}

		log.error("SRP-SRP Content Publish Custom Workflow terminated");
	}

	private List<String> getHeaderFromMenulinkPage(String pagepath, ResourceResolver resourceResolver) {

		log.error("SRP-Entered getHeaderFromMenulinkPage method");

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
