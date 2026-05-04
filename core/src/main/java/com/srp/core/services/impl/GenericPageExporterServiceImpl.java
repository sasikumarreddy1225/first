package com.srp.core.services.impl;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.srp.core.beans.request.TagsListBean;
import com.srp.core.constants.CommonConstants;
import com.srp.core.utils.SRPCommonUtils;
import com.srp.core.models.PageDataModel;
import com.srp.core.models.common.ExpericenceFragmentsRefDataModel;
import com.srp.core.models.common.MenuLinksReferenceDataModel;
import com.srp.core.models.common.SRPContainerDataModel;
import com.srp.core.services.GenericDataModel;
import com.srp.core.services.GenericPageExporterInterface;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;

import org.apache.sling.api.resource.Resource;
import com.google.gson.JsonElement;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.jcr.resource.api.JcrResourceConstants;
import org.apache.sling.models.factory.ModelFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.Page;
import com.day.cq.search.result.Hit;
import java.lang.reflect.InvocationTargetException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.StringUtils;

@Component(service = GenericPageExporterInterface.class)
public class GenericPageExporterServiceImpl implements GenericPageExporterInterface {

	private final Logger logger = LoggerFactory.getLogger(GenericPageExporterServiceImpl.class);

	@Reference
	private ModelFactory modelFactory;

	@Reference
	private QueryBuilder builder;
	
	private static final String COMPONENT_TYPE = "componentType";
	
	private static final String COMPONENT_VARIANT = "componentVariant";

	private static final String CONTAINER = "srp/components/srp-container";
	
	public static final String LABEL = "label";

	private String[] roleTag;
	private String[] geoTag;
	private String[] brandTag;
	private String template;
	private String page;
	private SlingHttpServletRequest request;

	Gson gson = new GsonBuilder().disableHtmlEscaping().create();

	@Override
	public String getPageAsJson(String pagePath, ResourceResolver resourceResolver, TagsListBean tagslist,
			SlingHttpServletRequest request, String templateType) {

		logger.error("SRP-Entered into getPageAsJson Method");

		// setting request params to gobal variables
		if (tagslist.getRoleTag().length > 0)
			this.roleTag = Arrays.copyOf(tagslist.getRoleTag(), tagslist.getRoleTag().length);
		else
			this.roleTag = null;

		if (tagslist.getGeoTag().length > 0)
			this.geoTag = Arrays.copyOf(tagslist.getGeoTag(), tagslist.getGeoTag().length);
		else
			this.geoTag = null;

		if (tagslist.getBrandTag().length > 0)
			this.brandTag = Arrays.copyOf(tagslist.getBrandTag(), tagslist.getBrandTag().length);
		else
			this.brandTag = null;

		this.template = templateType;
		this.page = pagePath;

		logger.error("SRP-Pagepath: {} and Templatetype : {}", pagePath, templateType);

		JsonObject jsonObject = new JsonObject();
		JsonArray components = new JsonArray();
		// fetching all page components that are inside SRP Container
		List<Resource> resources = getPageResources(pagePath, resourceResolver);

		for (Resource resource : resources) {
			// Fetching component properties as JSON
			JsonObject compJson = getComponentJson(resource, resourceResolver, request);
			if (null != compJson)
				components.add(compJson);
		}
		jsonObject.addProperty("pagePath", pagePath);
		jsonObject.addProperty("templateType", templateType);
		jsonObject.add("pageComponents", components);

		logger.error("SRP-Exiting getPageAsJson method");
		return gson.toJson(jsonObject);
	}

