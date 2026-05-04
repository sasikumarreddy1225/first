package com.srp.core.models.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.lenient;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.srp.core.services.SrpDomainService;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class CatalogDataModelTest {

	private final AemContext context = new AemContext();

	CatalogDataModel catalogDataModel;

	@Mock
	SrpDomainService srpDomainService;

	@BeforeEach
	void setUp() throws Exception {
		context.addModelsForClasses(CatalogDataModel.class, CatalogCategoryDataModel.class,
				CatalogLinksDataModel.class);
		context.load().json("/com/srp/core/models/common/CatalogDataModelTest.json", "/json");
		context.registerService(SrpDomainService.class, srpDomainService);
		lenient().when(srpDomainService.getDomainUrl()).thenReturn("www.adobe.com");
	}
	
	static class TestCase {
		String jsonPath;
		String expectedResponse;
		
		public TestCase(String jsonPath, String expectedResponse) {
			this.jsonPath = jsonPath;
			this.expectedResponse = expectedResponse;
		}
	}

	@Test
	void testCatalogModels() {
		
		TestCase[] tests = new TestCase[] {
				new TestCase("/json/catalog","CatalogDataModel [getLabels()=[LabelsDataModel [getLabel()=VIN Number, getLabelId()=d07bd077-59cf-6532-dde5-fc40bf4b07ff, getLabelType()=VIN_NUMBER]], getCategories()=[CatalogCategoryDataModel [getCategoryId()=Sample Category ID, getCategoryLabel()=Stellantis Vehicle Parts  Catalogs]], getCatalogs()=[CatalogLinksDataModel [getLabel()=Stellantis Original Parts & Technical Documentation, getCategory()=external, getLink()=#, getLinkType()=default, getIcon()=www.adobe.com/content/dam/srp/global/SL_Logo.png, getCategoryId()=Stellantis Vehicle Parts  Catalogs, getCatalogId()=Stellantis Original Parts & Technical Documentation, getAuthField()=authvalue]]]"),
                new TestCase("/json/catalog_null","CatalogDataModel [getLabels()=[], getCategories()=[], getCatalogs()=[]]")				
		};
		
		for(TestCase tc: tests) {
			context.currentResource(tc.jsonPath);
			CatalogDataModel catalogDataModel = context.request().adaptTo(CatalogDataModel.class);
			String actualResponse = catalogDataModel.toString();
			assertEquals(tc.expectedResponse, actualResponse);
		}
	}
}
