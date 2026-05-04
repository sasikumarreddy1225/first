package com.srp.core.models.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.jcr.RepositoryException;

import org.apache.jackrabbit.api.security.user.Group;
import org.apache.jackrabbit.api.security.user.User;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.srp.core.beans.request.TagsBeanClass;
import com.srp.core.services.AuthorInfoCheckService;
import com.srp.core.services.GenericDataModel;

import org.apache.sling.models.annotations.DefaultInjectionStrategy;

@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL, resourceType = SRPContainerDataModel.RESOURCE)
public class SRPContainerDataModel implements GenericDataModel {

	protected static final String RESOURCE = "srp/components/common/srp-container";
	ResourceResolver resourceResolver;

	Logger log = LoggerFactory.getLogger(SRPContainerDataModel.class);
	private List<Resource> children = new ArrayList<>();
	private List<String> userGroupsList = new ArrayList<>();
	TagsBeanClass srpcontainerTags = new TagsBeanClass();

	@OSGiService
	private AuthorInfoCheckService authorInfoCheckService;

	@SlingObject
	private Resource res;

	@ValueMapValue
	private List<String> geoTags;

	@ValueMapValue
	private List<String> roleTags;

	@ValueMapValue
	private List<String> brandTags;

	@ValueMapValue
	private String authorConfigError;

	private String noAuthorPermissions;

	@PostConstruct
	public void init() throws RepositoryException {

		log.error("SRP-Entered into SRPContainerDataModel init method");

		resourceResolver = res.getResourceResolver();
		srpcontainerTags.setBrandTags(brandTags);
		srpcontainerTags.setGeoTags(geoTags);
		srpcontainerTags.setRoleTags(roleTags);

		User user = resourceResolver.adaptTo(User.class); // login person details

		Iterator<Group> userGroup = user.memberOf();
		while (userGroup.hasNext()) {
			Group group = userGroup.next();
			userGroupsList.add(group.getID());// Fetching User groups assigned to Author
		}
		log.error("SRP-Author Group details {}", userGroupsList);

		// All applicable tags list based on author configuration permissions
		TagsBeanClass genericTags = authorInfoCheckService.getTagValuesOfAuthor(resourceResolver, userGroupsList);
		log.error("SRP-Group targetted tags {}", genericTags);

		Iterable<Resource> childResource = res.getChildren();// Fetching all Components authored with in Container

		// Component Visibility
		// check for author
		childResource.forEach(i -> setContainerChildrenCompByTaging(i, userGroupsList, srpcontainerTags, genericTags));
		log.error("SRP-exit from ContainerDataModel init");
	}

	// filtering child components in SRP container based on Tags selected
	protected void setContainerChildrenCompByTaging(Resource resource, List<String> userGroupsList,
			TagsBeanClass containerTags, TagsBeanClass genericTags) {
		log.error("SRP-entered into setContainerChildrenCompByTaging");
		if (checkIfAuthorIsAdmin(userGroupsList)) { // when author is admin
			log.error("SRP-Author is Admin");
			setChildrenForAdmin(resource, containerTags);
		} else { // Authors other than admins
			log.error("SRP-Author is not Admin");
			setChildrenForAuthor(resource, genericTags);
		}
		log.error("SRP-exit from setContainerChildrenCompByTaging");
	}

	private void setChildrenForAdmin(Resource resource, TagsBeanClass containerTags) {
		log.error("SRP-entered into setChildrenForAdmin method");
		if (containerTags.getBrandTags().isEmpty() && containerTags.getGeoTags().isEmpty()
				&& containerTags.getRoleTags().isEmpty()) { // No filters applied in container
															// component filtering within page
			children.add(resource);
			log.error("SRP-No Container Filters: Added Resource- {}", resource.getPath());
		} else { // when filters applied in container
			// component filtering within page
			int p = 0;
			TagsBeanClass componentTags = authorInfoCheckService.getResourceTags(resource);
			log.error("SRP-Group targetted tags {}", componentTags);
			p = compareTagFilters(componentTags, containerTags);
			if (p > 0) {
				children.add(resource);
				log.error("SRP-Applied Container Filters: Added Resource- {}", resource.getPath());
			}
		}
		log.error("SRP-exiting setChildrenForAdmin method");
	}

