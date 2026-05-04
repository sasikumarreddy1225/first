package com.srp.core.models.common;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.jcr.RepositoryException;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.lenient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.srp.core.beans.request.TagsBeanClass;
import com.srp.core.services.AuthorInfoCheckService;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.apache.jackrabbit.api.security.user.Group;
import org.apache.jackrabbit.api.security.user.User;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class SRPContainerDataModelTest {

	private final AemContext ctx = new AemContext();

	List<String> adminGroups = Arrays.asList("everyone");

	@Mock
	User user;

	@Mock
	Group everyone, other;

	@Mock
	Resource childRes;

	@Mock
	AuthorInfoCheckService authorInfoCheckService;

	TagsBeanClass genericTags = new TagsBeanClass();
	TagsBeanClass componentTags = new TagsBeanClass();

	ObjectMapper mapper = new ObjectMapper();
	List<String> role = Arrays.asList("sales-manager");
	List<String> geo = Arrays.asList("usa");
	List<String> brands = Arrays.asList("fiat");

	@BeforeEach
	void setUp() throws RepositoryException {
		Resource rs = ctx.load().json("/com/srp/core/models/common/SRPContainerDataModelTest.json", "/json");
		ctx.currentResource("/json/srp_container");
		ctx.addModelsForClasses(SRPContainerDataModel.class);
		ResourceResolver resourceResolver = rs.getResourceResolver();
		ctx.registerService(ResourceResolver.class, resourceResolver);
		ctx.registerAdapter(ResourceResolver.class, User.class, user);
		ctx.registerService(AuthorInfoCheckService.class, authorInfoCheckService);
		lenient().when(authorInfoCheckService.getResourceTags(any())).thenReturn(componentTags);
		lenient().when(authorInfoCheckService.getTagValuesOfAuthor(any(), any())).thenReturn(genericTags);
		lenient().when(authorInfoCheckService.getAdminGroups(any())).thenReturn(adminGroups);
		lenient().when(everyone.getID()).thenReturn("everyone");
		lenient().when(user.memberOf()).thenReturn(Arrays.asList(everyone).iterator());
	}

	@Test
	void toStringTestCase() {
		SRPContainerDataModel container = ctx.request().adaptTo(SRPContainerDataModel.class);
		String actualResp = container.toString();
		String expectedResp = "SRPContainerDataModel [getGeoTags()=[], getRoleTags()=[], getBrandTags()=[], getChildren()=[MockResource [path=/json/srp_container/login_details, props=org.apache.sling.testing.resourceresolver.MockValueMap@7bf3d02f : org.apache.sling.api.wrappers.ValueMapDecorator@7bf3d02f : {mobileImage=/content/dam/srp/asset.jpg, loginLogo=/content/dam/srp/asset.jpg, desktopImage=/content/dam/srp/asset.jpg, leftHeaderLogo=/content/dam/srp/asset.jpg, rightHeaderLogo=/content/dam/srp/asset.jpg, sling:resourceType=srp/components/common/login-details, jcr:primaryType=nt:unstructured}]], getAuthorConfigError()=null, getNoAuthorPermissions()=null]";
		assertEquals(expectedResp, actualResp);
	}

	@Test
	void toStringTestCaseForAuthor() throws RepositoryException {
		lenient().when(other.getID()).thenReturn("others");
		lenient().when(user.memberOf()).thenReturn(Arrays.asList(other).iterator());
		SRPContainerDataModel container = ctx.request().adaptTo(SRPContainerDataModel.class);
		String actualResp = container.toString();
		String expectedResp = "SRPContainerDataModel [getGeoTags()=[], getRoleTags()=[], getBrandTags()=[], getChildren()=[], getAuthorConfigError()=null, getNoAuthorPermissions()=true]";
		assertEquals(expectedResp, actualResp);
	}

	@Test
	void toStringTestCasewithFilters() {
		ctx.currentResource("/json/srp_container_with_filters");
		genericTags.setRoleTags(role);
		genericTags.setBrandTags(brands);
		genericTags.setGeoTags(geo);
		componentTags.setRoleTags(role);
		componentTags.setBrandTags(brands);
		componentTags.setGeoTags(geo);
		SRPContainerDataModel container = ctx.request().adaptTo(SRPContainerDataModel.class);
		String actualResp = container.toString();
		String expectedResp = "SRPContainerDataModel [getGeoTags()=[srp:geo/usa], getRoleTags()=[srp:position/04-sales-manager], getBrandTags()=[srp:brand/fiat], getChildren()=[MockResource [path=/json/srp_container_with_filters/login_details, props=org.apache.sling.testing.resourceresolver.MockValueMap@7bf3d02f : org.apache.sling.api.wrappers.ValueMapDecorator@7bf3d02f : {mobileImage=/content/dam/srp/asset.jpg, loginLogo=/content/dam/srp/asset.jpg, desktopImage=/content/dam/srp/asset.jpg, leftHeaderLogo=/content/dam/srp/asset.jpg, rightHeaderLogo=/content/dam/srp/asset.jpg, sling:resourceType=srp/components/common/login-details, jcr:primaryType=nt:unstructured}]], getAuthorConfigError()=null, getNoAuthorPermissions()=null]";
		assertEquals(expectedResp, actualResp);
	}

	@Test
	void withoutChildResourceTestCase() {
		ctx.currentResource("/json/srp_container_without_children");
		SRPContainerDataModel container = ctx.request().adaptTo(SRPContainerDataModel.class);
		assertNotNull(container.getChildren());
	}

	@Test
	void toStringTestCaseForAuthorWithFilters() throws RepositoryException, IOException {
		ctx.currentResource("/json/srp_container_with_filters");
		lenient().when(other.getID()).thenReturn("others");
		lenient().when(user.memberOf()).thenReturn(Arrays.asList(other).iterator());
		genericTags.setRoleTags(role);
		genericTags.setBrandTags(brands);
		genericTags.setGeoTags(geo);
		componentTags.setRoleTags(role);
		componentTags.setBrandTags(brands);
		componentTags.setGeoTags(geo);
		SRPContainerDataModel container = ctx.request().adaptTo(SRPContainerDataModel.class);
		String actualResp = container.toString();
		String expectedResp = "SRPContainerDataModel [getGeoTags()=[srp:geo/usa], getRoleTags()=[srp:position/04-sales-manager], getBrandTags()=[srp:brand/fiat], getChildren()=[MockResource [path=/json/srp_container_with_filters/login_details, props=org.apache.sling.testing.resourceresolver.MockValueMap@7bf3d02f : org.apache.sling.api.wrappers.ValueMapDecorator@7bf3d02f : {mobileImage=/content/dam/srp/asset.jpg, loginLogo=/content/dam/srp/asset.jpg, desktopImage=/content/dam/srp/asset.jpg, leftHeaderLogo=/content/dam/srp/asset.jpg, rightHeaderLogo=/content/dam/srp/asset.jpg, sling:resourceType=srp/components/common/login-details, jcr:primaryType=nt:unstructured}]], getAuthorConfigError()=null, getNoAuthorPermissions()=null]";
		assertEquals(expectedResp, actualResp);
	}

}
