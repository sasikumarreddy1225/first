package com.srp.core.models.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith({AemContextExtension.class, MockitoExtension.class })
class ListDataModelTest{
	
	private final AemContext context = new AemContext();
	
	ListDataModel listDataModel;
	
	@BeforeEach
	void setUp() {
		context.addModelsForClasses(ListDataModel.class);
		context.load().json("/com/srp/core/models/common/ListDataModelTest.json", "/json");	
	}
	
	@Test
	void testToString() {
		context.currentResource("/json/bannerlist");
		listDataModel = context.request().adaptTo(ListDataModel.class);
		String expRespString = "ListDataModel [getLabels()=[LabelsDataModel [getLabel()=Login, getLabelId()=65064648-b3d6-f219-98be-396e12feb351, getLabelType()=LOGIN_TITLE]], getExperienceFragments()=[ExperienceFragmentsDataModel [getXfPath()=/content/experience-fragments/srp/ee/it/en/ir/banner/master]]]";
		String actuRespString = listDataModel.toString();
		assertEquals(expRespString, actuRespString);
	}
	
	@Test
	void testNullToString() {
		context.currentResource("/json/banner_null");
		listDataModel = context.request().adaptTo(ListDataModel.class);
		String expResp = "ListDataModel [getLabels()=[], getExperienceFragments()=[]]";
		String actResp = listDataModel.toString();
		assertEquals(expResp, actResp);
	}

}
