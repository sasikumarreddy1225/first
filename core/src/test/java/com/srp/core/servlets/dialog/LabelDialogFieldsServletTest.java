package com.srp.core.servlets.dialog;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.testing.mock.osgi.MockOsgi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.srp.core.beans.response.LabelsResponseBean;
import com.srp.core.services.ApiServiceCall;
import com.srp.core.services.impl.ApiCallsServiceImpl;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class LabelDialogFieldsServletTest {

	private final AemContext ctx = new AemContext();

	@Mock
	SlingHttpServletRequest req;

	@Mock
	SlingHttpServletResponse resp;

	@Mock
	private ApiServiceCall apiServiceCall;

	ApiCallsServiceImpl impl = new ApiCallsServiceImpl();

	LabelDialogFieldsServlet servlet = new LabelDialogFieldsServlet();

	ObjectMapper mapper = new ObjectMapper();

	@BeforeEach
	void setUp() throws IOException {
		req = ctx.request();
		resp = ctx.response();
		ctx.registerService(ApiServiceCall.class, apiServiceCall);
		MockOsgi.injectServices(servlet, ctx.bundleContext());
		ctx.registerService(ApiServiceCall.class, apiServiceCall);
		String file = "src/test/resources/com/srp/core/models/servlets/dialog/ErrorLabelsBean.json";
		String json = new String(Files.readAllBytes(Paths.get(file)));
		mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		LabelsResponseBean labels = mapper.readValue(json, LabelsResponseBean.class);
		lenient().when(apiServiceCall.getSRPLabels("type=label")).thenReturn(labels);
	}

	@Test
	void testDoGet() throws ServletException, IOException {
		servlet.doGet(req, resp);
		assertEquals(200, resp.getStatus());
	}

	@Test
	void testLabelsBean() throws ServletException, IOException {
		String file = "src/test/resources/com/srp/core/models/servlets/dialog/EmptyResultsBean.json";
		String json = new String(Files.readAllBytes(Paths.get(file)));
		LabelsResponseBean labels = mapper.readValue(json, LabelsResponseBean.class);
		lenient().when(apiServiceCall.getSRPLabels("type=label")).thenReturn(labels);
		servlet.doGet(req, resp);
		assertNotNull(labels.getResults());
	}

	@Test
	void testEmptyResp() throws ServletException, IOException {
		LabelsResponseBean labels = null;
		lenient().when(apiServiceCall.getSRPLabels("type=label")).thenReturn(labels);
		servlet.doGet(req, resp);
		assertEquals(200, resp.getStatus());
	}

}
