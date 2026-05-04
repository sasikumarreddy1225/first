package com.srp.core.services.impl;

import org.apache.commons.lang3.StringUtils;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.Designate;

import com.srp.core.services.SrpDomainConfig;
import com.srp.core.services.SrpDomainService;

@Component(service = SrpDomainService.class, immediate = true)
@Designate(ocd = SrpDomainConfig.class)
public class SrpDomainImpl implements SrpDomainService {

	/** The domain name. */
	private String domainName;

	@Activate
	public void activate(SrpDomainConfig config) {
		setConfig(config);
	}

	@Modified
	public void modify(SrpDomainConfig config) {
		setConfig(config);
	}

	@Deactivate
	public void deactivate(SrpDomainConfig config) {
		domainName = StringUtils.EMPTY;
	}

	public void setConfig(SrpDomainConfig config) {
		domainName = config.domainName();
	}

	@Override
	public String getDomainUrl() {
		return domainName;
	}
}
