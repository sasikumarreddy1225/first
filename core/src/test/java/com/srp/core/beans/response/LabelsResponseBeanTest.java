package com.srp.core.beans.response;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

class LabelsResponseBeanTest {

	LabelsResponseBean labelsResponseBean = new LabelsResponseBean();

	ObjectMapper mapper = new ObjectMapper();

	@BeforeEach
	void setUp() throws IOException {
		String responseJson = "{\n" + "\"results\": [\n" + "{\"labelId\": \"123\", \"labelName\": \"admin\"}, \n"
				+ "{\"labelId\": \"456\", \"labelName\": \"user\"}\n" + "]\n" + "}";
		labelsResponseBean = mapper.readValue(responseJson, LabelsResponseBean.class);
	}

	@Test
	void testGetResults() {
		assertNotNull(labelsResponseBean);
		List<LabelsDataBean> results = labelsResponseBean.getResults();
		assertEquals("123", results.get(0).getLabelId());
		assertEquals("admin", results.get(0).getLabelName());
		assertEquals("456", results.get(1).getLabelId());
		assertEquals("user", results.get(1).getLabelName());
	}

	@Test
	void testNullResults() throws JsonProcessingException {
		String nullResponse = "{\"results\": null}";
		labelsResponseBean = mapper.readValue(nullResponse, LabelsResponseBean.class);
		assertNotNull(labelsResponseBean);
		assertNotNull(labelsResponseBean.getResults());
		assertTrue(labelsResponseBean.getResults().isEmpty());
	}

}