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
class TopBannerDataModelTest {
	
	private final AemContext context = new AemContext();
	
	TopBannerDataModel topBannerDataModel;
	
	@Mock
    SrpDomainService srpDomainService;
	
	@BeforeEach
	void setUp() {
		context.addModelsForClasses(TopBannerEntryDataModel.class, TopBannerDataModel.class);
        context.registerService(SrpDomainService.class, srpDomainService);
        lenient().when(srpDomainService.getDomainUrl()).thenReturn("www.adobe.com");
		context.load().json("/com/srp/core/models/common/TopBannerDataModelTest.json", "/json");
		
	}

	@Test
	void testToString() {
		context.currentResource("/json/banner_details");
		topBannerDataModel= context.request().adaptTo(TopBannerDataModel.class);
		String expectedToString = "TopBannerDataModel [getBanners()=[TopBannerEntryDataModel [getCtaLabel()=BANNER IMAGE 1, getImage()=www.adobe.com/content/dam/srp/asset.jpg, getLink()=/content/srp/ee/it/en/after-sales/ir, getLinkType()=default, getStartDate()=2024-11-18T18:30:00Z, getEndDate()=2024-11-28T18:30:00Z]]]";
		String actualToString = topBannerDataModel.toString();
		assertEquals(expectedToString, actualToString);
	}
	
	@Test
	void testNullToString() {
		context.currentResource("/json/banner_null");
		topBannerDataModel = context.request().adaptTo(TopBannerDataModel.class);
		String expResponse = "TopBannerDataModel [getBanners()=[]]";
		String actResponse = topBannerDataModel.toString();
		assertEquals(expResponse, actResponse);
	}

}
