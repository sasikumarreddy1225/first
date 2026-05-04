package com.srp.core.models.common;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.day.cq.wcm.api.WCMMode;
import com.srp.core.services.SrpDomainService;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class MenuLinksDataModelTest {

	private final AemContext context = new AemContext();

	@Mock
	Resource resource;

	@Mock
	ResourceResolver resolver;
	
	@Mock
	SrpDomainService srpDomainService;
	
	@Mock
    ModifiableValueMap mvp;
	
	SlingHttpServletRequest req = context.request();

	@BeforeEach
	void setUp() throws PersistenceException {
		context.addModelsForClasses(MenuLinksDataModel.class);
		context.load().json("/com/srp/core/models/common/MenuLinksDataModelTest.json", "/json");
		context.currentResource("/json/menulinks");
		context.registerService(SrpDomainService.class, srpDomainService);
		lenient().when(srpDomainService.getDomainUrl()).thenReturn("www.adobe.com");
	}
	
	@Test
	void toStringWithoutIDTestCase() {
		context.currentResource("/json/menulinks_withoutID");
		MenuLinksDataModel menulinks = req.adaptTo(MenuLinksDataModel.class);
		String actualResp = menulinks.toString();
		String expectedResp = "MenuLinksDataModel [getLabel()=Job Card List, getLinkType()=default, getType()=webLink, getLink()=www.google.com, getDocumentLink()=, getLinkDescription()=<p>This is Job Cards List</p>\r\n, isDefault()=false, getCategory()=external, getAuthField()=authvalue, getCatalogId()=Stellantis Original Parts & Technical Documentation]";
		assertEquals(expectedResp, actualResp);
		assertNotNull(menulinks.getName());
	}

	@Test
	void changeWcmMode() {
		WCMMode.DESIGN.toRequest(req);
		MenuLinksDataModel menulinks = req.adaptTo(MenuLinksDataModel.class);
		String actual = WCMMode.fromRequest(req).name();
		String expected = "DESIGN";
		assertEquals(expected, actual);
		assertNotNull(menulinks);
	}

	@Test
	void toStringTestCase() {
		WCMMode.EDIT.toRequest(req);
		MenuLinksDataModel menulinks = req.adaptTo(MenuLinksDataModel.class);
		String actualResp = menulinks.toString();
		String expectedResp = "MenuLinksDataModel [getLabel()=Job Card List, getLinkType()=default, getType()=docLink, getLink()=www.google.com, getDocumentLink()=www.adobe.com/content/dam/sample.pdf, getLinkDescription()=<p>This is Job Cards List</p>\r\n, isDefault()=false, getCategory()=external, getAuthField()=authvalue, getCatalogId()=Stellantis Original Parts & Technical Documentation]";
		assertEquals(expectedResp, actualResp);
		assertNotNull(menulinks.getName());
	}

}
