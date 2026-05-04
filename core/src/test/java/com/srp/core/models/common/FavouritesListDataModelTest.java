package com.srp.core.models.common;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.srp.core.services.SrpDomainService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.lenient;


@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class FavouritesListDataModelTest {
	
	private final AemContext context = new AemContext();
	FavouritesListDataModel favouritesListDataModel;

	@Mock
	SrpDomainService srpDomainService;

	@BeforeEach
	void setUp() {
		context.addModelsForClasses(FavouritesListDataModel.class);
		context.load().json("/com/srp/core/models/common/FavouritesListDataModelTest.json", "/json");
		
	}
	
	@Test
	void testToString(){
		context.currentResource("/json/favourite_title");
		context.registerService(SrpDomainService.class,srpDomainService);
		lenient().when(srpDomainService.getDomainUrl()).thenReturn("www.adobe.com");
		favouritesListDataModel= context.request().adaptTo(FavouritesListDataModel.class);
		String expectedToString = "FavouritesListDataModel [getLinks()=[FooterLinksEntryModel [getLabel()=WIDGET DOC LABEL 1, getLinkType()=newtab, getType()=docLink, getDocument()=www.adobe.com/content/dam/srp/global/Sample_document.pdf, getCategory()=external, getWebLink()=], FooterLinksEntryModel [getLabel()=WIDGET WEB LABEL 1, getLinkType()=newtab, getType()=webLink, getDocument()=, getCategory()=external, getWebLink()=/content/srp/ee]], getLabels()=[]]";
		String actualToString = favouritesListDataModel.toString();
		assertEquals(expectedToString,actualToString);

	}
	
	@Test
	void testNullGetLinks() {
		context.currentResource("/json/favourites_null");
		context.registerService(SrpDomainService.class,srpDomainService);
		lenient().when(srpDomainService.getDomainUrl()).thenReturn("www.adobe.com");
		favouritesListDataModel= context.request().adaptTo(FavouritesListDataModel.class);
		String expectedRes = "FavouritesListDataModel [getLinks()=[], getLabels()=[]]";
		String actualRes = favouritesListDataModel.toString();
		assertEquals(expectedRes, actualRes);
	}

}
