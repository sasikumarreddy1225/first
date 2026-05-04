package com.srp.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.gson.JsonObject;
import com.srp.core.beans.request.ContentListBean;
import com.srp.core.beans.request.PublishContentRequestBean;
import com.srp.core.constants.CommonConstants;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import com.srp.core.models.PageDataModel;

import com.adobe.granite.ui.components.ds.DataSource;
import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.wcm.api.constants.NameConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.wrappers.ValueMapDecorator;
import org.apache.commons.lang3.StringUtils;
import com.adobe.granite.ui.components.ds.ValueMapResource;
import org.apache.sling.api.resource.ResourceMetadata;
import com.adobe.granite.ui.components.ds.SimpleDataSource;

public class SRPCommonUtils {

	static Logger log = LoggerFactory.getLogger(SRPCommonUtils.class);

	private static final String GLOBAL_PATH = "srp/global";

	private SRPCommonUtils() {
		throw new IllegalStateException("This is an Utility class");
	}

	public static JsonObject getPageRegion(String pagePath) {

		log.error("SRP-Entered getPageRegion method");

		String[] path;
		String country = StringUtils.EMPTY;
		String language = StringUtils.EMPTY;

		JsonObject region = new JsonObject();
		path = pagePath.split(CommonConstants.SLASH);
		if (pagePath.contains(CommonConstants.EXPERIENCEFRAGMENTS_PATH)) {
			if (pagePath.toLowerCase().contains(GLOBAL_PATH)) {
				country = path[4];
				language = path[5];
			} else {
				country = path[5];
				language = path[6];
			}
		} else if (pagePath.contains(CommonConstants.PAGEPATH_ROOT)) {
			if (pagePath.toLowerCase().contains(GLOBAL_PATH)) {
				country = path[3];
				language = path[4];
			} else {
				country = path[4];
				language = path[5];
			}
		}
		region.addProperty("country", country);
		region.addProperty("language", language);

		log.error("SRP-Exiting getPageRegion method");
		return region;
	}

	public static String getTemplateFromResource(String pagePath, ResourceResolver resourceResolver) {

		log.error("SRP-Entered getTemplateFromResource method");
		String template = null;
		Resource page = resourceResolver.getResource(pagePath);
		Iterator<Resource> children = page.listChildren();
		while (children.hasNext()) {
			Resource content = children.next();
			String name = content.getName();
			if (name.equalsIgnoreCase(JcrConstants.JCR_CONTENT)) {
				ValueMap valueMap = content.adaptTo(ValueMap.class);
				template = valueMap.get(NameConstants.NN_TEMPLATE, String.class);
			}
		}
		log.error("SRP-Exiting getTemplateFromResource method");
		return template;

	}

	public static DataSource convertListToDatasource(SlingHttpServletRequest req, Map<String, String> linkedlist) {

		log.error("SRP-Entered convertListToDatasource method");
		List<Resource> list = new ArrayList<>();
		for (Map.Entry<String, String> m : linkedlist.entrySet()) {
			ValueMap vm = null;

			vm = new ValueMapDecorator(new HashMap<>());
			String value = m.getValue();
			String id = m.getKey();

			vm.put("value", value + "|" + id);
			vm.put("text", value);

			ResourceResolver resolver = req.getResourceResolver();
			list.add(new ValueMapResource(resolver, new ResourceMetadata(), JcrConstants.NT_UNSTRUCTURED, vm));
		}
		log.error("SRP-Exiting convertListToDatasource method");
		return new SimpleDataSource(list.iterator());

	}

	public static String getDeliveryURLWithDomainName(String url, SlingHttpServletRequest request) {

		log.error("SRP-Entered getDeliveryURLWithDomainName method");
		String scheme = request.getScheme();
		String domain = request.getServerName();
		int port = request.getServerPort();

		if (url.contains(domain))
			return url;

		if ((scheme.equals(CommonConstants.HTTP) && port == 80)
				|| (scheme.equals(CommonConstants.HTTPS) && port == 443)) {
			scheme = scheme + "://" + domain + url;
		} else {
			scheme = scheme + "://" + domain + ":" + port + url;
		}

		log.error("SRP-Exiting getDeliveryURLWithDomainName method");
		return scheme;

	}
	
	public static PublishContentRequestBean setRequestBeanforUpdateContent(List<String> pages, String event,
			ResourceResolver resourceResolver) {

		log.error("SRP-Entered setRequestBeanforUpdateContent method ");
		boolean externalPage = false;
		List<ContentListBean> contentList = new ArrayList<>();
		PublishContentRequestBean publishContentRequestBean = new PublishContentRequestBean();

		for (String pagePath : pages) {

			String template = getTemplateFromResource(pagePath, resourceResolver);
			ContentListBean contentListBean = new ContentListBean();
			contentListBean.setContentPath(pagePath);
			contentListBean.setEventType(event);
			contentListBean.setId(getPageId(pagePath, resourceResolver));

			if (pagePath.contains(CommonConstants.SLASH.concat(CommonConstants.EXPERIENCEFRAGMENTS_PATH))
					|| pagePath.contains(CommonConstants.SLASH.concat(CommonConstants.PAGEPATH_ROOT))) {

				contentListBean.setSelector(CommonConstants.CONTENTEXPORT_SELECTOR);

				if(StringUtils.equalsIgnoreCase(template, CommonConstants.MENULINKS_TEMPLATE))
					contentListBean.setType(CommonConstants.MENULINKS_LABEL);
				
				else if(StringUtils.equalsIgnoreCase(template, CommonConstants.LANDING_TEMPLATE))
					contentListBean.setType(CommonConstants.LANDING_LABEL);
				
				else if(StringUtils.equalsIgnoreCase(template, CommonConstants.BANNER_TEMPLATE))
					contentListBean.setType(CommonConstants.BANNER_LABEL);
				
				else if(StringUtils.equalsIgnoreCase(template, CommonConstants.FOOTER_TEMPLATE))
					contentListBean.setType(CommonConstants.FOOTER_LABEL);
				
				else if(StringUtils.equalsIgnoreCase(template, CommonConstants.HEADER_TEMPLATE))
					contentListBean.setType(CommonConstants.HEADER_LABEL);
				
				else
					contentListBean.setType(CommonConstants.OTHERS_LABEL);
			} 
			else {
				externalPage = true;
			}
			contentList.add(contentListBean);
		}
		publishContentRequestBean.setContentBean(contentList);
		publishContentRequestBean.setExternal(externalPage);
		log.error("SRP-Exiting setRequestBeanforUpdateContent method");
		return publishContentRequestBean;
	}

	public static String getPageId(String pagePath, ResourceResolver resourceResolver) {

		log.error("SRP-Entered getPageId method");

		Resource page = resourceResolver
				.getResource(pagePath.concat(CommonConstants.SLASH).concat(JcrConstants.JCR_CONTENT));
		PageDataModel model = page.adaptTo(PageDataModel.class);

		log.error("SRP-Exiting getPageId method");
		return model.getId();
	}

}


