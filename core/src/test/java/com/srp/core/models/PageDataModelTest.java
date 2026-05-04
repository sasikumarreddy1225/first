package com.srp.core.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class PageDataModelTest {

	private final AemContext ctx = new AemContext();

	PageDataModel pageDataModel;

	@BeforeEach
	void setUp() {
		ctx.addModelsForClasses(PageDataModel.class);
		ctx.load().json("/com/srp/core/models/PageDataModelTest.json", "/json");
		ctx.currentResource("/json/page");
		pageDataModel = ctx.request().adaptTo(PageDataModel.class);
	}

	@Test
	void test() {
		String actualResp = pageDataModel.toString();
		String expectedResp = "PageDataModel [getTitle()=Sales, getLabel()=Sales, getId()=abc12345]";
		assertEquals(expectedResp, actualResp);
	}

}
