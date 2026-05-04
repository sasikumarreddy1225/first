package com.srp.core.servlets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.servlet.ServletException;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletRequest;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.srp.core.services.GenericPageExporterInterface;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class GenericPageExporterServletTest {

	private final AemContext context = new AemContext();

	@Mock
	private MockSlingHttpServletRequest request;

	private MockSlingHttpServletResponse response;

	@Mock
	GenericPageExporterInterface genericPageExporterInterface;

	@InjectMocks
	GenericPageExporterServlet servlet = new GenericPageExporterServlet();

	String roleParams = "sales,service";
	String geoParams = "usa,italy";
	String brandParams = "fiat";

	@BeforeEach
	public final void setUp() throws Exception {
		System.setProperty("javax.xml.XMLConstants.FEATURE_SECURE_PROCESSING", "false");
		Resource resource = context.load().json("/com/srp/core/services/LandingPageContent.json",
				"/content/srp/ee/it/en/admin-landing-page");
		String responseFile = "src/test/resources/com/srp/core/services/GenericExporterServletResponse.json";
		String responseJson = new String(Files.readAllBytes(Paths.get(responseFile)));
		context.registerService(GenericPageExporterInterface.class, genericPageExporterInterface,
				org.osgi.framework.Constants.SERVICE_RANKING, Integer.MAX_VALUE);
		lenient().when(genericPageExporterInterface.getPageAsJson(any(), any(), any(), any(), any()))
				.thenReturn(responseJson);
		context.request().setResource(resource);
		request = context.request();
		response = context.response();
	}

	@Test
	void testDoGet() throws ServletException, IOException {
		servlet.doGet(request, response);
		assertEquals(200, response.getStatus());
	}

	@Test
	void testDoGetWithParams() throws ServletException, IOException {
		request.addRequestParameter("roleTags", roleParams);
		request.addRequestParameter("geoTags", geoParams);
		request.addRequestParameter("brandTags", brandParams);
		servlet.doGet(request, response);
		assertEquals(200, response.getStatus());
	}

}
