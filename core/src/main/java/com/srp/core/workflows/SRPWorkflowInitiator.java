package com.srp.core.workflows;

import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.ParticipantStepChooser;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.metadata.MetaDataMap;

@Component(service = ParticipantStepChooser.class, property = { ParticipantStepChooser.SERVICE_PROPERTY_LABEL + "= SRP Workflow Initiator" })
public class SRPWorkflowInitiator implements ParticipantStepChooser {
	private static final Logger log = LoggerFactory.getLogger(SRPWorkflowInitiator.class);

	@Override
	public String getParticipant(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap args)
            throws WorkflowException {
		log.error("SRP-Entered into getParticipant method");

		try {

			String initiator = workItem.getWorkflowData().getMetaDataMap().get("userId", String.class);
			log.error("SRP-Workflow initiator: {}", initiator);
			return initiator;

		} catch (Exception e) {
			log.error("SRP-Error in fetching the initiator", e);
		}

		return null;
	}
}
