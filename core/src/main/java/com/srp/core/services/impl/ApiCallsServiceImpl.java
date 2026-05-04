package com.srp.core.services.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.Designate;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.srp.core.beans.request.PublishContentRequestBean;
import com.srp.core.beans.response.LabelsResponseBean;
import com.srp.core.constants.CommonConstants;
import com.srp.core.services.ApiGatewayServiceConfig;
import com.srp.core.services.ApiServiceCall;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.http.entity.StringEntity;

@Component(service = ApiServiceCall.class, immediate = true)
@Designate(ocd = ApiGatewayServiceConfig.class)
public class ApiCallsServiceImpl implements ApiServiceCall {

	Logger log = LoggerFactory.getLogger(ApiCallsServiceImpl.class);
	
	/** The api map. */
	private Map<String, String> apiMap;
	
	/** The domain name. */
	private String domainName;
	
	/** The client. */
	private HttpClient httpClient;
	
	/** The apiurl. */
	private String[] apUrl;
	
	/**
	 * Activate.
	 *
	 * @param config the config
	 */
	@Activate
	protected final void activate(ApiGatewayServiceConfig config) {
		initializeConfig(config);
	}

	/**
	 * Update.
	 *
	 * @param config the config
	 */
	@Modified
	protected final void update(ApiGatewayServiceConfig config) {
		initializeConfig(config);
	}

	/**
	 * Deactivate.
	 *
	 * @param config the config
	 */
	@Deactivate
	protected final void deactivate(ApiGatewayServiceConfig config) {
		domainName = StringUtils.EMPTY;
		apUrl = null;
		this.apiMap = null;
	}

	/**
	 * Initialize config.
	 *
	 * @param config the config
	 */
	
	private void initializeConfig(ApiGatewayServiceConfig config) {
		this.apiMap = new HashMap<>();
		domainName = config.domainName();
		apUrl = config.apiURLs();

		for (String singleAPIInfo : apUrl) {
			String[] valueSplit = singleAPIInfo.split(CommonConstants.BACKSLASH_PIPE);
			if (valueSplit != null && valueSplit.length == 2 && StringUtils.isNotBlank(valueSplit[0])
					&& StringUtils.isNotBlank(valueSplit[1])) {
				this.apiMap.put(valueSplit[0], valueSplit[1]);
			}

		}
	}
		
	public String httpGetAPICall(String methodname, String paramsString) throws IOException {
		log.error("SRP-Entered httpGetAPICall method");
		String resp = StringUtils.EMPTY;
		if (apiMap.containsKey(methodname)) {
			String apiEndpoint = apiMap.get(methodname);
			String endpointUrl = domainName.concat(apiEndpoint);
			endpointUrl = endpointUrl.concat(CommonConstants.QUESTIONMARK).concat(paramsString);
			log.error("SRP-EndpointUrl: {}", endpointUrl);
			httpClient = HttpClients.createSystem();
			HttpGet get = new HttpGet(endpointUrl);
			get.addHeader("content-type", CommonConstants.APPLICATION_JSON);
			get.addHeader("Accept", CommonConstants.APPLICATION_JSON);
			HttpResponse httpResp = httpClient.execute(get);
			HttpEntity respEntity = httpResp.getEntity();
			if (null != respEntity) {
				resp = EntityUtils.toString(respEntity);
			}
			log.error("SRP-Response: {}", resp);
			log.error("SRP-Exiting httpGetAPICall method");
		}
		return resp;

	}
	
	public String httpPostAPICall(String methodname, String request) throws IOException {
		log.error("SRP-Entered httpPostAPICall method");
		String resp = StringUtils.EMPTY;
		if (apiMap.containsKey(methodname)) {
			String apiEndpoint = apiMap.get(methodname);
			String endpointUrl = domainName.concat(apiEndpoint);
			log.error("SRP-EndpointUrl: {}", endpointUrl);
			StringEntity reqEntity = new StringEntity(request);
			httpClient = HttpClients.createSystem();
			HttpPost post = new HttpPost(endpointUrl);
			post.addHeader("content-type", CommonConstants.APPLICATION_JSON);
			post.addHeader("Accept", CommonConstants.APPLICATION_JSON);
			log.error("SRP-Request: {}", request);
			post.setEntity(reqEntity);
			HttpResponse httpResp = httpClient.execute(post);
			HttpEntity respEntity = httpResp.getEntity();

			if (null != respEntity) {
				resp = EntityUtils.toString(respEntity);
			}
			log.error("SRP-Response: {}", resp);
			log.error("SRP-Exiting httpPostAPICall method");
		}
		return resp;

	}

	
	@Override
	public LabelsResponseBean getSRPLabels(String req) throws IOException {
		log.error("SRP-Entered getSrpLabels method");
		ObjectMapper mapper = new ObjectMapper();
		String resp = httpGetAPICall("getSrpLabels", req);
		LabelsResponseBean labelsResponseDataBean = mapper.readValue(resp, LabelsResponseBean.class);
		log.error("SRP-Exiting getSrpLabels method");
		return labelsResponseDataBean;
	}
	
	@Override
	public String updateSRPContent(PublishContentRequestBean publishContentRequestBean) throws IOException {
		log.error("SRP-Entered updatePageComponentsJsonAPIcall method");
		ObjectMapper mapper = new ObjectMapper();
		String request = mapper.writeValueAsString(publishContentRequestBean);
		String resp = httpPostAPICall("updateSRPContent", request);
		log.error("SRP-Exiting updatePageComponentsJsonAPIcall method");
		return resp;
	}
}