	private List<Resource> getPageResources(String pagePath, ResourceResolver resourceResolver) {

		logger.error("SRP-Entered into getPageResources method");
		List<Resource> pageResources = new ArrayList<>();
		Map<String, String> predicate = new HashMap<>();

		predicate.put("path", pagePath);
		predicate.put("1_property", JcrResourceConstants.SLING_RESOURCE_TYPE_PROPERTY);
		predicate.put("1_property.value", CommonConstants.SRP_CONTAINTER_COMP);
		predicate.put("p.limit", "-1");/// No limit for the result size

		Session session = resourceResolver.adaptTo(Session.class);
		Query query = builder.createQuery(PredicateGroup.create(predicate), session);
		SearchResult results = query.getResult();

		for (Hit hit : results.getHits()) {
			Resource resource;
			try {
				resource = hit.getResource();
				SRPContainerDataModel rootContainer = resource.adaptTo(SRPContainerDataModel.class);
				List<Resource> childResources = rootContainer.getChildren();
				pageResources.addAll(childResources);
			} catch (RepositoryException e) {
				logger.error("SRP-Exception Occured:", e);
			}
		}
		logger.error("SRP-Exiting from getPageResources");
		return pageResources;
	}

	private JsonObject getComponentJson(Resource resource, ResourceResolver resourceResolver,
			SlingHttpServletRequest request) {

		logger.error("SRP-Entered into getComponentJson method");
		this.request = request;
		JsonObject compData = null;
		JsonObject jsonObject = new JsonObject();
		String resourceType = resource.getResourceType();
		if (!excludeCompInJson(resourceType)) // check to include component in JSON
		{
			JsonObject data = new JsonObject();
			GenericDataModel model = null;
			if (modelFactory.isModelAvailableForResource(resource)) {
				logger.error("SRP-Model available for: {}", resourceType);
				// getting model from resource
				model = (GenericDataModel) modelFactory.getModelFromResource(resource);
				data = exportToJson(model); // generating JSON from model getters
			} else {
				logger.error("SRP-Model NOT available for: {}", resource.getResourceType());
			}

			if (null != data) {
				setComponentTypeAndVarient(jsonObject, resourceType);
				JsonObject regInfo = SRPCommonUtils.getPageRegion(page);// fetching country language of component
				data.add("country", regInfo.get("country"));
				data.add("language", regInfo.get("language"));
				JsonArray jsonArray = new JsonArray();
				jsonArray.add(data);
				jsonObject.add("data", jsonArray);
			}
		}
		// checking for children within component
		if (resource.hasChildren()) {
			getChildrenCompJson(resource, resourceResolver, jsonObject, request);
		}
		if (jsonObject.size() != 0)
			compData = jsonObject;

		logger.error("SRP-Exiting getComponentJson method");
		return compData;
	}

	private boolean excludeCompInJson(String resourceType) {
		boolean isExcluded = false;
		if (resourceType.equalsIgnoreCase(CommonConstants.SRP_CONTAINTER_COMP) || resourceType.equals(CONTAINER))
			isExcluded = true;

		return isExcluded;
	}

	private String getValuesAsJson(Method method, String fieldName, Object value, JsonObject jsonObject) {

		String compHasTags = "false";

		if (value instanceof String) {
			jsonObject.addProperty(fieldName, (String) value);
		} else if (value instanceof List) {
			JsonArray jsonArray = new JsonArray();
			for (Object object : (List<?>) value) {
				if (checkForTags(object, method)) {
					compHasTags = "true";
					JsonObject obj = new JsonObject();
					obj.addProperty("tag", (String) object);
					jsonArray.add(obj);
				} else
					jsonArray.add(exportToJson((GenericDataModel) object));

			}
			jsonObject.add(fieldName, jsonArray);
		} else if (value instanceof JsonObject) {
			jsonObject.add(fieldName, (JsonElement) value);
		} else if (value instanceof GenericDataModel) {
			jsonObject.add(fieldName, exportToJson((GenericDataModel) value));
		} else if (value instanceof Boolean) {
			jsonObject.addProperty(fieldName, (Boolean) value);
		}

		return compHasTags;
	}

