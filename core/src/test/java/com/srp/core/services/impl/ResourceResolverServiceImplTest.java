package com.srp.core.services.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class ResourceResolverServiceImplTest {
	
	private final AemContext context = new AemContext();
	
	ResourceResolverServiceImpl resourceResolverService = new ResourceResolverServiceImpl();
	
	
	@Mock
	ResourceResolverFactory resourceResolverFactory;
	
	@Mock
	ResourceResolver resourceResolver;
	
	@BeforeEach
	public final void setUp() throws Exception {
		PrivateAccessor.setField(resourceResolverService, "resolverFactory", resourceResolverFactory);
		context.registerService(ResourceResolverFactory.class, resourceResolverFactory, org.osgi.framework.Constants.SERVICE_RANKING,
				Integer.MAX_VALUE);
		lenient().when(resourceResolverFactory.getServiceResourceResolver(ArgumentMatchers.anyMap()))
		.thenReturn(resourceResolver);
	}
	
	@Test
	void testGetResourceResolver() throws LoginException {
		ResourceResolver resolver = resourceResolverService.getResourceResolver();
		assertNotNull(resolver);
	}
	
	@Test
	void testGetWriteResourceResolver() throws LoginException {
		ResourceResolver resolver = resourceResolverService.getWriteResourceResolver();
		assertNotNull(resolver);
	}
	
	@Test
	void testGetCloseResourceResolver() throws LoginException {
		ResourceResolver resolver = resourceResolverService.getWriteResourceResolver();
		resourceResolverService.closeResourceResolver(resolver);
		assertFalse(resolver.isLive());
	}
}