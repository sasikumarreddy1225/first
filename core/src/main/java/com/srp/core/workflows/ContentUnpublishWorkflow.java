package com.srp.core.workflows;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.http.ParseException;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.Route;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.srp.core.beans.request.PublishContentRequestBean;
import com.srp.core.constants.CommonConstants;
import com.srp.core.services.ApiServiceCall;
import com.srp.core.utils.SRPCommonUtils;

@Component(service = WorkflowProcess.class, property = { "process.label=SRP Content Unpublish Workflow" })
public class ContentUnpublishWorkflow implements WorkflowProcess {
	Logger log = LoggerFactory.getLogger(ContentUnpublishWorkflow.class);

	@Reference
	ApiServiceCall apiServiceCall;

	@Override
	public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap metaDataMap)
			throws WorkflowException {

		log.error("SRP-SRP Content Unpublish Custom Workflow Started");

		ResourceResolver resourceResolver = workflowSession.adaptTo(ResourceResolver.class);

		WorkflowData data = workItem.getWorkflowData();

		String pagePath = data.getPayload().toString();

		log.error("SRP-Workflow Started for page {}", pagePath);
		List<String> pagesList = Arrays.asList(pagePath);
		PublishContentRequestBean publishContentRequestBean = SRPCommonUtils.setRequestBeanforUpdateContent(pagesList,
				CommonConstants.LABEL_DELETE, resourceResolver);

		// initating a call to aws on contentUnpublish
		if (!publishContentRequestBean.isExternal()) {
			try {
				String resp = apiServiceCall.updateSRPContent(publishContentRequestBean);
				log.error("SRP-Response of AWS Publish API: {}", resp);
			} catch (ParseException | IOException e) {
				log.error(
						"Exception Occured in :SRP Content Unpublish Custom Workflow API Content Publish Call to AWS",
						e);
			}
		}

		List<Route> nextRoutes = workflowSession.getRoutes(workItem, false);
		workflowSession.complete(workItem, nextRoutes.get(0));

		log.error("SRP-SRP Content Unpublish Custom Workflow terminated");
	}
}