	private void getChildrenCompJson(Resource resource, ResourceResolver resourceResolver, JsonObject jsonObject,
			SlingHttpServletRequest request) {

		logger.error("SRP-Entered into getChildrenCompJson method");
		JsonArray childResourceJson = new JsonArray();
		Iterable<Resource> childResources = resource.getChildren();
		for (Resource childResource : childResources) {
			String childResourceType = childResource.getResourceType();

			if (!childResourceType.equals(JcrConstants.NT_UNSTRUCTURED)) {
				childResourceJson.add(getComponentJson(childResource, resourceResolver, request));
				jsonObject.add("components", childResourceJson);
			}
		}
		logger.error("SRP-Exiting getChildrenCompJson method");
	}

	private boolean checkForTags(Object object, Method method) {
		boolean hasTags = false;
		if (object instanceof String && (method.getName().equals("getGeoTags") || method.getName().equals("getRoleTags")
				|| method.getName().equals("getBrandTags"))) {
			hasTags = true;
		}
		return hasTags;
	}

	public JsonObject exportToJson(GenericDataModel model) {

		logger.error("SRP-Entered into exportToJsonmethod");
		model.setRequest(request);
		JsonObject jsonObject = new JsonObject();
		List<String> modelContainsFilteringTags = new ArrayList<>();
		// Get all getter methods from the model class

		for (Method method : model.getClass().getMethods()) {
			if (method.getName().startsWith("get") && method.getParameterCount() == 0) {

				String fieldName = method.getName().substring(3); // Remove "get" prefix
				fieldName = Character.toLowerCase(fieldName.charAt(0)) + fieldName.substring(1);

				// Invoke the getter method and add the value to the map
				Object value;
				try {
					value = method.invoke(model);
					// adding property in json based on its value type
					String hasTags = getValuesAsJson(method, fieldName, value, jsonObject);
					modelContainsFilteringTags.add(hasTags); // check for Tags
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					logger.error("SRP-Exception Occured:", e);
				}

			}
		}
		// Tags,Notifications based component checks
		if (modelContainsFilteringTags.contains("true")
				&& !isModelTobeIncluded(jsonObject, modelContainsFilteringTags)) {
			logger.error("SRP-Exiting exportToJson method excluding component as it is not compatblefor tags/nofitication");
			return null;
		}
		logger.error("SRP-Exiting exportToJson method");
		return jsonObject;
	}

	public boolean isModelTobeIncluded(JsonObject obj, List<String> modelContainsFilteringTags) {

		boolean tagsCheck = false;
		if (modelContainsFilteringTags.contains("true")) {
			tagsCheck = checkTags(obj);
			if (tagsCheck)
				return true;
		}
		return false;

	}

	public boolean checkTags(JsonObject obj) {
		boolean returnVal = false;
		boolean roleMatched = false;
		boolean brandMatched = false;
		boolean geoMatched = false;
		JsonArray compRoleTags = obj.getAsJsonArray("roleTags");
		JsonArray compGeoTags = obj.getAsJsonArray("geoTags");
		JsonArray compBrandTags = obj.getAsJsonArray("brandTags");

		if (geoTag == null)
			geoMatched = true;
		else
			geoMatched = compareTags(compGeoTags, geoTag);

		if (brandTag == null)
			brandMatched = true;
		else
			brandMatched = compareTags(compBrandTags, brandTag);

		if (roleTag == null)
			roleMatched = true;
		else
			roleMatched = compareTags(compRoleTags, roleTag);

		if (roleMatched && geoMatched && brandMatched)
			returnVal = true;

		return returnVal;
	}

	boolean compareTags(JsonArray compTags, String[] tags) {
		boolean tagMatched = false;
		int c = 0;
		if (compTags.isEmpty()) {
			c += 1;
		} else {
			for (JsonElement i : compTags) {
				for (String j : tags) {
					if (i.getAsJsonObject().get("tag").toString().toLowerCase().contains(j.toLowerCase())) {
						c += 1;
					}
				}
			}
		}

		if (c > 0)
			tagMatched = true;

		return tagMatched;
	}
	
