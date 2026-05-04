
package com.srp.core.services.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.jcr.RepositoryException;
import org.apache.http.ParseException;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.testing.mock.osgi.MockOsgi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.srp.core.beans.request.PublishContentRequestBean;
import com.srp.core.services.ApiGatewayServiceConfig;
import com.srp.core.services.ResourceResolverService;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class ApiCallsServiceImplTest {
	AemContext ctx = new AemContext();

	@InjectMocks
	ApiCallsServiceImpl impl = new ApiCallsServiceImpl();

	@Mock
	ResourceResolverService resourceResolverService;

	ApiGatewayServiceConfig config = Mockito.mock(ApiGatewayServiceConfig.class);

	private Map<String, String> apiMap = new HashMap<>();

	@BeforeEach
	public final void setUp() throws NoSuchFieldException, SecurityException, IllegalArgumentException,
			IllegalAccessException, LoginException, RepositoryException {
		String[] urls = { "getSrpLabels|dev/api/v1/notification/searchLabels", "updateSRPContent|dev/api/v1/notification/publish" };
		lenient().when(config.apiURLs()).thenReturn(urls);
		Field myVarField = ApiCallsServiceImpl.class.getDeclaredField("apiMap");
		myVarField.setAccessible(true);
		myVarField.set(impl, apiMap);
		Field domainname = ApiCallsServiceImpl.class.getDeclaredField("domainName");
		domainname.setAccessible(true);
		domainname.set(impl, "https://af1ikept75.execute-api.eu-west-1.amazonaws.com/");
		ctx.registerService(ResourceResolverService.class, resourceResolverService);
		MockOsgi.injectServices(impl, ctx.bundleContext());
		lenient().when(resourceResolverService.getResourceResolver()).thenReturn(ctx.resourceResolver());
	}

	
	@Test
	void testActivate() {
		impl.activate(config);
		impl.update(config);
		impl.deactivate(config);
		assertNotNull(impl);
	}
	
	@Test
	void testHttpGetAPICall() throws ParseException, IOException {
		apiMap.put("getSrpLabels", "endpoint");
		assertNotNull(impl.httpGetAPICall("getSrpLabels", "request"));
	}
	
	@Test
	void testUpdateSRPContent() throws ParseException, IOException {
		assertNotNull(impl.updateSRPContent(new PublishContentRequestBean()));
	}
	
	@Test
	void testHttpPostAPICall() throws ParseException, IOException {
		apiMap.put("getSrpLabels", "endpoint");
		assertNotNull(impl.httpPostAPICall("getSrpLabels", "request"));
	}
	
	@Test
	void testGetLabels() throws ParseException, IOException {
		apiMap.put("getSrpLabels", "endpoint");
		assertNotNull(impl.getSRPLabels("response"));
	}



}
