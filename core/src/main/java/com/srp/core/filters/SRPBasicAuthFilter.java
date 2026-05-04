
package com.srp.core.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.engine.EngineConstants;
import org.apache.sling.settings.SlingSettingsService;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.Designate;

import com.srp.core.services.SRPBasicAuthFilterConfig;

@Component(
		service = Filter.class, 
		immediate = true, 
		property = {
		EngineConstants.SLING_FILTER_SCOPE + "=" + 
		EngineConstants.FILTER_SCOPE_REQUEST,
		"sling.filter.pattern=^(?:/content/srp/.*|/content/experience-fragments/srp/.*|/content/dam/srp/.*)$"
		})

@Designate(ocd = SRPBasicAuthFilterConfig.class)
public class SRPBasicAuthFilter implements Filter {

	private String validUser;
	private String validPass;

	@Reference
	SlingSettingsService slingSettingsService;

	@Activate
	@Modified
	protected void activate(SRPBasicAuthFilterConfig config) {
		this.validUser = config.userName();
		this.validPass = config.passWord();
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if (!(request instanceof SlingHttpServletRequest) || !(response instanceof SlingHttpServletResponse)) {
			chain.doFilter(request, response);
			return;
		}

		String runMode = slingSettingsService.getRunModes().toString();
		if (!runMode.contains("publish")) {
			chain.doFilter(request, response);
			return;
		}

		SlingHttpServletRequest slingRequest = (SlingHttpServletRequest) request;
		SlingHttpServletResponse slingResponse = (SlingHttpServletResponse) response;

		String authHeader = slingRequest.getHeader("Authorization");

		if (authHeader != null && authHeader.startsWith("Basic ")) {
			String base64Credentials = authHeader.substring("Basic ".length()).trim();
			String credentials = new String(Base64.decodeBase64(base64Credentials));
			String[] values = credentials.split(":", 2);
			if (values.length == 2 && values[0].equals(validUser) && values[1].equals(validPass)) {
				chain.doFilter(request, response); // allow\
				return;
			}
		}

		slingResponse.setStatus(SlingHttpServletResponse.SC_UNAUTHORIZED);
		slingResponse.setHeader("WWW-Authenticate", "Basic realm=\"Protected Content\"");
		slingResponse.getWriter().write("HTTP Status 401 – Unauthorized");
	}

	@Override
	public void init(FilterConfig filterConfig) {
		/* Initialize the Filter Configuration */
	}

	@Override
	public void destroy() {
		/* Initialize the Filter Configuration */
	}

}