	private void setComponentTypeAndVarient(JsonObject jsonObject, String resourceType) {
		String[] componentType = resourceType.split(CommonConstants.SLASH);
		jsonObject.addProperty(COMPONENT_TYPE, componentType[componentType.length - 1]);
		String componentVariant = StringUtils.EMPTY;
		jsonObject.addProperty(COMPONENT_VARIANT, componentVariant);
	}
	
	@Override
	public String getMenulinksJson(String pagePath, ResourceResolver resourceResolver, TagsListBean tagslist,
			SlingHttpServletRequest request, String temp) throws RepositoryException {

		logger.error("SRP-Entered into getMenulinksJson method");
		JsonObject compJson = null;
		// setting request params to gobal variables
		if (tagslist.getRoleTag().length > 0)
			this.roleTag = Arrays.copyOf(tagslist.getRoleTag(), tagslist.getRoleTag().length);
		else
			this.roleTag = null;

		if (tagslist.getGeoTag().length > 0)
			this.geoTag = Arrays.copyOf(tagslist.getGeoTag(), tagslist.getGeoTag().length);
		else
			this.geoTag = null;

		if (tagslist.getBrandTag().length > 0)
			this.brandTag = Arrays.copyOf(tagslist.getBrandTag(), tagslist.getBrandTag().length);
		else
			this.brandTag = null;

		this.page = pagePath;
		JsonObject jsonObject = new JsonObject();
		JsonArray components = new JsonArray();
		this.template = temp;
		logger.debug("Pagepath:{} Templatetype:{}", pagePath, temp);
		// getting page components within SRP Container
		List<Resource> resources = getPageResources(pagePath, resourceResolver);
		for (Resource resource : resources) {
			String resType = resource.getResourceType();
			// getting component properties as json
			if (!resType.equals(CommonConstants.MENULINK_REFERENCE))
				compJson = getComponentJson(resource, resourceResolver, request);
			else if (null != compJson)
				compJson = getMenulinksData(resource, resourceResolver, request);
			components.add(compJson);
		}

		jsonObject.addProperty("pagePath", pagePath);
		jsonObject.addProperty("templateType", temp);
		jsonObject.add("pageComponents", components);

		logger.error("SRP-Exiting getMenulinksJson method");
		return gson.toJson(jsonObject);
	}

	public JsonObject getMenulinksData(Resource resource, ResourceResolver resourceResolver, SlingHttpServletRequest req)
			throws RepositoryException {

		logger.error("SRP-Entered getMenulinksData method");

		MenuLinksReferenceDataModel menulinksReferenceDataModel = resource.adaptTo(MenuLinksReferenceDataModel.class);
		String menulinksRoot = menulinksReferenceDataModel.getMenulinkPath();
		logger.error("SRP-Menulinks Root Path: {}", menulinksRoot);

		JsonObject jsonObj = new JsonObject();
		JsonArray menuList = new JsonArray();
		JsonArray data = new JsonArray();
		JsonObject compData = new JsonObject();

		String resourceType = resource.getResourceType();
		String[] componentType = resourceType.split(CommonConstants.SLASH);
		Resource pageRes = resourceResolver.resolve(menulinksRoot);
		Page pageobj = pageRes.adaptTo(Page.class);
		Iterator<Page> menuPages = pageobj.listChildren();

		processLevel1AppLinks(resourceResolver, req, menuPages, menuList);

		compData.add("parentMenu", menuList);
		data.add(compData);
		jsonObj.addProperty(COMPONENT_TYPE, componentType[componentType.length - 1]);
		jsonObj.add("data", data);
		logger.error("SRP-JsonObj = {}", jsonObj);
		logger.error("SRP-Exiting getApplinksData method");

		return jsonObj;
	}
	
