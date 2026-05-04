package com.srp.core.models.common;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.lenient;

import java.lang.reflect.Field;

import com.google.gson.JsonObject;
import com.srp.core.services.SrpDomainService;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class LoginDetailsDataModelTest {

    private final AemContext context = new AemContext();
    
    @InjectMocks
    LoginDetailsDataModel loginDetailsDataModel;
        
    @Mock
    SrpDomainService srpDomainService;

    @BeforeEach
    void setUp() throws Exception {
        context.addModelsForClasses(LoginDetailsDataModel.class, DocumentationDataModel.class, ReachLinksDataModel.class);
		context.registerService(SrpDomainService.class, srpDomainService);
        context.load().json("/com/srp/core/models/common/LoginDetailsDataModelTest.json", "/json");
        context.currentResource("/json/login-details");
		lenient().when(srpDomainService.getDomainUrl()).thenReturn("www.adobe.com");
    }

    @Test
    void testToString(){
    	loginDetailsDataModel = context.request().adaptTo(LoginDetailsDataModel.class);
        String expectedResponse = "LoginDetailsDataModel [getLoginFields()={\"leftHeaderLogo\":\"www.adobe.com/content/dam/srp/asset.jpg\",\"rightHeaderLogo\":\"www.adobe.com/content/dam/srp/asset.jpg\",\"loginLogo\":\"www.adobe.com/content/dam/srp/asset.jpg\",\"desktopImage\":\"www.adobe.com/content/dam/srp/asset.jpg\",\"mobileImage\":\"www.adobe.com/content/dam/srp/asset.jpg\",\"leftHeaderLogoAlt\":\"Left Header LOGO's Alt Text\",\"rightHeaderLogoAlt\":\"Right Header LOGO's Alt Text\",\"loginLogoAlt\":\"Login LOGO's Alt Text\",\"desktopImageAlt\":\"Desktop Image's Alt Text\",\"mobileImageAlt\":\"Mobile Image's Alt Text\"}, getOnBoardDocumentLinks()=[DocumentationDataModel [getLabel()=citreon, getType()=docLink, getLinkType()=newTab, getLink()=, getDocument()=www.adobe.com/content/dam/srp/asset.jpg]], getReachLinks()=[ReachLinksDataModel [getLabel()=reach, getType()=docLink, getLinkType()=newTab, getLink()=, getDocument()=www.adobe.com/content/dam/srp/asset.jpg]], getLabels()=[LabelsDataModel [getLabel()=Login, getLabelId()=65064648-b3d6-f219-98be-396e12feb351, getLabelType()=LOGIN_TITLE]], getRegistrationLink()=www.google.com, getRegistrationLabel()=O0183Y7291]";
    	String actualResponse = loginDetailsDataModel.toString();
        assertEquals(expectedResponse,actualResponse);
    }
    
    @Test
    void testNoLabels(){
    	loginDetailsDataModel = context.request().adaptTo(LoginDetailsDataModel.class);
    	context.currentResource("/json/lgndtls_nolabels");
        String expectedResponse = "LoginDetailsDataModel [getLoginFields()={\"leftHeaderLogo\":\"www.adobe.com/content/dam/srp/asset.jpg\",\"rightHeaderLogo\":\"www.adobe.com/content/dam/srp/asset.jpg\",\"loginLogo\":\"www.adobe.com/content/dam/srp/asset.jpg\",\"desktopImage\":\"www.adobe.com/content/dam/srp/asset.jpg\",\"mobileImage\":\"www.adobe.com/content/dam/srp/asset.jpg\",\"leftHeaderLogoAlt\":\"Left Header LOGO's Alt Text\",\"rightHeaderLogoAlt\":\"Right Header LOGO's Alt Text\",\"loginLogoAlt\":\"Login LOGO's Alt Text\",\"desktopImageAlt\":\"Desktop Image's Alt Text\",\"mobileImageAlt\":\"Mobile Image's Alt Text\"}, getOnBoardDocumentLinks()=[DocumentationDataModel [getLabel()=citreon, getType()=docLink, getLinkType()=newTab, getLink()=, getDocument()=www.adobe.com/content/dam/srp/asset.jpg]], getReachLinks()=[ReachLinksDataModel [getLabel()=reach, getType()=docLink, getLinkType()=newTab, getLink()=, getDocument()=www.adobe.com/content/dam/srp/asset.jpg]], getLabels()=[LabelsDataModel [getLabel()=Login, getLabelId()=65064648-b3d6-f219-98be-396e12feb351, getLabelType()=LOGIN_TITLE]], getRegistrationLink()=www.google.com, getRegistrationLabel()=O0183Y7291]";
    	String actualResponse = loginDetailsDataModel.toString();
        assertEquals(expectedResponse,actualResponse);
    }
    
    @Test
    void testLoginImages(){
    	loginDetailsDataModel = context.request().adaptTo(LoginDetailsDataModel.class);
    	context.currentResource("/json/lgndtls_images");
        String expectedResponse = "LoginDetailsDataModel [getLoginFields()={\"leftHeaderLogo\":\"www.adobe.com/content/dam/srp/asset.jpg\",\"rightHeaderLogo\":\"www.adobe.com/content/dam/srp/asset.jpg\",\"loginLogo\":\"www.adobe.com/content/dam/srp/asset.jpg\",\"desktopImage\":\"www.adobe.com/content/dam/srp/asset.jpg\",\"mobileImage\":\"www.adobe.com/content/dam/srp/asset.jpg\",\"leftHeaderLogoAlt\":\"Left Header LOGO's Alt Text\",\"rightHeaderLogoAlt\":\"Right Header LOGO's Alt Text\",\"loginLogoAlt\":\"Login LOGO's Alt Text\",\"desktopImageAlt\":\"Desktop Image's Alt Text\",\"mobileImageAlt\":\"Mobile Image's Alt Text\"}, getOnBoardDocumentLinks()=[DocumentationDataModel [getLabel()=citreon, getType()=docLink, getLinkType()=newTab, getLink()=, getDocument()=www.adobe.com/content/dam/srp/asset.jpg]], getReachLinks()=[ReachLinksDataModel [getLabel()=reach, getType()=docLink, getLinkType()=newTab, getLink()=, getDocument()=www.adobe.com/content/dam/srp/asset.jpg]], getLabels()=[LabelsDataModel [getLabel()=Login, getLabelId()=65064648-b3d6-f219-98be-396e12feb351, getLabelType()=LOGIN_TITLE]], getRegistrationLink()=www.google.com, getRegistrationLabel()=O0183Y7291]";
    	String actualResponse = loginDetailsDataModel.toString();
        assertEquals(expectedResponse,actualResponse);
    }
    
    @Test
    void testLoginImagesDifferentPath(){
    	loginDetailsDataModel = context.request().adaptTo(LoginDetailsDataModel.class);
    	context.currentResource("/json/lgndtls_differentcontentpath");
        String expectedResponse = "LoginDetailsDataModel [getLoginFields()={\"leftHeaderLogo\":\"www.adobe.com/content/dam/srp/asset.jpg\",\"rightHeaderLogo\":\"www.adobe.com/content/dam/srp/asset.jpg\",\"loginLogo\":\"www.adobe.com/content/dam/srp/asset.jpg\",\"desktopImage\":\"www.adobe.com/content/dam/srp/asset.jpg\",\"mobileImage\":\"www.adobe.com/content/dam/srp/asset.jpg\",\"leftHeaderLogoAlt\":\"Left Header LOGO's Alt Text\",\"rightHeaderLogoAlt\":\"Right Header LOGO's Alt Text\",\"loginLogoAlt\":\"Login LOGO's Alt Text\",\"desktopImageAlt\":\"Desktop Image's Alt Text\",\"mobileImageAlt\":\"Mobile Image's Alt Text\"}, getOnBoardDocumentLinks()=[DocumentationDataModel [getLabel()=citreon, getType()=docLink, getLinkType()=newTab, getLink()=, getDocument()=www.adobe.com/content/dam/srp/asset.jpg]], getReachLinks()=[ReachLinksDataModel [getLabel()=reach, getType()=docLink, getLinkType()=newTab, getLink()=, getDocument()=www.adobe.com/content/dam/srp/asset.jpg]], getLabels()=[LabelsDataModel [getLabel()=Login, getLabelId()=65064648-b3d6-f219-98be-396e12feb351, getLabelType()=LOGIN_TITLE]], getRegistrationLink()=www.google.com, getRegistrationLabel()=O0183Y7291]";
    	String actualResponse = loginDetailsDataModel.toString();
        assertEquals(expectedResponse,actualResponse);
    }

    private void setprivateField(String fieldName, String value) throws Exception {
    	Field field = LoginDetailsDataModel.class.getDeclaredField(fieldName);
    	field.setAccessible(true);
    	field.set(loginDetailsDataModel, value);
    }
    
    @Test
    void testGetLoginFields() throws Exception {
    	loginDetailsDataModel = context.request().adaptTo(LoginDetailsDataModel.class);
        setprivateField("leftHeaderLogo", "/content/dam/srp/asset.jpg");
        setprivateField("rightHeaderLogo", "/content/dam/srp/asset.jpg");
        setprivateField("loginLogo", "/content/dam/srp/asset.jpg");
        setprivateField("desktopImage", "/content/dam/srp/asset.jpg");
        setprivateField("mobileImage", "/content/dam/srp/asset.jpg");
        
    	JsonObject result = loginDetailsDataModel.getLoginFields();
    	
    	assertEquals("www.adobe.com/content/dam/srp/asset.jpg", result.get("leftHeaderLogo").getAsString());
    	assertEquals("www.adobe.com/content/dam/srp/asset.jpg", result.get("rightHeaderLogo").getAsString());
    	assertEquals("www.adobe.com/content/dam/srp/asset.jpg", result.get("loginLogo").getAsString());
    	assertEquals("www.adobe.com/content/dam/srp/asset.jpg", result.get("desktopImage").getAsString());
    	assertEquals("www.adobe.com/content/dam/srp/asset.jpg", result.get("mobileImage").getAsString());
    }
}