	private void setChildrenForAuthor(Resource resource, TagsBeanClass genericTags) {
		log.error("SRP-Entered setChildrenForAuthor method");
		int k = 0;
		// Child component tags
		TagsBeanClass componentTags = authorInfoCheckService.getResourceTags(resource);
		log.error("SRP-component targetted tags {} -- {}", componentTags, resource.getPath());
		if (genericTags.getBrandTags().isEmpty() && genericTags.getGeoTags().isEmpty()
				&& genericTags.getRoleTags().isEmpty()) { // No configurations are found for author
			noAuthorPermissions = "true";// displaying error in ui
			log.error(
					"Filtered Components Based on author Configuration(No Configurtion authored for this Author): Added Resoure - {}",
					resource.getPath());
		} else {
			k = compareTagFilters(componentTags, genericTags);
			if (k > 0) {
				// Author Configurations found and child component tags matches author
				// configurations
				children.add(resource);
				log.error("SRP-Filtered Components Based on author Configuration: Added Resoure - {}", resource.getPath());
			}
		}
		log.error("SRP-Exiting setChildrenForAuthor method");
	}

	private boolean checkIfAuthorIsAdmin(List<String> userGroupsList) {
		List<String> adminGroups = authorInfoCheckService.getAdminGroups(resourceResolver);
		int c = 0;
		for (String i : userGroupsList) {
			if (adminGroups.contains(i)) {
				c++;
			}
		}
		return c > 0;
	}

	protected int compareTagFilters(TagsBeanClass tags1, TagsBeanClass tags2) {
		int c = 0;
		if (!(tags1.getGeoTags().isEmpty() || tags2.getGeoTags().isEmpty())) {
			c += compareListOfTags(tags1.getGeoTags(), tags2.getGeoTags());
		}
		if (!(tags1.getRoleTags().isEmpty() || tags2.getRoleTags().isEmpty())) {
			c += compareListOfTags(tags1.getRoleTags(), tags2.getRoleTags());
		}
		if (!(tags1.getBrandTags().isEmpty() || tags2.getBrandTags().isEmpty())) {
			c += compareListOfTags(tags1.getBrandTags(), tags2.getBrandTags());
		}
		return c;
	}

	private int compareListOfTags(List<String> tag1, List<String> tag2) {
		int c = 0;
		for (String i : tag2) {
			for (String j : tag1) {
				if (i.contains(j))
					c++;
			}
		}
		return c;
	}

	public List<String> getGeoTags() {
		if (null == this.geoTags) {
			return new ArrayList<>();
		}
		return new ArrayList<>(geoTags);
	}

	public List<String> getRoleTags() {
		if (null == this.roleTags) {
			return new ArrayList<>();
		}
		return new ArrayList<>(roleTags);
	}

	public List<String> getBrandTags() {
		if (null == this.brandTags) {
			return new ArrayList<>();
		}
		return new ArrayList<>(brandTags);
	}

	public List<Resource> getChildren() {
		if (children.isEmpty()) {
			return new ArrayList<>();
		}
		return new ArrayList<>(children);
	}

	public String getAuthorConfigError() {
		return authorConfigError;
	}

	public String getNoAuthorPermissions() {
		return noAuthorPermissions;
	}

	@Override
	public String toString() {
		return "SRPContainerDataModel [getGeoTags()=" + getGeoTags() + ", getRoleTags()=" + getRoleTags()
				+ ", getBrandTags()=" + getBrandTags() + ", getChildren()=" + getChildren()
				+ ", getAuthorConfigError()=" + getAuthorConfigError() + ", getNoAuthorPermissions()="
				+ getNoAuthorPermissions() + "]";
	}

	@Override
	public void setRequest(SlingHttpServletRequest request) {
		// TODO Auto-generated method stub
		
	}

}
