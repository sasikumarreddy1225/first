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

import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.PersistenceException;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class UserMenuDataModelTest {

	private final AemContext context = new AemContext();
	UserMenuDataModel userMenuDataModel;
	@Mock
	SrpDomainService srpDomainService;
	
	@Mock
    ModifiableValueMap mvp;
	

	@BeforeEach
	void setUp() throws PersistenceException{
		context.addModelsForClasses(UserMenuDataModel.class, UserMenuEntryDataModel.class);
		context.load().json("/com/srp/core/models/common/UserMenuDataModelTest.json", "/json");
		context.currentResource("/json/userMenu");
		context.registerService(SrpDomainService.class, srpDomainService);
		lenient().when(srpDomainService.getDomainUrl()).thenReturn("www.adobe.com");
		
	}

	@Test
	void testToString(){
		context.currentResource("/json/userMenu");
		userMenuDataModel= context.request().adaptTo(UserMenuDataModel.class);
		String actualToString = userMenuDataModel.toString();
		String  expectedToString = "UserMenuDataModel [getUserMenuLinks()=[UserMenuEntryDataModel [getLabel()=LABEL1, getCategory()=external, getLink()=#, getLinkDescription()=desc1, getLinkType()=default, getAuthField()=SAML, getType()=docLink, getDocumentLink()=www.adobe.com/content/dam/srp/global/Legal-Notice.docx, getCatalogId()=Stellantis], UserMenuEntryDataModel [getLabel()=Label2, getCategory()=external, getLink()=#, getLinkDescription()=desc2, getLinkType()=default, getAuthField()=SAML, getType()=docLink, getDocumentLink()=www.adobe.com/content/dam/srp/global/Project-Legal-Notice.docx, getCatalogId()=Original Parts]], getLabels()=[LabelsDataModel [getLabel()=, getLabelId()=1234, getLabelType()=nt:unstructured]]]";
		assertEquals(expectedToString,actualToString);

	}
	
	@Test
	void testNullToString() {
		 context.currentResource("/json/userMenu_null");
		 userMenuDataModel= context.request().adaptTo(UserMenuDataModel.class);
	     String actualResponse = userMenuDataModel.toString();
	     String expectedResponse = "UserMenuDataModel [getUserMenuLinks()=null, getLabels()=[]]";
	     assertEquals(expectedResponse,actualResponse);
	}

}



