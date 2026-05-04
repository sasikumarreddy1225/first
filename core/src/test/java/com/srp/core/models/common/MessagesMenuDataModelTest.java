package com.srp.core.models.common;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.srp.core.models.LabelsDataModel;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith({AemContextExtension.class, MockitoExtension.class })
class MessagesMenuDataModelTest {

private final AemContext context = new AemContext();
	
	MessagesMenuDataModel messagesMenuDataModel;
	@BeforeEach
	void setUp() {
		context.addModelsForClasses(MessagesMenuDataModel.class);
		context.load().json("/com/srp/core/models/common/MessagesMenuDataModelTest.json", "/json");	
	}
	
	@Test
	void testToString() {
		context.currentResource("/json/messagesMenu");
		messagesMenuDataModel = context.request().adaptTo(MessagesMenuDataModel.class);
		String expRespString = "MessagesMenuDataModel [getLabels()=[LabelsDataModel [getLabel()=, getLabelId()=1234, getLabelType()=nt:unstructured]]]";
		String actuRespString = messagesMenuDataModel.toString();
		assertEquals(expRespString, actuRespString);
	}
	
	@Test
	void testLabelsNullToString() {
		messagesMenuDataModel = new MessagesMenuDataModel();
		List<LabelsDataModel> result = messagesMenuDataModel.getLabels();
		assertNotNull(result);
		assertTrue(result.isEmpty());
	}


}
