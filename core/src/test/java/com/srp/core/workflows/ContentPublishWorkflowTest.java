package com.srp.core.workflows;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.lenient;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;

import org.apache.http.ParseException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.testing.mock.osgi.MockOsgi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.HistoryItem;
import com.adobe.granite.workflow.exec.Route;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.Workflow;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.adobe.granite.workflow.model.WorkflowModel;
import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.replication.Replicator;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.Template;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.srp.core.beans.request.PublishContentRequestBean;
import com.srp.core.models.PageDataModel;
import com.srp.core.models.common.MenuLinksReferenceDataModel;
import com.srp.core.services.ApiServiceCall;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class ContentPublishWorkflowTest {

	private final AemContext ctx = new AemContext();

	@InjectMocks
	ContentPublishWorkflow workflow = new ContentPublishWorkflow();

	@Mock
	WorkflowSession workflowSession;

	@Mock
	WorkflowModel workflowModel;

	@Mock
	WorkflowData workflowData;

	@Mock
	WorkItem workItem;

	@Mock
	Workflow wf;

	@Mock
	private ApiServiceCall apiServiceCall;

	@Mock
	Page currentPage;

	@Mock
	Resource resource, pageRes, childRes, parentRes;

	@Mock
	ResourceResolver resourceResolver;

	@Mock
	MetaDataMap metaDataMap;

	@Mock
	Template template;

	@Mock
	ValueMap valMap;

	@Mock
	PageDataModel model;

	@Mock
	List<HistoryItem> histories;

	@Mock
	HistoryItem recentHistory;

	@Mock
	List<Route> nextRoutes;

	@Mock
	Route route;

	@Mock
	QueryBuilder builder;

	@Mock
	Query query;

	@Mock
	SearchResult results;

	@Mock
	Hit hit;


	@Mock
	MenuLinksReferenceDataModel menulinksRef;
	
	@Mock
	private Replicator replicator;

	@BeforeEach
	public final void setUp() throws Exception {
		ctx.registerService(QueryBuilder.class, builder);
		ctx.registerService(WorkflowSession.class, workflowSession);
		ctx.registerService(Replicator.class, replicator);
		lenient().when(workflowSession.adaptTo(ResourceResolver.class)).thenReturn(resourceResolver);
		lenient().when(resourceResolver.adaptTo(QueryBuilder.class)).thenReturn(builder);
		lenient().when(workItem.getWorkflowData()).thenReturn(workflowData);
		lenient().when(workflowData.getPayload()).thenReturn("/content/srp/ee/it/en/test-page");
		lenient().when(resourceResolver.resolve("/content/srp/ee/it/en/test-page")).thenReturn(resource);
		lenient().when(resource.adaptTo(any())).thenReturn(currentPage);
		lenient().when(currentPage.getTemplate()).thenReturn(template);
		ctx.registerService(ApiServiceCall.class, apiServiceCall);
		MockOsgi.injectServices(workflow, ctx.bundleContext());
		lenient().when(apiServiceCall.updateSRPContent(any())).thenReturn("success");

		lenient().when(resourceResolver.getResource(any())).thenReturn(pageRes);
		lenient().when(pageRes.listChildren()).thenReturn(Arrays.asList(childRes).iterator());
		lenient().when(childRes.getName()).thenReturn(JcrConstants.JCR_CONTENT);
		lenient().when(childRes.adaptTo(ValueMap.class)).thenReturn(valMap);


		lenient().when(pageRes.adaptTo(PageDataModel.class)).thenReturn(model);
		lenient().when(model.getId()).thenReturn("abc12345");
		lenient().when(workflowData.getMetaDataMap()).thenReturn(metaDataMap);
		lenient().when(metaDataMap.get("userId", String.class)).thenReturn("initiator");
		lenient().when(workItem.getWorkflow()).thenReturn(wf);
		lenient().when(workflowSession.getHistory(wf)).thenReturn(histories);
		lenient().when(histories.get(anyInt())).thenReturn(recentHistory);
		lenient().when(workflowSession.getRoutes(workItem, false)).thenReturn(nextRoutes);
		lenient().when(workflowSession.getBackRoutes(workItem, false)).thenReturn(nextRoutes);
		lenient().when(nextRoutes.get(0)).thenReturn(route);

		lenient().when(builder.createQuery(any(), any())).thenReturn(query);
		lenient().when(query.getResult()).thenReturn(results);
		lenient().when(results.getHits()).thenReturn(Arrays.asList(hit));
		lenient().when(hit.getResource()).thenReturn(parentRes);
		lenient().when(parentRes.getParent()).thenReturn(pageRes);
		lenient().when(pageRes.adaptTo(Page.class)).thenReturn(currentPage);
		lenient().when(currentPage.getPath()).thenReturn("abc");
		lenient().when(parentRes.adaptTo(MenuLinksReferenceDataModel.class)).thenReturn(menulinksRef);
		lenient().when(menulinksRef.getMenulinkPath()).thenReturn("/content/srp");


	}

	@Test
	void testPage() throws ServletException, IOException, WorkflowException {
		lenient().when(recentHistory.getUserId()).thenReturn("recentUser");
		workflow.execute(workItem, workflowSession, null);
		assertNotNull(workflow);
	}

	@Test
	void testParseException() throws WorkflowException, ParseException, IOException {
		lenient().when(apiServiceCall.updateSRPContent(any())).thenThrow(new ParseException());
		lenient().when(recentHistory.getUserId()).thenReturn("recentUser");
		workflow.execute(workItem, workflowSession, null);
		Exception exception = assertThrows(ParseException.class, () -> {
			throw new ParseException("ParseException");
		});
		assertEquals("ParseException", exception.getMessage());
	}

	@Test
	void testIoException() throws WorkflowException, ParseException, IOException {
		lenient().when(apiServiceCall.updateSRPContent(any())).thenThrow(new IOException());
		lenient().when(recentHistory.getUserId()).thenReturn("recentUser");
		workflow.execute(workItem, workflowSession, null);
		Exception exception = assertThrows(IOException.class, () -> {
			throw new IOException("IOException");
		});
		assertEquals("IOException", exception.getMessage());
	}

	@Test
	void testBeans() throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		String responseFile = "src/test/resources/com/srp/core/models/servlets/PublishContentRequestBean.json";
		String responseJson = new String(Files.readAllBytes(Paths.get(responseFile)));
		PublishContentRequestBean publishContentRequestBean = mapper.readValue(responseJson,
				PublishContentRequestBean.class);
		assertNotNull(publishContentRequestBean.toString());
	}

	@Test
	void testNulls() throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		String responseFile = "src/test/resources/com/srp/core/beans/TagBeanClassTest.json";
		String responseJson = new String(Files.readAllBytes(Paths.get(responseFile)));
		PublishContentRequestBean publishContentRequestBean = mapper.readValue(responseJson,
				PublishContentRequestBean.class);
		assertNotNull(publishContentRequestBean.toString());
		publishContentRequestBean.setContentBean(null);
	}



	@Test
	void testPageListNotEmpty() throws WorkflowException {
		lenient().when(menulinksRef.getMenulinkPath()).thenReturn("/content/srp");
		lenient().when(recentHistory.getUserId()).thenReturn("recentUser");
		workflow.execute(workItem, workflowSession, null);
		assertNotNull(workflow);
	}

}

