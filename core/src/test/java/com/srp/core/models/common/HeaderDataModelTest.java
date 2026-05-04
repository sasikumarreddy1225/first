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

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class HeaderDataModelTest {

	private final AemContext context = new AemContext();
	
	HeaderDataModel headerDataModel;
	
	@Mock
    SrpDomainService srpDomainService;
	
	@BeforeEach
    void setUp() throws Exception {
        
        context.load().json("/com/srp/core/models/common/HeaderDataModelTest.json", "/json");
    }
	
	@Test
	 void testToString(){
		context.currentResource("/json/header");
		context.addModelsForClasses(HeaderDataModel.class, HeaderApplicationLogoDataModel.class);
        context.registerService(SrpDomainService.class, srpDomainService);
        lenient().when(srpDomainService.getDomainUrl()).thenReturn("www.adobe.com");
        headerDataModel= context.request().adaptTo(HeaderDataModel.class);
        String expectedResponse = "HeaderDataModel [getDesktopErcsBlueLogo()=, getDesktopCatalogLogo()=www.adobe.com/content/dam/srp/asset.jpg, getDesktopBlueLogo()=www.adobe.com/content/dam/srp/global/Sample_document.pdf, getDesktopErcsCatalogLogo()=, getShowNotificationIcon()=true, getDesktopBlueLogoAlt()=Blue Logo, getDesktopCatalogLogoAlt()=Desktop Logo-123, getDesktopErcsBlueLogoAlt()=, getDesktopErcsCatalogLogoAlt()=, getApplicationLinks()=[HeaderApplicationLogoDataModel [getName()=Fb, getLink()=/content/srp/ee/it/en/ir-rp/landingPage, getIcon()=www.adobe.com/content/dam/srp/asset.jpg]], getLabels()=[]]";
        String actualResponse = headerDataModel.toString();
        assertEquals(expectedResponse,actualResponse);
    }
	
	@Test
	void testToStringNull() {
		context.currentResource("/json/header_null");
		context.addModelsForClasses(HeaderDataModel.class, HeaderApplicationLogoDataModel.class);
        context.registerService(SrpDomainService.class, srpDomainService);
        lenient().when(srpDomainService.getDomainUrl()).thenReturn("www.adobe.com");
		headerDataModel= context.request().adaptTo(HeaderDataModel.class);
        String expectedResponse = "HeaderDataModel [getDesktopErcsBlueLogo()=, getDesktopCatalogLogo()=, getDesktopBlueLogo()=, getDesktopErcsCatalogLogo()=, getShowNotificationIcon()=, getDesktopBlueLogoAlt()=, getDesktopCatalogLogoAlt()=, getDesktopErcsBlueLogoAlt()=, getDesktopErcsCatalogLogoAlt()=, getApplicationLinks()=[], getLabels()=[]]";
        String actualResponse = headerDataModel.toString();
        assertEquals(expectedResponse,actualResponse);
	}

}
