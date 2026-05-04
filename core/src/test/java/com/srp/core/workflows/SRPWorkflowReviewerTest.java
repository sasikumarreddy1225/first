package com.srp.core.workflows;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.lenient;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.srp.core.services.ResourceResolverService;

import io.wcm.testing.mock.aem.junit5.AemContext;

@ExtendWith(MockitoExtension.class)
class SRPWorkflowReviewerTest {

	private final AemContext ctx = new AemContext();

	@InjectMocks
	private SRPWorkflowReviewer reviewer = new SRPWorkflowReviewer();

	@Mock
	private ResourceResolverService resourceResolverService;

	@Mock
	private WorkItem workItem;

	@Mock
	private WorkflowSession workflowSession;

	@Mock
	private MetaDataMap metaDataMap;

	private ResourceResolver resourceResolver;

	@Mock
	private Resource payloadRes;

	@Mock
	private ValueMap valueMap;

	@Mock
	private WorkflowData workflowData;

	@Mock
	private Logger logger;

	private static final String PAYLOAD_PATH = "/content/dam/test";
	private static final String REVIEWER_GROUP = "reviewer1|reviewer2|reviewer3";
	private static final String[] REVIEWER_GROUP_SPLIT = REVIEWER_GROUP.split("\\|");

	@BeforeEach
	void setUp() throws LoginException {
		lenient().when(workItem.getWorkflowData()).thenReturn(workflowData);
		lenient().when(workflowData.getMetaDataMap()).thenReturn(metaDataMap);
		lenient().when(workflowData.getPayload()).thenReturn(PAYLOAD_PATH);
		ctx.registerService(ResourceResolverService.class, resourceResolverService);
		resourceResolver = ctx.resourceResolver();
		lenient().when(resourceResolverService.getResourceResolver()).thenReturn(resourceResolver);
	}

	@Test
	void testGetParticipant_Success() throws Exception {
		ctx.create().resource(PAYLOAD_PATH.concat("/jcr:content"), "jcr:primaryType", "cq:Page", "jcr:title",
				"Test Page", "cq:template", "/conf/srp/settings/wcm/templates/landing-page-template", "reviewerGroup",
				REVIEWER_GROUP);

		String participant = reviewer.getParticipant(workItem, workflowSession, metaDataMap);
		assertEquals(REVIEWER_GROUP_SPLIT[1], participant);
	}

	@Test
	void testGetParticipant_NullPayloadResource() throws Exception {
		String participant = reviewer.getParticipant(workItem, workflowSession, metaDataMap);
		assertNull(participant);
	}

	@Test
	void testGetParticipant_ExceptionHandling() throws Exception {
		lenient().when(resourceResolverService.getResourceResolver()).thenThrow(new RuntimeException("Error occurred"));
		String participant = reviewer.getParticipant(workItem, workflowSession, metaDataMap);
		assertNull(participant);
	}

}