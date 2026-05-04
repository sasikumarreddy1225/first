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
class SavedShoppingCartDataModelTest {

	private final AemContext context = new AemContext();
	
	SavedShoppingCartDataModel savedShoppingCartDataModel;
	
	@BeforeEach
	void setUp() {
		context.addModelsForClasses(SavedShoppingCartDataModel.class);
		context.load().json("/com/srp/core/models/common/GridComponentsTest.json", "/json");	
	}
	
	@Test
	void testToString() {
		context.currentResource("/json/job_cards");
		savedShoppingCartDataModel = context.request().adaptTo(SavedShoppingCartDataModel.class);
		String expRespString = "SavedShoppingCartDataModel [getLabels()=[LabelsDataModel [getLabel()=, getLabelId()=1234, getLabelType()=nt:unstructured]], getIsGrid()=true, getIsDisabled()=true]";
		String actuRespString = savedShoppingCartDataModel.toString();
		assertEquals(expRespString, actuRespString);
	}
	
	@Test
	void testLabelsNullToString() {
		savedShoppingCartDataModel = new SavedShoppingCartDataModel();
		List<LabelsDataModel> result = savedShoppingCartDataModel.getLabels();
		assertNotNull(result);
		assertTrue(result.isEmpty());
	}

}

