package com.srp.core.models.common;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.srp.core.models.LabelsDataModel;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith({AemContextExtension.class, MockitoExtension.class })
class NotificationsMenuDataModelTest {

private final AemContext context = new AemContext();
	
    NotificationsMenuDataModel notificationsMenuDataModel;
	@BeforeEach
	void setUp() {
		context.addModelsForClasses(NotificationsMenuDataModel.class);
		context.load().json("/com/srp/core/models/common/NotificationsMenuDataModelTest.json", "/json");	
	}
	
	@Test
	void testToString() {
		context.currentResource("/json/notificationsMenu");
		notificationsMenuDataModel = context.request().adaptTo(NotificationsMenuDataModel.class);
		String expRespString = "NotificationsMenuDataModel [getLabels()=[LabelsDataModel [getLabel()=, getLabelId()=1234, getLabelType()=nt:unstructured]]]";
		String actuRespString = notificationsMenuDataModel.toString();
		assertEquals(expRespString, actuRespString);
	}
	
	@Test
	void testLabelsNullToString() {
		notificationsMenuDataModel = new NotificationsMenuDataModel();
		List<LabelsDataModel> result = notificationsMenuDataModel.getLabels();
		assertNotNull(result);
		assertTrue(result.isEmpty());
	}


}

