package com.srp.core.servlets;

import java.io.IOException;

import javax.jcr.RepositoryException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.propertytypes.ServiceDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.srp.core.beans.request.TagsListBean;
import com.srp.core.constants.CommonConstants;
import com.srp.core.services.GenericPageExporterInterface;
import com.srp.core.utils.SRPCommonUtils;

@Component(service = { Servlet.class })
@SlingServletResourceTypes(resourceTypes = "sling/servlet/default", methods = HttpConstants.METHOD_GET, extensions = "json", selectors = "srpContentExport")
@ServiceDescription("Generic Page Exporter")
public class GenericPageExporterServlet extends SlingSafeMethodsServlet {
	private static final long serialVersionUID = 1L;
	private transient Logger logger = LoggerFactory.getLogger(GenericPageExporterServlet.class);

	@Reference
	private transient GenericPageExporterInterface genericPageExporterInterface;

	@Override
	protected void doGet(final SlingHttpServletRequest req, final SlingHttpServletResponse resp)
			throws ServletException, IOException {

		logger.error("SRP-Entered doGet method");
		String[] roleTags = null;
		String[] geoTags = null;
		String[] brandTags = null;
		// fetching request params
		String roles = req.getParameter("roleTags");
		String brands = req.getParameter("brandTags");
		String geo = req.getParameter("geoTags");

		if (null != roles && !roles.isBlank())
			roleTags = roles.split(",");
		if (null != brands && !brands.isBlank())
			brandTags = brands.split(",");
		if (null != geo && !geo.isBlank())
			geoTags = geo.split(",");

		TagsListBean tagsList = new TagsListBean();
		tagsList.setRoleTag(roleTags);
		tagsList.setGeoTag(geoTags);
		tagsList.setBrandTag(brandTags);

		final Resource resource = req.getResource();
		final ResourceResolver resourceResolver = req.getResourceResolver();
		resp.setContentType(CommonConstants.APPLICATION_JSON);
		resp.setCharacterEncoding("UTF-8");
		String templateType = SRPCommonUtils.getTemplateFromResource(resource.getPath(), resourceResolver);
		
		if (templateType.equalsIgnoreCase(CommonConstants.HEADER_MENULINKS_TEMP))
			try {
				// Call to frame Menulinks Json
				resp.getWriter().write(genericPageExporterInterface.getMenulinksJson(resource.getPath(),
						resourceResolver, tagsList, req, templateType));
			} catch (RepositoryException e) {
				logger.error("SRP-Exception occured in GenericPageExporterServlet", e);
			
		}else if(templateType.equalsIgnoreCase(CommonConstants.PRODUCT_LANDING_PAGE_TEMPLATE)){
			// Call to frame Exp fragment Json
			try {
				resp.getWriter().write(genericPageExporterInterface.getExperienceFragData(resource.getPath(),
						resourceResolver, tagsList, req, templateType));
			} catch (RepositoryException e) {
				// TODO Auto-generated catch block
				logger.error("WSP-Exception occured in GenericPageExporterServlet", e);
			}
		}else {
			 // call to frame other content Json
			resp.getWriter().write(genericPageExporterInterface.getPageAsJson(resource.getPath(), resourceResolver,
						tagsList,  req, templateType));
			logger.error("SRP-Exiting doGet method");
		}
	   
	}
}