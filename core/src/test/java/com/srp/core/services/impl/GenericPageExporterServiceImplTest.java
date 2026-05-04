package com.srp.core.services.impl;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.apache.jackrabbit.api.security.user.Group;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import org.apache.jackrabbit.api.security.user.User;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.Template;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.srp.core.beans.request.TagsBeanClass;
import com.srp.core.beans.request.TagsListBean;
import com.srp.core.models.common.ExpericenceFragmentsRefDataModel;
import com.srp.core.models.common.MenuLinksReferenceDataModel;
import com.srp.core.services.AuthorInfoCheckService;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.felix.utils.json.JSONParser;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class GenericPageExporterServiceImplTest {

	private final AemContext context = new AemContext(ResourceResolverType.JCR_MOCK);

	@Mock
	private QueryBuilder builder;

	GenericPageExporterServiceImpl impl = new GenericPageExporterServiceImpl();

	@Mock
	private Query query;

	@Mock
	SearchResult searchResult;

	@Mock
	Hit mockhit1;

	@Mock
	Resource resource, pageRes;

	@Mock
	Page page;

	@Mock
	User user;

	@Mock
	Group everyone;

	@Mock
	ResourceResolver resourceResolver;

	@Mock
	AuthorInfoCheckService authorInfoCheckService;
	
	@Mock
	MenuLinksReferenceDataModel menuLinksReferenceDataModel;
	
	@Mock
	ExpericenceFragmentsRefDataModel expericenceFragmentsRefDataModel;
	
	@Mock
	Iterator<Page> pagetIterator;

	@Mock
	TagsBeanClass genericTags, componentTags;

	TagsListBean nullTags = new TagsListBean();

	TagsListBean tags = new TagsListBean();

	JSONParser parser;

	List<String> adminGroups = Arrays.asList("everyone");
	String[] roles = { "after-sales" };
	String[] geo = { "usa" };
	String[] brands = { "distrigo" };
	String pagePath = "";
	
	@Mock SlingHttpServletRequest request;

	@Mock
	private Resource contentResource;


	@Mock
	private Template template;

	@BeforeEach
	public final void setUp() throws Exception {
		context.registerService(QueryBuilder.class, builder);
		context.registerInjectActivateService(impl);
		context.load().json("/com/srp/core/services/LandingPageContent.json",
				"/content/srp/ee/it/en/ir-rp/landingPage");
		lenient().when(builder.createQuery(any(PredicateGroup.class), any(Session.class))).thenReturn(query);
		lenient().when(query.getResult()).thenReturn(searchResult);
		lenient().when(searchResult.getHits()).thenReturn(Arrays.asList(mockhit1));
		context.registerAdapter(ResourceResolver.class, User.class, user);
		context.registerAdapter(ResourceResolver.class, QueryBuilder.class, builder);
		context.registerService(AuthorInfoCheckService.class, authorInfoCheckService);
		lenient().when(authorInfoCheckService.getResourceTags(any())).thenReturn(componentTags);
		lenient().when(authorInfoCheckService.getTagValuesOfAuthor(any(), any())).thenReturn(genericTags);
		lenient().when(user.memberOf()).thenReturn(Arrays.asList(everyone).iterator());
		lenient().when(authorInfoCheckService.getAdminGroups(any())).thenReturn(adminGroups);
		lenient().when(everyone.getID()).thenReturn("everyone");
		context.currentResource("/content/srp/ee/it/en/ir-rp/landingPage/jcr:content/root/container/srp_container");
		lenient().when(mockhit1.getResource()).thenReturn(context.currentResource());
		nullTags.setBrandTag(null);
		nullTags.setGeoTag(null);
		nullTags.setRoleTag(null);
		tags.setBrandTag(this.brands);
		tags.setGeoTag(this.geo);
		tags.setRoleTag(this.roles);
	}

	@Test
	void testGetPageAsJson() {
		String json = impl.getPageAsJson("/content/srp/ee/it/en/ir-rp/landingPage", context.resourceResolver(), tags,
				context.request(), "temp");
		assertNotNull(json);
	}

	@Test
	void testModelNoFilters() throws RepositoryException {
		lenient().when(mockhit1.getResource())
				.thenReturn(context.resourceResolver().getResource("/content/srp/ee/it/en/ir-rp/landingPage"));
		String json = impl.getPageAsJson("/content/srp/ee/it/en/ir-rp/landingPage", context.resourceResolver(),
				nullTags, context.request(), "temp");
		assertNotNull(json);
	}

	
	 @Test void testCompareTagsWithNoMatch() { 
		 
		 JsonArray compTags = new JsonArray();
	     JsonObject object = new JsonObject(); 
	     object.addProperty("tag", "tag-1");
	     compTags.add(object);
	     String[] tags = { "developer" };
	     boolean result = impl.compareTags(compTags, tags); 
	     assertFalse(result); 
	     }
	  
	 @Test void testCompareTagsWithMatch() { 
	     
		 JsonArray compTags = new JsonArray();
	     JsonObject object = new JsonObject(); 
	     object.addProperty("tag", "tag-1");
	     compTags.add(object);
	     String[] tags = { "tag-1" };
	     boolean result = impl.compareTags(compTags, tags);
	     assertTrue(result); 
	     }
	 
	 @Test void testCompareTagsWithNull() { 
		 JsonArray compTags = new JsonArray();
	     String[] tags = { "tag-1" };
	     boolean result = impl.compareTags(compTags, tags); 
	     assertTrue(result); 
	 }
	 

	@Test
	void testCheckTagsWithMatch() {
		JsonObject object = new JsonObject();

		JsonArray roleTags = new JsonArray();
		JsonObject roleObject = new JsonObject();
		roleObject.addProperty("tag", "admin");
		roleTags.add(roleObject);
		object.add("roleTags", roleTags);

		JsonArray brandTags = new JsonArray();
		JsonObject brandObject = new JsonObject();
		brandObject.addProperty("tag", "bmw");
		brandTags.add(brandObject);
		object.add("brandTags", brandTags);

		JsonArray geoTags = new JsonArray();
		JsonObject geoObject = new JsonObject();
		geoObject.addProperty("tag", "europe");
		geoTags.add(geoObject);
		object.add("geoTags", geoTags);

		boolean result = impl.checkTags(object);
		assertTrue(result);
	}
	
	@Test
	void testGetMenulinksJson() throws RepositoryException {
		String json = impl.getMenulinksJson("/content/srp/ee/it/en/ir-rp/landingPage", context.resourceResolver(), tags, context.request(), "temp");
		assertNotNull(json);
	}

	@Test
	void testGetMenuLinksData() throws RepositoryException {
		lenient().when(resource.adaptTo(MenuLinksReferenceDataModel.class)).thenReturn(menuLinksReferenceDataModel);
		lenient().when(menuLinksReferenceDataModel.getMenulinkPath()).thenReturn("/content/srp/en/menu");
		lenient().when(resource.getResourceType()).thenReturn("srp/components/common/menuLinks");
		lenient().when(resourceResolver.resolve("/content/srp/en/menu")).thenReturn(pageRes);
		lenient().when(pageRes.adaptTo(Page.class)).thenReturn(page);
		Iterator<Page> mockIterator = mock(Iterator.class);
		lenient().when(mockIterator.hasNext()).thenReturn(false);
		lenient().when(page.listChildren()).thenReturn(mockIterator);
		JsonObject data = impl.getMenulinksData(resource, resourceResolver, context.request());
		assertNotNull(data);
	}
	
	@Test
	void testGetExperienceFragData() throws RepositoryException {
		String json = impl.getExperienceFragData("/content/srp/ee/it/en/ir-rp/landingPage", context.resourceResolver(),
				tags, context.request(), "temp");
		assertNotNull(json);
	}

	 @Test
	    void testGetExperienceData() throws Exception {
	        lenient().when(resource.adaptTo(ExpericenceFragmentsRefDataModel.class)).thenReturn(expericenceFragmentsRefDataModel);
	        lenient().when(expericenceFragmentsRefDataModel.getExpFragPath()).thenReturn("/content/srp/en/menu");
	        lenient().when(resource.getResourceType()).thenReturn("srp/components/common/menuLinks");
	        lenient().when(resourceResolver.resolve("/content/srp/en/menu")).thenReturn(pageRes);
	        lenient().when(pageRes.adaptTo(Page.class)).thenReturn(page);
	        lenient().when(page.getTemplate()).thenReturn(template);
	        lenient().when(template.getPath()).thenReturn("/conf/srp/settings/wcm/templates/experience-fragment");
	        lenient().when(page.getContentResource()).thenReturn(contentResource);
	        lenient().when(contentResource.getPath()).thenReturn("/content/srp/en/menu/jcr:content");
	        JsonObject result = impl.getExperienceData(resource, resourceResolver, request);
	        assertNotNull(result);
	        assertTrue(result.has("data"));
	        assertNotNull(result.getAsJsonArray("data"));
	        assertTrue(result.getAsJsonArray("data").size() >= 0);
	    }
}