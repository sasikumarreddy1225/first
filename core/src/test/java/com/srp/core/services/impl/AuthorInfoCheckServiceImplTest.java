package com.srp.core.services.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

import java.util.Arrays;
import java.util.List;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
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
import com.srp.core.beans.request.TagsBeanClass;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class AuthorInfoCheckServiceImplTest {

    private final AemContext context = new AemContext(ResourceResolverType.JCR_MOCK);
    AuthorInfoCheckServiceImpl impl = new AuthorInfoCheckServiceImpl();

    List<String> adminGroups = Arrays.asList("everyone");

    @Mock
    private QueryBuilder querybuilder;

    @Mock
    private Query query;

    @Mock
    SearchResult searchResult;

    @Mock
    Hit hit;


    @BeforeEach
    public final void setUp() throws RepositoryException {
        context.registerService(QueryBuilder.class, querybuilder);
        context.registerInjectActivateService(impl);
        lenient().when(querybuilder.createQuery(any(PredicateGroup.class), any(Session.class))).thenReturn(query);
        lenient().when(query.getResult()).thenReturn(searchResult);
        lenient().when(searchResult.getHits()).thenReturn(Arrays.asList(hit));
        context.registerAdapter(ResourceResolver.class, QueryBuilder.class, querybuilder);
        context.load().json("/com/srp/core/models/configuration/AuthorConfigurationsDataModelTest.json", "/json");
        context.currentResource("/json/authorconf");
        lenient().when(hit.getResource()).thenReturn(context.currentResource());
    }

    @Test
    void testGetTagValuesOfAuthor() {
        TagsBeanClass tagsBeanClass = impl.getTagValuesOfAuthor(context.resourceResolver(), adminGroups);
        assertNotNull(tagsBeanClass);
    }

    @Test
    void testgetAdminGroups() throws RepositoryException {
        Resource resource = context.load().json("/com/srp/core/models/configuration/AdminConfigDataModelTest.json",
                "/json/adminGroups");
        context.request().setResource(resource);
        lenient().when(hit.getResource()).thenReturn(context.currentResource());
        List<String> groups = impl.getAdminGroups(context.resourceResolver());
        assertNotNull(groups);
    }

    @Test
    void testGetTagValuesOfAuthorException() {
        lenient().when(impl.getTagValuesOfAuthor(context.resourceResolver(), adminGroups))
                .thenThrow(new RepositoryException());
        TagsBeanClass tagsBeanClass = impl.getTagValuesOfAuthor(context.resourceResolver(), adminGroups);
        assertNotNull(tagsBeanClass);
    }

    @Test
    void testgetAdminGroupsException() throws RepositoryException {
        Resource resource = context.load().json("/com/srp/core/models/configuration/AuthorConfigurationsDataModelTest.json",
                "/json/adminGroups");
        context.request().setResource(resource);
        lenient().when(hit.getResource()).thenReturn(context.currentResource());
        lenient().when(impl.getAdminGroups(context.resourceResolver())).thenThrow(new RepositoryException());
        List<String> groups = impl.getAdminGroups(context.resourceResolver());
        assertNotNull(groups);
    }
}
