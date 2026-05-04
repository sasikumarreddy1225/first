package com.srp.core.servlets.dialog;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

import java.io.IOException;
import java.util.Arrays;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.testing.mock.osgi.MockOsgi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.srp.core.services.ResourceResolverService;
import com.srp.core.services.impl.ResourceResolverServiceImpl;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class AdminGroupsListDialogServletTest {

	private final AemContext ctx = new AemContext();

	@Mock
	SlingHttpServletRequest req;

	@Mock
	SlingHttpServletResponse resp;

	@Mock
	private QueryBuilder queryBuilder;

	@Mock
	private Session session;

	@Mock
	private Query query;

	@Mock
	SearchResult searchResult;

	@Mock
	Hit hit;

	@Mock
	Node node;

	@Mock
	Property prop1, prop2;

	@Mock
	ResourceResolverService resourceResolverService;

	ResourceResolverServiceImpl service = new ResourceResolverServiceImpl();

	AdminGroupsListDialogServlet servlet = new AdminGroupsListDialogServlet();

	
	@BeforeEach
	void setUp() throws LoginException, RepositoryException {
		ctx.registerService(QueryBuilder.class, queryBuilder);
		ctx.registerService(ResourceResolverService.class, resourceResolverService);
		MockOsgi.injectServices(servlet, ctx.bundleContext());
		ctx.registerInjectActivateService(service);
		lenient().when(resourceResolverService.getResourceResolver()).thenReturn(ctx.resourceResolver());
		lenient().when(queryBuilder.createQuery(any(PredicateGroup.class), any(Session.class))).thenReturn(query);
		lenient().when(query.getResult()).thenReturn(searchResult);
		lenient().when(searchResult.getHits()).thenReturn(Arrays.asList(hit));
		ctx.registerAdapter(ResourceResolver.class, QueryBuilder.class, queryBuilder);
		ctx.registerAdapter(ResourceResolver.class, Session.class, session);
		lenient().when(hit.getNode()).thenReturn(node);
		lenient().when(node.hasProperty("rep:principalName")).thenReturn(true);
		lenient().when(node.getProperty("rep:principalName")).thenReturn(prop1);
		lenient().when(prop1.getString()).thenReturn("everyone");
		lenient().when(node.getProperty("rep:authorizableId")).thenReturn(prop2);
		lenient().when(prop2.getString()).thenReturn("everyone");
		req = ctx.request();
		resp = ctx.response();
	}

	@Test
	void testDoGet() throws IOException {
		servlet.doGet(req, resp);
		assertEquals(200, resp.getStatus());
	}

	@Test
	void testException() throws IOException, LoginException {
		lenient().when(resourceResolverService.getResourceResolver()).thenThrow(new LoginException());
		servlet.doGet(req, resp);
		assertNotNull(resourceResolverService);
	}
}

