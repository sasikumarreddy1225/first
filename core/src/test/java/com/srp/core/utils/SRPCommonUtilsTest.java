package com.srp.core.utils;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.adobe.granite.ui.components.ds.DataSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.LinkedHashMap;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class SRPCommonUtilsTest {
	private final AemContext context = new AemContext(ResourceResolverType.JCR_MOCK);

	@Mock
	ResourceResolver resourceResolver;

	@Mock
	Resource resource, childRes;

	@Mock
	ValueMap valMap;

	@BeforeEach
	public final void setUp() {
		context.load().json("/com/srp/core/services/LandingPageContent.json", "/content/srp/ee/it/en/landing-page");
	}

	@Test
	void testGetTemplateFromResource() {
		String expectedTemplate = "/conf/srp/settings/wcm/templates/landing-page-template";
		String actualTemplate = SRPCommonUtils.getTemplateFromResource("/content/srp/ee/it/en/landing-page",
				context.resourceResolver());
		assertEquals(expectedTemplate, actualTemplate);
	}

	@Test
	void testConvertListToDataSource() {
		LinkedHashMap<String, String> linkedList = new LinkedHashMap<String, String>();
		linkedList.put("key", "value");
		DataSource ds = SRPCommonUtils.convertListToDatasource(context.request(), linkedList);
		assertNotNull(ds);
	}

	@ParameterizedTest
	@ValueSource(strings = { "/content/srp/ee/it/en/sales", "/content/srp/global/en/sales",
			"/content/experience-fragments/srp/ee/it/en/articles",
			"/content/experience-fragments/srp/global/en/articles", "www.google.com" })
	void togetRegionofPage(String arg) {
		assertNotNull(SRPCommonUtils.getPageRegion(arg));
	}

	@Test
	void testFullUrl() {
		SlingHttpServletRequest request = Mockito.mock(SlingHttpServletRequest.class);
		when(request.getScheme()).thenReturn("http");
		when(request.getServerName()).thenReturn("www.adobe.com");
		when(request.getServerPort()).thenReturn(80);

		String fullPath = "http:www.adobe.com/content/dam/image1.jpg";
		String result = SRPCommonUtils.getDeliveryURLWithDomainName(fullPath, request);
		assertEquals(fullPath, result);

	}

	@Test
	void testHttpsDefaultPortUrl() {
		SlingHttpServletRequest request = Mockito.mock(SlingHttpServletRequest.class);
		when(request.getScheme()).thenReturn("https");
		when(request.getServerName()).thenReturn("www.adobe.com");
		when(request.getServerPort()).thenReturn(443);

		String contentPath = "/content/dam/image1.jpg";
		String result = SRPCommonUtils.getDeliveryURLWithDomainName(contentPath, request);
		assertEquals("https://www.adobe.com" + contentPath, result);
	}

	@Test
	void testHttpDefaultPortUrl() {
		SlingHttpServletRequest request = Mockito.mock(SlingHttpServletRequest.class);
		when(request.getScheme()).thenReturn("http");
		when(request.getServerName()).thenReturn("www.adobe.com");
		when(request.getServerPort()).thenReturn(80);

		String contentPath = "/content/dam/image1.jpg";
		String result = SRPCommonUtils.getDeliveryURLWithDomainName(contentPath, request);
		assertEquals("http://www.adobe.com" + contentPath, result);
	}

	@Test
	void testDefaultPortUrlWithHttp() {
		SlingHttpServletRequest request = Mockito.mock(SlingHttpServletRequest.class);
		when(request.getScheme()).thenReturn("http");
		when(request.getServerName()).thenReturn("localhost");
		when(request.getServerPort()).thenReturn(4502);

		String contentPath = "/content/dam/image1.jpg";
		String result = SRPCommonUtils.getDeliveryURLWithDomainName(contentPath, request);
		assertEquals("http://localhost:4502" + contentPath, result);
	}

	@Test
	void testDefaultPortUrlWithHttps() {
		SlingHttpServletRequest request = Mockito.mock(SlingHttpServletRequest.class);
		when(request.getScheme()).thenReturn("https");
		when(request.getServerName()).thenReturn("localhost");
		when(request.getServerPort()).thenReturn(4502);

		String contentPath = "/content/dam/image1.jpg";
		String result = SRPCommonUtils.getDeliveryURLWithDomainName(contentPath, request);
		assertEquals("https://localhost:4502" + contentPath, result);
	}

}