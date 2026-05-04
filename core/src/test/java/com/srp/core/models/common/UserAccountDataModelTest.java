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
class UserAccountDataModelTest {

private final AemContext context = new AemContext();
	
	UserAccountDataModel userAccountDataModel;
	
	@BeforeEach
	void setUp() {
		context.addModelsForClasses(UserAccountDataModel.class);
		context.load().json("/com/srp/core/models/common/GridComponentsTest.json", "/json");	
	}
	
	@Test
	void testToString() {
		context.currentResource("/json/job_cards");
		userAccountDataModel = context.request().adaptTo(UserAccountDataModel.class);
		String expRespString = "UserAccountDataModel [getLabels()=[LabelsDataModel [getLabel()=, getLabelId()=1234, getLabelType()=nt:unstructured]]]";
		String actuRespString = userAccountDataModel.toString();
		assertEquals(expRespString, actuRespString);
	}
	
	@Test
	void testLabelsNullToString() {
	    userAccountDataModel = new UserAccountDataModel();
		List<LabelsDataModel> result = userAccountDataModel.getLabels();
		assertNotNull(result);
		assertTrue(result.isEmpty());
	}

}
