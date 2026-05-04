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

@ExtendWith({AemContextExtension.class, MockitoExtension.class })
class SubscriptionDataModelTest {
	
	private final AemContext context = new AemContext();
	
	SubscriptionDataModel subscriptionDataModel;
	
	@Mock
	SrpDomainService srpDomainService;
	
	@BeforeEach
	void setUp() {
		context.addModelsForClasses(SubscriptionBrandLogoDataModel.class,SubscriptionDataModel.class);
		context.registerService(SrpDomainService.class,srpDomainService);
		lenient().when(srpDomainService.getDomainUrl()).thenReturn("www.adobe.com");
		context.load().json("/com/srp/core/models/common/SubscriptionDataModelTest.json", "/json");	
	}
	
	@Test
	void testToString() {
		context.currentResource("/json/subscription");
		subscriptionDataModel = context.request().adaptTo(SubscriptionDataModel.class);
		String expRespString = "SubscriptionDataModel [getLabels()=[LabelsDataModel [getLabel()=Stellantis Subscriptions, getLabelId()=92eab938-058d-34c4-227d-1306063c9275, getLabelType()=STELLANTIS_SUBSCRIPTIONS]], getApplicationLinks()=[SubscriptionBrandLogoDataModel [getBrandLink()=#, getBrandLogo()=www.adobe.com/content/dam/srp/global/Abarth.png, getAuthField()=authvalue, getCategory()=]]]";
		String actuRespString = subscriptionDataModel.toString();
		assertEquals(expRespString, actuRespString);
	}
	
	@Test
	void testToStringNull() {
		context.currentResource("/json/subscription_null");
		subscriptionDataModel = context.request().adaptTo(SubscriptionDataModel.class);
		String expRespString = "SubscriptionDataModel [getLabels()=[], getApplicationLinks()=[]]";
		String actuRespString = subscriptionDataModel.toString();
		assertEquals(expRespString, actuRespString);
	}

}