	private void processLevel1AppLinks(ResourceResolver resourceResolver, SlingHttpServletRequest req,
			Iterator<Page> menuPages, JsonArray menuList) throws RepositoryException {
		while (menuPages.hasNext()) {
			JsonObject obj1 = new JsonObject();
			JsonArray menulinksList = new JsonArray();
			Page menuItem = menuPages.next();
			String menuLabel = menuItem.getTitle();
			logger.error("SRP-MenuItem {}", menuLabel);
			String menuTemp = menuItem.getTemplate().getPath();
			logger.error("SRP-MenuItem Template:{}", menuTemp);
			Resource menuResource = menuItem.getContentResource();

			if (checkResourceTagsCompatability(menuResource, menuTemp,
					CommonConstants.MENULINKS_TEMPLATE)) {
				obj1.addProperty(LABEL, menuLabel);
				String linkPath = menuResource.getPath() + "/root/container/srp_container";
				Resource menuLinkTest = resourceResolver.getResource(linkPath);
				if(menuLinkTest != null) {
					for(Resource child:menuLinkTest.getChildren()) {
						JsonObject obj2 = getComponentJson(child, resourceResolver, req);
						if (null != obj2)
							menulinksList.add(obj2);
					}
				}
				/*
				 * Map<String, String> predicate = new HashMap<>(); predicate.put("path",
				 * menuItem.getPath()); predicate.put("1_property",
				 * JcrResourceConstants.SLING_RESOURCE_TYPE_PROPERTY);
				 * predicate.put("1_property.value", CommonConstants.MENULINKS_COMP);
				 * predicate.put("p.limit", "-1");/// No limit for the result size Session
				 * session = resourceResolver.adaptTo(Session.class); Query query =
				 * builder.createQuery(PredicateGroup.create(predicate), session); SearchResult
				 * results = query.getResult();
				 * 
				 * for (Hit hit : results.getHits()) { Resource menulink = hit.getResource();
				 * JsonObject obj2 = getComponentJson(menulink, resourceResolver, req); if (null
				 * != obj2) menulinksList.add(obj2); }
				 */
				obj1.add("childMenu", menulinksList);
				menuList.add(obj1);
			}
		}

	}
	
	
	private boolean checkResourceTagsCompatability(Resource res, String resTemplate, String expTempate) {

		JsonObject jsonObj = new JsonObject();

		JsonArray roleJsonArray = new JsonArray();
		JsonArray geoJsonArray = new JsonArray();
		JsonArray brandJsonArray = new JsonArray();
		List<String> roles = new ArrayList<>();
		List<String> geo = new ArrayList<>();
		List<String> brands = new ArrayList<>();
		if (expTempate.equalsIgnoreCase(resTemplate)) {

			PageDataModel model = res.adaptTo(PageDataModel.class);
			roles = model.getRoleTags();
			geo = model.getGeoTags();
			brands = model.getBrandTags();

		}
		if (!roles.isEmpty()) {
			for (String i : roles) {
				JsonObject obj = new JsonObject();
				obj.addProperty("tag", i);
				roleJsonArray.add(obj);
			}
		}
		if (!geo.isEmpty()) {
			for (String i : geo) {
				JsonObject obj = new JsonObject();
				obj.addProperty("tag", i);
				geoJsonArray.add(obj);
			}
		}
		if (!brands.isEmpty()) {
			for (String i : brands) {
				JsonObject obj = new JsonObject();
				obj.addProperty("tag", i);
				brandJsonArray.add(obj);
			}
		}
		jsonObj.add("roleTags", roleJsonArray);
		jsonObj.add("geoTags", geoJsonArray);
		jsonObj.add("brandTags", brandJsonArray);

		return checkTags(jsonObj);
	}
	
