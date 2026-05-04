package com.srp.core.models.configuration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class AuthorConfigurationsDataModelTest {

	private final AemContext ctx = new AemContext();

	AuthorConfigurationsDataModel authorConfigurationsDataModel;

	@BeforeEach
	void setUp() {
		ctx.addModelsForClasses(AuthorConfigurationsDataModel.class);
		ctx.load().json("/com/srp/core/models/configuration/AuthorConfigurationsDataModelTest.json", "/json");
		ctx.currentResource("/json/authorconf");
	}

	@Test
	void toStringTestCase() {
		authorConfigurationsDataModel = ctx.request().adaptTo(AuthorConfigurationsDataModel.class);
		String actualResp = authorConfigurationsDataModel.toString();
		String expectedResp = "AuthorConfigurationsDataModel [getGeoTags()=[srp:geo/ee], getRoleTags()=[srp:role/04-sales-manager], getBrandTags()=[srp:brand/fiat], getGroupId()=everyone, getGroupName()=everyone]";
		assertEquals(expectedResp, actualResp);
	}

	@Test
	void toStringNullTestCase() {
		ctx.currentResource("/json/authorconf_null");
		authorConfigurationsDataModel = ctx.request().adaptTo(AuthorConfigurationsDataModel.class);
		String actualResp = authorConfigurationsDataModel.toString();
		String expectedResp = "AuthorConfigurationsDataModel [getGeoTags()=[], getRoleTags()=[], getBrandTags()=[], getGroupId()=null, getGroupName()=null]";
		assertEquals(expectedResp, actualResp);
	}

}
