package com.srp.core.workflows;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

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

@ExtendWith(MockitoExtension.class)

class SRPWorkflowInitiatorTest {

	@InjectMocks
	private SRPWorkflowInitiator initiator;

	@Mock
	private WorkItem workItem;

	@Mock
	private WorkflowSession workflowSession;

	@Mock
	private MetaDataMap metaDataMap;

	@Mock
	private WorkflowData workflowData;

	@Mock
	private Logger logger;

	@BeforeEach
	void setUp() {

		lenient().when(workItem.getWorkflowData()).thenReturn(workflowData);
		lenient().when(workflowData.getMetaDataMap()).thenReturn(metaDataMap);
		lenient().when(metaDataMap.get("userId", String.class)).thenReturn("testUser");
	}

	@Test
	void testGetParticipant_Success() throws Exception {
		String participant = initiator.getParticipant(workItem, workflowSession, metaDataMap);
		assertEquals("testUser", participant);

	}

	@Test
	void testGetParticipant_ExceptionHandling() throws Exception {
		when(metaDataMap.get("userId", String.class)).thenThrow(new RuntimeException("Error occurred"));
		String participant = initiator.getParticipant(workItem, workflowSession, metaDataMap);
		assertNull(participant);
	}
}