	@Override
	public String getExperienceFragData(String pagePath, ResourceResolver resourceResolver, TagsListBean tagslist,
			SlingHttpServletRequest request, String temp) throws RepositoryException {
		// TODO Auto-generated method stub
		logger.error("WSP-Entered into getExperienceFragData method");
		JsonObject compJson = null;
		JsonArray compArray = null;
		// setting request params to gobal variables
		if (tagslist.getRoleTag().length > 0)
			this.roleTag = Arrays.copyOf(tagslist.getRoleTag(), tagslist.getRoleTag().length);
		else
			this.roleTag = null;

		if (tagslist.getGeoTag().length > 0)
			this.geoTag = Arrays.copyOf(tagslist.getGeoTag(), tagslist.getGeoTag().length);
		else
			this.geoTag = null;

		if (tagslist.getBrandTag().length > 0)
			this.brandTag = Arrays.copyOf(tagslist.getBrandTag(), tagslist.getBrandTag().length);
		else
			this.brandTag = null;

		this.page = pagePath;
		JsonObject jsonObject = new JsonObject();
		JsonArray components = new JsonArray();
		this.template = temp;
		logger.debug("Pagepath:{} Templatetype:{}", pagePath, temp);
		// getting page components within SRP Container
		List<Resource> resources = getPageResources(pagePath, resourceResolver);
		for (Resource resource : resources) {
			String resType = resource.getResourceType();
			// getting component properties as json
			if (!resType.equals(CommonConstants.WSP_EXP_FRAG_REF_PATH)) 
				compJson = getComponentJson(resource, resourceResolver, request);
					
			else 
				compJson = getExperienceData(resource, resourceResolver, request);
			
			components.add(compJson);

		}

		jsonObject.addProperty("pagePath", pagePath);
		jsonObject.addProperty("templateType", temp);
		jsonObject.add("pageComponents", components);

		logger.error("WSP-Exiting getExperienceFragData method");
		return gson.toJson(jsonObject);
	}

	public JsonObject getExperienceData(Resource resource, ResourceResolver resourceResolver,
			SlingHttpServletRequest req) throws RepositoryException {
		logger.error("WSP-Entered getExperienceData method");

		ExpericenceFragmentsRefDataModel expericenceFragmentsRefDataModel = resource.adaptTo(ExpericenceFragmentsRefDataModel.class);
		String expfragpath = expericenceFragmentsRefDataModel.getExpFragPath();
		logger.error("WSP-ExpFrag Root Path: {}", expfragpath);

		JsonObject jsonObj = new JsonObject();
		JsonArray fragList = new JsonArray();
		

		String resourceType = resource.getResourceType();
		String[] componentType = resourceType.split(CommonConstants.SLASH);
		Resource pageRes = resourceResolver.resolve(expfragpath);
		Page pageobj = pageRes.adaptTo(Page.class);
		processExpFragLinks(resourceResolver, req, pageobj, fragList);
		jsonObj.addProperty(COMPONENT_TYPE, componentType[componentType.length - 1]);
		jsonObj.add("data",fragList);
		logger.error("WSP-JsonObj = {}", jsonObj);
		logger.error("WSP-Exiting getExperienceData method");

		return jsonObj;
	}
	
	public void processExpFragLinks(ResourceResolver resourceResolver, SlingHttpServletRequest req,
			Page pageobj, JsonArray menuList) throws RepositoryException {
			String menuLabel = pageobj.getTitle();
			logger.error("WSP-EXPItem {}", menuLabel);
			String menuTemp = pageobj.getTemplate().getPath();
			logger.error("WSP-EXPItem Template:{}", menuTemp);
			Resource menuResource = pageobj.getContentResource();

			if (checkResourceTagsCompatability(menuResource, menuTemp,
					CommonConstants.PRODUCT_LANDING_PAGE_TEMPLATE)) {
				String linkPath = menuResource.getPath() + "/root/srp_container";
				Resource menuLinkTest = resourceResolver.getResource(linkPath);
				if(menuLinkTest != null) {
					for(Resource child:menuLinkTest.getChildren()) {
						JsonObject obj2 = getComponentJson(child, resourceResolver, req);
						if (null != obj2)
							menuList.add(obj2);
					}
				}
			}
		}

}
