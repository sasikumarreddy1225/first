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
class FooterLinksDataModelTest {
	
	private final AemContext context = new AemContext();
	FooterLinksDataModel footerLinksDataModel;
	
	@Mock
	SrpDomainService srpDomainService;

	@BeforeEach
	void setUp() {
		context.addModelsForClasses(FooterLinksDataModel.class, FooterLinksEntryModel.class);
		context.load().json("/com/srp/core/models/common/FooterLinksDataModelTest.json", "/json");
		context.registerService(SrpDomainService.class, srpDomainService);
		lenient().when(srpDomainService.getDomainUrl()).thenReturn("www.adobe.com");
	}

	@Test
	void testToString(){
		context.currentResource("/json/footer_links");
		footerLinksDataModel= context.request().adaptTo(FooterLinksDataModel.class);
		String actualToString = footerLinksDataModel.toString();
		String  expectedToString = "FooterLinksDataModel [getLinks()=[FooterLinksEntryModel [getLabel()=Terms and Conditions, getLinkType()=default, getType()=docLink, getDocument()=www.adobe.com/content/dam/srp/global/Sample_document.pdf, getCategory()=external, getWebLink()=], FooterLinksEntryModel [getLabel()=Privacy and Policies, getLinkType()=newtab, getType()=webLink, getDocument()=, getCategory()=external, getWebLink()=/content/srp/global/en/login]], getLabels()=[LabelsDataModel [getLabel()=, getLabelId()=1234, getLabelType()=nt:unstructured]]]";
		assertEquals(expectedToString,actualToString);

	}

	@Test
	void testNullToString() {
		context.currentResource("/json/footer_null");
		footerLinksDataModel= context.request().adaptTo(FooterLinksDataModel.class);
		String actualToString = footerLinksDataModel.toString();
		String expToString = "FooterLinksDataModel [getLinks()=[], getLabels()=[]]";
		assertEquals(expToString, actualToString);
	}
}
