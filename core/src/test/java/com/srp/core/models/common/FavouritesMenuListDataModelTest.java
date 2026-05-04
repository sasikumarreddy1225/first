package com.srp.core.models.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;

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

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class FavouritesMenuListDataModelTest {
	

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
		context.addModelsForClasses(FavouritesMenuListDataModel.class);
		context.load().json("/com/srp/core/models/common/MenuLinksDataModelTest.json", "/json");
		context.currentResource("/json/favouritesmenulist");
		context.registerService(SrpDomainService.class, srpDomainService);
		lenient().when(srpDomainService.getDomainUrl()).thenReturn("www.adobe.com");
	}
	
	@Test
	void toStringWithoutIDTestCase() {
		context.currentResource("/json/favouritesmenulist_withoutId");
		FavouritesMenuListDataModel favmenulist = req.adaptTo(FavouritesMenuListDataModel.class);
		String actualResp = favmenulist.toString();
		String expectedResp = "FavouritesMenuListDataModel [getLabel()=TI Link, getLinkType()=newTab, getType()=webLink, getLink()=www.google.com, getDocumentLink()=, getLinkDescription()=, getCategory()=external, getAuthField()=SAML, getCatalogId()=Stellantis Original Parts & Technical Documentation]";
		assertEquals(expectedResp, actualResp);
		assertNotNull(favmenulist.getName());
	}

	@Test
	void changeWcmMode() {
		WCMMode.DESIGN.toRequest(req);
		FavouritesMenuListDataModel favmenulist = req.adaptTo(FavouritesMenuListDataModel.class);
		String actual = WCMMode.fromRequest(req).name();
		String expected = "DESIGN";
		assertEquals(expected, actual);
		assertNotNull(favmenulist);
	}

	@Test
	void toStringTestCase() {
		WCMMode.EDIT.toRequest(req);
		FavouritesMenuListDataModel favmenulist = req.adaptTo(FavouritesMenuListDataModel.class);
		String actualResp = favmenulist.toString();
		String expectedResp = "FavouritesMenuListDataModel [getLabel()=TI Link, getLinkType()=newTab, getType()=docLink, getLink()=www.google.com, getDocumentLink()=www.adobe.com/content/dam/sample.pdf, getLinkDescription()=, getCategory()=external, getAuthField()=SAML, getCatalogId()=Stellantis Original Parts & Technical Documentation]";
		assertEquals(expectedResp, actualResp);
		assertNotNull(favmenulist.getName());
	}

}
