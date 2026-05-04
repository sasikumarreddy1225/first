package com.srp.core.workflows;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.ParticipantStepChooser;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.srp.core.constants.CommonConstants;
import com.srp.core.services.ResourceResolverService;

@Component(service = ParticipantStepChooser.class, property = { ParticipantStepChooser.SERVICE_PROPERTY_LABEL + " = SRP Workflow Reviewer" })
public class SRPWorkflowReviewer implements ParticipantStepChooser{


	@Reference
	ResourceResolverService resourceResolverService;

	private static final Logger log = LoggerFactory.getLogger(SRPWorkflowReviewer.class);

	@Override
	public String getParticipant(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap args)
			throws WorkflowException {
		log.error("SRP-Entered getParticipant method for SRP Workflow reviewer");
		try {

			ResourceResolver resourceResolver = resourceResolverService.getResourceResolver();
			String payload = workItem.getWorkflowData().getPayload().toString();
			Resource payloadRes = resourceResolver.getResource(payload.concat("/jcr:content"));
			if (null != payloadRes) {
				String reviewerGroup = payloadRes.getValueMap().get("reviewerGroup", String.class);
				String[] reviewerGroupId = reviewerGroup.split(CommonConstants.BACKSLASH_PIPE);
				return reviewerGroupId[1];
			}	
			return null;

		} catch (Exception e) {
			log.error("SRP-Error in excluding initiator from reviewers", e);
		}
		log.error("SRP-Exiting getParticipant method for SRP Workflow reviewer");
		return null;
	}

}
