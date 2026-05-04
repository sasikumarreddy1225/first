package com.srp.core.models.common;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class ExpericenceFragmentsRefDataModelTest {

	private final AemContext ctx = new AemContext();

	ExpericenceFragmentsRefDataModel expericenceFragmentsRefDataModel;

	@BeforeEach
	void setUp() {
		ctx.addModelsForClasses(ExpericenceFragmentsRefDataModel.class);
		ctx.load().json("/com/srp/core/models/common/ExpReferenceDataModelTest.json", "/json");
		ctx.currentResource("/json/exp_reference");
		expericenceFragmentsRefDataModel = ctx.request().adaptTo(ExpericenceFragmentsRefDataModel.class);
	}

	@Test
	void testToString() {
		String expectedToString = "ExpericenceFragmentsRefDataModel [getExpFragPath()=/content/srp/menulinks/global/en]";
		String actualToString = expericenceFragmentsRefDataModel.toString();
		assertEquals(expectedToString, actualToString);
	}


}
