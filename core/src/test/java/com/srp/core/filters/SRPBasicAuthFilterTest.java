
package com.srp.core.filters;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.PrintWriter;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.FilterChain;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.settings.SlingSettingsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.srp.core.services.SRPBasicAuthFilterConfig;

public class SRPBasicAuthFilterTest {

	private SRPBasicAuthFilter filter;
	private SlingSettingsService slingSettingsService;
	private SlingHttpServletRequest request;
	private SlingHttpServletResponse response;
	private FilterChain chain;

	@BeforeEach
	public void setUp() {
		filter = new SRPBasicAuthFilter();
		slingSettingsService = mock(SlingSettingsService.class);
		request = mock(SlingHttpServletRequest.class);
		response = mock(SlingHttpServletResponse.class);
		chain = mock(FilterChain.class);

		// Inject mock
		filter.slingSettingsService = slingSettingsService;

		// Activate config
		SRPBasicAuthFilterConfig config = mock(SRPBasicAuthFilterConfig.class);
		when(config.userName()).thenReturn("admin");
		when(config.passWord()).thenReturn("admin123");
		filter.activate(config);
	}

	@Test
	public void testNonSlingRequest() throws Exception {
		filter.doFilter(mock(javax.servlet.ServletRequest.class), mock(javax.servlet.ServletResponse.class), chain);
		verify(chain).doFilter(any(), any());
	}

	@Test
	public void testNonPublishRunMode() throws Exception {
		when(slingSettingsService.getRunModes()).thenReturn(Collections.singleton("author"));
		filter.doFilter(request, response, chain);
		verify(chain).doFilter(request, response);
	}

	@Test
	public void testValidCredentials() throws Exception {
		Set<String> runModes = new HashSet<>();
		runModes.add("publish");
		when(slingSettingsService.getRunModes()).thenReturn(runModes);

		String credentials = "admin:admin123";
		String encoded = "Basic " + java.util.Base64.getEncoder().encodeToString(credentials.getBytes());
		when(request.getHeader("Authorization")).thenReturn(encoded);

		filter.doFilter(request, response, chain);
		verify(chain).doFilter(request, response);
	}

	@Test
	public void testInvalidCredentials() throws Exception {
		Set<String> runModes = new HashSet<>();
		runModes.add("publish");
		when(slingSettingsService.getRunModes()).thenReturn(runModes);

		String credentials = "admin:wrongpass";
		String encoded = "Basic " + java.util.Base64.getEncoder().encodeToString(credentials.getBytes());
		when(request.getHeader("Authorization")).thenReturn(encoded);

		PrintWriter writer = mock(PrintWriter.class);
		when(response.getWriter()).thenReturn(writer);

		filter.doFilter(request, response, chain);

		verify(response).setStatus(SlingHttpServletResponse.SC_UNAUTHORIZED);
		verify(response).setHeader("WWW-Authenticate", "Basic realm=\"Protected Content\"");
		verify(writer).write("HTTP Status 401 – Unauthorized");
	}

	@Test
	public void testMissingAuthorizationHeader() throws Exception {
		Set<String> runModes = new HashSet<>();
		runModes.add("publish");
		when(slingSettingsService.getRunModes()).thenReturn(runModes);

		when(request.getHeader("Authorization")).thenReturn(null);

		PrintWriter writer = mock(PrintWriter.class);
		when(response.getWriter()).thenReturn(writer);

		filter.doFilter(request, response, chain);

		verify(response).setStatus(SlingHttpServletResponse.SC_UNAUTHORIZED);
		verify(response).setHeader("WWW-Authenticate", "Basic realm=\"Protected Content\"");
		verify(writer).write("HTTP Status 401 – Unauthorized");
	}
}
