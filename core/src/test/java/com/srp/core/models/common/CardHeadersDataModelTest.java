package com.srp.core.models.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.srp.core.models.LabelsDataModel;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith({AemContextExtension.class, MockitoExtension.class })
class CardHeadersDataModelTest {
	
private final AemContext context = new AemContext();
	
    CardHeadersDataModel cardHeadersDataModel;
	
	@BeforeEach
	void setUp() {
		context.addModelsForClasses(JobCardsDataModel.class);
		context.load().json("/com/srp/core/models/common/CardHeadersDataModelTest.json", "/json");	
	}
	
	@Test
	void testToString() {
		context.currentResource("/json/card_headers");
		cardHeadersDataModel = context.request().adaptTo(CardHeadersDataModel.class);
		String expRespString = "CardHeadersDataModel [getLabels()=[LabelsDataModel [getLabel()=, getLabelId()=1234, getLabelType()=nt:unstructured]]]";
		String actuRespString = cardHeadersDataModel.toString();
		assertEquals(expRespString, actuRespString);
	}
	
	@Test
	void testLabelsNullToString() {
		cardHeadersDataModel = new CardHeadersDataModel();
		List<LabelsDataModel> result = cardHeadersDataModel.getLabels();
		assertNotNull(result);
		assertTrue(result.isEmpty());
	}

}
