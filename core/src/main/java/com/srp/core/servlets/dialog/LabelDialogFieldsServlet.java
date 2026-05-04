package com.srp.core.servlets.dialog;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.granite.ui.components.ds.DataSource;
import com.srp.core.beans.response.LabelsDataBean;
import com.srp.core.beans.response.LabelsResponseBean;
import com.srp.core.services.ApiServiceCall;
import com.srp.core.utils.SRPCommonUtils;


@Component(service = Servlet.class, property = { "sling.servlet.methods=" + HttpConstants.METHOD_GET,
		"sling.servlet.resourceTypes=" + LabelDialogFieldsServlet.SELECTOR })
public class LabelDialogFieldsServlet extends SlingSafeMethodsServlet{

	private static final long serialVersionUID = 1L;
	protected static final String SELECTOR = "getSrpLabels";
	private transient Logger log = LoggerFactory.getLogger(LabelDialogFieldsServlet.class);
	
	@Reference
	private transient ApiServiceCall apiServiceCall;
	
	@Override
	protected void doGet(SlingHttpServletRequest req, SlingHttpServletResponse resp)
			throws ServletException, IOException {

		log.error("SRP-Entered doGet method");

		LinkedHashMap<String, String> labelsList = new LinkedHashMap<>();
		String request = "type=label";
		LabelsResponseBean labelsResponseBean = apiServiceCall.getSRPLabels(request);
		if (null != labelsResponseBean) {
			List<LabelsDataBean> labels = labelsResponseBean.getResults();
			if (!labels.isEmpty()) {
				for (LabelsDataBean labelsDataBean : labels) {
					labelsList.put(labelsDataBean.getLabelId(), labelsDataBean.getLabelName());
				}
			}
			DataSource datasource = SRPCommonUtils.convertListToDatasource(req, labelsList);
			req.setAttribute(DataSource.class.getName(), datasource);
		}
		log.error("SRP-Exiting doGet method");
	}
}
